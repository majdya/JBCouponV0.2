package facades;

import java.util.ArrayList;
import java.util.Collection;

import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.CouponException;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import main.CouponClientFacade;

public class CompanyFacade implements CouponClientFacade, facades.CouponClientFacade {

	private Company company;
	private CompanyDAO companyDBDAO;
	private CustomerDAO customerDBDAO;
	private CouponDAO couponDBDAO;

	private boolean loggedIn;

	public CompanyFacade(String username, String password) {

		Company theCompany = new CompanyDBDAO().login(username, password);
		{
			if (theCompany != null) {
				company = theCompany;
				loggedIn = true;
				companyDBDAO = new CompanyDBDAO();
				customerDBDAO = new CustomerDBDAO();
				couponDBDAO = new CouponDBDAO();
			}
		}
	}

	public boolean isLogged() {
		return loggedIn;
	}

	public void createCoupon(Coupon coupon) throws CouponException {
		Coupon theCoupon = couponDBDAO.createCoupon(coupon, company);
		if (theCoupon != null) {
			company.getCoupons().add(theCoupon);
		}
	}

	public void removeCoupon(Coupon coupon) throws CouponException {
		couponDBDAO.removeCoupon(coupon);

	}

	public void updateCoupon(Coupon coupon) throws CouponException {
		couponDBDAO.updateCoupon(coupon);
	}

	public Coupon getCoupon(long id) throws CouponException {
		return couponDBDAO.getCoupon(id);
	}

	public Collection<Coupon> getAllCoupons() throws CouponException {
		return couponDBDAO.getAllCoupon();
	}

	public Collection<Coupon> getCouponByType(CouponType type) throws CouponException {

		ArrayList<Coupon> couponsByType = new ArrayList<>();
		for (Coupon coupon : companyDBDAO.getCoupons(company)) {
			if (coupon.getType().ordinal() == type.ordinal())
				;
			couponsByType.add(coupon);
		}

		return couponsByType;
	}

	public Collection<Coupon> getCouponByMaxPrice(double maxPrice) throws CouponException {
		ArrayList<Coupon> couponsByMaxPrice = new ArrayList<>();
		for (Coupon coupon : companyDBDAO.getCoupons(company)) {
			if (coupon.getPrice() < maxPrice)
				;
			couponsByMaxPrice.add(coupon);
		}
		return couponsByMaxPrice;

	}

	public Collection<Coupon> getCouponByMinPrice(double minPrice) throws CouponException {
		ArrayList<Coupon> couponsByMinPrice = new ArrayList<>();
		for (Coupon coupon : companyDBDAO.getCoupons(company)) {
			if (coupon.getPrice() > minPrice)
				;
			couponsByMinPrice.add(coupon);
		}
		return couponsByMinPrice;

	}

	public Collection<Coupon> getCouponByPrice(double minPrice, double maxPrice) throws CouponException {
		ArrayList<Coupon> couponsByPrice = new ArrayList<>();
		for (Coupon coupon : companyDBDAO.getCoupons(company)) {
			if (coupon.getPrice() > minPrice && coupon.getPrice() < maxPrice)
				;
			couponsByPrice.add(coupon);
		}
		return couponsByPrice;

	}

	// TODO
	public Collection<Coupon> getCouponByDate() throws CouponException {
		ArrayList<Coupon> CouponsByDate = new ArrayList<>();
		// TODO
		return couponDBDAO.getAllCoupon();

	}
	

}