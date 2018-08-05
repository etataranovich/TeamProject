package com.markava.itcompany.model;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Itcompany {

	@JsonProperty
	@XmlElement(name="id")
	private int id;
	
	@JsonProperty
	@XmlElement(name="regNumber")
	private String regNumber;
	
	@JsonProperty
	@XmlElement(name="year")
	private String year;
	
	@JsonProperty
	@XmlElement(name="name")
	private String name;
	
	@JsonProperty
	@XmlElement(name="owner")
	private Owner owner;
	
	@JsonProperty
	@XmlElementWrapper(name="offices")
	@XmlElement(name="office")
	private List<Office> offices;
	
	@JsonProperty
	@XmlElementWrapper(name="economists")
	@XmlElement(name="economist")
	private List<Economist> economists;
	
	@JsonProperty
	@XmlElementWrapper(name="developers")
	@XmlElement(name="developer")
	private List<Developer> developers;
	
	@JsonProperty
	@XmlElementWrapper(name="sales")
	@XmlElement(name="sale")
	private List<Sale> sales;
	
	@JsonProperty
	@XmlElementWrapper(name="customers")
	@XmlElement(name="customer")
	private List<Customer> customers;
	

	public Itcompany(int id, String regNumber, String year, String name, Owner owner, List<Office> offices,
			List<Economist> economists, List<Developer> developers, List<Sale> sales,
			List<Customer> customers) {
		super();
		this.id = id;
		this.regNumber = regNumber;
		this.year = year;
		this.name = name;
		this.owner = owner;
		this.offices = offices;
		this.economists = economists;
		this.developers = developers;
		this.sales = sales;
		this.customers = customers;
	}

	public int getId() {
		return id;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public String getYear() {
		return year;
	}

	public String getName() {
		return name;
	}

	public Owner getOwner() {
		return owner;
	}

	public List<Office> getOffices() {
		return offices;
	}

	public List<Economist> getEconomists() {
		return economists;
	}

	public List<Developer> getDevelopers() {
		return developers;
	}

	public List<Sale> getSales() {
		return sales;
	}

	public List<Customer> getCustomers() {
		return customers;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public void setOffices(List<Office> offices) {
		this.offices = offices;
	}

	public void setEconomists(List<Economist> economists) {
		this.economists = economists;
	}

	public void setDevelopers(List<Developer> developers) {
		this.developers = developers;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}

	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	public Itcompany() {
		super();
	}

	@Override
	public String toString() {
		return "Itcompany [Name=" + name + ", Year=" + year + ", RegNumber=" + regNumber+", owner=" + owner
				+ ", offices=" + offices + ", economists=" + economists + ", developers=" + developers + ", sales="
				+ sales + ", customers=" + customers + "]";
	}
	
}
