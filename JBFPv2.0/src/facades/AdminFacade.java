package facades;

import java.util.Collection;

import dao.CompanyDAO;
import dao.CouponDAO;
import dao.CustomerDAO;
import dbdao.CompanyDBDAO;
import dbdao.CouponDBDAO;
import dbdao.CustomerDBDAO;
import exceptions.CompanyException;
import exceptions.CustomerException;
import javaBeans.Company;
import javaBeans.Coupon;
import javaBeans.Customer;
import main.CouponClientFacade;

public class AdminFacade implements CouponClientFacade, facades.CouponClientFacade {

	private CompanyDAO companyDBDAO;
	private CustomerDAO customerDBDAO;
	private CouponDAO couponDBDAO;
	
	private boolean loggedIn;
	
	
	public AdminFacade(String password,String username){
		
		if (username.equals("admin")&& password.equals("1234")){
			loggedIn = true;
			companyDBDAO = new CompanyDBDAO();
			customerDBDAO = new CustomerDBDAO();
			couponDBDAO = new CouponDBDAO();
		}	
	}
	
	public boolean isLogged(){
		return loggedIn;
	}
	
	
	public Company createCompany(Company company) throws CompanyException {
		return companyDBDAO.createCompany(company);
	}
	
	public void removeCompany (Company company) throws CompanyException{
		if(company != null){
		companyDBDAO.removeCompany(company);
		for (Coupon coupon : company.getCoupons()) {
			couponDBDAO.removeCoupon(coupon);
			}
		}
	}
	
	public void updateCompany (Company company) throws CompanyException{
		companyDBDAO.updateCompany(company);
	}
	
	public Collection<Company> getAllCompanies() throws CompanyException {
		return companyDBDAO.getAllCompanies();
	}
	
	public Company getCompnay(long id) throws CompanyException {
		return companyDBDAO.getCompany(id);
	}
	
	public Customer createCustomer(Customer customer) throws CustomerException {
		return customerDBDAO.createCustomer(customer);
	}
	
	public void removeCustomer(Customer customer) throws CustomerException {
		customerDBDAO.removeCustomer(customer);
	}
	
	public void updateCustomer(Customer customer) throws CustomerException {
		customerDBDAO.updateCustomer(customer);
	}
	
	public Collection<Customer> getAllCustomers() throws CustomerException {
		return customerDBDAO.getAllCustomer();
	}
	
	public Customer getCustomer(long id) throws CustomerException {
		return customerDBDAO.getCustomer(id);
	}
	

}