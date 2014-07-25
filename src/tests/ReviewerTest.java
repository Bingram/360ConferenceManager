package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import objects.Author;
import objects.Paper;
import objects.Review;
import objects.Reviewer;
import objects.SubChair;
import objects.Submission;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the Reviewer class.
 * @author Crystal Miraflor
 *
 */
public class ReviewerTest {

	/**A reviewer*/
	private Reviewer rev;
	
	/**Another reviewer*/
	private Reviewer rev2;
	
	/**A sub chair*/
	private SubChair subCh;
	
	/**Some submissions for testing.*/
	private Submission sub0;
	private Submission sub1;
	private Submission sub2;
	private Submission sub3;
	private Submission sub4;
	
	/**Set up before the running the test*/
	@Before
	public void setUp() throws Exception {
		rev = new Reviewer("John", "Doe");
		rev2 = new Reviewer("Ray", "Voyeur");
		
		subCh = new SubChair("Alex", "Bare", "abare@gmail.com", 1);
		
		sub0 = new Submission(new Paper("Title0", new Author("A", "A", "A"), "", "file.txt"), "Today", 1);
		sub1 = new Submission(new Paper("Title1", new Author("B", "B", "B"), "", "file1.txt"), "Today", 1);
		sub2 = new Submission(new Paper("Title2", new Author("C", "C", "C"), "", "file2.txt"), "Today", 1);
		sub3 = new Submission(new Paper("Title3", new Author("D", "D", "D"), "", "file3.txt"), "Today", 1);
		sub4 = new Submission(new Paper("Title4", new Author("E", "E", "E"), "", "file4.txt"), "Today", 1);
		
		subCh.designateReviewer(sub0, rev2);
		subCh.designateReviewer(sub1, rev2);
		subCh.designateReviewer(sub2, rev2);
		subCh.designateReviewer(sub3, rev2);
		
		//so rev has 0 papers so far
		//rev2 has 4 papers assigned
		
		rev2.conductReview("Title0", sub0.getPaper(), "this is a review");
		rev2.conductReview("Title3", sub3.getPaper(), "another review");
		
	}

	
	
	/**
	 * This one just tests to see if designating a paper
	 * to the reviewer works. This should all return true.
	 */
	@Test
	public void testDesignateReviewer() {
		subCh.designateReviewer(sub4, rev);
		ArrayList<Submission> s = (ArrayList<Submission>) rev.getPapers();
		assertEquals("Should be true. rev was only assigned one paper", 1, s.size());
		
		subCh.designateReviewer(sub0, rev);
		subCh.designateReviewer(sub1, rev);
		s = (ArrayList<Submission>) rev.getPapers();
		assertEquals("Should be true. rev has now been assigned a total of 3", 3, s.size());
	}
	
	/**
	 * This one tests to make that no other papers can be assigned
	 * if the reviewer has the max number of papers assigned already.
	 */
	@Test
	public void testFailtoDesignate() {
		subCh.designateReviewer(sub2, rev2);
		ArrayList<Submission> s = (ArrayList<Submission>) rev2.getPapers();
		assertNotSame("Paper should not be added. Still 4.", 5, s.size());
		
	}
	
	/**
	 * This one tests that the correct paper is returned. 
	 * Should all return true.
	 */
	@Test
	public void testGetPaper() {
		Paper p = rev2.getPaper("Title0");
		assertEquals("Should return correct paper", sub0.getPaper(), p);
		p = rev2.getPaper("Title3");
		assertEquals("Should return correct paper", sub3.getPaper(), p);
	}
	
	/**
	 * This method tests retrieving a non-existing paper.
	 */
	public void testGetPaperFail() {
		Paper p = rev2.getPaper("Nothing");
		assertNotSame("Should return false", sub3.getPaper(), p);
		assertNull("Should be true that it is null", p); //if a paper is not found, null is returned
		//since paper is non-existing null should appear and so this
		//should return true. 
	}
	
	/**
	 * Tests the conduct review
	 */
	@Test
	public void testConductReview() {
		ArrayList<Review> r = rev2.getReviews();
		rev2.conductReview("Title2", sub2.getPaper(), "blah");
		assertEquals("Should be true.", 3, r.size());
		
	}
	
	/**
	 * This one tests that the correct review is returned.
	 * Should all return true.
	 */
	@Test
	public void testGetReview() {
		Review r = rev2.getReview("Title3");
		assertEquals("Should be true.", "Title3", r.getTitle());
	}

}
