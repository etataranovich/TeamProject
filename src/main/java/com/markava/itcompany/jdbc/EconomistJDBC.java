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
import com.markava.itcompany.dao.IEconomistDao;
import com.markava.itcompany.model.Economist;

public class EconomistJDBC extends AbstractBaseDao implements IEconomistDao {

	private final static Logger LOGGER = LogManager.getLogger(EconomistJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_ECONOMIST_BY_ITID = "SELECT employee.id, employee.name,employee.last_name,itcompany_has_employee.salary, economists.analize_type FROM economists LEFT JOIN employee ON employee.id=economists.Employee_id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id WHERE itcompany.id=?";
	private final static String GET_ECONOMIST_BY_ID = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,economists.analize_type FROM employee LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN economists ON employee.id=economists.Employee_id WHERE employee.id=?";
	private final static String GET_ECONOMIST_ALL = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,economists.analize_type FROM economists LEFT JOIN employee ON economists.Employee_id=employee.id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id ";
	private final static String INSERT_INTO_ECONOMIST = " INSERT into economists (economists.analize_type,economists.Employee_id) VALUES (?,?)";
	private final static String DELETE_ECONOMIST_BY_ID = "DELETE from economists WHERE id=?";
	private final static String UPDATE_ECONOMIST_BY_ID = "UPDATE economists SET economists.analize_type=? WHERE economists.id=?";

	@Override
	public List<Economist> getEconomistByItId(int id) {

		ArrayList<Economist> economists = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ECONOMIST_BY_ITID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Economist ec = new Economist();
				ec.setId(rs.getInt("ID"));
				ec.setName(rs.getString("NAME"));
				ec.setLastName(rs.getString("LAST_NAME"));
				ec.setSalary(rs.getDouble("SALARY"));
				ec.setAnalizeType(rs.getString("ANALIZE_TYPE"));
				economists.add(ec);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return economists;
	}

	@Override
	public void insert(Economist economist, List<Integer> itcId) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			int emplId = emp.insertEmployee(economist, itcId);
			ps = connection.prepareStatement(INSERT_INTO_ECONOMIST, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, economist.getAnalizeType());
			ps.setInt(2, emplId);
			int rowAffected = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				economist.setId(rs.getInt(1));
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
				LOGGER.error(e.getMessage());
			}
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}

	}

	@Override
	public void update(Economist economist) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_ECONOMIST_BY_ID);
			ps.setString(1, economist.getAnalizeType());
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
			ps = connection.prepareStatement(DELETE_ECONOMIST_BY_ID);
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
	public Economist get(int id) {
		Economist econ = new Economist();
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ECONOMIST_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				econ.setName(rs.getString("NAME"));
				econ.setLastName(rs.getString("LAST_NAME"));
				econ.setSalary(rs.getDouble("SALARY"));
				econ.setAnalizeType(rs.getString("ANALIZE_TYPE"));
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return econ;
	}

	@Override
	public List<Economist> get() {
		List<Economist> economists = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ECONOMIST_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Economist econ = new Economist();
				econ.setName(rs.getString("NAME"));
				econ.setLastName(rs.getString("LAST_NAME"));
				econ.setSalary(rs.getDouble("SALARY"));
				econ.setAnalizeType(rs.getString("ANALIZE_TYPE"));
				economists.add(econ);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return economists;
	}

	@Override
	public void insert(Economist entity) {
		// TODO Auto-generated method stub

	}

}
