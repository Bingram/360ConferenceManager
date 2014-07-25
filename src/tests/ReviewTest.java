package tests;

import static org.junit.Assert.*;

import objects.Author;
import objects.Paper;
import objects.Review;
import objects.Reviewer;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the review class.
 * @author Crystal Miraflor
 *
 */
public class ReviewTest {

	/**Review object for testing*/
	private Review rev;
	
	
	@Before
	public void setUp() throws Exception {
		Author a = new Author("A", "A", "A");
		Paper p = new Paper("Title0", a, "", "file.txt");
		rev = new Review("Title", a.getName(), p, "John Doe", "comments");
	}

	/**
	 * Tests to see if editing and 
	 * saving a review works.
	 */
	@Test
	public void testSaveReview() {
		String newComment = "This has been edited";
		rev.saveReview(newComment);
		assertEquals("Should return true!", newComment, rev.getComments());
	}

}
