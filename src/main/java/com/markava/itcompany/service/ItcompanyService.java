package com.markava.itcompany.service;

import java.util.ArrayList;
import java.util.List;

import com.markava.itcompany.jdbc.DeveloperJDBC;
import com.markava.itcompany.jdbc.EconomistJDBC;
import com.markava.itcompany.jdbc.ItcompanyJDBC;
import com.markava.itcompany.jdbc.OwnerJDBC;
import com.markava.itcompany.jdbc.SaleJDBC;
import com.markava.itcompany.model.Customer;
import com.markava.itcompany.model.Developer;
import com.markava.itcompany.model.Economist;
import com.markava.itcompany.model.Itcompany;
import com.markava.itcompany.model.Office;
import com.markava.itcompany.model.Sale;

public class ItcompanyService {

	private ItcompanyJDBC itjdbc;
	private OwnerJDBC ownjdbc;
	private DeveloperJDBC devjdbc;
	private EconomistJDBC ecojdbc;
	private SaleJDBC saljdbc;
	private CustomerService cusser;
	private OfficeService offser;
	
	public ItcompanyService() {
		itjdbc = new ItcompanyJDBC();
		devjdbc = new DeveloperJDBC();
		ecojdbc = new EconomistJDBC();
		saljdbc = new SaleJDBC();
		ownjdbc = new OwnerJDBC();
		offser = new OfficeService();
		cusser = new CustomerService();
	}
	

	public Itcompany getItcompany(int id) {
		
		Itcompany it = itjdbc.get(id);
		it.setDevelopers(devjdbc.getDeveloperByItId(id));
		it.setEconomists(ecojdbc.getEconomistByItId(id));
		it.setSales(saljdbc.getSaleByItId(id));
		it.setOwner(ownjdbc.getOwnerByItId(id));
		it.setOffices(offser.getOffice(id));
		it.setCustomers(cusser.getCustomer(id));
		return it;
	}

	public List<Itcompany> getAllItcompanies() {

		List<Itcompany> itcompanies = new ArrayList<>();
		itcompanies = itjdbc.get();
		for (Itcompany itcompany : itcompanies) {
			itcompany.setOwner(ownjdbc.getOwnerByItId(itcompany.getId()));
			itcompany.setOffices(offser.getOffice(itcompany.getId()));
			itcompany.setEconomists(ecojdbc.getEconomistByItId(itcompany.getId()));
			itcompany.setDevelopers(devjdbc.getDeveloperByItId(itcompany.getId()));
			itcompany.setSales(saljdbc.getSaleByItId(itcompany.getId()));
			itcompany.setCustomers(cusser.getCustomer(itcompany.getId()));
		}
		return itcompanies;
	}

	public void insertItcompany(Itcompany itcompany) {
		itjdbc = new ItcompanyJDBC();
		itjdbc.insert(itcompany);
		List<Integer> itid = new ArrayList<>();
		ownjdbc.insert(itcompany.getOwner(), itid);
		for (Developer dev : itcompany.getDevelopers()) {
			devjdbc.insert(dev, itid);
		}
		for (Economist econ : itcompany.getEconomists()) {
			ecojdbc.insert(econ, itid);
		}
		for (Sale sale : itcompany.getSales()) {
			saljdbc.insert(sale, itid);
		}
		for (Office off : itcompany.getOffices()) {
			offser.insertOffice(off, itid);
		}
		for (Customer customer : itcompany.getCustomers()) {
			cusser.insertCustomr(customer, itcompany.getId());
		}
	}
	public void updateItcompany(Itcompany itcompany) {
		itjdbc.update(itcompany);
		List<Integer> itid = new ArrayList<>();
		ownjdbc.update(itcompany.getOwner());
		for (Developer dev : itcompany.getDevelopers()) {
			devjdbc.update(dev);
		}
		for (Economist econ : itcompany.getEconomists()) {
			ecojdbc.update(econ);
		}

		for (Sale sale : itcompany.getSales()) {
			saljdbc.update(sale);
		}
		for (Office off : itcompany.getOffices()) {
			offser.updateOffice(off);
		}
		for (Customer customer : itcompany.getCustomers()) {
			cusser.updateCustomer(customer);
		}
	}
	public void deleteItcompany(Itcompany itcompany) {
		itjdbc.delete(itcompany.getId());
		ownjdbc.delete(itcompany.getOwner().getId());
		for (Developer dev : itcompany.getDevelopers()) {
			devjdbc.delete(dev.getId());
		}
		for (Economist econ : itcompany.getEconomists()) {
			ecojdbc.delete(econ.getId());
		}
		for (Sale sale : itcompany.getSales()) {
			ecojdbc.delete(sale.getId());
		}
		for (Office off : itcompany.getOffices()) {
			offser.deleteOffice(off.getId(), off.getAdress().getId());
		}
		for (Customer customer : itcompany.getCustomers()) {
			cusser.deleteCustomer(customer.getId(), customer.getAddress().getId());
		}
	}
}
