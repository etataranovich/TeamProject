package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="economist")
public class Economist extends Employee {

	@XmlElement(name="analizeType")
	private String analizeType;


	public Economist(int id, String name, String lastName, double salary, String phone, String analizeType) {
		super(id, name, lastName, salary, phone);
		this.analizeType = analizeType;
	}

	public Economist() {
	}

	public String getAnalizeType() {
		return analizeType;
	}

	public void setAnalizeType(String analizeType) {
		this.analizeType = analizeType;
	}

	@Override
	public String toString() {
		return "Economist [Name=" + getName() + ", Last Name=" + getLastName()+ ", Salary=" + getSalary() +", Analize type=" + analizeType  + "]";
	}
}
