package main;

import java.util.Collection;
import java.util.Date;

import dbdao.CouponDBDAO;
import javaBeans.Coupon;

public class ExpiredCouponsCleaner extends Thread {

	private boolean isRunning = false;
	CouponDBDAO couponDBDAO;

	protected ExpiredCouponsCleaner() {
		couponDBDAO = new CouponDBDAO();
	}

	@Override
	public void run() {

		System.out.println("Daily Expired Coupons Cleaner Initialized");
		Collection<Coupon> allSystemCoupons = couponDBDAO.getAllCoupon();

		
		try {
			while (isRunning == true && !allSystemCoupons.isEmpty()) {
				for (Coupon coupon : allSystemCoupons) {
					if (coupon.getEndDate().getTime() < new Date().getTime()) {
						
						couponDBDAO.removeCoupon(coupon);
						
						System.out.println(coupon);
					}
					System.out.println("Daily Expired Coupons Cleaner Done");

				}
			}
					Thread.sleep(86400000);
				
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		
		}
		}
		

	public void stopTask() {
		isRunning = false;
		Thread.currentThread().interrupt();
	}
}