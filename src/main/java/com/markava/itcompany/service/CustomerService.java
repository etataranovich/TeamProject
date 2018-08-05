package com.markava.itcompany.service;

import java.util.List;

import com.markava.itcompany.jdbc.AddressJDBC;
import com.markava.itcompany.jdbc.CustomerJDBC;
import com.markava.itcompany.model.Customer;
import com.markava.itcompany.model.Office;

public class CustomerService {
	
	private AddressJDBC adrjdbc;
	private CustomerJDBC cusjdbc;
		
	
	public void insertCustomr(Customer cus, int idcompany){
		adrjdbc.insert(cus.getAddress());
		cusjdbc.insert(cus,idcompany);
	}

	public List<Customer> getCustomer(int idcompany){
		cusjdbc = new CustomerJDBC();
		List<Customer> cus = cusjdbc.getCustomerByITId(idcompany);
		adrjdbc = new AddressJDBC();
		for(Customer customer:cus){
			int customerid = customer.getId();
			customer.setAddress(adrjdbc.getAddressByCustomer(customerid));
		}
		return cus;
	}
	
	public void updateCustomer(Customer cus){
		cusjdbc.update(cus);
		adrjdbc.update(cus.getAddress());
	}
	public void deleteCustomer(int idcustomer, int idaddress){
		cusjdbc.delete(idcustomer);
		adrjdbc.delete(idaddress);
	}
	
}	
