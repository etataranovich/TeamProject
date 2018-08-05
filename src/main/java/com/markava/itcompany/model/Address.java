package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "street", "house", "city" }, name = "address")
public class Address extends AbstractEntity{

//	@XmlElement(name="street")
	private String street;
//	@XmlElement(name="house")
	private String house;
//	@XmlElement(name="city")
	private City city;
	
	public Address() {
		super();
	}

	public Address(int id, String street, String house, City city) {
		super();
		this.street = street;
		this.house = house;
		this.city = city;
	}

	public String getStreet() {
		return street;
	}

	public String getHouse() {
		return house;
	}

	public City getCity() {
		return city;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public void setCity(City city) {
		this.city = city;
	}
	@Override
	public String toString() {
		return "Address [street=" + street + ", house=" + house + ", city=" + city +"]";
	}
}
