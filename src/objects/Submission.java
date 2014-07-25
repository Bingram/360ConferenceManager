package objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

/**
 * A class representing an author's submission to a conference.
 * 
 * @author Margaret Hoang, Hans Colby
 */
public class Submission extends Observable implements Serializable{
    /**
     * serializable for the database
     */
    private static final long serialVersionUID = -754656546456L;
    /**
     * The review object for this submission
     */
    public Review my_review;
    /** 
     * The paper being submitted. 
     * */
    private Paper my_paper;
    
    /** The list of reviews written for this submission. */
    private List<Review> my_reviews;
    
    /** The recommendation for this submission. */
    private Recommendation my_recommendation;
    /**
     * Every submission has a sub chair
     */
    private SubChair my_sub_chair;
    /**
     * Every submission has a chair
     */
    private Chair my_program_chair; // Necessary?
    /**
     * Every submission has a conference, id for reference?
     */
    private int my_conference_id;
    /**
     * Every submission has a conference deadline
     */
    private String my_deadline;
    /**
     * There is a reviewer for every submission
     */
    private Reviewer my_reviewer;
    /**
     * Status of the the review on the paper
     */
    private boolean my_reviewStatus;
    /**
     * blank submission for test case
     */
    public Submission() {
        //This is crystal I put this in place for testing something on ReviewGUI
    	my_paper = new Paper("Random", new Author("Jay", "Son", "jayson@gmail.com"), "Abstract",
                "testfile.txt");
    	my_paper.setDeadline("Later");
        my_recommendation = new Recommendation(false);
    	my_deadline = "Later";
    	my_review = null;
    	my_reviews = new ArrayList<Review>();
    	my_conference_id = 1;
    }
    /**
     * Creates a new Submission based on the given paper.
     * 
     * @param a_paper
     *            the given paper being submitted
     */
    public Submission(Paper a_paper, String the_deadline, int the_conferenceID) {
        my_paper = a_paper;
        my_paper.setDeadline(the_deadline);
        my_deadline = the_deadline;
        my_recommendation = new Recommendation(false);
        my_review = null;
        my_reviews = new ArrayList<Review>();
        my_conference_id = the_conferenceID;
    }
    
    /**
     * Sets this Submission's Recommendation field to the given
     * Recommendation object.
     * 
     * @param recommend the given Recommendation object
     */
    public void setRecommendation(Recommendation recommend) {
    	my_recommendation = recommend;
    }

    /**
     * Adds the given Review object to this Submission's list of reviews.
     * 
     * @param new_review the given list of Review objects
     */
    public void addReview(Review new_review) {
    	my_reviews.add(new_review);
    }
    
    /**
     * Adds the given String deadline to this Submission.
     * 
     * @param recommend a given String deadline
     */
    public void addDeadline(String the_deadline) {
    	my_deadline = the_deadline;
    }	
    
    /**
     * Checks the status of the paper, declined or accepted
     * @return true or false
     */
    public boolean checkPaperStatus() {
        return my_recommendation.getStatus();
    }
    /**
     * Returns the Author who submitted this submission. 
     * @return the Author who submitted this submission
     */
    public Author getAuthor() {
        return my_paper.getAuthor();
    }
    /**
     * Opens the review for the submission
     * @return review opened
     */
    public boolean openReview()
    {
        if(my_review==null)
            return false;
        else
            return true;
    }
    /**
     * Every paper has a program chair
     * @return program chair
     */
    public Chair getChair() {
        return my_program_chair;
    }
    /**
     * The due date of submission per conference
     * @return deadline date
     */
    public String getDeadline() {
        return my_deadline;
    }
    /**
     * Returns the paper from this submission. 
     * @return the paper from this submission
     */
    public Paper getPaper() {
        return my_paper;
    }
    /**
     * Returns the recommendation (yes-true/no-false) for this submission. 
     * @return the recommendation for this submission
     */
    public Recommendation getRecommendation() {
        return my_recommendation;
    }
    /**
     * Returns the list of Reviews for this submission.
     * @return the list of Reviews for this submission
     */
    public List<Review> getReviews() {
        return my_reviews;
    }
    /**
     * Returns the review status of the submission
     * @return the review status
     */
    public boolean getReviewStatus()
    {
        return my_reviewStatus;
    }
    /**
     * Sets the review status to desired status
     * @param reviewStatus
     */
    public void setReviewStatus(boolean reviewStatus)
    {
        my_reviewStatus = reviewStatus;
    }
    /**
     * Every submission is assigned a sub program chair
     * @return sub program chair
     */
    public SubChair getSubChair() {
        return my_sub_chair;
    }
    /**
     * Changes the status of the program/sub Chair of the paper
     * @param the_recommend is true/false of acceptance/decline.
     */
    public void recommend(boolean the_recommend) {
        my_recommendation.setStatus(the_recommend);
        setChanged();
        notifyObservers();
    }
    /**
     * Set progr chair 
     * @param a_paper the given paper
     */
    public void setChair(Chair the_Chair) {
        my_program_chair = the_Chair;
        setChanged();
        notifyObservers();
    }
    /**
     * Sets this submission's paper to the given Paper object, which is useful
     * for authors changing/editing a submission.
     * @param a_paper
     *            the given paper
     */
    public void setPaper(final Paper a_paper) {
        my_paper=a_paper;
        setChanged();
        notifyObservers();
    }
    /**
     * Set subprogr chair
     * @param a_paper
     *            the given paper
     */
    public void setSubprogramChair(SubChair the_subChair) {
        my_sub_chair = the_subChair;
        setChanged();
        notifyObservers();
    }
    /**
     * sets the reviewer assigned to this submission
     * @param the_reviewer
     */
    public void setReviewer(Reviewer the_reviewer)
    {
        my_reviewer = the_reviewer;
    }
    /**
     * returns the reviewer assigned to this submission
     * @return the reviewer
     */
    public Reviewer getReviewer()
    {
        return my_reviewer;
    }
    /**
     * returns the conference id
     * @return conference id
     */
    public int getConferenceID()
    {
        return my_conference_id;
    }
}
