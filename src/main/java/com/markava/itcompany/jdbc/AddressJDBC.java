package com.markava.itcompany.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markava.itcompany.dao.AbstractBaseDao;
import com.markava.itcompany.dao.IAddressDao;
import com.markava.itcompany.model.Address;
import com.markava.itcompany.model.City;

public class AddressJDBC extends AbstractBaseDao implements IAddressDao {

	private final static Logger LOGGER = LogManager.getLogger(AddressJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_ADDRESS_BY_ID = "SELECT adr.street, adr.house, city.name as CITY FROM address adr LEFT JOIN city ON city.id=adr.City_id WHERE adr.id=?";
	private final static String GET_ADDRESS_BY_OFFID = "SELECT adr.id, adr.street, adr.house, city.name as CITY FROM address adr LEFT JOIN city ON city.id=adr.City_id LEFT JOIN office ON adr.id=office.adress_id WHERE office.id=?";
	private final static String GET_ADDRESS_BY_CUSID = "SELECT adr.id, adr.street, adr.house, city.name as CITY FROM address adr LEFT JOIN city ON city.id=adr.City_id LEFT JOIN customer ON adr.id=customer.Address_id WHERE customer.id=?";
	private final static String GET_ADDRESS_ALL = "SELECT adr.street, adr.house, c.name as CITY FROM address adr LEFT JOIN city c ON c.id=adr.City_id";
	private final static String UPDATE_ADDRESS ="UPDATE address adr SET adr.street=?,adr.house=?, WHERE adr.id=?";
	private final static String DELETE_ADDRESS ="DELETE FROM address adr WHERE adr.id=?";
	private final static String INSERT_ADDRESS ="INSERT into address (street,house,City_id,Country_id) VALUES (?,?,?,?)";
	
	
	@Override
	public void insert(Address address) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(INSERT_ADDRESS,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1,address.getStreet());
			ps.setString(2,address.getHouse());
     		ps.setInt(3,address.getCity().getId());
     		ps.setString(4,address.getCity().getCountry());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()){
			   	address.setId(rs.getInt(1));
			}
			LOGGER.info("ID" + address.getId() +" "+ address.toString() + " was added to Addresses table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
}


	@Override
	public void update(Address address) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_ADDRESS);
			ps.setString(1,address.getStreet());
			ps.setString(2,address.getHouse());
     		ps.setInt(3,address.getCity().getId());
     		ps.setString(4,address.getCity().getCountry());
			ps.setInt(5,address.getId());
			ps.executeUpdate();
			LOGGER.info(address.toString() + " was updated at Addresses table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		
}
	@Override
	public void delete(int id) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(DELETE_ADDRESS);
			ps.setInt(1, id);
			ps.executeUpdate();
			LOGGER.info("Address with id="+ id +" was deleted from Addresses table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
}
		
	@Override
	public Address get(int id) {
		Address addr = new Address();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ADDRESS_BY_ID);
			LOGGER.info(ps.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
			addr.setStreet(rs.getString("STREET"));
			addr.setHouse(rs.getString("HOUSE"));
			addr.setCity(City.valueOf(rs.getString("CITY")));
			}

		} catch (SQLException | InterruptedException e) {
			LOGGER.error("SQLException");
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			if (connection != null ) {
				getConpool().releaseConnection(connection);
			}
		}
		return addr;
	}
	
	public Address getAddressByOffice(int id) {
		Address addr = new Address();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ADDRESS_BY_OFFID);
			LOGGER.info(ps.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
			addr.setId(rs.getInt("ID"));
			addr.setStreet(rs.getString("STREET"));
			addr.setHouse(rs.getString("HOUSE"));
			addr.setCity(City.valueOf(rs.getString("CITY")));
			}

		} catch (SQLException | InterruptedException e) {
			LOGGER.error("SQLException");
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			if (connection != null ) {
				getConpool().releaseConnection(connection);
			}
		}
		return addr;
	}
	
	public Address getAddressByCustomer(int id) {
		Address addr = new Address();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ADDRESS_BY_CUSID);
			LOGGER.info(ps.toString());
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()){
			addr.setId(rs.getInt("ID"));
			addr.setStreet(rs.getString("STREET"));
			addr.setHouse(rs.getString("HOUSE"));
			addr.setCity(City.valueOf(rs.getString("CITY")));
			}

		} catch (SQLException | InterruptedException e) {
			LOGGER.error("SQLException");
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			if (connection != null ) {
				getConpool().releaseConnection(connection);
			}
		}
		return addr;
	}
	

	@Override
	public List<Address> get() {
		List<Address> addresses = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ADDRESS_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Address addr = new Address();
				addr.setStreet(rs.getString("STREET"));
				addr.setHouse(rs.getString("HOUSE"));
				addr.setCity(City.valueOf(rs.getString("CITY")));
				addresses.add(addr);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return addresses;
	}
}
