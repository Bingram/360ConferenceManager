package GUI;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * NOTES
 * 
 * The way to give this a background is using JLayeredPane, which will hold different panels.
 * We put the background on one layer setting it opaque, and then the components on another non-opaque layer.
 * 
 * Example: http://docs.oracle.com/javase/tutorial/uiswing/components/layeredpane.html
 *
 */

/**
 * Main login page for conference management program.
 * Will handle user input of name and password as Strings.
 * @author Brian
 */
public class LoginPage extends JFrame implements ActionListener {

	/**
	 * serial version ID needed for serialization.
	 * It is not required, but it is recomended to avoid run-time excpetions.
	 * Each class needs a unique one, it it can be any number but must end in L
	 * Ex:   private static final long serialVersionUID = #########L;
	 */
	private static final long serialVersionUID = -6461099361534121839L;
	
	
	private JPanel panel = new JPanel();//new Jpanel to hold logon interface components
	
	private JTextField nameField = new JTextField();//name text field
    private JTextField passwordField = new JTextField();//password text field

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LoginPage login1 = new LoginPage();//create new logon page (THIS)
		
		login1.setVisible(true);//set this logon page visible
		
	}
	
	/**
	 * Constructor for this login page JFrame
	 */
	public LoginPage(){
		pageSetup();//call setup method for this JFrame
	}
	
	/**
	 * Sets up this frame with a panel and and its components
	 * Labels for text naming each field
	 * Fields to enter name and password
	 * Buttons for logon and quit
	 */
	public void pageSetup(){
		// TODO
		//JPanel panel = new JPanel();//new Jpanel to hold logon interface components
        getContentPane().add(panel);//get this JFrame and add panel to it
        final JFrame screen = this;//so close button can ActionListener reference this JFrame

        panel.setLayout(null);//no layout, components are absolutely set

        JButton loginBtn = new JButton("Login");//new JButton for logon
        JButton quitBtn = new JButton("Quit");//new JButton for quit
        
        quitBtn.setBounds(203, 100, 100, 30);//quit to the right of login
        loginBtn.setBounds(100, 100, 100, 30);//login to the left of quit 
        
        loginBtn.setToolTipText("Login to Conference Manager");//set tooltip hover-over text on logon button
        quitBtn.setToolTipText("Quit Conference Manager");//set tooltip hover-over text on quit button
        
        loginBtn.addActionListener(this);//see actionPerformed in this class
        quitBtn.addActionListener(new ActionListener(){//in-line actionlistener to close program

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				screen.dispose();//close program
			}
        	
        });
        
     //   JTextField nameField = new JTextField();//name text field
     //   JTextField passwordField = new JTextField();//password text field
        
        nameField.setBounds(203, 40, 100, 30);//name above password
        passwordField.setBounds(203, 70, 100, 30);//password below name, above buttons
        
        JLabel nameLabel = new JLabel("Name: ");//new JLabel for name field label
        JLabel passwordLabel = new JLabel("Password: ");//new JLabel for password field label
        
        nameLabel.setBounds(100, 40, 100, 20);// left of name field
        passwordLabel.setBounds(100, 70, 100, 20);//left of password field
        
        panel.add(nameLabel);//add name label
        panel.add(passwordLabel);//add password label
        
        panel.add(nameField);//add name field
        panel.add(passwordField);//add password field
        
        panel.add(loginBtn);//add logon button
        panel.add(quitBtn);//add logon button
        

        setTitle("Conference Manager");//set window title
        setSize(600, 400);//set window width and height, respectively
        setLocationRelativeTo(null);//starting location of window
        setDefaultCloseOperation(EXIT_ON_CLOSE);//close on exit
	}

	public boolean checkUserExists(String name){
		// TODO 
		//query database
		return true;
	}

	/**
	 * @author Margaret
	 * */
	@Override
	public void actionPerformed(ActionEvent arg0) {//perform logon action
		// TODO Auto-generated method stub
		///  FOR TESTING ONLY:
		//System.out.println(nameField.getText() + " " + passwordField.getText());
		
		// Pseudocode
		// if checkUserExists(nameField.getText(), passwordField.getText())
		//    //  check if user has a role
		//		//  if so, then (e.g.) new AuthorPage(name, password)
		//		// else new UserPage(name, pw)
		// else create new account....
		//     new UserPage(name, pw) 
		//UserPage user = new UserPage(nameField.getText(), passwordField.getText());
		getContentPane().remove(panel);
		
		
		//pack();
		UserPage page = new UserPage(nameField.getText(), passwordField.getText());
		getContentPane().add(page);
		setTitle("User Page");//set window title
		pack();
	}
	
	
}
