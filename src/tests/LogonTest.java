package tests;

import static org.junit.Assert.*;

import java.io.File;

import objects.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import GUI.LoginPage;

import com.almworks.sqlite4java.SQLiteConnection;

import java.sql.SQLException;

import dbAccess.userAccess;


/**
 * Test for user logon
 * @author Brian Ingram
 *
 */
public class LogonTest {
		
	SQLiteConnection db;
	userAccess users;
	
	@Before
	public void setTable() throws SQLException{
		
		db = new SQLiteConnection(new File("logontest.db"));

		users = new userAccess(db);
	}
	

	@Test
	public void testUserLogonGood(){
		//User ID=0 is Brian
		assertEquals(users.returnName(0), "Brian");
				
	}
	
	@Test
	public void testUserLogonBad(){
		//User ID=0 is Brian
		assertNotEquals(users.returnName(0), "John");
		
	}
	
	@Test
	public void testUserExists(){
		assertEquals(users.checkUser("Brian"), true);
	}
	
	@Test
	public void testUserDoesNotExist(){

		assertEquals(users.checkUser("Albus"), false);
	}
	
}
