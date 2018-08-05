package com.markava.itcompany.model;

import javax.xml.bind.annotation.XmlElement;

public enum City {
	MINSK(1,"BELARUS"),MOSCOW(2,"RUSSIA"),SPETERSBURG(3,"RUSSIA"),KIEV(4,"UKRAINE"),FRANKFURTAMMAIN(5,"GERMANY"),
	NICOSIA(6,"CYPRUS"),TELAVIV(7,"IZRAEL"),NEWDELHI(8,"INDIA"),LASVEGAS(9,"USA"),SANFRACISKO(10,"USA"),SYDNEY(11,"AUSTRALIA");
	
	@XmlElement(name="id")
	private int id;
	@XmlElement(name="country")
	private String country;
	

	private City(int id, String country) {
		this.setId(id);
		this.country = country;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
