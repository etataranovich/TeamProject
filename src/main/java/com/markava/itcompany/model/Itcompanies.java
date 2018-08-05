package com.markava.itcompany.model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="itcompanies")
@XmlAccessorType(XmlAccessType.FIELD)
public class Itcompanies {
	
	 @XmlElement(name="itcompany")
	private List<Itcompany> itcompanies;


	public void setItcompanies(List<Itcompany> itcompanies) {
		this.itcompanies = itcompanies;
	}
}
