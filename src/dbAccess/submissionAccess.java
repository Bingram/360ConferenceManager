package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import objects.Author;
import objects.Paper;
import objects.SubChair;
import objects.Submission;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * Provides access to SUBMISSIONS table.
 * @author Brian
 *
 */
public class submissionAccess {

	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_subs_db = null;
	private String table = "SUBMISSIONS";
	
	/**
	 * Constructor with SQLiteConnection passed
	 * @param sql SQLiteConnection passed to constructor
	 */
	public submissionAccess(SQLiteConnection sql){
		
		my_subs_db = sql;
		
	}
	
	/**
	 * Constructor that establishes its own connection to the database.
	 */
	public submissionAccess(){
		
		my_subs_db = new SQLiteConnection(dbfile);
		
		if(!my_subs_db.isOpen()){
			openSubsAccess();
		}
		
	}
	
	/**
	 * Executes an SQL query passed as string, called by internal methods.
	 * @param sql String SQL query to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_subs_db.isOpen()){
				openSubsAccess();
			}
			
			my_subs_db.exec(sql);

			if(my_subs_db.isOpen()){
				closeSubsAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds the_sub to submissions table
	 * @param the_sub submission to add to database
	 */
	public void addSubmission(Submission the_sub){
		String serial = serialSub(the_sub);//creates serialized version of paper object
		
		String my_query = "INSERT INTO " + table + " (CONFERENCEID,PAPERTITLE,SUBOBJECT) "
				+ "VALUES ("+ "'" + the_sub.getConferenceID() + "'" + "," + "'" + the_sub.getPaper().getTitle() + "'" + "," + "'" + serial + "'" + ");";
		executeSQL(my_query);
	}
	
	/**
	 * Updates the submission object file and table
	 * Based on paper title and conference id, updates all rows with matching values
	 * 
	 * @param the_submission Submission to update
	 */
	public void update(Submission the_submission){
		
		String serial = serialSub(the_submission);
		
		String my_query = "UPDATE " + table + " SET CONFERENCEID = " + the_submission.getConferenceID() + 
					", PAPERTITLE = " + "'" + the_submission.getPaper().getTitle() + "'" +
					", SUBOBJECT = " + "'" + serial + "'" +
					" WHERE CONFERENCEID = " + the_submission.getConferenceID() +
					" AND PAPERTITLE = " + "'" + the_submission.getPaper().getTitle() +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * returns a single submission object, using the paper title
	 * @param the_title title of paper to find
	 * @return paper object with matching title
	 */
	public Submission getSubmission(String the_title){
		
		String sql = "SELECT SUBOBJECT FROM " + table + " WHERE PAPERTITLE=" + "'" + the_title + "'" + ";";
		
		Submission temp = null;
		
		try {
			
			if(!my_subs_db.isOpen()){
				openSubsAccess();
			}
			
			my_subs_db.exec(sql);
			
			SQLiteStatement result = my_subs_db.prepare(sql);
			
			result.step();//get result of sql query
			temp = deserialSub(result.columnString(0));					
			
			if(my_subs_db.isOpen()){
				closeSubsAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
		
	}
	
	/**
	 * Returns a list of submissions based on the conference id
	 * @param the_conference_id int of the conference ID
	 * @return linkedlist of submission
	 */
	public ArrayList<Submission> getSubmissionsList(int the_conference_id){
		
		ArrayList<Submission> the_list = new ArrayList<Submission>();
		
		String sql = "SELECT SUBOBJECT FROM " + table + " WHERE CONFERENCEID=" + the_conference_id + ";";

		
		try {
			
			if(!my_subs_db.isOpen()){
				openSubsAccess();
			}
			
			my_subs_db.exec(sql);
			
			SQLiteStatement result = my_subs_db.prepare(sql);
			
			while(result.hasRow()){

				result.step();//get result of sql query
				Submission temp = deserialSub(result.columnString(0));
				the_list.add(temp);
			}			
			
			if(my_subs_db.isOpen()){
				closeSubsAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return the_list;
	}
	
	
	/**
	 * Prepare database connection for transactions.
	 * Must be done before every SQL query executed (or series of queries)
	 */
	public void openSubsAccess(){		
		try {
			if(!my_subs_db.isOpen()){
				my_subs_db.open();//open connection
				my_subs_db.exec("BEGIN TRANSACTION");//begin transactions on connection
			}
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Commit transactions on database, close connection.
	 * Must be done after every SQL query executed (or series of queries)
	 */
	public void closeSubsAccess(){
		try {
			if(my_subs_db.isOpen()){
				my_subs_db.exec("COMMIT;");
				my_subs_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Accepts a paper object, serializes it
	 * then returns the string of the object file name
	 * @param the_sub Paper to serialize
	 */
	private String serialSub(Submission the_sub){
		String filename = the_sub.getPaper().getTitle() +"_"+ the_sub.getAuthor().getName() + "_submission" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_sub);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the submission using the serializable file name
	 * example: papertitle_submission_authorname.ser
	 * @param s_name serial file name
	 * @return Submission with object name s_name
	 */
	private Submission deserialSub(String s_name){
		String filename = s_name;
	    
		Submission s = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      s = (Submission) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return s;
	    
	}
	
}

