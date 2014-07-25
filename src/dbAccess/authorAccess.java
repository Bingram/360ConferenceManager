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
 * An accessor to the AUTHORS table and objects
 * @author Brian
 *
 */

public class authorAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_author_db = null;
	private String table = "AUTHORS";
	
	/**
	 * Creates a connection to the AUTHORS table in the DB.
	 * 
	 */
	public authorAccess(){
		my_author_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Adds an author object, to both table and object file
	 * @param the_author Author to add
	 */
	public void addAuthor(Author the_author){
		String serial = serialAuthor(the_author);//serrialize author object, return file name
		
		String my_query = "INSERT INTO " + table + " (USERID,FIRSTNAME,LASTNAME,AUTHOROBJECT) " + 
				"VALUES (" + the_author.getID() + "," + "'" + the_author.getFirstname() + "'" + "," + 
				"'" + the_author.getLastname() + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates the author object passed to method,
	 * Along with table fields of USERID as int,
	 * FIRSTNAME,LASTNAME both as String and
	 * AUTHORIBJECT filename as String
	 * @param the_author Author to update
	 */
	public void update(Author the_author){
		
		String serial = serialAuthor(the_author);
		
		String my_query = "UPDATE " + table + " SET USERID = " + the_author.getID() + 
					", FIRSTNAME = " + "'" + the_author.getFirstname() + "'" + 
					", LASTNAME = " + "'" + the_author.getLastname() + "'" + 
					", AUTHOROBJECT = " + "'" + serial + "'" + 
					" WHERE USERID = " + the_author.getID() +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns an author with the corresponding userID int
	 * If no sub chair found with the ID, returns null
	 * @param the_id int userID of the author
	 * @return Author with matching userID
	 */
	public Author getAuthor(int the_id){
		Author temp = null;
		
		String my_query = "SELECT AUTHOROBJECT FROM " + table + 
				" WHERE USERID=" + the_id + ";";
		try {
			
			if(!my_author_db.isOpen()){
				openAuthorAccess();
			}
			
			SQLiteStatement result = my_author_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			System.out.println("Author found: "+result.columnString(0));
			String authorObjectString = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialAuthor(authorObjectString);
			
			if(my_author_db.isOpen()){
				closeSubChairAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	/**
	 * Returns an author with the corresponding first name
	 * If no author found with the name, returns null
	 * @param the_name Authors first name
	 * @return Author object with matching first name
	 */
	public Author getAuthor(String the_name){
		Author temp = null;
		
		String my_query = "SELECT AUTHOROBJECT FROM " + table + 
				" WHERE FIRSTNAME=" + "'" + the_name + "'"+";";
		try {
			
			if(!my_author_db.isOpen()){
				openAuthorAccess();
			}
			
			SQLiteStatement result = my_author_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String authorObjectString = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialAuthor(authorObjectString);
			
			if(my_author_db.isOpen()){
				closeSubChairAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
//	/**
//	 * Returns an author with the corresponding last name
//	 * If no author found with the name, returns null
//	 * @param the_name Authors last name
//	 * @return Author object with matching full name
//	 */
//	public Author getAuthor(String the_lastname){
//		Author temp = null;
//		
//		String my_query = "SELECT AUTHOROBJECT FROM " + table + 
//				" WHERE LASTNAME=" + the_lastname + ";";
//		try {
//			
//			if(!my_author_db.isOpen()){
//				openAuthorAccess();
//			}
//			
//			SQLiteStatement result = my_author_db.prepare(my_query);
//			
//			result.step();//get result of sql query
//		
//			String authorObjectString = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
//			
//			temp = deserialAuthor(authorObjectString);
//			
//			if(my_author_db.isOpen()){
//				closeSubChairAccess();
//			}
//			
//		} catch (SQLiteException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//				
//		return temp;
//	}
	
	/**
	 * Accepts an author object, serializes it
	 * then returns the string of the object file name
	 * @param the_author author to serialize
	 */
	private String serialAuthor(Author the_author){
		String filename = the_author.getName() +"_" + "AUTHOR" + ".ser";
	    
		System.out.println("");
		System.out.println("Serialize Author");
		System.out.println("Filename: "+filename);
		System.out.println("");
		
	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_author);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the author using the serializable file name
	 * example: authorname_AUTHOR.ser
	 * @param a_name serial file name
	 * @return Author with object name a_name
	 */
	private Author deserialAuthor(String a_name){
		String filename = a_name;
	    
		System.out.println("");
		System.out.println("De-Serialize Author");
		System.out.println("Filename: "+filename);
		System.out.println("");
		
		Author a = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      a = (Author) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return a;
	    
	}
	
	/**
	 * Execute sql statement
	 * @param sql statement to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_author_db.isOpen()){
				openAuthorAccess();
				my_author_db.setBusyTimeout(0);
				my_author_db.exec(sql);
			}

			if(my_author_db.isOpen()){
				my_author_db.setBusyTimeout(0);
				my_author_db.exec(sql);
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
	public void openAuthorAccess(){		
		try {
			if(!my_author_db.isOpen()){
				my_author_db.open();//open connection
				my_author_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
			if(my_author_db.isOpen()){
				my_author_db.exec("COMMIT;");
				my_author_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

