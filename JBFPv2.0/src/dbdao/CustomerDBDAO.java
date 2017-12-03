package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import SQL.CompanySQLStatements;
import SQL.CustomerSQLStatements;
import dao.CustomerDAO;
import exceptions.CompanyException;
import exceptions.CustomerException;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;
import main.ConnectionPool;

public class CustomerDBDAO implements CustomerDAO {

	private ConnectionPool pool;

	public CustomerDBDAO() {
		try {
			this.pool = ConnectionPool.getInstance();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("No Connection to DB");
			e.printStackTrace();
		}
	}

	/**
	 * CREATE CUSTOMER take in customer adds to system
	 * 
	 * @return Customer
	 * 
	 */

	@Override
	public Customer createCustomer(Customer customer) {
		Connection connection = null;
		ResultSet nameChecker, id = null;
		PreparedStatement add = null;

		try {
			connection = pool.getConnection();

			// Checking to see if the Customer already exists
			PreparedStatement statement = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_BY_NAME);
			statement.setString(1, customer.getCustName());
			nameChecker = statement.executeQuery();

			if (nameChecker.next()) {
				throw new CustomerException("Customer already exists in the DB");
			} else {

				// Creates the company in the database
				String sql = CustomerSQLStatements.INSERT_INTO_CUSTOMER;
				add = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				add.setString(1, customer.getCustName());
				add.setString(2, customer.getPassword());
				add.execute();
				System.out.println("Customer added");

				id = add.getGeneratedKeys();
				if (id.next()) {
					customer.setId(id.getLong(1));
					System.out.println(" ID = " + customer.getId() + "\n Name :" + customer.getCustName());
				}
				return customer;
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());

		} finally {
			if (connection != null)
				pool.returnConnection(connection);
		}
		return null;

	}

	/**
	 * REMOVE CUSTOMER take in customer removes it from the system
	 * 
	 * @void
	 */

	@Override
	public void removeCustomer(Customer customer) {

		Connection connection = null;
		ResultSet nameChecker = null;

		try {
			connection = pool.getConnection();
			// Checking to see if the company already exists
			PreparedStatement statement = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_BY_NAME);
			statement.setString(1, customer.getCustName());
			nameChecker = statement.executeQuery();

			if (!nameChecker.next()) {
				throw new CustomerException("Customer Dose Not exists in the DB");
			}

			// Remove all existing Customer information from the database
			statement = connection.prepareStatement(CustomerSQLStatements.DELETE_CUSTOMER_BY_NAME);
			statement.setString(1, customer.getCustName());
			statement.executeUpdate();

			// Removes all Customer coupons from DB
			statement = connection.prepareStatement(CustomerSQLStatements.DELETE_CUSTOMER_COUPON_BY_ID);
			statement.setLong(1, customer.getId());
			statement.executeUpdate();
			System.out.println("The customer named = " + customer.getCustName() + " was deleted from the system ");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			if (connection != null)
				pool.returnConnection(connection);
		}

	}

	/**
	 * UPDATE CUSTOMER takes in a customer updates all customer details except
	 * name
	 * 
	 * @void
	 */

	@Override
	public void updateCustomer(Customer customer) {

		Connection connection = null;
		PreparedStatement  exist, removeCustCoup, addCustCoup, updates = null;
		ResultSet existRs = null;

		try {

			connection = pool.getConnection();
			exist = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_BY_ID);
			exist.setFloat(1, customer.getId());
			existRs = exist.executeQuery();

			if (existRs.next()) {
				removeCustCoup = connection.prepareStatement(CustomerSQLStatements.DELETE_CUSTOMER_COUPON_BY_ID);
				removeCustCoup.setFloat(1,customer.getId() );
				removeCustCoup.executeUpdate();
				
				addCustCoup = connection.prepareStatement(CustomerSQLStatements.INSERT_INTO_CUSTOMER_COUPON);
				for (Coupon coupon : customer.getCoupons()) {
					addCustCoup.setFloat(1, customer.getId());
					addCustCoup.setFloat(2, coupon.getId());
					addCustCoup.executeUpdate();
				}
				updates = connection.prepareStatement(CustomerSQLStatements.UPDATE_CUSTOMER);
				updates.setString(1, customer.getCustName());
				updates.setString(2, customer.getPassword());
				updates.setLong(3, customer.getId());
				updates.executeUpdate();
				
				System.out.println("The Customer " + customer.getCustName() + " Updated");
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());

		} finally {
			if (connection != null) {
				pool.returnConnection(connection);
			}

		}

	}

	/**
	 * GET CUSTOMER take in long ID
	 * 
	 * @return Customer
	 */

	@Override
	public Customer getCustomer(long id) {

		Connection connection=null;
		PreparedStatement statement,getCust;
		ResultSet resultSet, couponRs = null;
		Customer customer = null;
		ArrayList<Coupon> coupons = new ArrayList<>();
		try {
			connection = pool.getConnection();
			getCust = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_BY_ID);
			getCust.setLong(1, id);
			resultSet = getCust.executeQuery();
			if (resultSet.next()) {
				customer = new Customer();
				customer.setId(resultSet.getLong("ID"));
				customer.setCustName(resultSet.getString("CUST_NAME"));
				customer.setPassword(resultSet.getString("PASSWORD"));

				CouponDBDAO couponDB = new CouponDBDAO();
				
				statement = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_FROM_CUSTOMER_COUPON);
				statement.setLong(1, customer.getId());
				couponRs = statement.executeQuery();
				while (couponRs.next()) {
					coupons.add(couponDB.getCoupon(couponRs.getLong(2)));
				}
				customer.setCoupons(coupons);
				return customer;
			}

		} catch (SQLException e) {
			System.out.println(e.getSQLState());
		} finally {
			if (connection != null) {
				pool.returnConnection(connection);
			}

		}
		return null;
	}

	/**
	 * GET ALL CUSTOMERS self-explanatory
	 * 
	 * @return HashSet<Customer>
	 */

	@Override
	public Collection<Customer> getAllCustomer() {

		Connection connection = null;
		PreparedStatement statement, getCoupons = null;
		ResultSet resultSet, couponRs = null;
		HashSet<Customer> allCustomers = new HashSet<Customer>();
		ArrayList<Coupon> customerCoupons = new ArrayList<Coupon>();
		CouponDBDAO couponDB = new CouponDBDAO();

		try {
			connection = pool.getConnection();
			
			
			statement = connection.prepareStatement(CustomerSQLStatements.SELECT_ALL_CUSTOMERS);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Customer customer = new Customer();
				customer.setId(resultSet.getLong(1));
				customer.setCustName(resultSet.getString(2));
				customer.setPassword(resultSet.getString(3));

				getCoupons = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_FROM_CUSTOMER_COUPON);
				getCoupons.setFloat(1, customer.getId());
				couponRs = getCoupons.executeQuery();

				while (couponRs.next()) {
					customerCoupons.add(couponDB.getCoupon(couponRs.getLong(2)));
				}
				allCustomers.add(customer);
			}

		} catch (SQLException e) {

			e.getSQLState();
			e.getMessage();

		} finally {

			if (connection != null) {
				pool.returnConnection(connection);
			}
		}
		return allCustomers;
	}

	/**
	 * GET COUPONS takes in a customer uses a HashSet because a customer can't
	 * buy a coupon more then once
	 * @param Customer 
	 * @return HashSet<Coupon>
	 */

	@Override
	public Collection<Coupon> getCoupons(Customer customer) {

		Connection connection = null;
		PreparedStatement getAllCustomerCoup, getCoupons = null;
		ResultSet resultSet, couponsRs = null;
		HashSet<Coupon> allCustomerCoupons = new HashSet<Coupon>();

		try {
			
			connection = pool.getConnection();
			getAllCustomerCoup = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_FROM_CUSTOMER_COUPON);
			getAllCustomerCoup.setLong(1, customer.getId());
			resultSet = getAllCustomerCoup.executeQuery();
			while (resultSet.next()) {
				//System.out.println("1");
				getCoupons = connection.prepareStatement(CustomerSQLStatements.SELECT_ALL_FROM_CUSTOMER_COUPON_BY_ID);
				getCoupons.setLong(1, customer.getId());
				couponsRs = getCoupons.executeQuery();
				//System.out.println("2");
				while (couponsRs.next()) {
					
					Coupon coupon = new Coupon();
					coupon.setId(resultSet.getInt(1));
					System.out.println("!");
					coupon.setTitle(couponsRs.getString("1"));
					
					System.out.println(couponsRs);
					
					java.util.Date startDate = new java.util.Date(couponsRs.getDate(3).getTime());
					
					coupon.setStartDate(startDate);
					java.util.Date endDate = new java.util.Date(couponsRs.getDate(4).getTime());
					coupon.setEndDate(endDate);
					coupon.setAmount(couponsRs.getInt(5));
					coupon.setType(CouponType.values()[couponsRs.getInt(6)]);
					coupon.setMessage(couponsRs.getString(7));
					coupon.setPrice(couponsRs.getDouble(8));
					coupon.setImage(couponsRs.getString(9));
					
					allCustomerCoupons.add(coupon);
				}
			}   

		} catch (SQLException e) {
			e.getMessage();
			e.getSQLState();
		} finally {
			if (connection != null) {
				pool.returnConnection(connection);
			}
		}
		return allCustomerCoupons;
	}

	/**
	 * LOGIN CUSTOMER same same but different
	 * 
	 * @return customer
	 */
	@Override
	public Customer Login(String customerName, String password) {

		Connection connection = null;
		PreparedStatement checkCustomer = null;
		ResultSet checkCustomerRs = null;

		try {
			connection = pool.getConnection();

			checkCustomer = connection.prepareStatement(CustomerSQLStatements.SELECT_CUSTOMER_BY_NAME_AND_PASS);
			checkCustomer.setString(1, customerName);
			checkCustomer.setString(2, password);
			checkCustomerRs = checkCustomer.executeQuery();

			if (checkCustomerRs.next()) {

				return getCustomer(checkCustomerRs.getLong("ID"));

			} else {
				System.out.println("Incorrect username or password");
				return null;
			}

		} catch (SQLException e) {
			e.getMessage();

		} finally {
			if (connection != null) {
				pool.returnConnection(connection);
			}

		}
		return null;

	}

	public void updateCustomerCoupon(Customer customer,Coupon coupon)
	{
		Connection connection = null;
		ResultSet checker;
		try {
			connection = pool.getConnection();

			// Checking to see if the Customer already exists
			PreparedStatement statement = connection.prepareStatement(CustomerSQLStatements.SELECT_FROM_CUSTOMER_COUPON);
			statement.setLong(1, customer.getId());
			statement.setLong(2, coupon.getId());
			checker = statement.executeQuery();
			if(!checker.next())
			{
				statement = connection.prepareStatement(CustomerSQLStatements.INSERT_INTO_CUSTOMER_COUPON);
				statement.setLong(1, customer.getId());
				statement.setLong(2, coupon.getId());
				statement.executeUpdate();
			}

			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				if (connection != null)
					pool.returnConnection(connection);
			}

		} 
	}