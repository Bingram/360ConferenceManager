package objects;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;


/**
 * The reviewer class for a conference. A reviewer
 * should be able to view/read papers assigned to them.
 * A reviewer should more importantly be able to review
 * a paper.
 * 
 * @author Crystal Miraflor
 *
 */
public class Reviewer extends User implements Serializable {
	
	/**
	 * Serial ID for the database
	 */
	private static final long serialVersionUID = 332050260141571313L;
	/**Limit/Max number of papers the reviewer can be assigned.*/
	private static final int LIMIT = 4;
	/**List of conferences this reviewer is participating in*/
	private List<Conference> my_conferences;
	/**List of papers given to review*/
	private List<Submission> my_papers;
	/**List of reviews*/
	private ArrayList<Review> my_reviews;
	/**
	 * Every Reviewer knows its sub chair
	 */
	private SubChair my_sub_chair;
	/**Constructs a Reviewer*/
	public Reviewer(final String the_firstname, final String the_lastname) { //final String the_email, final String the_password) {
		super(the_firstname, the_lastname);
		my_conferences = new ArrayList<Conference>();
		my_papers = new ArrayList<Submission>();
		my_reviews = new ArrayList<Review>();
	}
	
	/**
	 * Adds a conference to this Reviewer
	 * @param the_conference
	 */
	public void addConference(final Conference the_conference) {
		my_conferences.add(the_conference);
	}
	
	/**
	 * Assigns a paper to this Reviewer
	 * @param the_paper the paper being assigned
	 */
	public void assignPaper(final Submission the_paper, SubChair the_subChair) {
		if(my_papers.size() < LIMIT) {
			my_papers.add(the_paper);
			my_sub_chair = the_subChair;
		} else {
			System.out.println("This reviewer has already been assigned 4 papers.");
		}
	}
	
	/**
	 * Helper method used to read .txt files
	 * @param the_filename the file name 
	 */
	private String readTxtFile(final String the_filename) {
		final StringBuilder sb = new StringBuilder();
		try{
			FileInputStream fstream = new FileInputStream(the_filename + ".txt"); 
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		while ((strLine = br.readLine()) != null)   {
			sb.append(strLine + "\n");
		}
		in.close();
		}catch (Exception e) {//Catch exception if any
			  sb.append("Paper cannot be found or does not exist!");
		}
		return sb.toString();
	}
	
	
	/**
	 * Reads a paper from the list
	 * @param the_title the title/file name of the paper
	 */
	public String readPaper(final String the_title) {
		//to do
		//open .txt file
		//commented code is for when retrieving a code using the papers file
		//source method, assuming all file sources will .txt files
//		Paper paper = null;
//		for(int i=0; i < my_papers.size(); i++) {
//			if(my_papers.get(i).getTitle().equals(the_title)) {
//				paper = my_papers.get(i);
//			}
//		}
//		if(paper == null) {
//			System.out.println("That paper does not exist. Could not be found");
//		} else {
//			readTxtFile(paper.getFileSource());
//		}
		return readTxtFile(the_title);
	}	
	
	
	/**
	 * Obtains a paper 
	 * @return a paper
	 */
	public Paper getPaper(final String the_title) {
		Paper paper = null; //just some code in place if we have a get method to see what conference a paper is from
		for(int i=0; i < my_papers.size(); i++) { //brute force way of searching for that paper and then returning it?
			if(the_title.equals(my_papers.get(i).getPaper().getTitle())) { 
				paper = my_papers.get(i).getPaper();
			}
		}
		return paper;
	}
	
	
	/**
	 * Returns the review based on the string given
	 * @param the_title
	 * @return the review
	 */
	public Review getReview(final String the_title) {
		Review review = null;
		for(int i=0; i < my_reviews.size(); i++) {
			if(the_title.equals(my_reviews.get(i).getTitle())) {
				review = my_reviews.get(i);
			}
		}
		return review;
	}
	
	
	/**
	 * Creates a new review
	 * @param the_title the title of the review 
	 * @param the_paper the name of the paper that is being reviewed
	 */
	public void conductReview(final String the_title, final Paper the_paper, final String the_comments) { //just construct a new review object and add to the list?
		Review review = new Review(the_title, the_paper.getAuthor().getName(), the_paper, the_comments, this.getName());
		my_reviews.add(review);
	}
	
	
	/**
	 * Read the reviews 
	 * @param the_title the title/name of the review
	 */
	public String readReview(final String the_title) {
		Review review = null;
//		StringBuilder sb = new StringBuilder();
		for(int i=0; i < my_reviews.size(); i++) {
			if(my_reviews.get(i).getTitle().equals(the_title)) {
				review = my_reviews.get(i);
			}
		}
		if(review == null) {
			System.out.println("That review does not exist. Could not be found");
		} //else {
//			//readTxtFile(review.getComments());
//		}
//		return readTxtFile(the_title);
		return review.getComments();
	}
	
	
	/**
	 * Obtains a review
	 * @return a review
	 */
	public ArrayList<Review> getReviews() { //is this supposed to return one review or the list of reviews?
		
		return my_reviews;
	}
	
	/**
	 * Obtains the list of papers assigned
	 * to this reviewer.
	 * @return a list of papers.
	 */
	public List<Submission> getPapers() {
		
		return my_papers;
	}
	
	/**
	 * Getter method to find the sub chair assigned to
	 * @return the sub chair
	 */
	public SubChair getSubChair()
	{
	    return my_sub_chair;
	}

	
	
	
	
}
