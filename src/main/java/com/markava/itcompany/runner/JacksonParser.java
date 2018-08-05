package com.markava.itcompany.runner;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.markava.itcompany.model.Itcompany;
import com.markava.itcompany.service.ItcompanyService;

public class JacksonParser {

	private final static Logger LOGGER = LogManager.getLogger(JacksonParser.class);

	public static void main(String[] args) {

		ObjectMapper objectmapper = new ObjectMapper();
		ItcompanyService itSer = new ItcompanyService();

		try {
			File file = new File("src/main/resources/database/Jack.json");

			objectmapper.writeValue(file, itSer.getAllItcompanies());
			List<Itcompany> itcompanies = objectmapper.readValue(file,
					objectmapper.getTypeFactory().constructCollectionType(List.class, Itcompany.class));
			for (Itcompany it : itcompanies) {
				LOGGER.info(it);
			}
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
	}
}
