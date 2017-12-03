package utils;

//STEP 1. Import required packages
import java.sql.*;

public class Create {

	// JDBC driver name and database URL
		static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
		static final String DB_URL = "jdbc:mysql://localhost:3306/projectv0.2?autoReconnect=true&useSSL=false";
		// Database credentials
		static final String USER = "root";
		static final String PASS = "root";

	public static void schema() {
		Connection conn = null;
		try {
			
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);
			String DB_URL1 = "jdbc:mysql://localhost:3306/?user=root?autoReconnect=true&useSSL=false";
			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL1, USER, PASS);
			System.out.println("Connected database successfully...");

			//DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet = conn.getMetaData().getCatalogs();
			
			// Table exists?
			if (!resultSet.next()){
			// Creating a database schema
			Statement sta = conn.createStatement();
			sta.executeUpdate("CREATE SCHEMA project");
			System.out.println("Schema created.");
			sta.close();
			conn.close();
			}
			System.out.println("The Schema project already exisist in the DB");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void companyTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// checking if the table exist in the DB
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "Company", null);
			// Table exists?
			if (!resultSet.next())

			{
				// STEP 4: Execute a query
				System.out.println("Creating Company table in given database...");
				stmt = conn.createStatement();

				String sql = "CREATE TABLE Company " + "(ID INTEGER not NULL AUTO_INCREMENT, " + " COMP_NAME VARCHAR(255) not NULL, "
						+ " PASSWORD VARCHAR(255) not NULL, "+ " EMAIL VARCHAR(255) not NULL, " + " PRIMARY KEY ( ID ))";

				stmt.executeUpdate(sql);
				System.out.println("Created Company table in given database...");
			} else
				System.out.println("The Comapny  table already exisist in the DB");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}

	public static void customerTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// checking if the table exist in the DB
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "Customer", null);
			// Table exists
			if (!resultSet.next()) {
				// STEP 4: Execute a query
				System.out.println("Creating Customer table in given database...");
				stmt = conn.createStatement();

				String sql = "CREATE TABLE Customer " 
						+ "(ID INTEGER not NULL AUTO_INCREMENT, " 
						+ " CUST_NAME VARCHAR(255) not NULL, "
						+ " PASSWORD VARCHAR(255) not NULL, " 
						+ " PRIMARY KEY ( ID ))";

				stmt.executeUpdate(sql);
				System.out.println("Created Customer table in given database...");
			} else
				System.out.println("Customer table already exist in the DB");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public static void couponTable() 
	{
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// checking if the table exist in the DB
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "Coupon", null);
			// Table exists
			if (!resultSet.next()) {
				// STEP 4: Execute a query
				System.out.println("Creating table in given database...");
				stmt = conn.createStatement();

				String sql = "CREATE TABLE Coupon " 
						+ "(ID INTEGER not NULL AUTO_INCREMENT, "
						+ " TITLE VARCHAR(255) not NULL, "
						+ " START_DATE DATE not NULL, " 
						+ " END_DATE DATE not NULL, " + " AMOUNT INTEGER not NULL, "
						+ " TYPE ENUM('Restaurants','Electricity','Food','Health','Sports','Camping','Travel') not NULL, " 
						+ " MESSAGE VARCHAR(255) not NULL, "
						+ " PRICE  FLOAT not NULL, " 
						+ " IMAGE  VARCHAR(255) not NULL, " 
						+ " PRIMARY KEY ( ID ))";

				stmt.executeUpdate(sql);
				System.out.println("Created table in given database...");
			} else
				System.out.println("Coupon Table already exisit in the DB ");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try
	}

	public static void customerCouponTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// checking if the table exist in the DB
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "Customer_Coupon", null);
			// Table exists?
			if (!resultSet.next())

			{
				// STEP 4: Execute a query
				System.out.println("Creating Customer_Coupon table in given database...");
				stmt = conn.createStatement();

				String sql = "CREATE TABLE Customer_Coupon(CUST_ID INTEGER,COUPON_ID INTEGER, PRIMARY KEY (CUST_ID,COUPON_ID))";

				stmt.executeUpdate(sql);
				System.out.println("Created Customer_Coupon table in given database...");
			} else
				System.out.println("The Customer_Coupon  table already exisist in the DB");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}

	public static void companyCouponTable() {
		Connection conn = null;
		Statement stmt = null;
		try {
			// STEP 2: Register JDBC driver
			Class.forName(JDBC_DRIVER);

			// STEP 3: Open a connection
			System.out.println("Connecting to a selected database...");
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("Connected database successfully...");

			// checking if the table exist in the DB
			DatabaseMetaData metadata = conn.getMetaData();
			ResultSet resultSet;
			resultSet = metadata.getTables(null, null, "Company_Coupon", null);
			// Table exists?
			if (!resultSet.next())

			{
				// STEP 4: Execute a query
				System.out.println("Creating Company_Coupon table in given database...");
				stmt = conn.createStatement();

				String sql = "CREATE TABLE Company_Coupon(COMP_ID INTEGER,COUPON_ID INTEGER, PRIMARY KEY (COMP_ID,COUPON_ID))";

				stmt.executeUpdate(sql);
				System.out.println("Created Company_Coupon table in given database...");
			} else
				System.out.println("The Company_Coupon  table already exisist in the DB");
		} catch (SQLException se) {
			// Handle errors for JDBC
			se.printStackTrace();
		} catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		} finally {
			// finally block used to close resources
			try {
				if (stmt != null)
					conn.close();
			} catch (SQLException se) {
			} // do nothing
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally try
		} // end try

	}
}

