package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import objects.Author;
import objects.Chair;
import objects.Conference;
import objects.Submission;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * Gives access to the conference table.
 * Conference table has the title, ID and object name for serialization.
 * @author Brian
 *
 */
public class confAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_conference_db = null;
	private String table = "CONFERENCES";
	
	
	public confAccess(){
		
		my_conference_db = new SQLiteConnection(dbfile);

		
	}
	
	/**
	 * Add conference to table with title and ID
	 * @param the_title String name of conference
	 * @param the_id int ID number
	 */
	public void addConference(String the_title, int the_id){
		
		String serial = serialConf(new Conference(the_title, the_id));
		
		String my_query = "INSERT INTO " + table + " (CONFERENCEID,CONFERENCETITLE,CONFERENCEOBJECT) " + 
				"VALUES (" +  the_id  + "," + "'"+the_title+"'" + "," + "'" + serial + "'" + ");";
		
		executeSQL(my_query);//execute SQL query
	}
	
	public void addConference(Conference c){
		String serial = serialConf(c);
	
		String my_query = "INSERT INTO " + table + " (CONFERENCEID,CONFERENCETITLE,CONFERENCEOBJECT) " + 
			"VALUES (" +  c.getID()  + "," + "'"+c.getTitle()+"'" + "," + "'" + serial + "'" + ");";
	
		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates Conference object and table values
	 * Referencing the conference title
	 * 
	 * @param the_conf Chair object/user to update
	 */
	public void update(Conference the_conf){
		
		String serial = serialConf(the_conf);
		
		String my_query = "UPDATE " + table + " SET CONFERENCEID = " + the_conf.getID() + 
					", CONFERENCETITLE = " + "'" + the_conf.getTitle() + "'" + 
					", CONFERENCEOBJECT = " + "'" + serial + "'" + 
					" WHERE CONFERENCETITLE = " + "'" + the_conf.getTitle() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns an author with the corresponding userID
	 * If no sub chair found with the name, returns null
	 * @param the_id int userID of the author
	 * @return Author with matching userID
	 */
	public Conference getConf(int the_id){
		Conference temp = null;
		
		String my_query = "SELECT CONFERENCEOBJECT FROM " + table + 
				" WHERE CONFERENCEID=" + the_id + ";";
		try {
			
			if(!my_conference_db.isOpen()){
				openConfAccess();
			}
			
			SQLiteStatement result = my_conference_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialConf(object);
			
			if(my_conference_db.isOpen()){
				closeConfAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	/**
	 * Returns an conference with the corresponding title
	 * If no conference found with the name, returns null
	 * @param the_conference_title String title of the conference
	 * @return int confID with matching title
	 */
	public int getConf(String the_conference_title){
		Conference temp = null;
		int object = 0;
		
		String my_query = "SELECT CONFERENCEID FROM " + table + 
				" WHERE CONFERENCETITLE=" +"'"+ the_conference_title +"'"+ ";";
		try {
			
			if(!my_conference_db.isOpen()){
				openConfAccess();
			}
			
			SQLiteStatement result = my_conference_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			object = result.columnInt(0);//gets first result in first column of result (i.e.: USERNAME)
			
			//temp = deserialConf(object);
			
			if(my_conference_db.isOpen()){
				closeConfAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//return temp.getID();
		return object;
	}
	
	
	
	/**
	 * Accepts a conference object, serializes it
	 * then returns the string of the object file name
	 * @param the_author author to serialize
	 */
	private String serialConf(Conference the_conference){
		String filename = the_conference.getTitle() + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_conference);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the conference using the serializable file name
	 * example: conferencetitle.ser
	 * @param c_name serial file name
	 * @return Conference with object name a_name
	 */
	private Conference deserialConf(String c_name){
		String filename = c_name;
	    
		Conference c = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      c = (Conference) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return c;
	    
	}
	
	/**
	 * Executes an SQL query passed as string, called by internal methods.
	 * @param sql String SQL query to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_conference_db.isOpen()){
				openConfAccess();
			}
			
			my_conference_db.exec(sql);

			if(my_conference_db.isOpen()){
				closeConfAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds the_conf to conference table
	 * @param the_conf conference to add to database
	 */
	public void addConf(Conference the_conf){
		String serial = serialConf(the_conf);//creates serialized version of paper object
		
		String my_query = "INSERT INTO " + table + " (CONFERENCEID,CONFERENCETITLE,CONFERENCEOBJECT) "
				+ "VALUES ("+ "'" + the_conf.getID() + "'" + "," + "'" + the_conf.getTitle() + "'" + "," + "'" + serial + "'" + ");";
		executeSQL(my_query);
	}
	
	
	/**
	 * Prepare database connection for transactions.
	 * Must be done before every SQL query executed (or series of queries)
	 */
	public void openConfAccess(){		
		try {
			if(!my_conference_db.isOpen()){
				my_conference_db.open();//open connection
				my_conference_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
	public void closeConfAccess(){
		try {
			if(my_conference_db.isOpen()){
				my_conference_db.exec("COMMIT;");
				my_conference_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

}
