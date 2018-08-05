package com.markava.itcompany.dao;


import java.util.List;

import com.markava.itcompany.model.Customer;

public interface ICustomerDao extends GenericModelDao<Customer> {
	public List<Customer> getCustomerByITId(int id);
	public void insert(Customer customer, int idcompany);

}
