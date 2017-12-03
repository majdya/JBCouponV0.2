package SQL;

public class CompanySQLStatements {

	
	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company table by id. SQL statement is:
	 * 
	 * <pre>
	 * SELECT * FROM company WHERE ID = ?
	 * </pre>
	 * 
	 * where ? - is id of selected company.
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COMPANY_BY_ID = "SELECT * FROM Company WHERE ID = %d";

	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company table by name. SQL statement is:
	 * 
	 * <pre>
	 * SELECT * FROM company WHERE COMP_NAME = ?
	 * </pre>
	 * 
	 * where ? - is id of selected company.
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COMPANY_BY_NAME = "SELECT * FROM company WHERE comp_name = ?";

	
	/**
	 * The string that represents prepared SQL statement to apply changes to
	 * already exists company in company table. SQL statement is:
	 * 
	 * <pre>
	 * UPDATE company SET ID = ?, COMP_NAME = ?, PASSWORD = ?, EMAIL = ?
	 * </pre>
	 * 
	 * where first ? - is the id of current company, second ? - is the name of
	 * current company, third ? - is the password of current company, forth ? -
	 * is the email of current company.
	 * 
	 * @return String represents SQL UPDATE statement.
	 */
	public static String UPDATE_COMPANY1="update company set password = ?, email  = ? where ID = ?";

	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company table. SQL statement is:
	 * 
	 * <pre>
	 * SELECT * FROM company
	 * </pre>
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_ALL_COMPANIES = "SELECT * FROM company";

	/**
	 * The string that represents prepared SQL statement to insert Company
	 * object into the company table. SQL statement is:
	 * 
	 * <pre>
	 * INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES (?, ?, ?)
	 * </pre>
	 * 
	 * where first ? - is the name of current Company object, second ? - is the
	 * password of current Company object, third ? - is the email of current
	 * Company object.
	 * 
	 * @return String represents SQL INSERT statement.
	 */
	public static String INSERT_INTO_COMPANY = "INSERT INTO company (COMP_NAME, PASSWORD, EMAIL) VALUES ( ?, ?, ?)";

	/**
	 * The string that represents prepared SQL statement to delete current
	 * company from Company table. SQL statement is:
	 * 
	 * <pre>
	 * DELETE FROM company WHERE ID = ?
	 * </pre>
	 * 
	 * where ? is ID of current company.
	 * 
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_COMPANY_BY_NAME = "DELETE FROM company WHERE ID = ?";
	
	
	public static String SELECT_COUPON_BY_ID = "SELECT * FROM coupon WHERE id = ?";
	public static String SELECT_FROM_COMPANY_COUPON_BY_ID = "SELECT * FROM company_coupon WHERE comp_id = ?";
	
	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company table. SQL statement is:
	 * 
	 * <pre>
	 * SELECT [specified columns] FROM company [WHERE specified scope]
	 * </pre>
	 * 
	 * @param ColumnsToSelect
	 *            String represents specified columns (one or more) to select.
	 *            If not null - modifies current prepared SQL statement. For
	 *            example:
	 * 
	 *            <pre>
	 * SELECT ID, COMP_NAME FROM company
	 *            </pre>
	 * 
	 * @param whereCondition
	 *            String represents specified scope for selecting. If not null -
	 *            modifies current prepared SQL statement. For example:
	 * 
	 *            <pre>
	 * SELECT * FROM company WHERE ID = ?
	 *            </pre>
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COMPANY(String ColumnsToSelect, String whereCondition) {
		String sql = "SELECT * FROM company";
		if (ColumnsToSelect != null && ColumnsToSelect.length() > 1)
			sql = "SELECT " + ColumnsToSelect + " FROM company";
		if (whereCondition != null && whereCondition.length() > 1)
			sql = sql + " WHERE " + whereCondition;
		return sql;
	}


	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company table by name and password. SQL statement is:
	 * 
	 * <pre>
	 * SELECT ID, EMAIL FROM company WHERE COMP_NAME = ? AND PASSWORD = ?
	 * </pre>
	 * 
	 * where first ? - is the name of selected company and second ? - is
	 * password of the selected company.
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COMPANY_BY_NAME_AND_PASSWORD = "SELECT ID, EMAIL FROM company WHERE COMP_NAME = ? AND PASSWORD = ?";
	

	/**
	 * The string that represents prepared SQL statement to select company from
	 * Company_Coupon table by company's id. SQL statement is:
	 * 
	 * <pre>
	 * SELECT * FROM company_coupon WHERE COMP_ID = ?
	 * </pre>
	 * 
	 * where ? - is the id of selected company.
	 * 
	 * @return String represents SQL SELECT statement.
	 */
	public static String SELECT_COMPANY_FROM_COMPANY_COUPON() {
		return "SELECT * FROM company_coupon WHERE COMP_ID = ?";
	}


	/**
	 * The string that represents prepared SQL statement to insert id of company
	 * and its coupon into the company_coupon table. SQL statement is:
	 * 
	 * <pre>
	 * INSERT INTO company_coupon (COMP_ID, COUPON_ID) VALUES (?, ?)
	 * </pre>
	 * 
	 * where first ? - is the id of the company, second ? - is the id of the
	 * coupon.
	 * 
	 * @return String represents SQL INSERT statement.
	 */
	public static String INSERT_INTO_COMPANY_COUPON = "INSERT INTO company_coupon (COMP_ID, COUPON_ID) VALUES (?, ?)";
	


	/**
	 * The string that represents prepared SQL statement to apply changes to
	 * already exists company in company table. SQL statement is:
	 * 
	 * <pre>
	 * UPDATE company SET PASSWORD = ?, EMAIL = ? WHERE ID = ?
	 * </pre>
	 * 
	 * where first ? - is the password of current company, second ? - is the
	 * email of current company, third ? - is the id of current company.
	 * 
	 * @return String represents SQL UPDATE statement.
	 */
	public static String UPDATE_COMPANY() {
		return "UPDATE company SET PASSWORD = ?, EMAIL = ? WHERE ID = ?";
	}


	

	/**
	 * The string that represents prepared SQL statement to delete current
	 * company and its coupon from Company_Coupon table by company's id. SQL
	 * statement is:
	 * 
	 * <pre>
	 * DELETE FROM company_coupon WHERE COMP_ID = ?
	 * </pre>
	 * 
	 * where ? is ID of current company.
	 * 
	 * @return String represents SQL DELETE statement.
	 */
	public static String DELETE_COMPANY_COUPON_BY_ID = "DELETE FROM company_coupon WHERE COMP_ID = ?";
	

}