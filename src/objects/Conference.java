package objects;

import java.io.Serializable;
import java.util.List;

/**
 *	A class representing a conference
 * 
 * @author Crystal Miraflor
 *
 */
public class Conference implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = -27326183762L;
	/**Name/Title of conference*/
	private String my_title; //need the name of each conference??
	
	/**Program chair of the conference*/
	private Chair my_program_chair;
	
	/**List of subprogram chairs for this conference*/
	private List<SubChair> my_subchairs;
	
	/**List of papers submitted to this conference*/
	private List<Paper> my_papers;
	
	//since we have a method for returning users i added this
	/**List of users in the conference*/
	private List<User> my_users;
	
	/**The deadline of this conference*/
	private String my_deadline; //considered format MMDDYYYY
	/**The ID of this conference*/
	private int my_ID;
	
	//should add a deadline field to conference
	//how should our formats for dates go? MMDDYYYY???
	/**
	 * Creates new Conference object
	 * @param the_title the title of the conference
	 * @param the_chair the head program chair of this conference
	 */
	public Conference(final String the_title, final Chair the_chair, final List<SubChair> the_subchairs,
			final List<Paper> the_papers, final List<User> the_users, final String the_deadline) { 
		setTitle(the_title);
		my_program_chair = the_chair;
		my_subchairs = the_subchairs;
		my_papers = the_papers;
		my_users = the_users; 
		my_deadline = the_deadline;
		
		//will the list of subchairs, papers, and users be passed
		//through the constructor or added into a list through methods??
		//pass lists through constructor but also have methods to modify these lists. (set/get)
		//to do
	}
	
	/**
	 * Creates new Conference object, same as other but with space for ID after title
	 * @param the_title the title of the conference
	 * @param the_chair the head program chair of this conference
	 */
	public Conference(final String the_title, int the_id, final Chair the_chair, final List<SubChair> the_subchairs,
			final List<Paper> the_papers, final List<User> the_users, final String the_deadline) { 
		setTitle(the_title);
		my_program_chair = the_chair;
		my_subchairs = the_subchairs;
		my_papers = the_papers;
		my_users = the_users; 
		my_deadline = the_deadline;
		my_ID = the_id;
		
		//will the list of subchairs, papers, and users be passed
		//through the constructor or added into a list through methods??
		//pass lists through constructor but also have methods to modify these lists. (set/get)
		//to do
	}
	
	/**
	 * Simple constructor for conference using title and ID
	 * @param the_title String the conference title
	 * @param the_id int the conference ID
	 */
	public Conference(final String the_title, final int the_id){
		
		my_title = the_title;
		my_ID = the_id;
		
	}
	
	/**
	 * Obtains the program chair of this conference
	 * @return the program chair
	 */
	public Chair getChair() {
		return my_program_chair;
	}
	
	/**
	 * Obtains the list of subchairs of this conference
	 * @return a list of sub program chairs
	 */
	public List<SubChair> getSubProgramChairs() {
		return my_subchairs;
	}
	
	/**
	 * Obtains the list of papers submitted to this conference
	 * @return a list of papers
	 */
	public List<Paper> getPapers() {
		return my_papers;
	}
	
	
	/**
	 * Obtains the list of users in this conference
	 * @return a list of users
	 */
	public List<User> getUsers() {
		return my_users;
	}
	
	//Some set/add methods we might need
	/**
	 * Adds a SubChair into the conference list of subchairs
	 * @param the_sub the sub chair
	 */
	public void addSubChair(final SubChair the_sub) {
		my_subchairs.add(the_sub);
	}
	
	/**
	 * Adds a Paper into the conference list of papers
	 * @param the_papers the paper
	 */
	public void addPaper(final Paper the_paper) {
		my_papers.add(the_paper);
	}
	
	/**
	 * Adds a User into the conference list of users
	 * @param the_user the user
	 */
	public void addUsers(final User the_user) {
		my_users.add(the_user);
	}

	/**
	 * Returns the title of this conference
	 * @return String the title of conference
	 */
	public String getTitle() {
		return my_title;
	}

	/**
	 * Returns this conferences ID number
	 * @return int the onference ID
	 */
	public int getID() {
		return my_ID;
	}

	/**
	 * Set this conference ID number
	 * @param the_id int the conference ID
	 */
	public void setID(int the_id) {
		my_ID = the_id;
	}

	/**
	 * Sets the title of this conference
	 * @param my_title String the title of conference
	 */
	public void setTitle(String the_title) {
		my_title = the_title;
	}
	public String getDeadLine()
	{
	    return my_deadline;
	}
}
