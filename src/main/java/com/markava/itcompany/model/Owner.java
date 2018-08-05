package com.markava.itcompany.model;

public class Owner extends Employee {

	
	public Owner() {
	}

	public Owner(int id, String name, String lastName, double salary, String phone) {
		super(id, name, lastName, salary, phone);
	}

	@Override
	public String toString() {
		return "[Owner [Name=" + getName() + ",LastName=" + getLastName()+ ",Salary=" + getSalary()+  "]";
	}
}
