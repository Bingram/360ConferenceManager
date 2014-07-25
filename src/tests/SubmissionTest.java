package tests;

import static org.junit.Assert.*;
import objects.Author;
import objects.Chair;
import objects.Paper;
import objects.Recommendation;
import objects.Review;
import objects.Reviewer;
import objects.SubChair;
import objects.Submission;

import org.junit.Test;

/**
 * A class containing the JUnit tests of the methods of class Submission.
 * 
 * @author Margaret
 */
public class SubmissionTest {
	
	protected Submission setUp() {
		Author testAuthor = new Author("Doe", "John", "theemail@Uw.edu");
		Paper testPaper = new Paper("World Peace", testAuthor, 
				"This paper is about peace.", "./paper.txt");
		return new Submission(testPaper, "January 1 2014", 4);
	}
    
	@Test
	public void testProgramChair() {
		Submission testSubmission = setUp();
		testSubmission.setChair(new Chair("Hoang", "Margaret", "mhoang@email.com", 4));
		assertEquals(testSubmission.getChair().getEmail(), "mhoang@email.com");
	}

	@Test
	public void testSubprogramChair() {
		Submission testSubmission = setUp();
		testSubmission.setSubprogramChair(new SubChair("Hans", "Colby", "hcolby@email.com", 4));
		assertEquals(testSubmission.getSubChair().getEmail(), "hcolby@email.com");
	}
	
	@Test
	public void testReviewer() {
		Submission testSubmission = setUp();
		testSubmission.setReviewer(new Reviewer("Crystal", "Miraflor"));
		assertEquals(testSubmission.getReviewer().getFirstname(), "Crystal");
	}
	
	@Test
	public void testGetAuthor() {
		Submission testSubmission = setUp();
		assertEquals(testSubmission.getAuthor().getEmail(), "theemail@Uw.edu");
	}
	
	@Test
	public void testPaper() {
		Submission testSubmission = setUp();
		Paper newPaper = new Paper("South", new Author("Ernest", "Shackleton", 
				"eshackleton@email.com"), "Voyage to Antarctica", "./south.txt");
		testSubmission.setPaper(newPaper);
		assertEquals(testSubmission.getPaper().getTitle(), "South");
	}
	
	@Test
	public void testRecommendation() {
		Submission testSubmission = setUp();
		Recommendation rec = new Recommendation(true);
		testSubmission.setRecommendation(rec);
		assertNotEquals(testSubmission.getRecommendation().getStatus(), false);
	}
	
	@Test
	public void testRecommendAndCheckPaperStatus() {
		Submission testSubmission = setUp();
		Recommendation rec = new Recommendation(true);
		testSubmission.setRecommendation(rec);
		testSubmission.recommend(false);
		assertEquals(testSubmission.checkPaperStatus(), false);
	}
	
	@Test
	public void testReviewStatus() {
		Submission testSubmission = setUp();
		testSubmission.setReviewStatus(false);
		assertNotEquals(testSubmission.getReviewStatus(), true);
	}
	
	@Test
	public void testReviews() {
		Submission testSubmission = setUp();
		Paper newPaper = new Paper("South", new Author("Ernest", "Shackleton", 
				"eshackleton@email.com"), "Voyage to Antarctica", "./south.txt");
		testSubmission.addReview(new Review("Review for South", "Shackleton", newPaper, 
									"Anon", "A good read well-worth the effort."));
		testSubmission.addReview(new Review("Review 2 for South", "Shackleton", newPaper, 
				"Anon 2", "Did not like."));
		testSubmission.addReview(new Review("Review 3 for South", "Shackleton", newPaper, 
				"Anon 3", "Would recommend."));
		
		String reviewAuthors = "";
		for (int i = 0; i < testSubmission.getReviews().size(); i++) {
			reviewAuthors += testSubmission.getReviews().get(i).getReviewer() + " ";
		}
		assertEquals(reviewAuthors, "Anon Anon 2 Anon 3 ");
	}
	
	@Test
	public void testDeadline() {
		Submission testSubmission = setUp();
		testSubmission.addDeadline("August 1 2015");
		assertEquals(testSubmission.getDeadline(), "August 1 2015");
	}
	
	@Test
	public void testGetConferenceID() {
		Submission testSubmission = setUp();
		assertEquals(testSubmission.getConferenceID(), 4);
	}
}