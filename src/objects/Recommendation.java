/**
 * 
 */
package objects;

import java.io.Serializable;

/**
 * @author team awesome
 */
public class Recommendation implements Serializable{
    /**
     * serializable for the database
     */
    private static final long serialVersionUID = -232132132132L;
    
    /**
     * recommendation status, default is false
     */
	private boolean my_status = false;
	
	/**
	 * Title of the paper for recommendation
	 */
	private String my_paper;
	/**
	 * A recommendation object with the status of the papers recommendation
	 * @param status boolean of if the paper is recommended
	 */
	public Recommendation(boolean status) {
		my_status = status;
	}
	
	/**
	 * Creates a recommendation with status and paper title
	 * @param status boolean status of the recommendation
	 * @param the_paper String the title of the paper
	 */
	public Recommendation(boolean status, String the_paper) {
		my_status = status;
	}
	
	/**
	 * Get the status of this recommendation
	 * @return boolean status of recommendation
	 */
	public boolean getStatus() {
		return my_status;
	}
	
	/**
	 * Sets the status of this recommendation
	 * @param a_status boolean of recommendations tatus
	 */
	public void setStatus(boolean a_status) {
		my_status = a_status;
	}

	/**
	 * The title of the paper this recommendation is for
	 * @return String the paper title
	 */
	public String getPaper() {
		return my_paper;
	}

	/**
	 * Sets the paper title for this recommendation
	 * @param my_paper String paper title
	 */
	public void setPaper(String the_paper) {
		my_paper = the_paper;
	}
}
