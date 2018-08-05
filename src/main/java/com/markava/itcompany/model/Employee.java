package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class Employee extends AbstractEntity{
	
	@XmlElement(name="name")
	private String name;
	@XmlElement(name="lastName")
	private String lastName;
	@XmlElement(name="salary")
	private double salary;
	@XmlTransient
	@JsonIgnore
	private String phone;
	
	public Employee() {
		super();
	}

	public Employee(int id, String name, String lastName,double salary, String phone) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.salary=salary;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

	public double getSalary() {
		return salary;
	}

	public String getPhone() {
		return phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setSalary(double salary) {
		this.salary = salary;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
