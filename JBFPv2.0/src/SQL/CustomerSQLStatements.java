package SQL;

public class CustomerSQLStatements {

	public static final String SELECT_CUSTOMER_BY_USER_PASS = null;

	public static final String SELECT_CUSTOMER_BY_NAME_AND_PASS = "select * from customer where cust_name =? and password=?";

	public static final String SELECT_COUPON_BY_ID = "select * from coupon where CUST_ID = ?";

	/**
	 * The string that represents prepared SQL statement to select customer from Customer table.
	 * SQL statement is: <pre>SELECT * FROM customer</pre>
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_CUSTOMER_BY_NAME ="SELECT * FROM customer WHERE cust_name = ? ";
	
	public static String SELECT_CUSTOMER_BY_ID ="SELECT * FROM customer WHERE ID = ? ";
	

	
	/**
	 * The string that represents prepared SQL statement to select customer 
	 * from Customer_Coupon table by customer's id.
	 * SQL statement is: <pre>SELECT * FROM customer_coupon WHERE CUST_ID = ?</pre> 
	 * where ? - is the id of selected customer.
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_CUSTOMER_FROM_CUSTOMER_COUPON = "SELECT * FROM customer_coupon WHERE CUST_ID = ?";
	
	public static String SELECT_FROM_CUSTOMER_COUPON = "SELECT * FROM customer_coupon WHERE CUST_ID = ? AND COUPON_ID = ?";
	
	/**
	 * The string that represents prepared SQL statement to insert Customer object into the customer table.
	 * SQL statement is: <pre>INSERT INTO customer (CUST_NAME, PASSWORD) VALUES (?, ?)</pre> 
	 * where first ? - is the name of current Customer object,
	 * second ? - is the password of current Customer object.
	 * @return String represents SQL INSERT statement.
	 */
	public static String INSERT_INTO_CUSTOMER = "INSERT INTO customer (CUST_NAME, PASSWORD) VALUES (?, ?)";

	/**
	 * The string that represents prepared SQL statement to insert id of customer and its coupon 
	 * into the customer_coupon table.
	 * SQL statement is: <pre>INSERT INTO customer_coupon (CUST_ID, COUPON_ID) VALUES (?, ?)</pre> 
	 * where first ? - is the id of the customer, second ? - is the id of the coupon.
	 * @return String represents SQL INSERT statement.
	 */
	public static String INSERT_INTO_CUSTOMER_COUPON = "INSERT INTO customer_coupon (CUST_ID, COUPON_ID) VALUES (?, ?)";
	
	public static String SELECT_ALL_FROM_CUSTOMER_COUPON_BY_ID ="SELECT * FROM customer_coupon WHERE CUST_ID = ?";
	/**
	 * The string that represents prepared SQL statement to apply changes
	 * to already exists customer in customer table.
	 * SQL statement is: <pre>UPDATE customer SET PASSWORD = ? WHERE ID = ?</pre> 
	 * where first ? - is the password of current customer, second ? - is the id of current customer.
	 * @return String represents SQL UPDATE statement.
	 */
	public static String UPDATE_CUSTOMER = "UPDATE customer SET CUST_NAME = ?, PASSWORD = ? WHERE ID = ?";
	
	public static String SELECT_ALL_CUSTOMERS = "SELECT * FROM customer";
	/**
	 * The string that represents prepared SQL statement to delete current customer 
	 * from customer table by customer's id.
	 * SQL statement is: <pre>DELETE FROM customer WHERE ID = ?</pre> 
	 * where ? - is the id of current customer.
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_CUSTOMER_COUPON_BY_ID = "delete from customer_coupon where cust_id = ?";
	
	/**
	 * The string that represents prepared SQL statement to delete current customer 
	 * from customer table by customer's id.
	 * SQL statement is: <pre>DELETE FROM customer WHERE ID = ?</pre> 
	 * where ? - is the id of current customer.
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_CUSTOMER_BY_NAME = "DELETE FROM customer WHERE CUST_NAME = ?";
	
	/*
	*//**
	 * The string that represents prepared SQL statement to select customer from Customer table.
	 * SQL statement is: <pre>SELECT [specified columns] FROM customer [WHERE specified scope]</pre>
	 * @param selectWhat String represents specified columns (one or more) to select.
	 * If not null - modifies current prepared SQL statement. For example:
	 * <pre>SELECT ID, CUST_NAME FROM customer</pre>
	 * @param selectFrom String represents specified scope for selecting. If not null - 
	 * modifies current prepared SQL statement. For example:
	 * <pre>SELECT * FROM customer WHERE ID = ?</pre>
	 * @return String represents SQL SELECT statement.
	 *//*
	public static String SELECT_CUSTOMER(String selectWhat, String selectFrom) {
		String sql = "SELECT * FROM customer";
		if (selectWhat != null && !" ".equals(selectWhat) && selectWhat.length() > 1) 
			sql = "SELECT " + selectWhat + " FROM customer";
		if (selectFrom != null) sql = sql + " WHERE " + selectFrom;
		return sql;
	}
	*//**
	 * The string that represents prepared SQL statement to delete current customer and its coupon 
	 * from Customer_Coupon table by customer's id.
	 * SQL statement is: <pre>DELETE FROM customer_coupon WHERE CUST_ID = ?</pre> 
	 * where ? - is the id of current customer.
	 * @return String represents SQL DELETE statement.
	 *//*
	public static String DELETE_FROM_CUSTOMER_COUPON_BY_CUST() {
		return "DELETE FROM customer_coupon WHERE CUST_ID = ?";
	}
	
	*//**
	 * The string that represents prepared SQL statement to delete current customer and its coupon 
	 * from Customer_Coupon table by id of customer's coupon.
	 * SQL statement is: <pre>DELETE FROM customer_coupon WHERE COUPON_ID = ?</pre> 
	 * where ? - is the id of current customer.
	 * @return String represents SQL DELETE statement.
	 *//*
	public static String DELETE_FROM_CUSTOMER_COUPON_BY_COUPON() {
		return "DELETE FROM customer_coupon WHERE COUPON_ID = ?";
	}
*/
}