package utils;

import java.time.Instant;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CustomerFacade;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;
import main.CouponSystem;

public class Test {

	// build TABLES
	public static void createTabels() {
		Create.companyTable();
		Create.customerTable();
		Create.couponTable();
		Create.companyCouponTable();
		Create.customerCouponTable();

	}

	// Fill the database with companies
	public static void fillCompDb(AdminFacade adminFacade) {
		try {

			for (int i = 0; i < 10; i++) {
				Company company = new Company();
				String companyName = "Company" + i;
				String companyPassword = "" + i + i + i + i;
				company.setCompName(companyName);
				company.setEmail(companyName + "@support.com");
				company.setPassword(companyPassword);
				adminFacade.createCompany(company);
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void duplicateCompTest(AdminFacade adminFacade) {
		System.out.println("trying to create the a Company with the same name twice");

		try {
			Company company = new Company();
			company.setCompName("Company1");
			adminFacade.createCompany(company);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void removeComp(AdminFacade adminFacade) {
		System.out.println("Removing company by geting compnay");

		try {
			adminFacade.removeCompany(adminFacade.getCompnay(9));
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void updateComp(AdminFacade adminFacade) {
		System.out.println("updating Company Details");

		try {
			Company company = adminFacade.getCompnay(1);
			company.setEmail("dsfxx7@support.com");
			company.setPassword("767xxxx2");
			adminFacade.updateCompany(company);
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void getAllComps(AdminFacade adminFacade) {
		System.out.println("getting all companies ");

		try {

			System.out.println(adminFacade.getAllCompanies());

		} catch (Exception e) {
			e.getMessage();
		}

		System.out.println("getting the company we just updated");

	}

	public static void getCompByID(AdminFacade adminFacade) {
		try {

			System.out.println(adminFacade.getCompnay(2));

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void createCust(AdminFacade adminFacade) {
		System.out.println("creating some customers");

		try {

			for (int i = 1; i < 31; i++) {
				Customer customer = new Customer();
				String customerName = "customer" + i;
				customer.setCustName(customerName);
				customer.setPassword("1234");
				adminFacade.createCustomer(customer);
			}

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void removeCust(AdminFacade adminFacade) {
		System.out.println("getting all customers and removing 5 of them");

		try {
			for (Customer c : adminFacade.getAllCustomers()) {
				if (c.getId() >= 10 && c.getId() <= 15) {
					adminFacade.removeCustomer(c);
				}
			}
		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void getAllCust(AdminFacade adminFacade) {
		System.out.println("geting all customers now");

		try {

			System.out.println(adminFacade.getAllCustomers());

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void updateCust(AdminFacade adminFacade) {
		try {

			System.out.println("upadting customer details and getting customer");
			Customer customer = adminFacade.getCustomer(9);
			customer.setCustName("zzZZZZzze");
			customer.setPassword("TESTRRRrrRR");
			adminFacade.updateCustomer(customer);
			System.out.println(adminFacade.getCustomer(9));

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static CompanyFacade companyLogin(CouponSystem couponSystem, AdminFacade adminFacade) {
		System.out.println("loging in to Company facade");
		Company company = adminFacade.getCompnay(1);
		CompanyFacade companyFacade = (CompanyFacade) couponSystem.login(company.getCompName(), company.getPassword(),
				ClientType.company);
		return companyFacade;
	}

	public static void createCoupons(AdminFacade adminFacade, CompanyFacade companyFacade) {
		System.out.println("Creating a few coupons\n");
		try {
			for (int i = 0; i < 10; i++) {
				Coupon coupon = new Coupon();
				coupon.setTitle("Food coupon1" + i);
				coupon.setMessage("Food coupon1 " + i);
				coupon.setAmount(i * i);
				coupon.setPrice(i * 100);
				coupon.setStartDate(new Date(Calendar.DAY_OF_MONTH - i));
				Date startDate = new Date();
				coupon.setEndDate(startDate);
				Date endDate = new Date();
				coupon.setEndDate(endDate);
				coupon.setType(CouponType.FOOD);
				coupon.setImage("link");
				companyFacade.createCoupon(coupon);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public static void updateCoupon(AdminFacade adminFacade, CompanyFacade companyFacade) {
		System.out.println("Updateing a Coupon");
		Coupon coupon = companyFacade.getCoupon(7);

		coupon.setPrice(800.00);

		Calendar c = Calendar.getInstance();
		c.setTime(new Date()); // Now use today date.
		c.add(Calendar.DATE, 80); // Adds 15 days
		coupon.setEndDate(c.getTime());
		try {
			companyFacade.updateCoupon(coupon);

		} catch (Exception e) {
			e.getMessage();
		}
	}

	public static void getCoupon(CompanyFacade companyFacade) {
		System.out.println("Getting coupon");
		System.out.println(companyFacade.getCoupon(7));
	}

	public static void getAllcoupons(CompanyFacade companyFacade) {
		System.out.println("Getting all the company coupons");

		System.out.println(companyFacade.getAllCoupons());
	}


	public static void prucherCoupon(CustomerFacade customerFacade, CompanyFacade companyFacade) {
		customerFacade.purchaseCoupon(companyFacade.getCoupon(9));
	}

	
	
	
	
	public static Collection<Coupon> getAllPurchasedCoupons(CustomerFacade customerFacade, Customer customer) {
		return customerFacade.getAllPurchasedCoupons(customer);
	}
}
