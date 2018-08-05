package com.markava.itcompany.model;



import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;


public class Employees {
	
	private List<Developer> developers;
	private List<Economist> economists;
	private List<Sale> sales;
	public List<Developer> getDevelopers() {
		return developers;
	}
	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}
	public List<Economist> getEconomists() {
		return economists;
	}
	public void setEconomists(List<Economist> economists) {
		this.economists = economists;
	}
	public List<Sale> getSales() {
		return sales;
	}
	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

}
