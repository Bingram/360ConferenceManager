package objects;
import java.io.Serializable;
import java.util.ArrayList;
import dbAccess.submissionAccess;

/**
 * Sub Program Chair Class
 * This class is designed to do the roles of a sub program chair.
 * Which are:
 * To receive papers from the program chairs.
 * Assign papers to reviewers
 * Base on reviews, reccomend paper.
 * @author Hans
 */
public class SubChair extends User implements Serializable{
    /**
     * Serializable for the database
     */
    private static final long serialVersionUID = -532432525434L;
    /**
     * List of the submissions under the sub chair
     */
    private ArrayList<Submission> listOfPapers;
    /**
     * List of all the reviewers under the sub chair
     */
    private ArrayList<Reviewer> listOfReviewers = new ArrayList<Reviewer>();
    /**
     * Every Sub chair knows its Program Chair
     */
    private Chair my_chair;
    /**
     * Constructor for sub program chair
     * @param firstName
     * @param lastName
     * @param eMail
     */
    public SubChair(String firstName, String lastName, String eMail, int ConferenceID)
    {
        super(firstName, lastName, eMail);
        submissionAccess my_papers = new submissionAccess();
        listOfPapers = my_papers.getSubmissionsList(ConferenceID);
    }
    /**
     * View deadline for a paper
     * @param the_paper
     * @return deadline
     */
    public String viewDeadLine(Submission the_paper)
    {
        return the_paper.getDeadline();
    }
    /**
     * View the status for a paper 
     * @param the_Paper
     * @return status
     */
    public boolean viewStatus(Submission the_Paper)
    {
        return the_Paper.checkPaperStatus();
    }
    /**
     * Indicator that Subchair has hit its capacity for papers
     * @return
     */
    public boolean isFull()
    {
        return listOfPapers.size()>=4;
    }
    /**
     * Assigns a reviewer to a paper. Returns false if there is already 4 reviewers
     * @param the_reviewer
     */
    public void designateReviewer(Submission the_paper, Reviewer the_reviewer)
    {
    	if(!(the_reviewer.getName().equals(the_paper.getAuthor().getName()))) {
    		the_reviewer.assignPaper(the_paper, this);
        	listOfReviewers.add(the_reviewer);
        	listOfPapers.add(the_paper);
    	} else {
    		System.out.println("Cannot assign the reviewer their own paper");
    	}
    }
    
    public void assignChair(Chair the_chair)
    {
        my_chair = the_chair;
    }
    /**
     * Reads the file for the review
     * @return review
     */
    public void readReview(Submission the_paper)
    {
        the_paper.openReview();
    }
    /**
     * be able to return to a recommendation to change it
     * IE: Author made necessary changes to be accepted
     */
    public void editRecommendation(Submission the_paper, boolean the_decision)
    {
        the_paper.recommend(the_decision);
    }
    /**
     * When getting assigned its important to know if you can assign a paper to 
     * a subchair, size matters.
     * @param the_paper
     * @return
     */
    public boolean addPaper(Submission the_paper)
    {
        assert true;//has 4 papers
        if(isFull()==true)
        {
            System.out.println("already assigned 4 papers");
            return false;
        }
        else
        {
            listOfPapers.add(the_paper);
            the_paper.setSubprogramChair(this);
            return true;
        }
    }
    /**
     * Returns list of papers assigned to Sub Chair
     * @return
     */
    public ArrayList<Submission> getPapers()
    {
        return listOfPapers;
    }
    /**
     * returns the assigned program chair
     * @return the program chair
     */
    public Chair getChair()
    {
        return my_chair;
    }
    

}
