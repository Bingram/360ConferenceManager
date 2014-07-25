package objects;
import java.io.Serializable;
import java.util.ArrayList;

import dbAccess.submissionAccess;

/**
 * Program Chair
 * This class is to be the back of the program code for the 
 * Conference Program Chair. Responsibilties are to receive papers from 
 * authors wishing to have their paper in the conference. He then assigns this paper 
 * to his SubProgram chairs. He then receives a recommendation from the SubProgram chair
 * and from there make a decision to have the authors paper in the conference.
 * @author Hans
 *
 */
public class Chair extends User implements Serializable{
    /**
     * serializable for database
     */
    private static final long serialVersionUID = -3231129382093L;
    /**
     * Chair knows its sub chairs
     */
    ArrayList<SubChair> listOfSubChairs;
    /**
     * Knows all the papers assigned to them
     */
    ArrayList<Submission> listOfPapers;
    /**
     * Knows all the authors who submitted papers under their conference.
     */
    ArrayList<Author> listOfAuthors;
    /**
     * Constructor for Chair, extending from User class.
     * Uses the database to access all the papers assigned.
     * @param lastName
     * @param firstName
     * @param eMail
     */
    public Chair(String lastName, String firstName, String eMail, int ConferenceID)
    {
        super(lastName, firstName, eMail);
        submissionAccess myPaper = new submissionAccess();
        listOfPapers= myPaper.getSubmissionsList(ConferenceID);
        
    }
    /**
     * Changes the status of the paper to a desired status
     * (Acccepted, Denied, Reviewed, Recommended, Assigned, etc.. are all acceptable statuses)
     * @param paper
     */
    public void reccomendPaper(boolean accepted, Submission the_paper)
    {
        the_paper.recommend(accepted);
    }
    /**
     * Returns the status of a paper
     * @param the_paper
     * @return
     */
    public boolean getStatus(Submission the_paper)
    {
        
        return the_paper.checkPaperStatus();
    }
    /**
     * Assigns the sub chair to a paper
     * @param the_subChair
     * @param the_paper
     * @return
     */
    public boolean assign(SubChair the_subChair, Submission the_paper)
    {
        assert true;//full meaning it cannot take any more papers
        if(the_subChair.isFull()==true)
            return false; 
        else
        {
            assert true;// because in sub chair it has its own addPaper, which returns true if it can.
            the_subChair.addPaper(the_paper);
            the_paper.setSubprogramChair(the_subChair);
            return true;
        }
    }
    /**
     * returns the papers assigned sub chair
     * @param paper
     * @return
     */
    public SubChair getAssigned(Submission the_paper)
    {
        return the_paper.getSubChair();
    }
    /**
     * Gives the author of the Paper requested
     * @param the_paper
     * @return the author
     */
    public Author getAuthor(Submission the_paper)
    {
        return the_paper.getAuthor();
    }
    /**
     * Adds a paper to the list of the program chair's assigned papers
     * @param the_paper
     */
    public void addPaper(Submission the_paper)
    {
        listOfPapers.add(the_paper);
    }
    /**
     * This returns the list of papers assigned the program chair
     * @return the list of papers assigned
     */
    public ArrayList<Submission> getPapers()
    {
        return listOfPapers;
    }
}
