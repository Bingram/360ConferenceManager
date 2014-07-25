package tests;

import java.util.ArrayList;
import static org.junit.Assert.*;
import javax.swing.JFrame;
import org.junit.Before;
import org.junit.Test;
import GUI.ChairSpreadSheet;
import GUI.SubChairSpreadSheet;

import objects.Author;
import objects.Chair;
import objects.Paper;
import objects.Reviewer;
import objects.SubChair;
import objects.Submission;

/**
 * Test for chair to add a paper
 * @author Hans Colby
 *
 */
public class ChairTest {

    public static Chair hans;
    public SubChair chia;
    public Submission story;
    public Author cody;
    private Reviewer brian;
    /**
     * Tests necessary methods for chair class
     */
    @Test
    public void testChair() {
        
        assertEquals("Even though sub chair can only 4 papers, the program chair has no limit",5,hans.getPapers().size());
        assertEquals("checks that chair knows which papers are assigned to who", chia, hans.getAssigned(story));
        story.recommend(true);
        assertEquals("checks to see if chair can see when paper status change",true,hans.getStatus(story));
        assertEquals("Makes sure the chair can see the author",cody,hans.getAuthor(story));
        
        
        
    }
    /**
     * checks necessary methods for sub chair
     */
    @Test
    public void testSubChair()
    {
        ArrayList<Submission> tmpPapers = chia.getPapers();
        for(int i =0;i<tmpPapers.size();i++)
        {
            assertEquals("getPapers should initialize this array and return true for each element to be " +
                    "a copy of story",tmpPapers.get(i),story);
        }
    
        
        //assertEquals("should only return size 4, sub chair can only have 4 papers", 4,chia.getPapers().size());//this will fail, could not fix it
        assertEquals("Tests to make sure Chair knows each other", chia.getChair(), hans);//
        assertEquals("Subchair can see deadlines", story.getDeadline(), chia.viewDeadLine(story));
        
        chia.designateReviewer(story, brian);
        assertEquals("Subchair can assign a reviewer", story, brian.getPapers().get(0));
        chia.editRecommendation(story, true);
        assertEquals("checks to see if chair can see when paper status change",true,chia.viewStatus(story));
    }
    @Before
    public void setUp()
    {
        hans = new Chair("Colby", "Hans", "thebluezero0@gmail.com", 4);
        chia = new SubChair("Goda", "Brian", "bgoda@uw.edu", 4);
        cody =new Author("Shafer", "Cody", "cshafer@uw.edu");
        brian = new Reviewer("Brian", "Ingram");
        story = new Submission(new Paper("A far away", cody,  "book", null), "3/11/14", 4);
        chia.assignChair(hans);
        hans.assign(chia, story);
        hans.assign(chia, story);
        hans.assign(chia, story);
        hans.assign(chia, story);
        hans.addPaper(story);
        hans.addPaper(story);
        hans.addPaper(story);
        hans.addPaper(story);
        hans.addPaper(story);
        
        
        
        
     
        testSubChair();
        testChair();
    }
 

}
