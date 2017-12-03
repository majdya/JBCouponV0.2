package SQL;

public class CouponSQLStatements {

	/**
	 * The string that represents prepared SQL statement to select coupon from Coupon table.
	 * SQL statement is: <pre>SELECT * FROM coupon</pre>
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_ALL_COUPON = "SELECT * FROM coupon";
	
	/**
	 * The string that represents prepared SQL statement to select coupon from Coupon table by specified id.
	 * SQL statement is: <pre>SELECT * FROM coupon WHERE ID = ?</pre> where ? - is id of selected coupon.
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COUPON_BY_ID = "SELECT * FROM coupon WHERE ID = ?";
	
	public static String SELECT_COUPON_BY_TYPE = "SELECT * FROM coupon WHERE type = ?";
	
	public static String SELECT_COUPON_BY_TITLE = "SELECT * FROM coupon WHERE title = ?";
	/**
	 * The string that represents prepared SQL statement to select coupon 
	 * from Company_Coupon table by specified coupon's id.
	 * SQL statement is: <pre>SELECT * FROM company_coupon WHERE COUPON_ID = ?</pre> 
	 * where ? - is the id of selected coupon.
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COUPON_FROM_COMPANY_COUPON() {
		return "SELECT * FROM company_coupon WHERE COUPON_ID = ?";
	}
	
	/**
	 * The string that represents prepared SQL statement to select coupon 
	 * from Customer_Coupon table by specified coupon's id.
	 * SQL statement is: <pre>SELECT * FROM customer_coupon WHERE COUPON_ID = ?</pre> 
	 * where ? - is the id of selected coupon.
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COUPON_FROM_CUSTOMER_COUPON = "SELECT * FROM customer_coupon WHERE COUPON_ID = ?";
	
	
	/**
	 * The string that represents prepared SQL statement to insert Coupon object into the coupon table.
	 * SQL statement is: <pre>INSERT INTO coupon 
	 * (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) 
	 * VALUES (?, ?, ?, ?, ?, ?, ?, ?)</pre> 
	 * where first ? - is the title of current Coupon object,
	 * second ? - is the date when current Coupon object was created,
	 * third ? - is the date when current Coupon object will expire,
	 * forth ? - is the amount of current Coupon object in the stock,
	 * fifth ? - is the type of current Coupon object,
	 * sixth ? - is the message of current Coupon object,
	 * seventh ? - is the price of the current Coupon object,
	 * eighth ? - is the image of current Coupon object.
	 * @return String represents SQL INSERT statement.
	 */
	public static String INSERT_INTO_COUPON = "INSERT INTO coupon (TITLE, START_DATE, END_DATE, AMOUNT, TYPE, MESSAGE, PRICE, IMAGE) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	
	public static String INSERT_INTO_COMPANY_COUPON = "INSERT INTO company_coupon(comp_id, coupon_id) values(?,?)";
	/**
	 * The string that represents prepared SQL statement to apply changes
	 * to already exists coupon in coupon table by id.
	 * SQL statement is: <pre>UPDATE coupon SET END_DATE = ?, PRICE = ? WHERE ID = ?</pre> 
	 * where first ? - is the date when current coupon will expire,
	 * second ? - is the price of the current coupon,
	 * third ? - is id of the current coupon.
	 * @return String represents SQL UPDATE statement.
	 */
	public static String UPDATE_COUPON = "UPDATE coupon SET END_DATE = ?, PRICE = ?, amount=? WHERE ID = ?";

	
	/**
	 * The string that represents prepared SQL statement to delete current coupon from Coupon table by id.
	 * SQL statement is: <pre>DELETE FROM coupon WHERE ID = ?</pre> where ? is ID of current company.
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_COUPON = "DELETE FROM coupon WHERE ID = ?";
	
	
	/**
	 * The string that represents prepared SQL statement to delete current coupon and its company 
	 * from Company_Coupon table by coupon's id.
	 * SQL statement is: <pre>DELETE FROM company_coupon WHERE COUPON_ID = ?</pre> 
	 * where ? is ID of current company.
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_FROM_COMPANY_COUPON = "DELETE FROM company_coupon WHERE COUPON_ID = ?";
	
	
	/**
	 * The string that represents prepared SQL statement to delete current coupon and its customer 
	 * from Customer_Coupon table by coupon's id.
	 * SQL statement is: <pre>DELETE FROM customer_coupon WHERE COUPON_ID = ?</pre> 
	 * where ? is ID of current company.
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_FROM_CUSTOMER_COUPON = "DELETE FROM customer_coupon WHERE COUPON_ID = ?";
	
}