/**
 * UserPage.java
 * TCSS 360
 * 2.28.14
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import objects.User;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

/**
 * A class to serve as a template for users' GUI.
 * When a user first logs in, they are not necessarily already acting in a particular role.
 * Therefore this class should not be abstract.
 * 
 * @author Margaret Hoang
 */
public class UserPage extends JPanel implements Observer {
	
	String[] conferences = {"", "Comic-Con", "Steamcon", "Star Trek Con", "Home & Garden Con"};

	/**
	 * 
	 */
	public UserPage(String name, String password) {
		// TODO Auto-generated constructor stub
		
		//  Theoretically, here the UserPage will create a new User object with
		// the given name & password.  However, after demonstrating somehow that
		// they are an Author and/or SubProg Chair and/or Reviewer, then those
		// objects will be created (?).
		super();
		final User new_user = new User(name, password, "email@email.com");
	    new_user.addObserver(this);
		pageSetup();
	}
	
	public void pageSetup() {
		
        JComboBox conventions = new JComboBox(conferences);
        conventions.setSelectedIndex(0);
        
        JLabel banner = new JLabel("HOME PAGE");
        banner.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel welcome = new JLabel("Would you like to join a conference?");
        
     // This may have to be moved to the LoginPage object
        // I just didn't want to make a lot of edits to LoginPage
       // this.add(conventions, BorderLayout.CENTER);
        
        JMenuBar left_menubar = new JMenuBar();
        left_menubar.setLayout(new GridLayout(3, 0));
        JLabel quicklinks = new JLabel("Quick Links");
        left_menubar.add(quicklinks);
        JMenu submitted = new JMenu("Submitted Papers");
        left_menubar.add(submitted);
        JMenu reviewed = new JMenu("Reviewed Papers");
        left_menubar.add(reviewed);
        
        JMenuBar right_menubar = new JMenuBar();
        right_menubar.setLayout(new GridLayout(3, 0));
        JLabel contact = new JLabel("Contact");
        right_menubar.add(contact);
        JLabel subprog = new JLabel("Subprogram Chair\n(253)-123-4567");
        right_menubar.add(subprog);
        JLabel progchair = new JLabel("Program Chair\n(253)-987-6543");
        right_menubar.add(progchair);
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addComponent(banner, GroupLayout.PREFERRED_SIZE, 563, GroupLayout.PREFERRED_SIZE)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(left_menubar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addComponent(welcome, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
        			.addComponent(right_menubar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        groupLayout.setVerticalGroup(
        	groupLayout.createParallelGroup(Alignment.LEADING)
        		.addGroup(groupLayout.createSequentialGroup()
        			.addComponent(banner)
        			.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
        				.addComponent(left_menubar, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
        				.addComponent(welcome, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
        				.addComponent(right_menubar, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)))
        );
        setLayout(groupLayout);
	}

	public void update(final Observable the_obj, final Object the_arg) {
		// TODO
	}

}
