package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import objects.Author;
import objects.Paper;
import objects.Review;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * An accessor to the REVIEW table and objects
 * @author Brian
 *
 */
public class reviewAccess {
	
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_review_db = null;
	private String table = "REVIEWS";
	
	/**
	 * Creates a connection to the REVIEWS table in the DB.
	 * 
	 */
	public reviewAccess(){
		my_review_db = new SQLiteConnection(dbfile);
		
		
	}
	
	/**
	 * Adds a review, to both table and object file
	 * @param the_review Review to add
	 */
	public void addReview(Review the_review){
		String serial = serialReview(the_review);
		
		String my_query = "INSERT INTO " + table + " (PAPERTITLE,REVIEWER,FILENAME,REVIEWOBJECT) " + 
				"VALUES (" + "'" + the_review.getTitle() + "'" + "," + 
				"'" + the_review.getReviewer() + "'" + "," + "'" + the_review.getFileSource() + "'" + "," + "'" + serial + "'" + ");";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates the review
	 * Serializes review object, should overwrite the original as long as paper title hasn't changed
	 * Updates the table values for the file of the review, and the review object the row represents
	 * 
	 * NOTE: If there are multiple reviews for a paper, this will update all of them to the review passed in...
	 * @param the_review Review to update
	 */
	public void update(Review the_review){
		
		String serial = serialReview(the_review);
		
		String my_query = "UPDATE " + table + " SET FILENAME = " +"'"+ the_review.getFileSource() +"'" + 
					", REVIEWOBJECT = " + "'" + serial + "'" + " WHERE PAPERTITLE = " + "'" + the_review.getTitle() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Returns a review for the corresponding paper.
	 * If no review found for paper, returns null
	 * @param the_paper_title String title of the paper
	 * @return
	 */
	public Review getReview(String the_paper_title){
		Review temp = null;
		
		String my_query = "SELECT REVIEWOBJECT FROM " + table + 
				" WHERE PAPERTITLE=" + "'" + the_paper_title + "'" + ";";
		try {
			
			if(!my_review_db.isOpen()){
				openReviewAccess();
			}
			
			SQLiteStatement result = my_review_db.prepare(my_query);
			
			if(result.step()){//get result of sql query
		
				String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
				temp = deserialReview(object);
			}
			
			if(my_review_db.isOpen()){
				closeReviewAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return temp;
	}
	
	/**
	 * Returns a review for a particular paper, and the reviewer who created it.
	 * @param the_paper_title String title of paper
	 * @param the_review_author String reviewername of reviewer of paper
	 * @return returns the review matching paper title and reviewername
	 */
	public Review getReview(String the_paper_title, String the_review_author){
		Review temp = null;
		
		String my_query = "SELECT REVIEWOBJECT FROM " + table + 
				" WHERE PAPERTITLE=" + "'" + the_paper_title + "'" + " AND REVIEWER=" + "'" + the_review_author + "'" +";";
		try {
			
			if(!my_review_db.isOpen()){
				openReviewAccess();
			}
			
			SQLiteStatement result = my_review_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialReview(object);
			
			if(my_review_db.isOpen()){
				closeReviewAccess();
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
	 * @param r Review to serialize
	 */
	private String serialReview(Review r){
		String filename = r.getTitle() +"_" + r.getAuthor() + "_REVIEW" + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(r);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the review using the serializable file name
	 * example: papertitle_reviewername.ser
	 * @param r_name serial file name
	 * @return Review with object name r_name
	 */
	private Review deserialReview(String r_name){
		String filename = r_name;
	    
		Review r = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      r = (Review) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return r;
	    
	}
	
	private void executeSQL(String sql){
		
		try {
			

			my_review_db.setBusyTimeout(0);
			
			if(!my_review_db.isOpen()){
				
				openReviewAccess();

				my_review_db.setBusyTimeout(0);
				
				my_review_db.exec(sql);

			}
			
			
			if(my_review_db.isOpen()){

				my_review_db.setBusyTimeout(0);
				
				my_review_db.exec(sql);
				

				System.out.println(my_review_db.getErrorMessage());

				closeReviewAccess();
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
	public void openReviewAccess(){		
		try {
			if(!my_review_db.isOpen()){
				my_review_db.open();//open connection
				my_review_db.setBusyTimeout(1);
				my_review_db.exec("BEGIN TRANSACTION");//begin transactions on connection
			}
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Commit transactions on database, close connection.
	 * Must be done after every SQL query executed (or series of queries)
	 * @throws SQLiteException 
	 */
	public void closeReviewAccess() throws SQLiteException{
		

			my_review_db.exec("COMMIT;");
			if(my_review_db.isOpen()){
				my_review_db.dispose();
			}
			
		
		
	}

}
