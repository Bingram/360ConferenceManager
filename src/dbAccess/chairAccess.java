package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import objects.Author;
import objects.Chair;
import objects.Paper;
import objects.Review;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * An accessor to the CHAIRS table and objects
 * @author Brian
 *
 */

public class chairAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_chair_db = null;
	private String table = "CHAIRS";
	
	/**
	 * Creates a connection to the CHAIRS table in the DB.
	 * Chairs must be present in the table, and flat file ending in   .ser
	 */
	public chairAccess(){
		my_chair_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Adds a chair object, to both table and object file
	 * @param the_chair chair object to add
	 * @param the_conference title of chairs conference 
	 * 
	 */
	public void addChair(Chair the_chair, String the_conference_title){
		String serial = serialChair(the_chair);
		
		//Builds CHAIRNAME from USER/CHAIR LastName FirstName
		String my_query = "INSERT INTO " + table + " (CHAIRID,CHAIRNAME,CONFERENCETITLE,CHAIROBJECT) " + 
				"VALUES (" + the_chair.getID() + "," + "'" + the_chair.getLastname()+ " " + the_chair.getFirstname() + "'" + "," + 
				"'" + the_conference_title + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates Chair object and table values
	 * 
	 * @param the_chair Chair object/user to update
	 */
	public void update(Chair the_chair){
		
		String serial = serialChair(the_chair);
		
		//CHAIRNAME is the_chairs full name, LAST FIRST, built from getting USER/CHAIR fields
		String my_query = "UPDATE " + table + " SET CHAIRID = " + the_chair.getID() + 
					", CHAIRNAME = " + "'" + the_chair.getLastname()+ " " + the_chair.getFirstname() + "'" + 
					", CHAIROBJECT = " + "'" + serial + "'" + 
					" WHERE CHAIRID = " + "'" + the_chair.getID() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns a chair object with the corresponding full name, "LAST FIRST"
	 * If no chair found with the full name, returns null
	 * @param the_name String the user/chair full name "LAST FIRST"
	 * @return Chair with matching full name
	 */
	public Chair getChair(String the_name){
		Chair temp = null;
		
		String my_query = "SELECT CHAIROBJECT FROM " + table + 
				" WHERE CHAIRNAME=" + "'" + the_name + "'" + ";";
		
		
		try {
			
			if(!my_chair_db.isOpen()){
				openChairAccess();
			}
			
			SQLiteStatement result = my_chair_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result 
			
			temp = deserialChair(object);
			
			if(my_chair_db.isOpen()){
				closeChairAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	
	
	/**
	 * Accepts a review object, serializes it
	 * then returns the string of the object file name
	 * @param the_chair Review to serialize
	 */
	private String serialChair(Chair the_chair){
		String filename = the_chair.getName() +"_" + "CHAIR" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_chair);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the chair using the serializable file name
	 * example: chairname_CHAIR.ser
	 * @param c_name serial file name
	 * @return Chair with object name c_name
	 */
	private Chair deserialChair(String c_name){
		String filename = c_name;
	    
		Chair c = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      c = (Chair) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return c;
	    
	}
	
	/**
	 * Execute sql statement
	 * @param sql statement to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_chair_db.isOpen()){
				openChairAccess();
			}
			
			my_chair_db.exec(sql);

			if(my_chair_db.isOpen()){
				closeChairAccess();
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
	public void openChairAccess(){		
		try {
			if(!my_chair_db.isOpen()){
				my_chair_db.open();//open connection
				my_chair_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
	public void closeChairAccess(){
		try {
			if(my_chair_db.isOpen()){
				my_chair_db.exec("COMMIT;");
				my_chair_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

