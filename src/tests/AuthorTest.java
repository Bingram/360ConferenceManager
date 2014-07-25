package tests;

import static org.junit.Assert.*;
import objects.Author;
import objects.Chair;
import objects.Conference;
import objects.Paper;
import objects.Submission;

import org.junit.Test;

/**
 * A class containing the JUnit tests of the methods of class Author.
 * 
 * @author Margaret
 */
public class AuthorTest {
	
	protected Author setUp() {
		return new Author("Doe", "John", "theemail@Uw.edu");
	}
    
	@Test
	public void testProgramChair() {
		Author testAuthor = setUp();
		Chair testChair = new Chair("Hoang", "Margaret", "mhoang@email.com", 4);
		Conference testConf = new Conference("OOPSLA", 4, testChair, null,
				null, null, "TOMORROW");
		testAuthor.setprogChair(testConf);
		assertEquals(testAuthor.getprogChair(testConf).getEmail(), "mhoang@email.com");
	}
	
	@Test
	public void testGetPaperListAndAddPaper() {
		Author testAuthor = setUp();
		Paper newPaper = new Paper("South", new Author("Ernest", "Shackleton", 
				"eshackleton@email.com"), "Voyage to Antarctica", "./south.txt");
		testAuthor.addPaper(newPaper);
		assertEquals(testAuthor.getPaperList().get(0).getAuthor().getEmail(),
				"eshackleton@email.com");
	}
	
	//  In reality this returns list of submissions
	@Test
	public void testGetPapers() {
		Author testAuthor = setUp();
		Paper newPaper = new Paper("South", new Author("Ernest", "Shackleton", 
				"eshackleton@email.com"), "Voyage to Antarctica", "./south.txt");
		Submission submit =  new Submission(newPaper, "January 1 2014", 4);
		testAuthor.addSubmission(submit);
		assertEquals(testAuthor.getPapers().get(0).getPaper().getTitle(), "South");
	}
}