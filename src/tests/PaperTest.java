package tests;

import static org.junit.Assert.*;

import objects.Author;
import objects.Paper;

import org.junit.Test;

/**
 * A class containing the JUnit tests of the methods of class Paper.
 * 
 * @author Margaret
 */
public class PaperTest {
	
	protected Paper setUp() {
		Author test = new Author("Doe", "John", "theemail@Uw.edu");
		return new Paper("World Peace", test, 
				"This paper is about peace.", "./paper.txt");
	}
    
	@Test
	public void testFileSource() {
		Paper testPaper = setUp();
		String a_filename = "./newPaper.txt";
		testPaper.setFileSource("./newPaper.txt");
		assertEquals(testPaper.getFileSource(), a_filename);
	}

	@Test
	public void testGetTitle() {
		Paper testPaper = setUp();
		String title = "World Peace";
		assertEquals(testPaper.getTitle(), title);
	}
	
	@Test
	public void testGetAuthor() {
		Paper testPaper = setUp();
		assertEquals(testPaper.getAuthor().getEmail(), "theemail@Uw.edu");
	}
	
	@Test
	public void testDeadline() {
		Paper testPaper = setUp();
		String date = "January 1 2014";
		testPaper.setDeadline("January 1 2014");
		assertEquals(testPaper.getDeadline(), date);
	}
	
	@Test
	public void testGetAbstract() {
		Paper testPaper = setUp();
		String authorAbstract = "This paper is about peace.";
		assertEquals(testPaper.getAbstract(), authorAbstract);
	}
}
