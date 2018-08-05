package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="developer")
public class Developer extends Employee {

	@XmlElement(name="project")
	private String project;
	

	public Developer(int id, String name, String lastName, double salary, String phone, String project) {
		super(id, name, lastName, salary, phone);
		this.project = project;
	}
	
	public Developer() {
	}
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	@Override
	public String toString() {
		return "Developer [Name=" + getName()+ "Last Name=" + getLastName()+ ", Salary=" + getSalary() + ", Project=" + project  +"]";
	}
	

}
