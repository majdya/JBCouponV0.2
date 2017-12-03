package dbdao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import SQL.CompanySQLStatements;
import dao.CompanyDAO;
import exceptions.CompanyException;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import main.ConnectionPool;

public class CompanyDBDAO implements CompanyDAO {

	private ConnectionPool pool;

	public CompanyDBDAO() {
		try {
			this.pool = ConnectionPool.getInstance();
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("No Connection to DB");
			e.printStackTrace();
		}
	}

	/**
	 * CREATE COMPANY takes in a company Object and inserting it to the DB
	 * 
	 * @return Company Object
	 */

	@Override
	public Company createCompany(Company company) {
		Connection connection = null;
		ResultSet nameChecker = null;
		PreparedStatement add = null;
		ResultSet id = null;

		try {
			connection = pool.getConnection();
			// Checking to see if the company already exists
			PreparedStatement statement = connection.prepareStatement(CompanySQLStatements.SELECT_COMPANY_BY_NAME);
			statement.setString(1, company.getCompName());
			nameChecker = statement.executeQuery();

			if (nameChecker.next()) {
				throw new CompanyException("Company already exists in the DB");
			}

			// Creates the company in the database
			String sql = CompanySQLStatements.INSERT_INTO_COMPANY;
			add = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			add.setString(1, company.getCompName());
			add.setString(2, company.getPassword());
			add.setString(3, company.getEmail());
			add.execute();
			System.out.println("Company added");

			// assigning Auto Generated Keys to the company Object
			id = add.getGeneratedKeys();
			if (id.next()) {
				company.setId(id.getLong(1));
				System.out.println(" ID = " + company.getId() + "\n Name: " + company.getCompName() + "\n Email: "
						+ company.getEmail());
			}
			return company;
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
	 * REMOVE COMPANY removes all company information including the company
	 * coupons
	 * 
	 * @void
	 */

	@Override
	public void removeCompany(Company company) {

		Connection connection = null;
		ResultSet nameChecker = null;

		try {
			connection = pool.getConnection();
			// Checking to see if the company already exists
			PreparedStatement statement = connection.prepareStatement(CompanySQLStatements.SELECT_COMPANY_BY_NAME);
			statement.setString(1, company.getCompName());
			nameChecker = statement.executeQuery();

			if (!nameChecker.next()) {
				throw new CompanyException("Customer Dose Not exists in the DB");
			}

			// Remove all existing Company information from the database
			statement = connection.prepareStatement(CompanySQLStatements.DELETE_COMPANY_BY_NAME);
			statement.setString(1, company.getCompName());
			statement.executeUpdate();

			// Removes all Compnay coupons from DB
			statement = connection.prepareStatement(CompanySQLStatements.DELETE_COMPANY_COUPON_BY_ID);
			statement.setLong(1, company.getId());
			statement.executeUpdate();
			System.out.println("The company named = " + company.getCompName() + " was deleted from the system ");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			if (connection != null)
				pool.returnConnection(connection);
		}

	}

	/**
	 * UPDATE COMPANY updating company details dosn't updating the company name
	 * 
	 * @void
	 */

	@Override
	public void updateCompany(Company company) {

		Connection connection = null;
		PreparedStatement removeCompCoup, addCompCoup = null;
		PreparedStatement updateCompDetails = null;

		// updates company details
		// password and email

		try {
			connection = pool.getConnection();
			updateCompDetails = connection.prepareStatement(CompanySQLStatements.UPDATE_COMPANY1);
			updateCompDetails.setString(1, company.getPassword());
			updateCompDetails.setString(2, company.getEmail());
			updateCompDetails.setFloat(3, company.getId());
			updateCompDetails.executeUpdate();
			
			
			removeCompCoup = connection.prepareStatement(CompanySQLStatements.DELETE_COMPANY_COUPON_BY_ID);
			removeCompCoup.setLong(1, company.getId());
			removeCompCoup.executeUpdate();
			
			
			addCompCoup = connection.prepareStatement(CompanySQLStatements.INSERT_INTO_COMPANY_COUPON);
			for (Coupon coupon : company.getCoupons()) {
				addCompCoup.setFloat(1, company.getId());
				addCompCoup.setFloat(1, coupon.getId());
				addCompCoup.executeUpdate();
			}

			System.out.println("Mission Accomplished");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			if (connection != null)
				pool.returnConnection(connection);
		}

	}

	/**
	 * GET COMAPNY getting all company information takes in a long id
	 * 
	 * @return Company Object
	 */

	@Override
	public Company getCompany(long id) {
		Connection connection = null;
		Company company = new Company();

		Collection<Coupon> companyCoupons = new ArrayList<Coupon>();

		// gets the company by id
		// the function takes in a long id
		// and returns a company object matching the id

		try {
			connection = pool.getConnection();
			connection.createStatement();

			// Retrieves all the company's Information by id
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(String.format(CompanySQLStatements.SELECT_COMPANY_BY_ID, id));
			if (result.next()) {
				company = new Company();
				company.setId(result.getLong("ID"));
				company.setCompName(result.getString("COMP_NAME"));
				company.setPassword(result.getString("PASSWORD"));
				company.setEmail(result.getString("EMAIL"));

				// gets the company coupons
				PreparedStatement getCoupons = connection.prepareStatement(CompanySQLStatements.SELECT_FROM_COMPANY_COUPON_BY_ID);
				getCoupons.setFloat(1, company.getId());
				
				ResultSet getCouponsRs = getCoupons.executeQuery();
				while (getCouponsRs.next()) {
					CouponDBDAO couponDBDAO = new CouponDBDAO();
					companyCoupons.add(couponDBDAO.getCoupon(getCouponsRs.getLong(2)));
				}

				return company;

			} else {
				throw new CompanyException("Company id is not Valid");
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
	 * GET ALL COMPANIES gets all the companies in the system
	 * 
	 * @return HashSet<COMPANY> (HashSet of Companies will eliminate
	 *         Duplications)
	 */

	@Override
	public Collection<Company> getAllCompanies() {

		Connection connection = null;
		Company company = null;
		HashSet<Company> allCompanies = new HashSet<Company>();
		Collection<Coupon> allCompanyCoup = new ArrayList<Coupon>();
		try {
			connection = pool.getConnection();

			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(CompanySQLStatements.SELECT_ALL_COMPANIES);

			while (result.next()) {

				company = new Company();
				company.setId(result.getLong("ID"));
				company.setCompName(result.getString("comp_name"));
				company.setPassword(result.getString("password"));
				company.setEmail(result.getString("email"));

				PreparedStatement getCompanies = connection.prepareStatement(CompanySQLStatements.SELECT_FROM_COMPANY_COUPON_BY_ID);
				getCompanies.setFloat(1, company.getId());
				ResultSet getCompaneisRS = getCompanies.executeQuery(); 
				while (getCompaneisRS.next()) {
					CouponDBDAO couponDBDAO = new CouponDBDAO();
					allCompanyCoup.add(couponDBDAO.getCoupon(getCompaneisRS.getLong("COUPON_ID")));
				}
				company.setCoupons(allCompanyCoup);
				allCompanies.add(company);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getSQLState());
		} finally {
			if (connection != null)
				pool.returnConnection(connection);
		}
		return allCompanies;
	}

	/**
	 * GET COUPONS takes in a Company Object and returns the company coupons
	 * 
	 * @return HashSet<COUPONS>
	 */

	@Override
	public Collection<Coupon> getCoupons(Company company) {
		
		
		Connection connection = null;
		HashSet<Coupon> allCoupons = new HashSet<Coupon>();
		try {
			connection = pool.getConnection();

			PreparedStatement getCompanies = connection.prepareStatement(CompanySQLStatements.SELECT_FROM_COMPANY_COUPON_BY_ID);
			getCompanies.setFloat(1, company.getId());;
			ResultSet result = getCompanies.executeQuery(); 
			while (result.next()) {

				PreparedStatement getCoupon = connection.prepareStatement(CompanySQLStatements.SELECT_COUPON_BY_ID);
				getCoupon.setFloat(1, result.getLong("COUPON_ID"));;
				ResultSet couponRs = getCoupon.executeQuery(); 

				while (couponRs.next()) {
					Coupon coupon = new Coupon();
					coupon.setId(couponRs.getLong("ID"));
					coupon.setTitle(couponRs.getString("Title"));
					coupon.setStartDate(couponRs.getDate("Start_Date"));
					coupon.setEndDate(couponRs.getDate("End_Date"));
					coupon.setAmount(couponRs.getInt("Amount"));
					coupon.setMessage(couponRs.getString("Message"));
					coupon.setType(CouponType.values()[couponRs.getInt("Type")]);
					coupon.setPrice(couponRs.getDouble("Price"));
					coupon.setImage(couponRs.getString("Image"));
					allCoupons.add(coupon);
				}
				return allCoupons;
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
	 * LOGIN COMPANY checks to see if a company with this password and username
	 * exists in the system and returns it if one exists
	 * 
	 * @return company
	 */
	@Override
	public Company login(String compName, String password) {

		Connection connection = null;
		PreparedStatement checkCompany = null;
		ResultSet checkCompanyRs = null;

		try {
			connection = pool.getConnection();

			checkCompany = connection.prepareStatement(CompanySQLStatements.SELECT_COMPANY_BY_NAME_AND_PASSWORD);
			checkCompany.setString(1, compName);
			checkCompany.setString(2, password);
			checkCompanyRs = checkCompany.executeQuery();

			if (checkCompanyRs.next()) {
				return getCompany(checkCompanyRs.getLong("ID"));

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
}