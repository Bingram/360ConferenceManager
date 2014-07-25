package GUI;

import javax.swing.JPanel;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;

import dbAccess.authorAccess;
import dbAccess.paperAccess;
import objects.Author;
import objects.Paper;
import objects.Submission;
import objects.User;

/**
 * @author Margaret
 *
 */
public class SubmissionGUI extends JPanel implements Observer {
	private final JLabel lblWelcomeFirstnameLastname = new JLabel("SUBMISSION");
	private JTextField textField;
	private JTextField textField_1;
	private JFrame my_frame;
	
	private Submission my_submission;
	private Paper my_paper;
	private Author my_user;
	
	/**
	 * Create the panel.
	 */
	public SubmissionGUI(JFrame popup, Author me) {
		super();
		my_submission = new Submission();
		my_paper = new Paper();
		my_frame = popup;
		my_user = me;
		initGUI();
	}
	private void initGUI() {
		System.out.println(my_user.getName());
		setBackground(new Color(0, 128, 128));
		lblWelcomeFirstnameLastname.setVerticalAlignment(SwingConstants.TOP);
		lblWelcomeFirstnameLastname.setForeground(Color.WHITE);
		lblWelcomeFirstnameLastname.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeFirstnameLastname.setFont(new Font("Vrinda", Font.BOLD | Font.ITALIC, 15));
		
		JComboBox category_options = new JComboBox();
		category_options.setModel(new DefaultComboBoxModel(new String[] {"CSE", "CSS", "IT", "IS"}));
		
		JLabel lblNewLabel = new JLabel("Category:");
		
		JLabel lblConference = new JLabel("Conference:");
		
		JComboBox conf_options = new JComboBox();
		conf_options.setModel(new DefaultComboBoxModel(new String[] {"LAS", "ICMEE", "PNWCSE", "OOPSLA"}));
		
		textField = new JTextField();
		textField.setColumns(10);
		
		final JLabel lblTitleOfPaper = new JLabel("Title of paper (max, 100 characters):");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JLabel lblKeywordsforSearching = new JLabel("Keywords (for searching; comma-separated):");
		
		JLabel lblAbstractmax = new JLabel("Abstract (max, 100 words):");
		
		final JTextArea the_abstract = new JTextArea();
		the_abstract.setLineWrap(true);
		
		JButton btnNewButton = new JButton("Submit!");
		btnNewButton.addActionListener(new ActionListener() {

			/**
			 * @author Margaret 
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//  Whatever values were submitted in the fields
				// must be passed on to the table
				paperAccess newpaper = new paperAccess();
				newpaper.addPaper(my_paper);
				
				System.out.println(my_paper);
				my_user.addPaper(my_paper);
				authorAccess myauthor = new authorAccess();
				myauthor.update(my_user);
				
				SubmissionGUI.this.setVisible(false);
//				SubmissionGUI.this.getParent().removeAll();
				my_frame.dispose();
				revalidate();
				repaint();
			}
		});
		
		JButton btnBrowse = new JButton("Browse...");
		btnBrowse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser find_paper = new JFileChooser();
				int returnVal = find_paper.showOpenDialog(SubmissionGUI.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File my_file = find_paper.getSelectedFile();
		            
		            my_paper = new Paper(textField.getText(), my_user, the_abstract.getText(), my_file.getName());
		            System.out.println(my_file.getName());
		            my_submission.setPaper(my_paper);
		        }
			}
		});
		
		JLabel lblChooseFile = new JLabel("Choose file:");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(85)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(161)
							.addComponent(lblWelcomeFirstnameLastname, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblChooseFile)
								.addComponent(lblAbstractmax)
								.addComponent(lblConference)
								.addComponent(lblNewLabel)
								.addComponent(lblTitleOfPaper)
								.addComponent(lblKeywordsforSearching))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(the_abstract, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
									.addComponent(conf_options, 0, 147, Short.MAX_VALUE)
									.addComponent(category_options, 0, 147, Short.MAX_VALUE)
									.addComponent(textField)
									.addComponent(textField_1))
								.addComponent(btnNewButton)
								.addComponent(btnBrowse))))
					.addContainerGap(119, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(lblWelcomeFirstnameLastname, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(conf_options, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblConference))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(category_options, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTitleOfPaper))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKeywordsforSearching))
					.addGap(26)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAbstractmax)
						.addComponent(the_abstract, GroupLayout.PREFERRED_SIZE, 116, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBrowse)
						.addComponent(lblChooseFile))
					.addGap(17)
					.addComponent(btnNewButton)
					.addGap(42))
		);
		setLayout(groupLayout);
	}
	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		repaint();
	}
}
