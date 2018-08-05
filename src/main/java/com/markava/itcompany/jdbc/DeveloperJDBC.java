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
import com.markava.itcompany.dao.IDeveloperDao;
import com.markava.itcompany.model.Developer;


public class DeveloperJDBC extends AbstractBaseDao implements IDeveloperDao {

	private final static Logger LOGGER = LogManager.getLogger(DeveloperJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_DEVELOPER_BY_ITID = "SELECT employee.id, employee.name,employee.last_name,itcompany_has_employee.salary, developers.project FROM developers LEFT JOIN employee ON employee.id=developers.Employee_id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id WHERE itcompany.id=?";
	private final static String GET_DEVELOPER_BY_ID = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,developers.project FROM employee LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN developers ON employee.id=developers.Employee_id WHERE developers.id=?";
	private final static String GET_DEVELOPER_ALL = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,developers.project FROM developers LEFT JOIN employee ON developers.Employee_id=employee.id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id ";
	private final static String INSERT_INTO_DEVELOPER = " INSERT into developers (developers.project,developers.Employee_id) VALUES (?,?)";
	private final static String DELETE_DEVELOPER_BY_ID = "DELETE from developers WHERE id=?";
	private final static String UPDATE_DEVELOPER_BY_ID = "UPDATE developers SET developers.project=? WHERE developers.id=?";

	@Override
	public List<Developer> getDeveloperByItId(int id) {

		List<Developer> developers = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_DEVELOPER_BY_ITID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Developer dev = new Developer();
				dev.setId(rs.getInt("ID"));
				dev.setName(rs.getString("NAME"));
				dev.setLastName(rs.getString("LAST_NAME"));
				dev.setSalary(rs.getDouble("SALARY"));
				dev.setProject(rs.getString("PROJECT"));
				developers.add(dev);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return developers;

	}
	@Override
	public Developer get(int id) {
		Developer dev = new Developer();
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_DEVELOPER_BY_ID);
	        ps.setInt(1, id);
	        rs = ps.executeQuery();
	        while (rs.next()) {
	        	dev.setName(rs.getString("NAME"));
	        	dev.setLastName(rs.getString("LAST_NAME"));
	        	dev.setSalary(rs.getDouble("SALARY"));
	        	dev.setProject(rs.getString("PROJECT"));
	        }
	    } catch (SQLException | InterruptedException e) {
	    	LOGGER.error(e.getMessage());
	    }
		finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return dev;	
	}
	

	@Override
	public void insert(Developer developer, List<Integer>itcId) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			int emplId = emp.insertEmployee(developer, itcId );
			ps = connection.prepareStatement(INSERT_INTO_DEVELOPER,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, developer.getProject());
		    ps.setInt(2, emplId  );
			int rowAffected = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) 
				developer.setId(rs.getInt(1));
			if(rowAffected == 1)
				{
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException|InterruptedException ex) {
			// roll back the transaction
			try {
				if (connection != null)
					connection.rollback();
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
			}
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}


	@Override
	public void update(Developer developer) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_DEVELOPER_BY_ID);
			ps.setString(1, developer.getProject());
			ps.executeUpdate();
			connection.commit();
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}

	}

	@Override
	public void delete(int id) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			emp.deleteEmployee(id);
			ps = connection.prepareStatement(DELETE_DEVELOPER_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			connection.commit();
			LOGGER.info("Developer with " + id + " was deleted from Developers table");
		} catch (SQLException ex) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}
	@Override
	public List<Developer> get() {

		List<Developer> developers = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_DEVELOPER_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Developer dev = new Developer();
				dev.setName(rs.getString("NAME"));
				dev.setLastName(rs.getString("LAST_NAME"));
				dev.setSalary(rs.getDouble("SALARY"));
				dev.setProject(rs.getString("PROJECT"));
				developers.add(dev);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
//TODO			connection.setAutoCommit(true);
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return developers;

	}
	@Override
	public void insert(Developer entity) {
		// TODO Auto-generated method stub
		
	}

}
