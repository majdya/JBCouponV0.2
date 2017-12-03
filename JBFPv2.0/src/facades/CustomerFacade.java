package facades;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;
import main.CouponClientFacade;

public class CustomerFacade implements CouponClientFacade, facades.CouponClientFacade {

	private Customer customer;
	// private CompanyDAO companyDBDAO;
	private CustomerDAO customerDBDAO;
	private CouponDAO couponDBDAO;

	private boolean loggedIn;

	public CustomerFacade(String customerName, String password) {

		Customer theCustomer = new CustomerDBDAO().Login(customerName, password);
		{
			if (theCustomer != null) {
				customer = theCustomer;
				loggedIn = true;
				// companyDBDAO = new CompanyDBDAO();
				customerDBDAO = new CustomerDBDAO();
				couponDBDAO = new CouponDBDAO();
			}
		}
	}

	public boolean isLogged() {
		return loggedIn;
	}

	public void purchaseCoupon(Coupon coupon) {

		if (coupon.getAmount() > 0) {
			if (coupon.getEndDate().after(new Date())) {
				for (Coupon c : customer.getCoupons()) {
					if (c.getId() == coupon.getId()) {
						System.out.println("coupon has already been purchased one by the customer");
						break;
					}
					coupon.setAmount(coupon.getAmount() - 1);
					couponDBDAO.updateCoupon(coupon);
					((CustomerDBDAO) customerDBDAO).updateCustomerCoupon(customer,coupon);
					System.out.println("purchase Completed");
				}

				

			}
		} else {
			System.out.println("out of stock");
		}

	}

	public Collection<Coupon> getAllPurchasedCoupons(Customer customer) {
		return customerDBDAO.getCoupons(customer);
	}

	public Collection<Coupon> getAllPurchasedCouponsByType(CouponType type) {
		ArrayList<Coupon> customerPerchasedCuponsByType = new ArrayList<>();
		for (Coupon coupon : customer.getCoupons()) {
			if (coupon.getType().ordinal() == type.ordinal()) {
				customerPerchasedCuponsByType.add(coupon);
			}
			if (customerPerchasedCuponsByType.isEmpty() == true) {
				System.out.println("No Coupons of that type has been purchased");
				return null;
			}
		}
		return customerPerchasedCuponsByType;
	}

	public Collection<Coupon> getAllPurchasedCouponsUpToPrice(double price) {

		ArrayList<Coupon> allCustomerUpToPrice = new ArrayList<Coupon>();
		for (Coupon coupon : customer.getCoupons()) {
			if (coupon.getPrice() < price)
				allCustomerUpToPrice.add(coupon);
		}
		if (allCustomerUpToPrice.isEmpty()) {
			System.out.println("the customer did not purchased coupons under the price of " + price);
			return null;
		}

		return allCustomerUpToPrice;

	}

}