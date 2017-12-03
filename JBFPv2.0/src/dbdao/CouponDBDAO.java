package dbdao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import SQL.CouponSQLStatements;
import dao.CouponDAO;
import exceptions.CouponException;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import main.ConnectionPool;



public class CouponDBDAO implements CouponDAO{
	
	private ConnectionPool pool;
	public CouponDBDAO(){
	try {
		this.pool = ConnectionPool.getInstance();
	} catch (SQLException | ClassNotFoundException e) {
		System.out.println("No Connection to DB");
		e.printStackTrace();
		} 
	}
	
	/**CREATE COUPON 
	 * takes in a Coupon Object & and the Company Object 
	 * crates a new coupon registered under the company id 
	 * in COUPNAY_COUPON
	 * 
	 * @return Coupon
	 * 
	 */
	
	@Override
	public Coupon createCoupon(Coupon coupon, Company company) {
	
	Connection connection = null;
	PreparedStatement cheackTitle, addToComp_coup = null;
	ResultSet titleRs,id = null;
	PreparedStatement createCoup = null;
	
	
	try{
		connection = pool.getConnection();
		cheackTitle = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_TITLE);
		
		cheackTitle.setString(1, coupon.getTitle());
		titleRs = cheackTitle.executeQuery();
		
		if (titleRs.next()){
			throw new CouponException("Title already exists");
		}
		
		createCoup = connection.prepareStatement(CouponSQLStatements.INSERT_INTO_COUPON,Statement.RETURN_GENERATED_KEYS);
		createCoup.setString(1, coupon.getTitle());
		java.sql.Date startDate= new java.sql.Date(new java.util.Date().getTime());
		createCoup.setDate(2, startDate);
		java.sql.Date endDate = new java.sql.Date(coupon.getEndDate().getTime());
		createCoup.setDate(3, endDate);		
		createCoup.setInt(4, coupon.getAmount());
		createCoup.setInt(5, coupon.getType().ordinal());
		createCoup.setString(6, coupon.getMessage());
		createCoup.setDouble(7, coupon.getPrice());
		createCoup.setString(8, coupon.getImage());
		createCoup.execute();
		
		System.out.println("Coupon Added");
		
		id = createCoup.getGeneratedKeys();
		if(id.next()){
			coupon.setId(id.getLong(1));
			System.out.println(" ID = "+coupon.getId()+"\n Title :" + coupon.getTitle() );
		}
		addToComp_coup = connection.prepareStatement(CouponSQLStatements.INSERT_INTO_COMPANY_COUPON);
		addToComp_coup.setLong(1,  company.getId());
		addToComp_coup.setLong(2,  coupon.getId());
		addToComp_coup.executeUpdate();
			System.out.println("Coupon_added to comp_coup as well");
		
		
	}catch(SQLException e){
		System.out.println(e.getSQLState());
		System.out.println(e.getMessage());
	}finally{
		if(connection != null){
			pool.returnConnection(connection);
		}
	}
	return coupon;
	
		
	}
	
	/**REMOVE COUPON
	 * takes in a coupon
	 * checks to see if coupon exists
	 * if it does deleting it from all tables
	 * (including joined ones)
	 *
	 * @void 
	 */
	
	@Override
	public void removeCoupon(Coupon coupon) {
		
		Connection connection = null;
		PreparedStatement checkCoupon, removeCoupon;
		ResultSet couponRs = null;
		try{
		connection = pool.getConnection();
		checkCoupon = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_ID);
		checkCoupon.setLong(1, coupon.getId());
		couponRs = checkCoupon.executeQuery();
		if(!couponRs.next()){
			throw new CouponException("Coupon Does not Exsits");
		}
		removeCoupon = connection.prepareStatement(CouponSQLStatements.DELETE_COUPON);
		removeCoupon.setLong(1, coupon.getId());
		removeCoupon.executeUpdate();
		//removeCoupon.executeUpdate("DELETE FROM coupon WHERE title = ?");
		removeCoupon = connection.prepareStatement(CouponSQLStatements.DELETE_FROM_COMPANY_COUPON);
		removeCoupon.setLong(1, coupon.getId());
		removeCoupon.executeUpdate();
		
		removeCoupon = connection.prepareStatement(CouponSQLStatements.DELETE_FROM_CUSTOMER_COUPON);
		removeCoupon.setLong(1, coupon.getId());
		removeCoupon.executeUpdate();
		System.out.println("Coupon Removed");
			
		}catch(SQLException e){
			e.printStackTrace();
			e.getMessage();
			
		}finally{
			if(connection != null){
				pool.returnConnection(connection);
			}
		}
		
		
	}
	
	/**UPDATE COUPON
	 * takes in a coupon 
	 * checks if coupon exists
	 * if it does updating end_date, price and amount by id
	 * @void  
	 */
	
	
	@Override
	public void updateCoupon(Coupon coupon) {
		
		Connection connection = null;
		PreparedStatement checkCoup = null; 
		ResultSet checkCoupRs = null;
		PreparedStatement updateCoup = null;
		try {
		connection = pool.getConnection();
		checkCoup = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_ID);
		checkCoup.setLong(1, coupon.getId());
		checkCoupRs = checkCoup.executeQuery();
		if(!checkCoupRs.next()){
			throw new CouponException("No such coupon Exsits");
		}
		updateCoup = connection.prepareStatement(CouponSQLStatements.UPDATE_COUPON);
		Date endDate = new Date(coupon.getEndDate().getTime());
		updateCoup.setDate(1, endDate);
		updateCoup.setDouble(2, coupon.getPrice());
		updateCoup.setInt(3, coupon.getAmount());
		updateCoup.setLong(4,  coupon.getId());
		updateCoup.executeUpdate();
		System.out.println("Update Successful"); 
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			
		}finally{
				if(connection != null){
				pool.returnConnection(connection);
			}
		}
		
		
	}
	
	/**GET COUPON
	 * take in long ID
	 * @return Coupon 
	 * 
	 */
	
	@Override
	public Coupon getCoupon(long id) {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		
		try {
			connection = pool.getConnection();
			statement = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_ID);
			statement.setLong(1, id);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Coupon coupon = new Coupon();
				
				coupon.setId(id);
				coupon.setTitle(resultSet.getString(2));
				java.util.Date startDate = new java.util.Date(resultSet.getDate(3).getTime());
				coupon.setStartDate(startDate);
				java.util.Date endDate = new java.util.Date(resultSet.getDate(4).getTime());
				coupon.setEndDate(endDate);
				coupon.setAmount(resultSet.getInt(5));	
				
				String v = resultSet.getString(6);
				coupon.setType(CouponType.values()[resultSet.getString(6).indexOf(v)]);
				coupon.setMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));
				
				return coupon;
				
			}else{
				 throw new CouponException("coupon does no exists in the system");
			}
			
		} catch (SQLException e) {
			e.getSQLState();
			e.getMessage();
			
		}finally{
			if(connection != null){
				pool.returnConnection(connection);
			}
		}
		return null;
	}
	
	
	/**GET ALL COUPON
	 * returns an ArrayList of all the system coupons
	 * @return ArrayList<Coupon>
	 */
	
	@Override
	public Collection<Coupon> getAllCoupon() {
		
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		ArrayList<Coupon> allcoupons = new ArrayList<Coupon>();
		
		try {
	
			connection = pool.getConnection();
			statement = connection.prepareStatement(CouponSQLStatements.SELECT_ALL_COUPON);
			resultSet = statement.executeQuery();
		
			while(resultSet.next()){
				
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setTitle(resultSet.getString(2));
				java.util.Date startDate = new java.util.Date(resultSet.getDate(3).getTime());
				coupon.setStartDate(startDate);
				java.util.Date endDate = new java.util.Date(resultSet.getDate(4).getTime());
				coupon.setEndDate(endDate);
				coupon.setAmount(resultSet.getInt(5));
				coupon.setType(CouponType.values()[resultSet.findColumn("TYPE")]);
				coupon.setMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));
				
				allcoupons.add(coupon);
			}
			return allcoupons;
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			
		}finally{
			
			if(connection != null){
				pool.returnConnection(connection);
			}
		}
		return null;
	}
	
	/**GET ALL COUPON BY TYPE
	 * takes in a couponType
	 * returns an ArrayList with all the coupons from that type 
	 * @return ArrayList<Coupon>
	 */
	
	@Override
	public Collection<Coupon> getCouponByType(CouponType type) {
		
		Connection connection = null;
		PreparedStatement statement, exist = null;
		ResultSet resultSet, existRs = null;
		ArrayList<Coupon> allCouponsByType = new ArrayList<Coupon>();

		try {
	
			connection = pool.getConnection();
			exist = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_TYPE);
			exist.setInt(1, type.ordinal());
			existRs = exist.executeQuery();
			if(!existRs.next()){
				
				throw new CouponException("No coupons of that type Exists in DB");
			}
			statement = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_TYPE);
			statement.setInt(1, type.ordinal());
			resultSet = statement.executeQuery();
			
			while(resultSet.next()){
				
				Coupon coupon = new Coupon();
				coupon.setId(resultSet.getInt(1));
				coupon.setTitle(resultSet.getString(2));
				java.util.Date startDate = new java.util.Date(resultSet.getDate(3).getTime());
				coupon.setStartDate(startDate);
				java.util.Date endDate = new java.util.Date(resultSet.getDate(4).getTime());
				coupon.setEndDate(endDate);
				coupon.setAmount(resultSet.getInt(5));
				coupon.setType(CouponType.values()[resultSet.getInt(6)]);
				coupon.setMessage(resultSet.getString(7));
				coupon.setPrice(resultSet.getDouble(8));
				coupon.setImage(resultSet.getString(9));
				
				allCouponsByType.add(coupon);
			}
			return allCouponsByType;
			
		} catch (SQLException e) {
			e.printStackTrace();
			e.getMessage();
			e.getErrorCode();
			
		}finally{
			
			if(connection != null){
				pool.returnConnection(connection);
			}
		}
		return null;
	}
	
	//not needed
	
	@Override
	public void update_Coupon_price_id(Coupon coupon) {
		Connection connection = null;
		PreparedStatement existsCheck = null; 
		ResultSet existsAnswer = null;
		PreparedStatement update = null;
		
		try{
			connection = pool.getConnection();
			
			existsCheck = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_BY_ID);
			existsCheck.setLong(1, coupon.getId());
			existsAnswer = existsCheck.executeQuery();
			if(!existsAnswer.next()){
				throw new CouponException("Coupon does not exists in DB");
			}
			update = connection.prepareStatement(CouponSQLStatements.UPDATE_COUPON);
			Date endDate = new Date(coupon.getEndDate().getTime());
			update.setDate(1, endDate);
			update.setDouble(2, coupon.getPrice());
			update.setInt(3, coupon.getAmount());
			update.setLong(4, coupon.getId());
			System.out.println("Update Successful");
			
		}catch(SQLException e){
			e.getSQLState();
			e.getMessage();
		}finally {
			
			if(connection != null){
				pool.returnConnection(connection);
			}
			
		}
		
	}
	
	
	/**REMOVE THE COUPON FROM THE COUTOMER JOINT TABLE
	 * take in a coupon
	 * @void
	 */
	
	@Override
	public void removeCoupon_from_Customer_Coupon(Coupon coupon) {
		
		Connection connection = null;
		PreparedStatement existCheck, remover  = null;
		ResultSet resultSet = null;
		
		try {
			connection = pool.getConnection();
			existCheck = connection.prepareStatement(CouponSQLStatements.SELECT_COUPON_FROM_CUSTOMER_COUPON);
			existCheck.setLong(1,coupon.getId());
			resultSet = existCheck.executeQuery();
			if(!resultSet.next()){
				throw new CouponException("Coupon Does not exist");
			}
			remover = connection.prepareStatement(CouponSQLStatements.DELETE_FROM_CUSTOMER_COUPON);
			remover.setLong(1,coupon.getId());
			remover.executeUpdate();
		} catch (SQLException e) {
			e.getSQLState();
			e.getMessage();
		}finally {
			if(connection != null){
				pool.returnConnection(connection);
			}
		}
		
	}		

}