package tests;


import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;

import com.almworks.sqlite4java.SQLiteConnection;

import dbAccess.authorAccess;
import dbAccess.chairAccess;
import dbAccess.confAccess;
import dbAccess.paperAccess;
import dbAccess.recommendAccess;
import dbAccess.reviewAccess;
import dbAccess.reviewerAccess;
import dbAccess.subChairAccess;
import dbAccess.submissionAccess;
import dbAccess.userAccess;

/**
 * 
 * @author Brian
 * 
 * Modification functionality was not tested due to the importance of consistent data 
 * in our database for everyone to test from.
 *
 */
public class AccessTest {
	
	private userAccess users;
	private paperAccess papers;
	private subChairAccess subchairs;
	private submissionAccess submssions;
	private reviewerAccess reviewers;
	private reviewAccess reviews;
	private confAccess conferences;
	private chairAccess chairs;
	private authorAccess authors;
	
	@Before
	public void setTable() throws SQLException{

		users = new userAccess();//
		
		papers = new paperAccess();//
		
		authors = new authorAccess();//
		
		chairs = new chairAccess();//
		
		conferences = new confAccess();//
		
		reviews = new reviewAccess();//
		
		reviewers = new reviewerAccess();//
		
		submssions = new submissionAccess();//
		
		subchairs = new subChairAccess();//
		
	}
	

	@Test
	public void testUserLogonGood(){
		//User ID=238 is Brian
		assertEquals(users.returnName(238), "bingram");
				
	}
	
	@Test
	public void testUserLogonBad(){
		//User ID=239 is 
		assertNotEquals(users.returnName(239), "John");
		
	}
	
	@Test
	public void testUserExists(){
		//check user exists by username
		assertEquals(users.checkUser("bingram"), true);
	}
	
	@Test
	public void testUserDoesNotExist(){
		//checks user exists by username
		assertEquals(users.checkUser("Albus"), false);
	}
	
	@Test
	public void testPaperExists(){
		//Checks for a paper
		assertEquals(papers.getPaper("Whales").getTitle(), "Whales");
	}
	
	@Test
	public void testAuthorExists(){
		//Checks for a paper
		assertEquals(authors.getAuthor("Brian").getFirstname(), "Brian");
	}
	
	@Test
	public void testConferenceExists(){
		//verify a conference has the right ID and exists
		assertEquals(conferences.getConf("OOPSLA"), 4);
	}
	
	@Test
	public void testChair(){
		//Get chair
		assertEquals(chairs.getChair("Hoang Margaret").getFirstname(), "Margaret");
	}
	
	@Test
	public void testSubChair(){
		//Get subchair
		//Due to a late bug in the DB, first and last names got swapped
		assertEquals(subchairs.getSubChair("Hans Colby").getFirstname(), "Colby");
	}
	
	@Test
	public void testReview(){
		//Tests a review exists and its corresponding author
		assertEquals(reviews.getReview("random").getReviewer(), null);
	}
	
	@Test
	public void testReviewer(){
		//Get reviewer
		//Due to a late bug in the DB, first and last names got swapped
		assertEquals(reviewers.getReviewer("Crystal Miraflor").getFirstname(), "Miraflor");
	}
	
	@Test
	public void testSubmission(){
		//Get Submission
		assertEquals(submssions.getSubmission("Who I Am Hates Who Ive Been").getAuthor().getFirstname(), "K");
	}
	

}
