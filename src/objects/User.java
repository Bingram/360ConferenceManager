package objects;

import java.io.Serializable;
import java.util.Observable;

/**
 * This represents a user in the conference program.
 * 
 * @author Brian
 * 
 */
public class User extends Observable implements Serializable {
    /**
     * serializable for the database
     */
    private static final long serialVersionUID = -29203820938210938L;

    // Users username
    private String my_user_name;

    // Users first name
    private String my_first_name;

    // Users last name
    private String my_last_name;

    // users email address as string
    private String my_email;

    // users password as string
    private String my_password;

    // users ID #
    private int my_id;

    //No more blank constructor
    //

    /**
     * Constructor for user, with first & last name only, 
     * password and email blank
     * 
     * @param the_first String users first name
     * @param the_last  String users last name
     */
    public User(String the_first, String the_last) {
        my_first_name = the_first;
        my_last_name = the_last;
        my_email = "";
        my_password = "";

    }

    /**
     * Constructor for user, with name, email and password
     * 
     * @param the_first String users first name
     * @param the_last String users last name
     * @param the_email String users email
     * @param the_password String users login password
     */
    public User(String the_first, String the_last, String the_email,
            String the_password) {
        my_first_name = the_first;
        my_last_name = the_last;
        my_email = the_email;
        my_password = the_password;

    }

    /**
     * Constructor for user, with name, email and no password
     * 
     * @param the_first String users first name
     * @param the_last  String users last name
     * @param the_email String users email
     */
    public User(String the_first, String the_last, String the_email) {
        my_first_name = the_first;
        my_last_name = the_last;
        my_email = the_email;
        my_password = "";
        // todo
    }

    /**
     * Create a new user with just a full name, LAST FIRST
     * Must add other fields after creating with this constructor
     * @param name String users full name, LAST FIRST
     */
    public User(String name) {
    	
    	String[] split = name.split("\\s+");
		my_last_name = split[0];
		my_first_name = split[1];
	}

	/**
     * Returns this users full name as a string 
     * with a space between LAST & FIRST
     * 
     * @return String "LAST" + SPACE + "FIRST"
     */
    public String getName() {
        return (my_last_name + " " + my_first_name);
    }

    /**
     * Sets the users full name to
     * LAST & FIRST  /  the_last & the_first
     * 
     * @param the_last String users last name
     * @param the_first String users first name
     */
    public void setName(String the_last, String the_first) {
        my_first_name = the_first;
        my_last_name = the_last;
    }
    
    

    /**
     * Returns users ID
     * 
     * @return int users ID
     */
    public int getID() {
        return my_id;
    }

    /**
     * Sets users ID to int
     * 
     * @param the_id int the users ID
     */
    public void setID(int the_id) {
        my_id = the_id;
    }

    /**
     * Returns the users password as String
     * 
     * @return String my_password
     */
    public String getPassword() {
        return my_password;
    }

    /**
     * Sets the users password, to the_password
     * 
     * @param the_password String users password
     */
    public void setPassword(String the_password) {
    	
        my_password = the_password;
    }

    /**
     * Returns the users email address as String
     * 
     * @return String my_email
     */
    public String getEmail() {

        return my_email;
    }

    /**
     * Sets the users email address
     * 
     * @param the_email String users email
     */
    public void setEmail(String the_email) {

        my_email = the_email;
    }

    /**
     * Returns the username
     * 
     * @return String my_user_name
     */
    public String getUsername() {
        return my_user_name;
    }

    /**
     * Set the username to the_name
     * 
     * @param the_username String the my_user_name to set
     */
    public void setUsername(String the_username) {
        my_user_name = the_username;
    }

    /**
     * Return users first name
     * 
     * @return String users first name
     */
    public String getFirstname() {
        return my_first_name;
    }

    /**
     * Set users first name to the_first
     * 
     * @param the_first String users first name
     */
    public void setFirstname(String the_first) {
        my_first_name = the_first;
    }

    /**
     * Return the users last name
     * 
     * @return String users last name
     */
    public String getLastname() {
        return my_last_name;
    }

    /**
     * Set the users last name
     * 
     * @param the_last String users last name
     */
    public void setLastname(String the_last) {
        my_last_name = the_last;
    }

}
