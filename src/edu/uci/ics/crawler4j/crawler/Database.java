package edu.uci.ics.crawler4j.crawler;

import java.sql.*;

/**
 * This class represents a database so that I can make simple methods and hide the exact sql calls.
 * 
 * @author Allen
 *
 */
public class Database {
	private Connection conn = null;
	
	public Database(String username, String password) {
		
		try {
			conn = login(username, password);
			System.out.println("-----Database Login successful------");
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			
		
		} catch(java.lang.ClassNotFoundException ex) {
			System.err.println("In database constructor: " + ex);
			System.err.println("You might be getting this error if you did include mysql-connector-java-5.0.8-bin.jar in your build path.");
			
		}catch (Exception ex) {
			System.err.println("In database constructor: " + ex);
			System.err.println("You might be getting this error if you did not set your MySQL password in MyContainers.");
			
		} 
		
	}
	
	/**
	 * Adds a class to the database. Call commit to commit the change.
	 * @param professor
	 * @param classname
	 */
	public void addClass(String DESCRIPTION, String NAME, int CLASSNUMBER, int INSTRUCTOR) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into CLASS (DESCRIPTION, NAME, CLASSNUMBER, INSTRUCTOR) values (?, ?, ?, ?)");
			pstmt.setString(1, DESCRIPTION);
			pstmt.setString(2, NAME);
			pstmt.setLong(3, CLASSNUMBER);
			pstmt.setLong(4, INSTRUCTOR);
			
			
			if(pstmt.executeUpdate() != 1) {
				throw new Exception();	
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Adds a department to the database
	 * @param NAME
	 */
	public void addDepartment(String NAME) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into DEPARTMENT (NAME) values (?)");
			pstmt.setString(1, NAME);
			
			if(pstmt.executeUpdate() != 1) {
				throw new Exception();	
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Adds an instructor to the database
	 * 
	 * @param SEED
	 * @param SOCIAL
	 * @param POLYRATING
	 * @param NAME
	 * @param USERNAME
	 * @param DEPARTMENT
	 */
	public void addInstructor(String SEED, int SOCIAL, String POLYRATING, String NAME, String USERNAME, int DEPARTMENT) {
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement ("Insert into INSTRUCTOR (SEED, SOCIAL, POLYRATING, NAME, USERNAME, DEPARTMENT) values (?, ?, ?, ?, ?, ?)");
			pstmt.setString(2,"A");
			pstmt.setString(3,"B");
			pstmt.setInt(4,1);
			pstmt.setString(3, POLYRATING);
			pstmt.setString(4, NAME);
			pstmt.setString(5, NAME);
			pstmt.setString(6, USERNAME);
			pstmt.setLong(7, DEPARTMENT);
			
			if(pstmt.executeUpdate() != 1) {
				throw new Exception();	
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	/**
	 * Commits changes to the database.
	 */
	public void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Helper method for making an Oracle connection
	 */
	private static Connection login(String uid, String pword) throws Exception {	  
	// Console console = System.console();
     Connection conn = null;
     //System.out.println ("Connecting...");
     Class.forName ("com.mysql.jdbc.Driver"); 
     //System.out.println ("Driver class found and loaded.");   
     conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", uid, pword)		;
     //System.out.println ("connected.");  
     return conn;
   }
}	
