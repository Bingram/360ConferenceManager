package objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A class to represent the role of Author in a conference(s).
 * 
 * @author Margaret Hoang, and edited by other team members (?)
 */
public class Author extends User implements Serializable {
	
	/** A long integer ID number, in case this class should be serialized. */
	private static final long serialVersionUID = -3407512345567899L;
	
	/** An ArrayList of this author's Submissions. */
	private ArrayList<Submission> my_authorSubmissions;
	
	/** An ArrayList of this author's Papers. */
	private ArrayList<Paper> my_authorPapers = new ArrayList<Paper>();
	
	/** The Program Chair in the hierarchy above this Author. */
    private Chair my_chair;
    
	/**
	 * Constructs a new Author object with the given last name, first name, and email address as strings.
	 * 
	 * @param a_lastname the given author's last name name as a string
	 * @param a_firstname the given author's first name name as a string
	 * @param an_email the given author's email address as a string
	 */
	public Author(final String a_lastname, final String a_firstname, final String an_email) {
		super(a_lastname, a_firstname, an_email);
		my_authorPapers = new ArrayList<Paper>();
		my_authorSubmissions = new ArrayList<Submission>();
	}
	
	/**
	 * Returns the conference program chair specific to the given conference.
	 * 
	 * @param a_conf a given conference
	 * @return program chair the program chair associated with the given conference
	 */
	public Chair getprogChair(final Conference a_conf) {
		return my_chair;
	}
	
	/**
	 * Sets the program chair for the author based on the given conference.
	 * 
	 * @param a_conf a given conference
	 */
	public void setprogChair(Conference a_conf) {
	    my_chair = a_conf.getChair();
	}	
	
	/**
	 * Adds the given paper to this author's list of papers.
	 * 
	 * @param a_paper a given Paper object
	 */
	public void addPaper(final Paper a_paper) {
		my_authorPapers.add(a_paper);
	}
	
	/**
	 * Adds the given submission to this author's list of submissions.
	 * 
	 * @param a_paper a given Submission object
	 */
	public void addSubmission(final Submission new_submission) {
		my_authorSubmissions.add(new_submission);
	}
	
	/**
	 * Returns the ArrayList of this author's Papers.
	 * 
	 * @return this list of this author's Papers
	 */
	public ArrayList<Paper> getPaperList() {
	    return my_authorPapers;
	}
	
	/**
	 * Returns the list of this author's submissions.
	 * 
	 * @return list of submissions
	 */
	// The method name should not be confused with getPaperList(). 
	// I would change it but I don't want to potentially break the code with refactoring
	public ArrayList<Submission> getPapers() {
	    return my_authorSubmissions;
	}
	
}
