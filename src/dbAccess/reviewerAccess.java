package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import objects.Author;
import objects.Chair;
import objects.Paper;
import objects.Recommendation;
import objects.Review;
import objects.Reviewer;
import objects.SubChair;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * An accessor to the REVIEWERS table and objects
 * @author Brian
 *
 */



public class reviewerAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_reviewer_db = null;
	private String table = "REVIEWERS";
	
	/**
	 * Creates a connection to the REVIEWERS table in the DB.
	 * 
	 */
	public reviewerAccess(){
		my_reviewer_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Adds a reviewer, to both table and object file
	 * @param the_reviewer reviewer to add
	 */
	public void addReviewer(Reviewer the_reviewer){
		String serial = serialReviewer(the_reviewer);
		
		//REVIEWERID,REVIEWERNAME,PAPERTITLE,REVIEWEROBJECT
		String my_query = "INSERT INTO " + table + " (REVIEWERID,REVIEWERNAME,PAPERTITLE,REVIEWEROBJECT) " + 
				"VALUES ("+ the_reviewer.getID() + "," + "'" + the_reviewer.getName().toString() + "'" + "," + "'" + "" + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates the reviewer object file and table
	 * Based on reviwername and id, updates all rows with matching values
	 * 
	 * @param the_reviewer Reviewer to update
	 */
	public void update(Reviewer the_reviewer){
		
		String serial = serialReviewer(the_reviewer);
		
		String my_query = "UPDATE " + table + " SET REVIEWERID = " + the_reviewer.getID() + 
					", REVIEWERNAME = " + "'" + the_reviewer.getLastname()+ " " + the_reviewer.getFirstname() + "'" +
					", REVIEWOBJECT = " + "'" + serial + "'" +
				" WHERE REVIEWERNAME = " + "'" + the_reviewer.getLastname()+ " " + the_reviewer.getFirstname() + "'" +
					" AND REVIEWERID = " + the_reviewer.getID() + ";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns a reviewer with the corresponding name
	 * If no reviewer is found with the name, returns null
	 * @param the_name String the reviewer name
	 * @return Reviewer with matching name
	 */
	public Reviewer getReviewer(String the_name){
		Reviewer temp = null;
		
		String my_query = "SELECT REVIEWEROBJECT FROM " + table + 
				" WHERE REVIEWERNAME=" + "'" + the_name + "'" + ";";
		try {
			
			if(!my_reviewer_db.isOpen()){
				openReviewerAccess();
			}
			
			SQLiteStatement result = my_reviewer_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialReviewer(object);
			
			if(my_reviewer_db.isOpen()){
				closeRecAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	
	
	/**
	 * Accepts a reviewer object, serializes it
	 * then returns the string of the object file name
	 * @param the_reviewer reviewer to serialize
	 */
	private String serialReviewer(Reviewer the_reviewer){
		String filename = the_reviewer.getName() +"_" + "REVIEWER" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_reviewer);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the reviewer using the serializable file name
	 * example: reviewername_REVIEWER.ser
	 * @param r_name serial file name
	 * @return Reviewer with object name r_name
	 */
	private Reviewer deserialReviewer(String r_name){
		String filename = r_name;
	    
		Reviewer r = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      r = (Reviewer) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return r;
	    
	}
	
	/**
	 * Execute sql statement
	 * @param sql statement to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_reviewer_db.isOpen()){
				openReviewerAccess();
				my_reviewer_db.setBusyTimeout(0);
				my_reviewer_db.exec(sql);

			}
			
			
			if(my_reviewer_db.isOpen()){

				my_reviewer_db.setBusyTimeout(0);
				
				my_reviewer_db.exec(sql);

				closeRecAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Prepare database connection for transactions.
	 * Must be done before every SQL query executed (or series of queries)
	 */
	public void openReviewerAccess(){		
		try {
			if(!my_reviewer_db.isOpen()){
				my_reviewer_db.open();//open connection
				my_reviewer_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
	public void closeRecAccess(){
		try {
			if(my_reviewer_db.isOpen()){
				my_reviewer_db.exec("COMMIT;");
				my_reviewer_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

