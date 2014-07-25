package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import objects.Author;
import objects.Chair;
import objects.Conference;
import objects.Paper;
import objects.Recommendation;
import objects.Review;
import objects.SubChair;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * An accessor to the RECOMMENDATIONS table and objects
 * @author Brian
 *
 */


//RECID,PAPERTITLE,RECOBJECT
public class recommendAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_recommends_db = null;
	private String table = "RECOMMENDATIONS";
	
	/**
	 * Creates a connection to the RECOMMENDATIONS table in the DB.
	 * 
	 */
	public recommendAccess(){
		my_recommends_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Adds an recommendation, to both table and object file
	 * @param the_rec recommendation to add
	 */
	public void addRec(Recommendation the_rec){
		String serial = serialRec(the_rec);
		
		String my_query = "INSERT INTO " + table + " (PAPERTITLE,RECOBJECT) " + 
				"VALUES (" + "'" + the_rec.getPaper() + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates Recommendation object and table values
	 * Referencing the paper title
	 * 
	 * @param the_rec Recommendation object/user to update
	 */
	public void update(Recommendation the_rec){
		
		String serial = serialRec(the_rec);
		
		String my_query = "UPDATE " + table + " SET CONFERENCETITLE = " + "'" + the_rec.getPaper() + "'" +  
					", CONFERENCEOBJECT = " + "'" + serial + "'" + 
					" WHERE CONFERENCETITLE = " + "'" + the_rec.getPaper() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns a recommednation with the corresponding paper title
	 * If no recommendation is found with the paper title, returns null
	 * @param the_paper_title String the paper title
	 * @return Author with matching userID
	 */
	public Recommendation getRec(String the_paper_title){
		Recommendation temp = null;
		
		String my_query = "SELECT RECOBJECT FROM " + table + 
				" WHERE PAPERTITLE=" + "'" + the_paper_title + "'" + ";";
		try {
			
			if(!my_recommends_db.isOpen()){
				openRecAccess();
			}
			
			SQLiteStatement result = my_recommends_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialRec(object);
			
			if(my_recommends_db.isOpen()){
				closeRecAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	
	
	/**
	 * Accepts a recommendation object, serializes it
	 * then returns the string of the object file name
	 * @param the_rec author to serialize
	 */
	private String serialRec(Recommendation the_rec){
		String filename = the_rec.getPaper() +"_" + "RECOMMENDATION" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_rec);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the recommendation using the serializable file name
	 * example: papertitle_RECOMMENDATION.ser
	 * @param r_name serial file name
	 * @return Recommendation with object name a_name
	 */
	private Recommendation deserialRec(String r_name){
		String filename = r_name;
	    
		Recommendation r = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      r = (Recommendation) in.readObject();
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
			
			if(!my_recommends_db.isOpen()){
				openRecAccess();
			}
			
			my_recommends_db.exec(sql);

			if(my_recommends_db.isOpen()){
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
	public void openRecAccess(){		
		try {
			if(!my_recommends_db.isOpen()){
				my_recommends_db.open();//open connection
				my_recommends_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
			if(my_recommends_db.isOpen()){
				my_recommends_db.exec("COMMIT;");
				my_recommends_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

