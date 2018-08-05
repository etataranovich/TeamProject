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
import com.markava.itcompany.model.Developer;
import com.markava.itcompany.model.Employee;
import com.markava.itcompany.model.Itcompany;

public class EmloyeeJDBC extends AbstractBaseDao {

	private final static Logger LOGGER = LogManager.getLogger(EmloyeeJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	PreparedStatement psAssignment = null;
	private ResultSet rs = null;
	// private final static String GET_EMPLOYEE_BY_ITID = "SELECT
	// employee.name,employee.last_name,itcompany_has_employee.salary FROM
	// employee LEFT JOIN itcompany_has_employee ON
	// itcompany_has_employee.Employee_id=employee.id LEFT JOIN itcompany ON
	// itcompany_has_employee.ITcompany_id=itcompany.id WHERE itcompany.id=?";
	// private final static String GET_EMPLOYEE_BY_ID = "SELECT
	// employee.name,employee.last_name,itcompany_has_employee.salary FROM
	// employee LEFT JOIN itcompany_has_employee ON
	// itcompany_has_employee.Employee_id=employee.id WHERE employee.id=?";
	private final static String INSERT_INTO_EMPLOYEE = " INSERT into employee (employee.name,employee.last_name) VALUES (?,?)";
	private final static String INSERT_INTO_ITHASEMPLOYEE = "INSERT into itcompany_has_employee (itcompany_has_employee.ITcompany_id,itcompany_has_employee.Employee_id,itcompany_has_employee.salary) VALUES (?,?,?)";
	private final static String DELETE_EMPLOYEE_BY_ID = "DELETE from employee WHERE id=?";
	private final static String DELETE_EMPLOYEE_ITHASEMPLOYEE = "DELETE from itcompany_has_employee WHERE itcompany_has_employee.Employee_id=?";
	private final static String UPDATE_EMPLOYEE_BY_ID = "UPDATE employee SET employee.name=?,employee.last_name=?,itcompany_has_employee.salary=? WHERE employee.id=?";
	//private final static String UPDATE_EMPLOYEE_ITHASEMPLOYEE = "UPDATE itcompany_has_employee SET itcompany_has_employee.salary=? WHERE itcompany_has_employee.Employee_id=?";

	public int insertEmployee(Employee employee, List<Integer> itcId) throws InterruptedException {
		int emplId = 0;
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(INSERT_INTO_EMPLOYEE, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getLastName());
			// ps.setString(4, developer.getProject());
			int rowAffected = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				emplId = rs.getInt(1);
			employee.setId(rs.getInt(1));
			if (rowAffected == 1) {
				psAssignment = connection.prepareStatement(INSERT_INTO_ITHASEMPLOYEE);
				for (Integer itId : itcId) {
					psAssignment.setInt(1, itId);
					psAssignment.setInt(2, (rs.getInt(1)));
					psAssignment.setDouble(3, employee.getSalary());
					psAssignment.executeUpdate();
				}
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException ex) {
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
			closepsAssignment(psAssignment);
			getConpool().releaseConnection(connection);
		}
		return emplId;
	}

	public int deleteEmployee(int id) throws InterruptedException {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID);
			ps.setInt(1, id);
			int rowAffected = ps.executeUpdate();
			LOGGER.info("Employee with id=" + id + " was deleted from Employee table");
			if (rowAffected == 1) {
				psAssignment = connection.prepareStatement(DELETE_EMPLOYEE_ITHASEMPLOYEE);
				psAssignment.setInt(1, id);
				psAssignment.executeUpdate();
				LOGGER.info("Employee with id=" + id + " was deleted from IThasEmployee table");
				connection.commit();
			} else {
				connection.rollback();
			}
		} catch (SQLException ex) {
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
			closepsAssignment(psAssignment);
			getConpool().releaseConnection(connection);
		}
		return id;
	}

	public void updateEmployee(Employee employee) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(UPDATE_EMPLOYEE_BY_ID);
			ps.setString(1, employee.getName());
			ps.setString(2, employee.getLastName());
			ps.setDouble(3, employee.getSalary());
			ps.executeUpdate();
			LOGGER.info(employee.toString() + " was updated at Employee table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}

	}

}
