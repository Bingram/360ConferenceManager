package GUI;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import javax.swing.JButton;

import objects.Paper;

/**
 * A GUI that displays the contents of a paper allowing
 * it to be read.
 * 
 * @author Crystal Miraflor
 *
 */
public class ReadPaperGUI extends JPanel {
	
	/**Title of paper*/
	private final JLabel title = new JLabel("Paper");
	
	/**Scroll pane for the GUI*/
	private final JScrollPane scrollPane = new JScrollPane();
	
	/**Text area to display the contents of the paper*/
	private final JTextArea textArea = new JTextArea();
	
	/**Close button*/
	private final JButton btnClose = new JButton("Close");
	
	/**The paper*/
	private final Paper p;

	/**
	 * Create the panel.
	 */
	public ReadPaperGUI(final String the_title, final Paper the_paper) {
		title.setFont(new Font("Arial Narrow", Font.BOLD, 18));
		title.setForeground(new Color(255, 255, 255));
		title.setText(the_title);
		p = the_paper;
		initGUI();
	}
	
	/**Set up method*/
	private void initGUI() {
		setBackground(new Color(0, 128, 128));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(277)
							.addComponent(title, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(76)
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(263)
							.addComponent(btnClose, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(70, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(title, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 428, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClose)
					.addContainerGap(14, Short.MAX_VALUE))
		);
		
		scrollPane.setViewportView(textArea);
		setLayout(groupLayout);
		
//		String content = readTxtFile(p.getFileSource());
//		textArea.setText(content);
		textArea.setEditable(false);
		
		
	}
	
	/**
	 * Helper method used to read .txt files
	 * @param the_filename the file name 
	 */
	private String readTxtFile(final String the_filename) {
		final StringBuilder sb = new StringBuilder();
		try{
			FileInputStream fstream = new FileInputStream(the_filename /*+ ".txt"*/); 
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
		while ((strLine = br.readLine()) != null)   {
			sb.append(strLine + "\n");
		}
		in.close();
		}catch (Exception e) {//Catch exception if any
			  sb.append("Paper cannot be found or does not exist!");
		}
		return sb.toString();
	}
}
