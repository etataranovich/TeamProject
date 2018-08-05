package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="sale")
public class Sale extends Employee {

	@XmlElement(name="clientCount")
	private int clientCount;

	public Sale(int id, String name, String lastName, double salary, String phone, int clientCount) {
		super(id, name, lastName, salary, phone);
		this.clientCount = clientCount;
	}
	
	public Sale() {
	}

	public int getClientCount() {
		return clientCount;
	}

	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}
	@Override
	public String toString() {
		return "Sale [Name=" + getName()+ ", Last Name=" + getLastName() + ", Salary=" + getSalary() + ", clientCount=" + clientCount + "]";
	}

}
