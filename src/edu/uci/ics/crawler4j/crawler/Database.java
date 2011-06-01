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
			conn.setAutoCommit(false);
			conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		
		} catch (Exception ex) {
			System.err.println("In database constructor: " + ex);
		}
		
	}
	
	
	/**
	 * Adds a department to the database
	 * @param NAME
	 */
	public void addDepartment(int ID, String NAME, String ABBREV, String URL) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.DEPARTMENT (ID, NAME, ABBREV, URL) values (?, ?, ?, ?)");
			pstmt.setInt(1, ID);
			pstmt.setString(2, NAME);
			pstmt.setString(3, ABBREV);
			pstmt.setString(4, URL);
			
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
	public void addInstructor(int ID, String TEACHERURL, int SOCIAL, String POLYRATING, String FIRSTNAME, String LASTNAME, String USERNAME, int DEPARTMENT) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.INSTRUCTOR (ID, TEACHERURL, SOCIAL, POLYRATING, FIRSTNAME, LASTNAME, USERNAME, DEPARTMENT) values (?, ?, ?, ?, ?, ?, ?, ?)");
			pstmt.setLong(1, ID);
			pstmt.setString(2, TEACHERURL);
			pstmt.setLong(3, SOCIAL);
			pstmt.setString(4, POLYRATING);
			pstmt.setString(5, FIRSTNAME);
			pstmt.setString(6, LASTNAME);
			pstmt.setString(7, USERNAME);
			pstmt.setLong(8, DEPARTMENT);
			
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
	 * 
	 * @param ID
	 * @param TITLE
	 * @param LINK
	 */
	public void addPublication(int ID, String TITLE, String LINK) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.PUBLICATION (ID, TITLE, LINK) values (?, ?, ?)");
			pstmt.setLong(1, ID);
			pstmt.setString(2, TITLE);
			pstmt.setString(3, LINK);
			
			if(pstmt.executeUpdate() != 1) {
				throw new Exception();	
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void addOffice(int ID, int BUILDING, int ROOM) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.OFFICE (ID, BUILDING, ROOM) values (?, ?, ?)");
			pstmt.setInt(1, ID);
			pstmt.setInt(2, BUILDING);
			pstmt.setInt(3, ROOM);
			
			
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
	 * Adds a class to the database. Call commit to commit the change.
	 * @param professor
	 * @param classname
	 */
	public void addClass(int ID, String DESCRIPTION, String NAME, int CLASSNUMBER, int INSTRUCTOR) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.CLASS (ID, DESCRIPTION, NAME, CLASSNUMBER, INSTRUCTOR) values (?, ?, ?, ?, ?)");
			pstmt.setInt(1, ID);
			pstmt.setString(2, DESCRIPTION);
			pstmt.setString(3, NAME);
			pstmt.setLong(4, CLASSNUMBER);
			pstmt.setLong(5, INSTRUCTOR);
			
			
			if(pstmt.executeUpdate() != 1) {
				throw new Exception();	
			}
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void addWrote(int ID, int INSTRUCTOR, int PUBLICATION) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.WROTE (ID, INSTRUCTOR, PUBLICATION) values (?, ?, ?)");
			pstmt.setInt(1, ID);
			pstmt.setInt(2, INSTRUCTOR);
			pstmt.setInt(3, PUBLICATION);
			
			
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
	 * Relationship between instructors and offices
	 *
	 */
	public void addLocated(int ID, int INSTRUCTOR, int OFFICE) {
		PreparedStatement pstmt = null;
		
		try {
			pstmt = conn.prepareStatement ("Insert into crawler.LOCATED (ID, INSTRUCTOR, OFFICE) values (?, ?, ?)");
			pstmt.setInt(1, ID);
			pstmt.setInt(2, INSTRUCTOR);
			pstmt.setInt(3, OFFICE);
			
			
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
	 * Prints out the instructors
	 */
	public void printInstructors() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.INSTRUCTOR");
	
			
			rset = pstmt.executeQuery();
			
			System.out.println("The Instructor table now contains:");
			System.out.println("ID\tFIRST\tLAST\tUSERNAME");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("FIRSTNAME") + "\t");
				System.out.print(rset.getString("LASTNAME") + "\t");
				System.out.print(rset.getString("USERNAME") + "\t");
				System.out.println();
	
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printDepartments() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.DEPARTMENT");
	
			
			rset = pstmt.executeQuery();
			
			System.out.println("The Department table now contains:");
			System.out.println("ID\tNAME\tABBREV\tURL");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("NAME") + "\t");
				System.out.print(rset.getString("ABBREV") + "\t");
				System.out.print(rset.getString("URL") + "\t");
				System.out.println();
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
	
	public void printPublications() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.PUBLICATION");
	
			rset = pstmt.executeQuery();
			
			System.out.println("The Publication table now contains:");
			System.out.println("ID\tTITLE\tLINK");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("TITLE") + "\t");
				System.out.print(rset.getString("LINK") + "\t");
				System.out.println();
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printOffices() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.OFFICE");
	
			rset = pstmt.executeQuery();
			
			System.out.println("The Office table now contains:");
			System.out.println("ID\tBUILDING\tROOM");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("BUILDING") + "\t");
				System.out.print(rset.getString("ROOM") + "\t");
				System.out.println();
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printClasses() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.CLASS");
	
			rset = pstmt.executeQuery();
			
			System.out.println("The Class table now contains:");
			System.out.println("ID\tDESCRIPTION\tNAME\tCLASSNUMBER\tINSTRUCTOR");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("DESCRIPTION") + "\t");
				System.out.print(rset.getString("NAME") + "\t");
				System.out.print(rset.getString("CLASSNUMBER") + "\t");
				System.out.print(rset.getString("INSTRUCTOR") + "\t");
				System.out.println();
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printWrote() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.WROTE");
	
			rset = pstmt.executeQuery();
			
			System.out.println("The Wrote table now contains:");
			System.out.println("ID\tINSTRUCTOR\tPUBLICATION");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("INSTRUCTOR") + "\t");
				System.out.print(rset.getString("PUBLICATION") + "\t");
				System.out.println();
			}
			
			rset.close();
			pstmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void printLocated() {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		try {
			pstmt = conn.prepareStatement ("select * from crawler.LOCATED");
	
			rset = pstmt.executeQuery();
			
			System.out.println("The Located table now contains:");
			System.out.println("ID\tINSTRUCTOR\tOFFICE");
			
			while(rset.next()) {
				System.out.print(rset.getString("ID") + "\t");
				System.out.print(rset.getString("INSTRUCTOR") + "\t");
				System.out.print(rset.getString("OFFICE") + "\t");
				System.out.println();
			}
			
			rset.close();
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
