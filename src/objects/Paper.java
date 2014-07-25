package objects;

import java.io.Serializable;

/**
 * A class to represent a scholarly paper in the conference.
 * 
 * @author Margaret Hoang, and edited by other team members
 */
public class Paper implements Serializable {
    /** Serializable for the database. */
    private static final long serialVersionUID = -3407516127746372120L;

    /** The title of this paper. */
    private String my_title;

    /** The author of this paper. */
    private Author my_author; 

    /** The abstract summary of this paper. */
    private String my_abstract;

    /** The file containing this paper. */
    private String my_file;
    
    /** Dead line for the paper. */
    private String my_deadline;

    /**
     * Creates a new Paper object with the given title, author, abstract, and
     * file source.
     * 
     * @param a_title the paper's title
     * @param an_author the paper's author
     * @param an_abstract the paper's abstract
     * @param a_source the path of the paper's file
     */
    public Paper(String a_title, Author an_author, String an_abstract,
            String a_file) {
        my_title = a_title;
        my_author = an_author;
        my_abstract = an_abstract;
        my_file = a_file;
    }

    /** Default constructor. */
    public Paper() {
        my_title = "";
        my_author = null;
        my_abstract = "";
        my_file = "";
    }

    /**
     * Returns this paper's file name.
     * 
     * @return this paper's file name
     */
    public String getFileSource() {
        return my_file;
    }

    /**
     * Returns this paper's author.
     * 
     * @return this paper's author
     */
    public Author getAuthor() {
        return my_author;
    }

    /**
     * Set this paper's filename to the given string.
     * 
     * @param a_source the given filename string
     */
    public void setFileSource(String a_source) {
        my_file = a_source;
    }

    /**
     * Returns this paper's title.
     * 
     * @return the title of the paper
     */
    public String getTitle() {
        return my_title;
    }
    
    /**
     * Sets the deadline for the paper.
     * Used for writing the review class, nothing else.
     * @param the_deadline
     */
    public void setDeadline(String the_deadline) {
        my_deadline = the_deadline;
    }
    
    /**
     * Returns the paper's deadline
     * @return the deadline
     */
    public String getDeadline() {
        return my_deadline;
    }
    
    /**
     * Returns the paper's written abstract.
     * @return abstract
     */
    public String getAbstract() {
        return my_abstract;
    }

}
