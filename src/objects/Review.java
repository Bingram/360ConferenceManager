package objects;

import java.io.Serializable;

/**
 * A class to represent the review of a paper submitted to a conference.
 * @author Brian
 *
 */
public class Review implements Serializable{
    /**
     * serializable for the database
     */
    private static final long serialVersionUID = -231324412321L;
	/**
	 * Title of paper being reivewed
	 */
	private String my_title;
	/**
	 * Author of review
	 */
	private String my_author;
	/**
	 * Paper being reviewed
	 */
	private Paper my_paper;
	/**
	 * Comments on the paper
	 */
	private String my_comments;
	/**
	 * Filepath of the paper source.
	 */
    private String my_filesource;
    
    /**
     * The name of the reviewer who conducted this review
     */
    private String my_reviewer;
    
	/**
	 * Constructor for review, accepts a paper title, author of review, and the paper being reviewed.
	 *
	 * @param the_title title of paper being reviewed
	 * @param the_author person who wrote review
	 * @param the_paper paper being reviewed
	 * @param the_comments reviewer comments
	 */
	public Review(String the_title, String the_author, Paper the_paper, String the_reviewer, String the_comments){
		my_title = the_title;
		my_author = the_author;
		my_paper = the_paper;
		my_comments = the_comments;
		my_reviewer = the_reviewer;
	}
	/**
	 * Blank constructor
	 */
	public Review(){
		
	}
	/** 
	 * Returns this reviews file 
	 * @return this review's file
	 */
	public String getFileSource() {
		return my_filesource;
	}
	/**
	 * Set this paper's File to the given string path. 
	 * @param the_source file path as string
	 */
	public void setFileSource(String the_source) {
		my_filesource = the_source;
	}
	/**
	 * Getter method for the title.
	 * @return the title of the paper being reviewed
	 */
	public String getTitle() {
		return my_title;
	}
	/**
	 * Getter method for the author.
	 * @return the author of the paper being reviewed
	 */
	public String getAuthor() {
		return my_author;
	}
	/**
	 * Getter method for the paper
	 * @return the paper that is being reviewed
	 */
	public Paper getPaper() {
		return my_paper;
	}
	/**
	 * Returns the comments
	 * @return the reviewers comments
	 * @author Crystal
	 */
	public String getComments() {
		return my_comments;
	}
	
	/**
	 * Returns the reviewer of this review
	 * @author Crystal
	 * @return the reviewer
	 */
	public String getReviewer() {
		return my_reviewer;
	}
	
	/**
	 * Saves the newly edited comments.
	 * @param the_comments the comments
	 * @author Crystal
	 */
	public void saveReview(String the_comments) {
		my_comments = the_comments;
	}
}
