package com.markava.itcompany.runner;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markava.itcompany.jdbc.AddressJDBC;
import com.markava.itcompany.jdbc.CustomerJDBC;
import com.markava.itcompany.jdbc.DeveloperJDBC;
import com.markava.itcompany.jdbc.EconomistJDBC;
import com.markava.itcompany.jdbc.ItcompanyJDBC;
import com.markava.itcompany.jdbc.OfficeJDBC;
import com.markava.itcompany.jdbc.OwnerJDBC;
import com.markava.itcompany.jdbc.SaleJDBC;
import com.markava.itcompany.model.Address;
import com.markava.itcompany.model.City;
import com.markava.itcompany.model.Customer;
import com.markava.itcompany.model.Developer;
import com.markava.itcompany.model.Economist;
import com.markava.itcompany.model.Itcompany;
import com.markava.itcompany.model.Office;
import com.markava.itcompany.model.Owner;
import com.markava.itcompany.model.Sale;
import com.markava.itcompany.service.CustomerService;
import com.markava.itcompany.service.ItcompanyService;
import com.markava.itcompany.service.OfficeService;

public class Main {
	private final static Logger LOGGER = LogManager.getLogger();

	public static void main(String[] args) throws InterruptedException {
		
		Itcompany it = new Itcompany();
    	it.setName("Amasty");
    	it.setRegNumber("178964218");
    	it.setYear("2009");
    	Owner own = new Owner();
    	own.setName("Alexandr");
    	own.setLastName("Stelmah");
    	own.setSalary(14300.78);
    	it.setOwner(own);
    	Developer dev = new Developer();
    	dev.setName("Andei");
    	dev.setLastName("Zubrytsky");
    	dev.setSalary(2134.76);
    	dev.setProject("Neueslof");
    	List<Developer> devs = new ArrayList<>();
    	devs.add(dev);
    	it.setDevelopers(devs);
    	Economist econ = new Economist();
    	econ.setName("Olga");
    	econ.setLastName("Bobko");
    	econ.setSalary(950.36);
    	econ.setAnalizeType("Economic analize");
    	List<Economist> econs = new ArrayList<>();
    	econs.add(econ);
    	it.setEconomists(econs);
    	Sale sale = new Sale();
    	sale.setName("Alexandra");
    	sale.setLastName("Gudova");
    	sale.setSalary(1100.36);
    	sale.setClientCount(5);
    	List<Sale> sales = new ArrayList<>();
    	sales.add(sale);
    	it.setSales(sales);
    	Address adr = new Address();
    	adr.setStreet("Summit Street");
    	adr.setHouse("457K");
    	adr.setCity(City.SANFRACISKO);
    	Address adr1= new Address();
    	adr1.setStreet("Dombrovskaya");
    	adr1.setHouse("9");
    	adr1.setCity(City.MINSK);
    	Office off = new Office();
    	off.setAdress(adr1);
    	List<Office> offices = new ArrayList<>();
    	offices.add(off);
    	Customer cus = new Customer();
    	cus.setName("Walmart");
    	cus.setAddress(adr);
    	

    	ItcompanyService itser = new ItcompanyService();
    	itser.getItcompany(1);
    	System.out.println(itser.getItcompany(1));
    	
    	System.out.println("********************************");
  
//    	itser.insertItcompany(it);
//    	System.out.println();
    	
    	
    	
    	
//		List<Integer> itcIds = new ArrayList<>();
//		itcIds.add(4);
//    	Owner own1 = new Owner();
//    	own1.setName("Katya");
//    	own1.setLastName("Pinskaya");
//    	own1.setSalary(2654.15);
    	OwnerJDBC ownjdbc = new OwnerJDBC();
    	//ownjdbc.insert(own1, itcIds);
    	ownjdbc.delete(19);
    	
    	
    	System.out.println("********************************");
    	
//    	ItcompanyJDBC itjdbc = new ItcompanyJDBC();
//    	Itcompany itcom = new Itcompany();
//    	itcom.setName("Amasty");
//    	itcom.setRegNumber(178964218);
//    	itcom.setYear(2009);
//    	itjdbc.insert(itcom);

    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
//    	
//    	Office off = new Office();
//    	off.setAdress(adr1);
//    	List<Office> offices = new ArrayList<>();
//    	offices.add(off);
//    	it.setOffices(offices);
//    	

    	
    	

    	
//    	CustomerService cusser = new CustomerService();
//    	cusser.getCustomer(2);
//    	System.out.println(cusser.getCustomer(2));
//    	itser.updateItcompany(it);
//    	System.out.println(itser.getItcompany(1).toString());

//    	ItcompanyJDBC itj = new ItcompanyJDBC();
//    	itj.get(1);
//    	System.out.println(itj.get(1));
    	
//		OfficeService offser = new OfficeService();
//		offser.getOffice(1);
		

//		AddressJDBC addr = new AddressJDBC();
//		addr.getAddressByOffice(1);
//		System.out.println(addr.getAddressByCustomer(2).toString());
//	System.out.println("--------------------------------------------------------------");	
//		AddressJDBC adrjdbc = new AddressJDBC();
//		CustomerJDBC cusjdbc = new CustomerJDBC();
//		List<Customer> cuss = cusjdbc.getCustomerByITId(1);
//		for(Customer customer:cuss){
//			int customerid = customer.getId();
//			customer.setAddress(adrjdbc.getAddressByCustomer(customerid));
//		}
//		System.out.println(cuss);
//		System.out.println("--------------------------------------------------------------");
//		
//		CustomerService cusser = new CustomerService();
//		cusser.getCustomer(1);
//		
//		System.out.println("--------------------------------------------------------------");
//		
//		OfficeJDBC offjdbc = new OfficeJDBC();
//		List<Office> off = offjdbc.getOfficeByItId(1);
//		adrjdbc = new AddressJDBC();
//		for(Office office:off){
//			int officeid = office.getId();
//			office.setAdress(adrjdbc.getAddressByOffice(officeid));
//		}
//		System.out.println(off);
//		System.out.println("--------------------------------------------------------------");
		
		
		
//		   OfficeJDBC offjdbc = new OfficeJDBC();
//			List<Office> off1 = offjdbc.getOfficeByItId(1);
//			for(Office office:off1){
//				
//				office = office.setAdress(adrjdbc.getAddressByOffice(office.getId()));
//				System.out.println( office.toString());
//			
//		}
    	

//		OfficeService offser = new OfficeService();
//		offser.getOffice(1);
//		System.out.println(offser.getOffice(1));
//		addr.get(4);
//		LOGGER.info(addr.get().toString());
//		LOGGER.info("---------------------------");
//		LOGGER.info(addr.get(1));
//		LOGGER.info(addr.getAddressById(2).toString());
//		CustomerJDBC cus = new CustomerJDBC();
//		cus.getCustomerByITId(1);

//		Owner own1 = new Owner();
//		own1.setName("Kawgta");
//		own1.setLastName("Sasara");
//		own1.setSalary(107.02);
		
		
//		sale.setClientCount(4);
		
//		List<Integer> itcIds = new ArrayList<>();
//		itcIds.add(4);
//		Developer developer = new Developer ();
//		developer.setName("");
//		developer.setLastName("");
//		developer.setProject("");
//		developer.setSalary(1500.12);
//		DeveloperJDBC dev1 = new DeveloperJDBC();
//		dev1.insert(developer, itcIds);
//		dev1.delete(36);
		
//		dev1.insert(own, itcIds);
//		dev.delete(24);
		
//		dev.get();
		//dev.delete(7);
//		dev.get(4);
//		developer.setSalary(2100.45);
//		developer.setProject("Prygozhdjar");


//		dev.deleteDeveloper(12);
//		LOGGER.info(dev.get(4).toString()); 

//		EconomistJDBC eco = new EconomistJDBC();
//		eco.delete(34);
//		eco.delete(25);
//		System.out.println(eco.getEconomistByItId(1).toString());
//		ItcompanyJDBC itc = new ItcompanyJDBC();
//		itc.getItcompanyById(3);
//		LOGGER.info(itc.getItcompanyById(3).toString());
//		OfficeJDBC of = new OfficeJDBC();
//		OwnerJDBC own = new OwnerJDBC();
//		own.getOwnerById(1);
//		LOGGER.info(own.getOwnerById(1).toString());
//		SaleJDBC sal = new SaleJDBC();
//		sal.delete(35);
//		sal.delete(26);
//		LOGGER.info(sal.getSaleById(1).toString());
	}

}
