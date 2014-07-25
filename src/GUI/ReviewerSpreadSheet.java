package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import objects.Author;
import objects.Review;
import objects.Reviewer;
import objects.Submission;

public class ReviewerSpreadSheet extends JPanel {
    private static final long serialVersionUID = 24L;
    private boolean DEBUG = false;
    public ArrayList<Review> list;
    private JFrame my_popup;
    private static Reviewer my_reviewer;

    public ReviewerSpreadSheet(JFrame popup, Reviewer the_reviewer) {
        super(new GridLayout(1, 0));
        my_reviewer = the_reviewer;
        my_popup = popup;
        String[] columnNames = { "Paper Title", "Author",
                "Sub Chair", "Email", "Deadline" };
        list = the_reviewer.getReviews();
        Object[][] data = new Object[list.size()][5];
        
        // example of chairs spreadsheet
        for (int i = 0; i < list.size(); i++) {
            data[i][0] = new String(list.get(i).getPaper().getTitle());
            data[i][1] = new String(list.get(i).getAuthor());
            data[i][2] = the_reviewer.getSubChair().getName();
            

            data[i][3] = the_reviewer.getSubChair().getEmail();
            data[i][4] = list.get(i).getPaper().getDeadline();//Needs to be in the class! 
            
            
        }

        final JTable table = new JTable(data, columnNames);

        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        // Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i = 0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j = 0; j < numCols; j++) {

                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");

    }

    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Reviewer Spreadsheet");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create and set up the content pane.
        ReviewerSpreadSheet newContentPane = new ReviewerSpreadSheet(frame,my_reviewer);
        newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);

        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

}