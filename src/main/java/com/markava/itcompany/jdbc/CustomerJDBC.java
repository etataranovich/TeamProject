package com.markava.itcompany.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markava.itcompany.dao.AbstractBaseDao;
import com.markava.itcompany.dao.ICustomerDao;
import com.markava.itcompany.model.Customer;
import com.markava.itcompany.model.Office;



public class CustomerJDBC extends AbstractBaseDao implements ICustomerDao{

	private final static Logger LOGGER = LogManager.getLogger(CustomerJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_CUSTOMER_BY_ITID = "SELECT customer.id, customer.name from customer LEFT JOIN itcompany_has_customer ON customer.id=itcompany_has_customer.Customer_id LEFT JOIN itcompany ON itcompany.id=itcompany_has_customer.ITcompany_id  WHERE itcompany.id=?";
	private final static String GET_CUSTOMER_BY_ID = "SELECT customer.id, customer.name from customer WHERE customer.id=?";
	private final static String GET_DEVELOPER_ALL = "SELECT customer.id, customer.name from customer";
	private final static String INSERT_CUSTOMER = "INSERT into customer (customer.name,customer.Address_id) VALUES (?,?)";
	private final static String UPDATE_CUSTOMER_BY_ID = "UPDATE customer SET customer.name =? WHERE  customer.id=?";
	private final static String DELETE_CUSTOMER_BY_ID = "DELETE from customer WHERE id=?";
	
	@Override
	public List<Customer> getCustomerByITId(int id) {
		
		ArrayList<Customer> customers = new ArrayList<>();
		
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_CUSTOMER_BY_ITID);
	        ps.setInt(1, id);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        Customer c = new Customer();
	        c.setId(rs.getInt("ID"));
	        c.setName(rs.getString("NAME"));
	        //c.setAddress(rs.getString("ADDRESS"));
	        customers.add(c);
	        }
	    } catch (SQLException | InterruptedException e) {
	    	LOGGER.error(e.getMessage());
	    }
		finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return customers;
	}

	@Override
	public void insert(Customer customer) {
}
		

	@Override
	public void update(Customer customer) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(UPDATE_CUSTOMER_BY_ID);
			ps.setString(1,customer.getName());
			ps.executeUpdate();
			LOGGER.info(customer.toString() + " was updated at Customer table");
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
			ps = connection.prepareStatement(DELETE_CUSTOMER_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			LOGGER.info("Customer with " +id + " was deleted from Customer table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		}
		finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		
	}

	@Override
	public Customer get(int id) {
		Customer cus = new Customer();
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_CUSTOMER_BY_ID);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        rs.next();
	        cus.setId(rs.getInt("ID"));
	        cus.setName(rs.getString("NAME"));
	    } catch (SQLException | InterruptedException e) {
	    	LOGGER.error(e.getMessage());
	    }
		finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return cus;
}

	@Override
	public List<Customer> get() {
ArrayList<Customer> customers = new ArrayList<>();
		
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_DEVELOPER_ALL);
	        ResultSet rs = ps.executeQuery();
	        while (rs.next()) {
	        Customer c = new Customer();
	        c.setId(rs.getInt("ID"));
	        c.setName(rs.getString("NAME"));
	        customers.add(c);
	        }
	    } catch (SQLException | InterruptedException e) {
	    	LOGGER.error(e.getMessage());
	    }
		finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return customers;
	}

	@Override
	public void insert(Customer customer, int idcompany) {
			try {
				connection = getConpool().getConnection();
				ps = connection.prepareStatement(INSERT_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1,customer.getName());
				ps.setInt(2,customer.getAddress().getId());
				ps.setInt(3,idcompany);
				ps.executeUpdate();
				rs = ps.getGeneratedKeys();
				if (rs.next()) {
					customer.setId(rs.getInt(1));
				}
				LOGGER.info("ID" + customer.getId() + " " + customer.toString() + " was added to Office table");
			} catch (SQLException | InterruptedException e) {
				LOGGER.error(e.getMessage());
			} finally {
				closeRSet(rs);
				closePrStatement(ps);
				getConpool().releaseConnection(connection);
			}

		}
}

