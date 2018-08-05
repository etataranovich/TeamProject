package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "id", "name", "address" }, name = "customer")
public class Customer  {

//	@XmlElement(name="id")
	private int id;
//	@XmlElement(name="name")
	private String name;
//	@XmlElement(name="address")
	private Address address;

	public Customer(int id, String name, Address address) {
		super();
		this.name=name;
		this.address=address;
	}
	
	public Customer() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Address getAddress() {
		return address;
	}


	public void setAddress(Address address) {
		this.address = address;
	}
	
	@Override
	public String toString() {
		return "Customer [ID="+id+ "Name=" + name + ", address=" + address+"]";
	}
}
