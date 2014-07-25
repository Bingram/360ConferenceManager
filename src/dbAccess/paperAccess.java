package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;

import objects.Author;
import objects.Paper;
import objects.Reviewer;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * Provides access to PAPERS table.
 * @author Brian
 *
 */
public class paperAccess {

	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_paper_db = null;
	private String table = "PAPERS";
		
	/**
	 * Constructor that establishes its own connection to the database.
	 */
	public paperAccess(){
		
		my_paper_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Executes an SQL query passed as string, called by internal methods.
	 * @param sql String SQL query to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_paper_db.isOpen()){
				openPaperAccess();

				my_paper_db.setBusyTimeout(0);
				my_paper_db.exec(sql);
			}
			


			if(my_paper_db.isOpen()){
				my_paper_db.setBusyTimeout(0);
				my_paper_db.exec(sql);
				closePaperAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Adds paper object to paper table and file
	 * Paper must have title and and author
	 * @param the_paper Paper to add to database
	 */
	public void addPaper(Paper the_paper){
		String serial = serialPaper(the_paper);//creates serialized version of paper object,
		System.out.println(the_paper.getFileSource());
		String my_query = "INSERT INTO " + table + " (TITLE,AUTHOR,FILENAME,PAPEROBJECT) "
				+ "VALUES ("+ "'" + the_paper.getTitle() + "'" + "," + "'" + the_paper.getAuthor().getName() + "'" + "," + "'" + the_paper.getFileSource() + "'" + "," + "'" + serial + "'" + ");";
		executeSQL(my_query);
	}
	
	/**
	 * Updates the paper object file and table
	 * Based on reviwername and id, updates all rows with matching values
	 * 
	 * @param the_paper Paper to update
	 */
	public void update(Paper the_paper){
		
		String serial = serialPaper(the_paper);
		
		String my_query = "UPDATE " + table + " SET TITLE = " + the_paper.getTitle() + 
					", AUTHOR = " + "'" + the_paper.getAuthor().getLastname()+ " " + the_paper.getAuthor().getFirstname() + "'" +
					", FILE = " + "'" + the_paper.getFileSource() + "'" +
					", PAPEROBJECT = " + "'" + serial + "'" +
					" WHERE TITLE = " + "'" + the_paper.getTitle() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * returns a single paper object, using the paper title
	 * returns null if paper title not found
	 * @param the_title title of paper to find
	 * @return Paper object with matching title
	 */
	public Paper getPaper(String the_title){
		
		String sql = "SELECT PAPEROBJECT FROM " + table + " WHERE TITLE=" + "'" + the_title + "'" + ";";
		
		Paper temp = null;
		
		try {
			
			if(!my_paper_db.isOpen()){
				openPaperAccess();
			}
			
			my_paper_db.exec(sql);
			
			SQLiteStatement result = my_paper_db.prepare(sql);
			
			result.step();//get result of sql query
			
			System.out.println("Paper Table Access");//console test
			System.out.println("Returned Paper Title: " + result.columnString(0));//console test for resulting returned paper
			
			temp = deserialPaper(result.columnString(0));					
			
			if(my_paper_db.isOpen()){
				closePaperAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
		
	}
	
	/**
	 * Returns a list of papers based on the conference id
	 * @param the_conference_id int of the conference ID
	 * @return linkedlist of papers
	 */
	public List<Paper> getPaperList(int the_conference_id){
		
		List<Paper> the_list = new LinkedList<Paper>();
		
		String sql = "SELECT PAPEROBJECT FROM " + table + " WHERE CONFERENCEID=" + the_conference_id + ";";

		
		try {
			
			if(!my_paper_db.isOpen()){
				openPaperAccess();
			}
			
			my_paper_db.exec(sql);

			System.out.println("Paper Table Access");//console test
			System.out.println("Paper List Returned");
			
			SQLiteStatement result = my_paper_db.prepare(sql);
			
			while(result.hasRow()){

				
				System.out.println("Added Paper (Title): " + result.columnString(0));//console test for resulting returned paper
				
				Paper temp = deserialPaper(result.columnString(0));
				
				result.step();//get result of sql query
				
				the_list.add(temp);
			}			
			
			if(my_paper_db.isOpen()){
				closePaperAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return the_list;
	}
	
	/**
	 * Returns all the papers for all the conferences
	 *
	 * @return linkedlist of papers
	 */
	public List<Paper> getAllPapers(){
		
		List<Paper> the_list = new LinkedList<Paper>();
		
		String sql = "SELECT PAPEROBJECT FROM " + table + ";";

		
		try {
			
			if(!my_paper_db.isOpen()){
				openPaperAccess();
			}
			
			my_paper_db.exec(sql);
			
			System.out.println("Paper Table Access");//console test
			System.out.println("Paper List Returned");
			
			SQLiteStatement result = my_paper_db.prepare(sql);
			
			while(result.hasRow()){

				
				System.out.println("Added Paper (Title): " + result.columnString(0));//console test for resulting returned paper
				
				Paper temp = deserialPaper(result.columnString(0));
				
				result.step();//get result of sql query
				
				the_list.add(temp);
			}						
			
			if(my_paper_db.isOpen()){
				closePaperAccess();
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
	public void openPaperAccess(){		
		try {
			if(!my_paper_db.isOpen()){
				my_paper_db.open();//open connection
				my_paper_db.exec("BEGIN TRANSACTION");//begin transactions on connection
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
	public void closePaperAccess(){
		try {
			if(my_paper_db.isOpen()){

				my_paper_db.setBusyTimeout(0);
				my_paper_db.exec("COMMIT;");
				my_paper_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Accepts a paper object, serializes it
	 * then returns the string of the object file name
	 * @param p Paper to serialize
	 */
	private String serialPaper(Paper p){
		System.out.println(p.getTitle());
		System.out.println(p.getAuthor().getName());
		String filename = p.getTitle().toString() +"_" + p.getAuthor().getName().toString() + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(p);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the paper using the serializable file name
	 * example: papertitle_authorname.ser
	 * @param p_name serial file name
	 * @return Paper with object name p_name
	 */
	private Paper deserialPaper(String p_name){
		String filename = p_name;
	    
		Paper p = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      p = (Paper) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return p;
	    
	}
	
}
