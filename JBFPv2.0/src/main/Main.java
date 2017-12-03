package main;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.print.attribute.standard.Copies;

import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.CompanyException;
import facades.AdminFacade;
import facades.ClientType;
import facades.CompanyFacade;
import facades.CustomerFacade;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;
import javaBeans.Customer;
import utils.Create;
import utils.Test;

public class Main {
/*
 *@author Majd Yaqoub 
 *John Bryce coupon system project ! 
 * This is not the last visrion i will be adding so many changes
 * to this project and upload it as JBCouponsV1  
 * */
	public static void main(String[] args) {

		//Test.createTabels();

		System.out.println("STARTING SYSTEM");

		CouponSystem couponSystem = CouponSystem.getInstance();

		// when couponSystem is starting a Thread will start the
		// ExpirdCouponCleaner task that will do just that
		// but there is no coupons yet
		System.out.println("loging to admin facade");

		//Admin Join
		AdminFacade adminFacade = (AdminFacade) couponSystem.login("admin", "1234", ClientType.admin);

/*
		//Fill Companies 
		Test.fillCompDb(adminFacade);
		 //Test.fillCompDb(adminFacade); 
		Test.duplicateCompTest(adminFacade);
		 //Test Remove company function 
		Test.removeComp(adminFacade);  
		 // Test Update Company 
		Test.updateComp(adminFacade);
		// Test Get All Companies
		Test.getAllComps(adminFacade);
		// Test GetCustomer By ID
		Test.getCompByID(adminFacade);
		//Test Create Cusomer 
		Test.createCust(adminFacade); 
		//Test Delete Customer 
		Test.removeCust(adminFacade); 
		//Test Get All Customers
		 Test.getAllCust(adminFacade);
		//Test Update Customer 
		 Test.updateCust(adminFacade);
		*/
			
		 //Test Company Login
		CompanyFacade companyFacade = Test.companyLogin(couponSystem, adminFacade);
		/*
		//Testing creation of coupons
		Test.createCoupons(adminFacade, companyFacade);
		//Testing the update of the coupon
		Test.updateCoupon(adminFacade, companyFacade);  
		//Testing get Coupon By ID
		Test.getCoupon(companyFacade);
		//Testing get All Coupons By ID
		Test.getAllcoupons(companyFacade);
		*/
		
		//Test.getCoupon(companyFacade);
		
		
		System.out.println("Logging in to customer facade");

		Customer customer = adminFacade.getCustomer(2);
		

		CustomerFacade customerFacade = (CustomerFacade) couponSystem.login(customer.getCustName(),
				customer.getPassword(), ClientType.customer);

		//Test Prucher A Coupon
		Test.prucherCoupon(customerFacade, companyFacade);


		System.out.println(customerFacade.getAllPurchasedCoupons(customer).size());
		

		
	}

}