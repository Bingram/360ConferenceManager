package GUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import objects.Author;
import objects.Paper;
import objects.Review;
import objects.Reviewer;
import objects.SubChair;
import objects.Submission;
import objects.User;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import dbAccess.reviewAccess;
import dbAccess.reviewerAccess;
import dbAccess.userAccess;
import javax.swing.JComboBox;

/**
 * The GUI for conducting a Review.
 * Here you'll be able to submit a review by
 * typing in the title of the paper and typing
 * in the author. Then write the review comments
 * in the text box. If a reviewer would like to edit an
 * existing review, type in the title in the text
 * field and hit "Edit Existing..."
 * 
 * @author Crystal Miraflor
 *
 */
public class ReviewGUI extends JPanel {
	

	/**Frame to hold the GUI*/
	private JFrame my_frame;
	
	/**Current user*/
	private User user;
	
	/**The review being used or created*/
	private Review r;
	
	/**The reviewer*/
	private Reviewer rev;
	
	/**List of papers for the reviewer*/
	private List<Submission> papers;
	
	/**Various labels for the Reviewer GUI*/
	private final JLabel lblReview = new JLabel("REVIEW");
	private final JLabel lblTitleOfPaper = new JLabel("Title of Paper");
	private final JLabel lblAuthorOfPaper = new JLabel("Author of Paper");
	private final JLabel lblReviewComments = new JLabel("Review Comments");
	
	/**The text fields of the Reviewer GUI*/
	private final JTextField title_textbox = new JTextField();
	private final JTextField author_textbox = new JTextField();
	private final JTextField edit_textbox = new JTextField();

	/**The buttons on the Reviewer GUI*/
	private final JButton btnSubmit = new JButton("Submit");
	private final JButton btnCancel = new JButton("Cancel");
	private final JButton btnEditExisting = new JButton("Edit Existing...");

	/**Combo box to select an assigned paper*/
	private final JComboBox<Paper> comboBox = new JComboBox();

	/**Scrollpane and text area used to write the review*/
	private final JScrollPane scrollPane = new JScrollPane();
	private final JTextArea review_comments = new JTextArea();
	
	/**Access objects for the database*/
	private reviewAccess review_access = new reviewAccess();
	private final reviewerAccess reviewer_access = new reviewerAccess();
	private final JButton btnReadAPaper = new JButton("Read a Paper");
	

	/**
	 * Create the panel. Constructor for the ReviewGUI.
	 */
	public ReviewGUI(JFrame popup, User the_user) {
		super();
//		rev = reviewer_access.getReviewer(user.getName());
//		papers = rev.getPapers();
		edit_textbox.setColumns(10);
		lblReviewComments.setForeground(new Color(255, 255, 255));
		lblReviewComments.setFont(new Font("Tahoma", Font.PLAIN, 12));
		author_textbox.setColumns(10);
		title_textbox.setColumns(10);
		lblAuthorOfPaper.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblAuthorOfPaper.setForeground(new Color(255, 255, 255));
		lblTitleOfPaper.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblTitleOfPaper.setForeground(new Color(255, 255, 255));
		lblReview.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblReview.setForeground(new Color(255, 255, 255));
		lblReview.setBackground(new Color(0, 128, 128));
		my_frame = popup;
		user = the_user;
		r = null;
		initGUI();
	
	}

	
	/**
	 * Sets up the GUI
	 */
	public void initGUI() {
		setBackground(new Color(0, 139, 139));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap(71, Short.MAX_VALUE)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblTitleOfPaper)
										.addGroup(groupLayout.createSequentialGroup()
											.addComponent(lblAuthorOfPaper)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGap(29))
								.addGroup(groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(btnReadAPaper)
										.addComponent(btnEditExisting, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE))
									.addGap(18)))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(title_textbox, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
								.addComponent(author_textbox)
								.addComponent(edit_textbox, GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
								.addComponent(comboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(292)
							.addComponent(lblReview)))
					.addContainerGap(186, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReviewComments)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(48)
							.addComponent(btnSubmit, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addGap(131))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblReview)
					.addGap(27)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTitleOfPaper)
						.addComponent(title_textbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthorOfPaper)
						.addComponent(author_textbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnEditExisting)
						.addComponent(edit_textbox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReadAPaper))
					.addGap(15)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 243, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnSubmit)
								.addComponent(btnCancel)))
						.addComponent(lblReviewComments))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(review_comments);
		edit_textbox.setText("Want to edit a review? Type in the title!");
		review_comments.setText("Write your review/comments here!");
//		setupComboBox();
		setLayout(groupLayout);
		

		
	/////////////////////buttons and actions below//////////////////////////////
		btnEditExisting.addActionListener(new ActionListener() {
			/**
			 * Loads an existing review into the text box,
			 * allowing the review to be edited. 
			 * 
			 * @author Crystal Miraflor
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String loadFile = edit_textbox.getText();
				if(loadFile.isEmpty() || loadFile.equals("Want to edit a review? Type in the title!")) {
					JOptionPane.showMessageDialog(null, "Please type the title of the paper whose review you wish to edit.");
				}
				//r = review_access.getReview(loadFile); //gets review just based on the title 
				r = review_access.getReview(loadFile, user.getName()); //gets review based on both title and reviewer name
				if (r == null) {
					JOptionPane.showMessageDialog(null, "This review does not exist.");
				} else {
					review_comments.setText(r.getComments()/**loadfile*/);
					title_textbox.setText(r.getTitle());
					author_textbox.setText(r.getAuthor());
				}		
			}

			
		});
		
		btnCancel.addActionListener(new ActionListener() {
			/**
			 * Gives action to the cancel button. Closes
			 * the Review window.
			 * 
			 * @author Crystal Miraflor
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				my_frame.dispose();
			}
			
		});
		
		btnSubmit.addActionListener(new ActionListener() {
			/**
			 * Submits a new/edited review to the database.
			 * 
			 * @author Crystal Miraflor
			 */
			@Override
			public void actionPerformed(ActionEvent e) {
				
				boolean close_flag = false;
				if (title_textbox.getText().isEmpty() || author_textbox.getText().isEmpty() || (review_comments.getText().isEmpty()
						|| review_comments.getText().equals("Want to edit a review? Type in the title!"))) {
					JOptionPane.showMessageDialog(null, "One or more of the required fields is blank. Please fill it out!");
				} else if (review_comments.getText().equals("Write your review/comments here!")) {
					JOptionPane.showMessageDialog(null, "Please fill out your review!");
				} else {
					close_flag = true;
					if(review_access.getReview(title_textbox.getText()) != null) {
						r.saveReview(review_comments.getText());
						review_access.update(r);
					} else {
						Paper p = new Paper(title_textbox.getText(), new Author(
								author_textbox.getText(), "", ""), "Abstract", title_textbox.getText() + ".txt");
						review_access = new reviewAccess();
						review_access.addReview(new Review(title_textbox.getText(), author_textbox.getText(), p, user.getName(), review_comments.getText()));
						
					}
					
				}
				
				if(close_flag) {
					my_frame.dispose();
				}
						
			}
			
		});
		
		btnReadAPaper.addActionListener(new ActionListener() {
			/**
			 * Lets the reviewer read a selected paper.
			 */
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				Paper p = papers.get(comboBox.getSelectedIndex()).getPaper();
		        JFrame popup = new JFrame();
		        popup.getContentPane().setLayout(new GridLayout(1, 1));
		        popup.getContentPane().add(new ReadPaperGUI(p.getTitle(), p));
		        popup.setVisible(true);
		        popup.pack();
		        repaint();
			}
			
		});
		
	}
	
	/**Sets up the combo box*/
	public void setupComboBox() {
		ArrayList<Submission> list = (ArrayList<Submission>) rev.getPapers();
		for(int i = 0; i < list.size(); i++) {
			comboBox.addItem(list.get(i).getPaper());
		}
	}
}
