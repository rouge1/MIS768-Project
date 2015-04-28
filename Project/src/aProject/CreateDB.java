package aProject;

import java.sql.*;

/**
 * The CreateDB class stores the methods on how to connect to MySQL, create the aProjectDB database,
 * create the caseLocations and login tables, and populate the login table with login data for three 
 * users for Group #2's term group programming project.
 * @author Group #2
 * @version 1.0
 */

public class CreateDB {

	public static void DB() {
		// Create a named constant for the URL.
		// NOTE: This value is specific for MySQL.
		
		final String DB_URL = "jdbc:mysql://localhost:3306/";
	    final String DB_GROUP_URL = "jdbc:mysql://localhost:3306/aProjectDB";
	    final String USERNAME = "root";
	    final String PASSWORD = "";

	    try {
	         // Create a connection to the database.
	         Connection conn =
	                DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
	            
	         
	         	// Create the database. If the database already exists, drop it. 
	            createDataBase(conn);
	            conn.close();
	            
	         //Create a connection to the database and to the aProject database   
	         Connection conn2 =
	        		 DriverManager.getConnection(DB_GROUP_URL, USERNAME, PASSWORD);
				
				// Build the caseLocations table
				buildCaseLocationsTable(conn2);
				
				// Build the login table
				buildLoginTable(conn2);
				
	         // Close the connection.
	         conn2.close();
	      }
	      catch (Exception ex) {
	         System.out.println("ERROR: " + ex.getMessage());
	      }
	   }
	
		/**
		 * The createDatabase method creates the DB. If the DB already exists, drop the DB first.
		 * @param conn Connection. Connection to the MySQL database.
		 */
	
	   public static void createDataBase(Connection conn) {
		   System.out.println("Checking for existing database.");
		   
		   try{
			   Statement stmt = conn.createStatement();
			   
			   //Drop the existing database
			   try {
				   stmt.executeUpdate("Drop DATABASE aProjectDB");
			   }
			   catch(SQLException ex) {
					// No need to report an error.
					// The database simply did not exist.
				}
			   //Create a new database
			   try {
				   stmt.execute("Create DATABASE aProjectDB");
				   //stmt.execute("USE coffee");
				   System.out.println("Database aProject created.");
			   }
			   catch(SQLException ex) {
					// No need to report an error.
					// The database simply did not exist.
				}			   
		   }
	  	   catch(SQLException ex) {
	  		   System.out.println("ERROR: " + ex.getMessage());
	  		   ex.printStackTrace();
			}
	   } 
	   
	   /**
	    * The buildCaseLocationsTable method creates the caseLocations table.
	    * @param conn. Connection to the MySQL database.
	    */

	   public static void buildCaseLocationsTable(Connection conn){
			try {
	         // Get a Statement object.
	         Statement stmt = conn.createStatement();
	         
	         String query;
	         
				// Create the table.
				stmt.execute("CREATE TABLE caseLocations (" +
							"caseID MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
	   				       "caseNumber CHAR(25), " +
	                      "caseType CHAR(10) NOT NULL, " +
	                      "caseDate DATE NOT NULL, " +
	                      "caseLat CHAR(10) NOT NULL, " +
	                      "caseLong CHAR(10) NOT NULL, " +
	                      "caseElev CHAR(10) NOT NULL " +
	                      ")");
								 								 
				System.out.println("Table caseLocations created.");
				
			}
			catch (SQLException ex) {
	         System.out.println("ERROR: " + ex.getMessage());
			}
		}

		/**
		 * The buildLoginTable method creates the Login table and adds some rows to it.
		 * @param conn. Connection to the MySQL database.
		 */
		public static void buildLoginTable(Connection conn){
	      try {
	         // Get a Statement object.
	         Statement stmt = conn.createStatement();
	         
	         // Create the table.
	         stmt.execute("CREATE TABLE login (" +
	            "userID MEDIUMINT NOT NULL PRIMARY KEY AUTO_INCREMENT, " +
	            "userName CHAR(10) NOT NULL, " +
	            "password CHAR(25) NOT NULL, "  +
	            "userType CHAR(5) NOT NULL" +
	            ")");
	         
	         // INSERT login row 1
	         stmt.execute("INSERT INTO login " +
	        		 "(userName, password, userType) " +
	        		 "VALUES ( " +
                     "'Andrea', " +
                     "'test', " +
                     "'ADMIN' )");
	         
	         // INSERT login row 2
	         stmt.execute("INSERT INTO login " +
	        		 "(userName, password, userType) " +
	        		 "VALUES ( " +
                     "'Daniel', " +
                     "'test2', " +
                     "'USER' )");

	         // INSERT login row 3
	         stmt.execute("INSERT INTO login " +
	        		 "(userName, password, userType) " +
	        		 "VALUES ( " +
                     "'Simon', " +
                     "'test3', " +
                     "'ADMIN' )");

	         
	         System.out.println("Table login created.");
			}
			catch (SQLException ex) {
	         System.out.println("ERROR: " + ex.getMessage());
			}
		}


}


