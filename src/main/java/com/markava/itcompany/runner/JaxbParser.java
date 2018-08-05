package com.markava.itcompany.runner;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.markava.itcompany.model.Itcompanies;
import com.markava.itcompany.model.Itcompany;
import com.markava.itcompany.service.ItcompanyService;

public class JaxbParser {

	private static final String FILE_NAME = "src/main/resources/database/Jaxb.xml";

	public static void main(String[] args) {
		try {

			JAXBContext context = JAXBContext.newInstance(Itcompany.class);
			Marshaller m = context.createMarshaller();
			// for pretty-print XML in JAXB
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

			// Write to System.out for debugging
			// m.marshal(emp, System.out);

			// Write to File
			ItcompanyService itSer = new ItcompanyService();
			List<Itcompany> itcompanies = itSer.getAllItcompanies();
			m.marshal(itcompanies, new File(FILE_NAME));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

}
