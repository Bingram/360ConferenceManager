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
import objects.SubChair;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * An accessor to the SUBCHAIRS table and objects
 * @author Brian
 *
 */

public class subChairAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_sub_chair_db = null;
	private String table = "SUBCHAIRS";
	
	/**
	 * Creates a connection to the SUBCHAIRS table in the DB.
	 * 
	 */
	public subChairAccess(){
		my_sub_chair_db = new SQLiteConnection(dbfile);
		
	}
	
	/**
	 * Adds a sub chair, to both table and object file
	 * @param the_sub_chair sub chair to add
	 * @param the_conference title of sub chairs conference 
	 */
	public void addChair(SubChair the_sub_chair, String the_conference_title){
		String serial = serialChair(the_sub_chair);
		
		String my_query = "INSERT INTO " + table + " (SUBCHAIRID,SUBCHAIRNAME,CONFERENCETITLE,SUBCHAIROBJECT) " + 
				"VALUES (" + the_sub_chair.getID() + "," + "'" + the_sub_chair.getName().toString() + "'" + "," + 
				"'" + the_conference_title + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates the subchair object file and table
	 * Based on subchair name and id, updates all rows with matching values
	 * 
	 * @param the_subchair SubChair to update
	 */
	public void update(SubChair the_subchair){
		
		String serial = serialChair(the_subchair);
		
		String my_query = "UPDATE " + table + " SET SUBCHAIRID = " + the_subchair.getID() + 
					", SUBCHAIRNAME = " + "'" + the_subchair.getLastname()+ " " + the_subchair.getFirstname() + "'" +
					", SUBCHAIROBJECT = " + "'" + serial + "'" +
					" WHERE SUBCHAIRID = " + the_subchair.getID() +
					" AND SUBCHAIRNAME = " + "'" + the_subchair.getLastname()+ " " + the_subchair.getFirstname() +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns a sub chair with the corresponding name
	 * If no sub chair found with the name, returns null
	 * @param the_paper_title String title of the paper
	 * @return
	 */
	public SubChair getSubChair(String the_name){
		SubChair temp = null;
		
		String my_query = "SELECT SUBCHAIROBJECT FROM " + table + 
				" WHERE SUBCHAIRNAME=" + "'" + the_name + "'" + ";";
		try {
			
			if(!my_sub_chair_db.isOpen()){
				openSubChairAccess();
			}
			
			SQLiteStatement result = my_sub_chair_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialChair(object);
			
			if(my_sub_chair_db.isOpen()){
				closeSubChairAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	
	
	/**
	 * Accepts a sub chair object, serializes it
	 * then returns the string of the object file name
	 * @param the_sub_chair SubChair to serialize
	 */
	private String serialChair(SubChair the_sub_chair){
		String filename = the_sub_chair.getName() +"_" + "SUBCHAIR" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_sub_chair);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the sub chair using the serializable file name
	 * example: subchairname_SUBCHAIR.ser
	 * @param c_name serial file name
	 * @return SubChair with object name c_name
	 */
	private SubChair deserialChair(String c_name){
		String filename = c_name;
	    
		SubChair c = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      c = (SubChair) in.readObject();
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
			
			if(!my_sub_chair_db.isOpen()){
				openSubChairAccess();
			}
			
			my_sub_chair_db.exec(sql);

			if(my_sub_chair_db.isOpen()){
				closeSubChairAccess();
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
	public void openSubChairAccess(){		
		try {
			if(!my_sub_chair_db.isOpen()){
				my_sub_chair_db.open();//open connection
				my_sub_chair_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
	public void closeSubChairAccess(){
		try {
			if(my_sub_chair_db.isOpen()){
				my_sub_chair_db.exec("COMMIT;");
				my_sub_chair_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

