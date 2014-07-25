package dbAccess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import objects.Author;
import objects.Paper;
import objects.Submission;
import objects.User;

import com.almworks.sqlite4java.SQLite;
import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;

/**
 * Provides access to USERS table in the conference database.
 * @author Brian
 *
 */
public class userAccess {
		
	private final File dbfile = new File("backoffice.db");
	private SQLiteConnection my_user_db = null;
	private String table = "USERS";
	
	/**
	 * Constructor with SQLiteConnection passed
	 * @param sql SQLiteConnection passed to constructor
	 */
	public userAccess(SQLiteConnection sql){
		
		my_user_db = sql;

	
		
	}
	
	/**
	 * Constructor that establishes its own connection to the USER DB.
	 */
	public userAccess(){
		
		my_user_db = new SQLiteConnection(dbfile);
		
		
		
	}
	
	/**
	 * executes the sql statement passed as String
	 * @param sql String of the sql statement to execute
	 */
	private void executeSQL(String sql){
		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			my_user_db.exec(sql);

			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Returns the username of the user with matching ID
	 * @param ID int of the user ID
	 * @return username of mathcing user ID
	 */
	public String returnName(int ID){
		
		String name = "";
		
		String my_query = "SELECT USERNAME FROM " + table + 
				" WHERE USERID=" + ID + ";";
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			name = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)

			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return name;
	}
	
	/**
	 * Best not to use this one, unless you plan to update the user later.
	 * Dose not create a serialized object, use addUser(user) instead.
	 * 
	 * Adds a user with name and ID, all other fields are blank
	 * (ID NOTE: possible this would be generated but for now just assign a new one)
	 * @param name String name of user
	 * @param ID Int ID of user, must be unique...current ones are 2 digit, maybe make a 3 digit? 
	 */
	public void addUser(String name, int ID){
		
		String serial = serialUser(new User(name));
		
		String my_query = "INSERT INTO " + table + " (USERID,USERNAME,USEROBJECT) " + 
				"VALUES (" + ID + "," + "'"+name+"'" + "," + "'"+serial+"'" + ");";
		
		executeSQL(my_query);//execute SQL query
		
	}
	
	/**
	 * Adds a user as passed to the method, fills fields from object
	 * @param u User to add
	 */
	public void addUser(User u){
		String serial = serialUser(u);
		
		//USERID,USERNAME,FIRSTNAME,LASTNAME,EMAIL,PASSWORD,CONFERENCEID,CONFERENCDTITLE,ROLEID,ROLE,USEROBJECT
		//Single quotes around Strings
		String my_query = "INSERT INTO " + table + " (USERID,USERNAME,PASSWORD,FIRSTNAME,LASTNAME,EMAIL,USEROBJECT) " + 
				"VALUES (" + u.getID() + "," + "'" + u.getUsername()+ "'" + "," + 
				"'" + u.getPassword()+ "'" + "," + "'" + u.getFirstname() + "'" + "," + "'" + u.getLastname() + "'" + "," + "'" + u.getEmail() + "'"+ "," + "'" +serial+ "'" + ");";

		executeSQL(my_query);//execute SQL query
		
	}
	
	/**
	 * Updates the user object file and table
	 * Based on username and conferenceid, updates all rows with matching values
	 * 
	 * @param the_user User to update
	 * @param the_conf_id int Conference ID where user belongs
	 */
	public void update(User the_user, int the_conf_id){
		
		String serial = serialUser(the_user);
		
		String my_query = "UPDATE " + table + " SET USERNAME = " + the_user.getUsername() + 
					", LASTNAME = " + "'" + the_user.getLastname() + "'" +
					", FIRSTNAME = " + "'" + the_user.getFirstname() + "'" +
					", NAME = "+ "'" + the_user.getLastname() + " "+ the_user.getFirstname() +"'" +
					", EMAIL = " + "'" + the_user.getEmail() + "'" +
					", USEROBJECT = " + "'" + serial + "'" +
					" WHERE USERNAME = " + "'" + the_user.getUsername() + "'" +
					" AND CONFERENCEID = " + the_conf_id +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Updates the user object file and table
	 * Based on username and conferencdetitle, updates all rows with matching values
	 * 
	 * @param the_user User to update
	 * @param the_conf_title String Conference title where user belongs
	 */
	public void update(User the_user, String the_conf_title){
		
		String serial = serialUser(the_user);
		
		String my_query = "UPDATE " + table + " SET USERNAME = " + the_user.getUsername() + 
					", LASTNAME = " + "'" + the_user.getLastname() + "'" +
					", FIRSTNAME = " + "'" + the_user.getFirstname() + "'" +
					", NAME = "+ "'" + the_user.getLastname() + " "+ the_user.getFirstname() +"'" +
					", EMAIL = " + "'" + the_user.getEmail() + "'" +
					", USEROBJECT = " + "'" + serial + "'" +
					" WHERE USERNAME = " + "'" + the_user.getUsername() + "'" +
					" AND CONFERENCDETITLE = " + "'" + the_conf_title + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Updates the user object file and table
	 * Based on username, updates all rows with matching value
	 * 
	 * @param the_user User to update
	 */
	public void update(User the_user){
		
		String serial = serialUser(the_user);
		
		String my_query = "UPDATE " + table + " SET USERNAME = " + the_user.getUsername() + 
					", LASTNAME = " + "'" + the_user.getLastname() + "'" +
					", FIRSTNAME = " + "'" + the_user.getFirstname() + "'" +
					", NAME = "+ "'" + the_user.getLastname() + " "+ the_user.getFirstname() +"'" +
					", EMAIL = " + "'" + the_user.getEmail() + "'" +
					", USEROBJECT = " + "'" + serial + "'" +
					" WHERE USERNAME = " + "'" + the_user.getUsername() + "'" +";";

		executeSQL(my_query);//execute SQL query
		
		
	}
	
	/**
	 * Updates a users role for a given conference and username
	 * @param the_username String username of user
	 * @param the_conference_id int Conference ID for user
	 * @param the_role String role of user at the given conference
	 */
	public void addUserRole(String the_username, int the_conference_id, String the_role){
		String my_query = "UPDATE " + table + " SET ROLE=" +"'"+ the_role +"'"+ " WHERE USERNAME="  +"'"+ the_username +"'"+ " AND CONFERENCEID=" + the_conference_id + ";";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Updates a users conference ID
	 * 
	 * @author Brian
	 * @author Margaret (modified existing code)
	 * @param the_username String username of user
	 * @param the_conference_id int Conference ID for user
	 */
	public void addConferenceID(String the_username, int the_conference_id){
		String my_query = "UPDATE " + table + " SET CONFERENCEID=" +"'"+ the_conference_id +"'"+ " WHERE USERNAME="  +"'"+ the_username +"'" + ";";

		executeSQL(my_query);//execute SQL query
	}
	
	/**
	 * Should return the first conference ID associated with this user.
	 * @author Brian
	 * @author Margaret (modified existing method)
	 * @param the_user_name 
	 * @return the first conference ID associated with this user
	 */
	public int getConferenceID(String the_user_name){
		int conf_id = 0;
		
		String sql = "SELECT CONFERENCEID FROM " + table + " WHERE USERNAME=" + "'" + the_user_name + "'" + ";";

		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			my_user_db.exec(sql);
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
			
			conf_id = result.columnInt(0); //  This should be the conference ID number
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			conf_id = 0; // MH
		}
		
		
		return conf_id;
	}
	
	/**
	 * Get ArrayList of users
	 * @return ArrayList<User>
	 */
	public List<User> getUserList(){
		
		List<User> temp = new ArrayList<User>();
		
		String sql = "SELECT USEROBJECT FROM " + table + ";";

		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			my_user_db.exec(sql);
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			while(result.hasRow()){

				result.step();//get result of sql query
				User u = deserialUser(result.columnString(0));
				temp.add(u);
			}			
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return temp;
		
		
	}
	
	
	/**
	 *Returns the user object, first instance if there are multiple.
	 *
	 * @param the_user_name String username of user to get
	 * @return temp the user with selected user name
	 */
	public User getUser(String the_user_name){
		User temp = null;
		
		String sql = "SELECT USEROBJECT FROM " + table + " WHERE USERNAME=" + "'" + the_user_name + "'" + ";";

		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialUser(object);
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return temp;
	}
	
	/**
	 * Gets the user object currently held in serialized object form
	 * Using the username of the User passed in
	 * @param the_user User to retrieve, must have registered username
	 * @return User deserialized User object matching the username
	 */
	public User getUser(User the_user){
		User temp = null;
		
		String sql = "SELECT USEROBJECT FROM " + table + " WHERE USERNAME=" + "'" + the_user.getUsername() + "'" + ";";

		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			temp = deserialUser(object);
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return temp;
	}
	
	/**
	 * NOTE: currently returns null, may need to change role implementation
	 * 
	 * Gets the particular user object based upon user name (not full name),
	 * title of the conference for which the role is applicable.
	 * 
	 * Role must be the all-caps title of the table in which that user is held, i.e. 
	 * to get a reviewer, set role String to REVIEWERS.
	 * 
	 * Ex: john has username john316, the conference is CES, of which he is a reviewer
	 * @param the_user_name String the username of the user
	 * @param the_conference_title String the title of conference to retrieve from
	 * @param the_role String of the users role in the conference
	 * @return User object corresponding to their role
	 */
	public User getUserObject(String the_user_name, String the_conference_title, String the_role){
		User temp = null;
		
		return temp;
	}
	
	/**
	 * Returns the String value of a users role, if multiple roles, then first is returned
	 * @param the_user_name String username of the user
	 * @param the_conference_title String title of conference they are a user
	 * @return String of the role they play in the conference
	 */
	public String getUserRole(String the_user_name, String the_conference_title){
		String the_role = "";
		
		String my_query = "SELECT ROLE FROM " + table + 
				" WHERE USERNAME=" + "'" + the_user_name + "'" + " AND " + 
				" CONFERENCDETITLE=" + "'" + the_conference_title + "'" + ";";
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			the_role = object;
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return the_role;
	}
	
	
	/**
	 * Returns the String value of a users role, if multiple roles, then first is returned
	 * 
	 * @author Brian
	 * @author Margaret (modified existing code)
	 * @param the_user_name String username of the user
	 * @param the_conference_ID conference ID as an integer
	 * @return String of the role they play in the conference
	 */
	public String getUserRole(String the_user_name, int the_conference_ID){
		String the_role = "";
		
		String my_query = "SELECT ROLE FROM " + table + 
				" WHERE USERNAME=" + "'" + the_user_name + "'" + " AND " + 
				" CONFERENCEID=" + the_conference_ID  + ";";
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(my_query);
			
			result.step();//get result of sql query
		
			String object = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
			
			System.out.println();
			System.out.println("USER: " + the_user_name + "\nRole: " + object);
			System.out.println();
			
			the_role = object;
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			the_role = null;  /// MH
		}
		
		return the_role;
	}
	

	/**
	 * Check for user by ID
	 * @param the_id user ID to find
	 * @return boolean of existence
	 */
	public boolean checkUser(int the_id){

		boolean status = false;
		
		String sql = "SELECT USERNAME FROM " + table + " WHERE ID=" + the_id + ");";

		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			my_user_db.exec(sql);
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
		
			String name = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
						
			if(name!=null){status = true;};
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//TODO
		return status;	
	}

	/**
	 * Check for user by the_user.getID()
	 * @param the_user user to find
	 * @return boolean of existence
	 */
	public boolean checkUser(User the_user){

		boolean status = false;
		
		String sql = "SELECT USERNAME FROM " + table + " WHERE ID=" + the_user.getID() + ");";
		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			my_user_db.exec(sql);
			
			SQLiteStatement result = my_user_db.prepare(sql);//prepare query
			
			result.step();//get results of sql query
		
			String name = result.columnString(0);//gets first result in first column of result (i.e.: USERNAME)
						
			if(name!=null){status = true;};
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//TODO
		return status;	
	}

	/**
	 * Check for user by username
	 * @param the_name
	 * @return boolean whether user exists
	 */
	public boolean checkUser(String the_name){
	
		String name = "";
		
		boolean status = false;
		
		String sql = "SELECT USERNAME FROM " + table + " WHERE USERNAME=" + "'" + the_name + "'" + ";";
		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
			
			name = result.columnString(0);//gets first result in first column of result set (i.e.: USERNAME)

			result.cancel();
			
			System.out.println("This user exists: " + name);
			
			if(name!=null){status = true;};
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//TODO
		return status;
	}
	
	/**
	 * Check for user by user name and password
	 * @param the_name
	 * @return boolean whether user exists
	 */
	public boolean checkUser(String the_user_name, String the_password){
	
		String name = "";
		
		String pass = "";
		
		boolean status = false;
		
		String sql = "SELECT USERNAME, PASSWORD FROM " + table + " WHERE USERNAME=" + "'" + the_user_name + "'" + " AND PASSWORD=" + "'" + the_password + "'" + ";";
		
		try {
			
			if(!my_user_db.isOpen()){
				openUserAccess();
			}
			
			SQLiteStatement result = my_user_db.prepare(sql);
			
			result.step();//get result of sql query
			
			name = result.columnString(0);//gets first result in first column of result set (i.e.: USERNAME)
			
			pass = result.columnString(1);
			
			result.cancel();
			
			if(name.equals(the_user_name) && pass.equals(the_password)){
				status = true;
			}
			
			if(my_user_db.isOpen()){
				closeUserAccess();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//TODO
		return status;
	}
	
	/**
	 * Accepts a user object, serializes it
	 * then returns the string of the object file name
	 * @param the_user user to serialize
	 */
	private String serialUser(User the_user){
		String filename = the_user.getName().toString() + ".ser";
	    

	    // save the object to file
	    FileOutputStream fos = null;
	    ObjectOutputStream out = null;
	    try {
	      fos = new FileOutputStream(filename);
	      out = new ObjectOutputStream(fos);
	      out.writeObject(the_user);

	      out.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return filename;
	}
	
	/**
	 * Returns the user using the serializable file name
	 * example: username.ser
	 * @param u_name serial file name
	 * @return User with object name a_name
	 */
	private User deserialUser(String u_name){
		String filename = u_name;
	    
		User u = null;
	    
		FileInputStream fis = null;
	    ObjectInputStream in = null;
	    try {
	      fis = new FileInputStream(filename);
	      in = new ObjectInputStream(fis);
	      u = (User) in.readObject();
	      in.close();
	    } catch (Exception ex) {
	      ex.printStackTrace();
	    }
	    
	    return u;
	    
	}
	
	/**
	 * Prepare database connection for transactions.
	 * Must be done before every SQL query executed (or series of queries)
	 */
	public void openUserAccess(){		
		try {
			if(!my_user_db.isOpen()){
				my_user_db.open();//open connection
				my_user_db.exec("BEGIN TRANSACTION");//begin transactions on connection
			}
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Commit transactions on database, close connection.
	 * Must be done after every SQL query executed (or series of queries)
	 */
	public void closeUserAccess(){
		try {
			if(my_user_db.isOpen()){
				my_user_db.exec("COMMIT;");
				my_user_db.dispose();
			}
			
		} catch (SQLiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
