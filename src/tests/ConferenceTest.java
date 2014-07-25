package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import objects.Author;
import objects.Chair;
import objects.Conference;
import objects.Paper;
import objects.SubChair;
import objects.User;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for the conference class.
 * @author Crystal Miraflor
 *
 */
public class ConferenceTest {

	private Conference conf;
	
	@Before
	public void setUp() throws Exception {
		conf = new Conference("Number 1", new Chair("Doe", "John", "jdoe@gmail.com", 1), new ArrayList<SubChair>(),
				new ArrayList<Paper>(), new ArrayList<User>(), "never");
	}

	/**
	 * Check if adding paper works
	 */
	@Test
	public void testAddPaper() {
		Paper p = new Paper("Title0", new Author("a","a","a"), "", "file.txt");
		conf.addPaper(p);
		List<Paper> list = conf.getPapers();
		assertEquals("Should be equal.", p, list.get(0));
		
	}
	
	/**
	 * Check if setting the title works
	 */
	@Test
	public void testSetTitle() {
		String newTitle = "This has changed";
		conf.setTitle(newTitle);
		assertEquals("Should have changed.", newTitle, conf.getTitle());
		
	}
	
	/**
	 * Check if adding a user works
	 */
	@Test
	public void testAddUser() {
		User user = new User("John", "Doe", "jdoe@gmail.com");
		conf.addUsers(user);
		List<User> list = conf.getUsers();
		assertEquals("Should be equal", user, list.get(0));
		
	}

}
