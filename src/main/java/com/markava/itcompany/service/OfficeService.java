package com.markava.itcompany.service;

import java.util.List;

import com.markava.itcompany.jdbc.AddressJDBC;
import com.markava.itcompany.jdbc.OfficeJDBC;
import com.markava.itcompany.model.Office;

public class OfficeService {

	private OfficeJDBC offjdbc;
	private AddressJDBC adrjdbc;

	public void insertOffice(Office off, List<Integer> itcId){		
		adrjdbc.insert(off.getAdress());
		offjdbc.insert(off,itcId);
		
}
	public List<Office> getOffice(int idcompany){
		offjdbc = new OfficeJDBC();
		List<Office> off = offjdbc.getOfficeByItId(idcompany);
		adrjdbc = new AddressJDBC();
		for(Office office:off){
			int officeid = office.getId();
			office.setAdress(adrjdbc.getAddressByOffice(officeid));
		}
		return off;
	}
	
	public void updateOffice(Office off){
		offjdbc.update(off);
		adrjdbc.update(off.getAdress());
	}
	
	
	public void deleteOffice(int idoffice, int idaddress){
		offjdbc.delete(idoffice);
		adrjdbc.delete(idaddress);
	}	
}