package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@XmlRootElement(name="office")
public class Office {
	
	@XmlElement(name="id")
	private int id;
	@XmlTransient
	@JsonIgnore
	public String phone;
	@XmlElement(name="adress")
	private Address adress;
	
	public Office(int id, String phone, Address adress) {
		super();
		this.id = id;
		this.phone = phone;
		this.adress = adress;
	}

	public Office() {
		super();
	}

	public int getId() {
		return id;
	}


	public String getPhone() {
		return phone;
	}

	public Address getAdress() {
		return adress;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setAdress(Address adress) {
		this.adress = adress;
	}

	
	@Override
	public String toString() {
		return "Office [Adress=" + adress +"]";
	}
}
