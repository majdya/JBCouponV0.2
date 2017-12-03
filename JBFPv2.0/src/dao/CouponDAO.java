package dao;

import java.util.Collection;

import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.CouponType;

public interface CouponDAO {
	
	 public Coupon createCoupon(Coupon coupon,Company company); 
	 public void removeCoupon(Coupon coupon);
	 public void updateCoupon(Coupon coupon);
	 public Coupon getCoupon(long id);
	 public Collection<Coupon> getAllCoupon();
	 public Collection<Coupon> getCouponByType(CouponType type);
	 public void update_Coupon_price_id(Coupon coupon);
	 public void removeCoupon_from_Customer_Coupon(Coupon coupon);

}