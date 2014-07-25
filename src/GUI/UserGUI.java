package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

import objects.Author;
import objects.Chair;
import objects.Paper;
import objects.Reviewer;
import objects.SubChair;
import objects.User;
import dbAccess.authorAccess;
import dbAccess.chairAccess;
import dbAccess.confAccess;
import dbAccess.paperAccess;
import dbAccess.userAccess;

/**
 * A class to hold the main GUI for this Conference Manager.
 * 
 * @author Margaret
 * @author Crystal
 * @author Hans
 */
public class UserGUI extends JPanel {
	
	/** A long integer ID number, in case this class should be serialized. */
	private static final long serialVersionUID = -6889306615190583929L;
	
	/** The JPanel that holds the menu for this UserGUI. */
	private final JPanel menu_panel = new JPanel();
	
	/** The JPanel that holds the right sidebar content. */
	private final JPanel rightSidebar_panel = new JPanel();
	
    /** The label showing where the Quick Links are. */
    private final JLabel lblToolbar = new JLabel("Quick Links");
    
    /** Various labels to show where/what things are.*/
    private final JLabel lblWelcomeFirstnameLastname = new JLabel("Welcome!");
    private final JLabel label = new JLabel("");
    private final JLabel lblContacts = new JLabel("Contact");
    private final JLabel label_2 = new JLabel("");
    private final JLabel lblProgramChair = new JLabel("Program Chair");
    private final JLabel lblSubprogramChair = new JLabel("Subprogram Chair");
    
    /** A scrollpane and JTextArea to display center content. */
    private JScrollPane scrollPane = new JScrollPane();
    private JTextArea content = new JTextArea("HELLO HELLO", 5, 5);
    
    /** Buttons for the various user stories. */
    private final JButton btnSubmittedPapers = new JButton("Submitted Papers");
    private final JButton btnSubmitAPaper = new JButton("SUBMIT A PAPER");
    private final JButton btnReviews = new JButton("Reviews");
    private final JButton btnReviewPaper = new JButton("Edit/Conduct Review");
    private final JButton btnAssignSubProgChairs = new JButton("Assign SubProgram Chairs");
    private JButton btnAssignReviewer = new JButton("Assign Reviewer");
    
    /** The user viewing this GUI. */
    private User my_user;
    
    /** The conference ID associated with the user viewing this GUI. */
    private int my_conf_id;

    /**
     * Constructs a new UserGUI for a given user with a given conference ID.
     * 
     * @author WindowBuilder, edited by Margaret
     * @param a_user a given user
     * @param conf_title the given user's conference ID
     */
    public UserGUI(User a_user, int conf_title) {
    	this(a_user);
        my_conf_id = conf_title;
    }
    
    /**
     * Constructs a UserGUI for a default/generic user.
     * 
     * @author WindowBuilder, edited by Margaret
     * @param a_user a default/generic user
     */
    public UserGUI(User a_user) {
        my_user = a_user;
//        label_2.setIcon(new ImageIcon("./default-avatar2.jpg"));
        label_2.setHorizontalAlignment(SwingConstants.CENTER);
        label_2.setForeground(Color.BLACK);
        label_2.setFont(new Font("Arimo", Font.BOLD, 13));
        label_2.setEnabled(true);
        label_2.setBackground(Color.LIGHT_GRAY);
        lblContacts.setHorizontalAlignment(SwingConstants.CENTER);
        lblContacts.setForeground(Color.BLACK);
        lblContacts.setFont(new Font("Arimo", Font.BOLD, 13));
        lblContacts.setEnabled(true);
        lblContacts.setBackground(Color.LIGHT_GRAY);
        initGUI();
    }

    /**
     * @author Crystal
     * @param frame
     * @param panel
     */
    public void popUpWndow(JFrame frame, JPanel panel) {
        JFrame popup = frame;
        popup.getContentPane().setLayout(new GridLayout(1, 1));
        popup.getContentPane().add(panel);
        popup.setVisible(true);
        popup.pack();
        repaint();
    }

    /**
     * Initializes layout, labels, and buttons for this UserGUI.
     * 
     * @author WindowBuilder, edited by Margaret
     */
    public void initGUI() {
        setBackground(new Color(0, 128, 128));
        lblToolbar.setHorizontalAlignment(SwingConstants.CENTER);
        lblToolbar.setBackground(Color.LIGHT_GRAY);
        lblToolbar.setForeground(Color.BLACK);
        lblToolbar.setEnabled(true);
        lblToolbar.setFont(new Font("Arimo", Font.BOLD, 13));
        lblWelcomeFirstnameLastname.setVerticalAlignment(SwingConstants.TOP);
        lblWelcomeFirstnameLastname.setForeground(Color.WHITE);
        lblWelcomeFirstnameLastname.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcomeFirstnameLastname.setFont(new Font("Vrinda", Font.BOLD | Font.ITALIC, 15));
        
        GroupLayout gl_panel_1 = new GroupLayout(rightSidebar_panel);
        setUpRightSidebarLayout(gl_panel_1);
        rightSidebar_panel.setLayout(gl_panel_1);

        scrollPane = new JScrollPane(content);
        content.setVisible(true);
        scrollPane.setVisible(true);

        btnSubmitAPaper.setForeground(new Color(0, 0, 0));
        btnSubmitAPaper.setBackground(Color.YELLOW);
        btnSubmitAPaper.addActionListener(new ActionListener() {

            /** 
             * Handles the actions of the Submit Paper button, which creates a new
             * SubmissionGUI (in which the paper is actually submitted).
             * 
             * @author Margaret 
             */
            @Override
            public void actionPerformed(ActionEvent arg0) {
                JFrame popup = new JFrame();
                authorAccess the_author = new authorAccess();
                Author my_author = the_author.getAuthor(my_user.getFirstname());
                the_author.closeSubChairAccess();
                SubmissionGUI submitpaper = new SubmissionGUI(popup, my_author); // user's Author obj
                submitpaper.setVisible(true);
                popup.getContentPane().setLayout(new GridLayout(1, 1));
                popup.getContentPane().add(submitpaper);
                popup.setVisible(true);
                popup.pack();
                repaint();
            }
        });
        
        createMenu();
        
        GroupLayout groupLayout = new GroupLayout(this);
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addComponent(menu_panel, GroupLayout.PREFERRED_SIZE, 187, GroupLayout.PREFERRED_SIZE)
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addPreferredGap(ComponentPlacement.RELATED)
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(groupLayout.createSequentialGroup()
                                                .addGap(47)
                                                .addComponent(lblWelcomeFirstnameLastname, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(rightSidebar_panel, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                );
        scrollPane.setToolTipText("");
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                .addGroup(groupLayout.createSequentialGroup()
                        .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                .addComponent(rightSidebar_panel, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
                                .addComponent(menu_panel, GroupLayout.PREFERRED_SIZE, 394, GroupLayout.PREFERRED_SIZE)
                                .addGroup(groupLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(lblWelcomeFirstnameLastname)
                                        .addGap(18)
                                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)))
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
        setLayout(groupLayout);
    }
    
    /**
     * Initializes the menu, based on the type of user.
     * 
     * @author Margaret
     * @author Hans
     * @author Crystal
     */
    private void createMenu() {
    	final JFrame spreadsheet = new JFrame();
    	if (my_user instanceof Reviewer) {
        	defaultMenuPanel(menu_panel, btnReviewPaper, btnReviews, btnSubmittedPapers);
        	btnSubmittedPapers.addActionListener(new ActionListener() {
        		
                /** @author Hans */
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    Reviewer me_Reviewer = new Reviewer(my_user.getFirstname(), my_user.getLastname());
                    ReviewerSpreadSheet author_pages = new ReviewerSpreadSheet(spreadsheet, me_Reviewer);
                    author_pages.setVisible(true);
                    popUpWndow(spreadsheet, author_pages);
                }
            });
            btnReviewPaper.addActionListener(new ActionListener() {
            	
                /**  @author Crystal Miraflor */
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    JFrame popup = new JFrame();
                    ReviewGUI reviewpaper = new ReviewGUI(popup, my_user);
                    reviewpaper.setVisible(true);
                    popUpWndow(popup, reviewpaper);
                }
            });
        } else if (my_user instanceof Chair) {
        	defaultMenuPanel(menu_panel, btnAssignSubProgChairs, btnSubmitAPaper, btnSubmittedPapers);
        	btnSubmittedPapers.addActionListener(new ActionListener() {
        		
                /** @author Hans, edited by Margaret */
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    chairAccess chairs = new chairAccess();
                    Chair me_chair = chairs.getChair(my_user.getLastname() + " " + my_user.getFirstname());
                    ChairSpreadSheet chair_papers = new ChairSpreadSheet(spreadsheet, me_chair);
                    chair_papers.setVisible(true);
                    popUpWndow(spreadsheet, chair_papers);
                }
            });
        	btnAssignSubProgChairs.addActionListener(new ActionListener() {
        		
                /** 
                 * Buggy method.  Attempts to display the list of SubProgram Chairs.
                 * 
                 * @author Margaret 
                 */
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    content.setText("Subprog Chairs");
                   // userAccess all_subs = new userAccess();
                    //my_history.getUser(my_user.getName()).g
                    paperAccess the_papers = new paperAccess();
                    List<Paper> submitted_papers = the_papers.getAllPapers();
                    System.out.println(submitted_papers.toString());
                    //List<Paper> submitted_papers = the_papers.getPaperList(4); // Use conference 4 for testing
                    StringBuilder all_the_papers = new StringBuilder();
                    for (int i = 0; i < submitted_papers.size(); i++) {
                        all_the_papers.append(submitted_papers.get(i).toString() + "\n");
                    }
                    System.out.println(all_the_papers.toString());
                    //	content.setText(all_the_papers.toString());
                    scrollPane.add(content);
                    content.revalidate();
                    content.setVisible(true);
                    content.repaint();
                }

            });
        } else if (my_user instanceof SubChair) {
            defaultMenuPanel(menu_panel, btnAssignSubProgChairs, btnSubmitAPaper, btnSubmittedPapers);
        	btnSubmittedPapers.addActionListener(new ActionListener() {
        		
                /** @author Hans */
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    SubChair me_subchair = new SubChair(my_user.getLastname(),
                            my_user.getFirstname(), my_user.getEmail(), my_conf_id);
                    SubChairSpreadSheet subchair_papers = new SubChairSpreadSheet(spreadsheet, me_subchair);
                    subchair_papers.setVisible(true);
                    popUpWndow(spreadsheet, subchair_papers);
                }
            });
        } else if (my_user instanceof Author) {
        	defaultMenuPanel(menu_panel, btnSubmitAPaper, btnSubmittedPapers, btnReviews);
        	btnSubmittedPapers.addActionListener(new ActionListener() {
        		
                /** @author Hans , edited by Margaret*/
                @Override
                public void actionPerformed(ActionEvent arg0) {
                	authorAccess theauthors = new authorAccess();
                	Author me_Author = theauthors.getAuthor(my_user.getFirstname());
                    AuthorSpreadSheet Author_papers = new AuthorSpreadSheet(spreadsheet, me_Author);
                    Author_papers.setVisible(true);
                    popUpWndow(spreadsheet, Author_papers);
                    System.out.println("Submitted papers");
                }
            });
        }
    }
    
    /**
     * Sets the given panel to GroupLayout and adds to it the given buttons.
     * 
     * @author WindowBuilder, created and edited by Margaret
     * @param panel a given JPanel
     * @param btn1 the first button
     * @param btn2 the second button
     * @param btn3 the third button
     */
    private void defaultMenuPanel(JPanel panel, JButton btn1, JButton btn2, JButton btn3) {
    	GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                        .addGap(56)
                        .addComponent(lblToolbar))
                        .addGroup(gl_panel.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                                        .addComponent(btn1, 0, 0, Short.MAX_VALUE)
                                        .addComponent(btn2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btn3, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE))
                                        .addGap(13)
                                        .addComponent(label, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE))
                );
        gl_panel.setVerticalGroup(
                gl_panel.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel.createSequentialGroup()
                        .addGap(68)
                        .addComponent(lblToolbar, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(btn1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.UNRELATED)
                        .addComponent(btn2, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
                        .addGap(13)
                        .addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
                                .addComponent(btn3, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                                .addComponent(label, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
                );
        panel.setLayout(gl_panel);
    }
    
    
    /**
	 * Sets the GroupLayout specifications for the right sidebar panel
	 * and adds to it the relevant components which are fields in this Login.java class.
	 * 
	 * @author WindowBuilder, method created by Margaret
	 * @param gl_panel the given GroupLayout panel, which is the right sidebar in this GUI
	 */
	private void setUpRightSidebarLayout(GroupLayout gl_panel_1) {
		gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                        .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                .addGroup(gl_panel_1.createSequentialGroup()
                                        .addGap(24)
                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                                .addComponent(lblContacts, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(lblProgramChair)
                                                .addComponent(lblSubprogramChair)))
                                                .addGroup(gl_panel_1.createSequentialGroup()
                                                        .addGap(23)
                                                        .addComponent(label_2, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)))
                                                        .addContainerGap(182, Short.MAX_VALUE))
                );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_1.createSequentialGroup()
                        .addContainerGap(38, Short.MAX_VALUE)
                        .addComponent(label_2, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                        .addGap(37)
                        .addComponent(lblContacts, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblProgramChair)
                        .addPreferredGap(ComponentPlacement.RELATED)
                        .addComponent(lblSubprogramChair)
                        .addGap(114))
                );
	}
}
