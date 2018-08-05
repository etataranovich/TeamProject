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
import com.markava.itcompany.dao.IOwnerDao;
import com.markava.itcompany.model.Owner;

public class OwnerJDBC extends AbstractBaseDao implements IOwnerDao {

	private final static Logger LOGGER = LogManager.getLogger(OwnerJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_OWNER_BY_ITID = "SELECT employee.id, employee.name,employee.last_name,itcompany_has_employee.salary FROM owner LEFT JOIN employee ON employee.id=owner.Employee_id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id WHERE itcompany.id=?";
	private final static String GET_OWNER_BY_ID = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary FROM employee LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN sales ON employee.id=sales.Employee_id WHERE employee.id=?";
	private final static String GET_OWNER_ALL = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary FROM sales LEFT JOIN employee ON sales.Employee_id=employee.id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id ";
	private final static String INSERT_INTO_OWNER = "INSERT into owner (owner.Employee_id) VALUES (?)";
	private final static String DELETE_OWNER_BY_ID = "DELETE from owner WHERE id=?";

	@Override
	public Owner getOwnerByItId(int id) {

		Owner own = new Owner();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OWNER_BY_ITID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			own.setId(rs.getInt("ID"));
			own.setName(rs.getString("NAME"));
			own.setLastName(rs.getString("LAST_NAME"));
			own.setSalary(rs.getDouble("SALARY"));

		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return own;
	}

	@Override
	public void insert(Owner owner, List<Integer> itcId) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			int emplId = emp.insertEmployee(owner, itcId);
			ps = connection.prepareStatement(INSERT_INTO_OWNER, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, emplId);
			int rowAffected = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				owner.setId(rs.getInt(1));
			if (rowAffected == 1) {
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException | InterruptedException ex) {
			// roll back the transaction
			try {
				if (connection != null)
					connection.rollback();
			} catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}

	}

	@Override
	public void insert(Owner entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Owner entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(int id) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			emp.deleteEmployee(id);
			ps = connection.prepareStatement(DELETE_OWNER_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			connection.commit();
			LOGGER.info("Economist with " + id + " was deleted from Economists table");
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
	public List<Owner> get() {
		List<Owner> owners = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OWNER_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Owner own = new Owner();
				own.setName(rs.getString("NAME"));
				own.setLastName(rs.getString("LAST_NAME"));
				own.setSalary(rs.getDouble("SALARY"));
				owners.add(own);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return owners;
	}

	@Override
	public Owner get(int id) {
		Owner own = new Owner();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OWNER_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			own.setName(rs.getString("NAME"));
			own.setLastName(rs.getString("LAST_NAME"));
			own.setSalary(rs.getDouble("SALARY"));

		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return own;
	}
}
