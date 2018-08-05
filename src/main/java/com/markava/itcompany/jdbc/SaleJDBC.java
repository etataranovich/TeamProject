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
import com.markava.itcompany.dao.ISaleDao;
import com.markava.itcompany.model.Sale;

public class SaleJDBC extends AbstractBaseDao implements ISaleDao {

	private final static Logger LOGGER = LogManager.getLogger(SaleJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_SALE_BY_ITID = "SELECT employee.id, employee.name,employee.last_name,itcompany_has_employee.salary, sales.client_count FROM sales LEFT JOIN employee ON employee.id=sales.Employee_id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN itcompany ON itcompany_has_employee.ITcompany_id=itcompany.id WHERE itcompany.id=?";
	private final static String GET_SALE_BY_ID = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,sales.client_count FROM employee LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id LEFT JOIN sales ON employee.id=sales.Employee_id WHERE employee.id=?";
	private final static String GET_SALES_ALL = "SELECT employee.name,employee.last_name,itcompany_has_employee.salary,sales.client_count FROM sales LEFT JOIN employee ON sales.Employee_id=employee.id LEFT JOIN itcompany_has_employee ON itcompany_has_employee.Employee_id=employee.id ";
	private final static String INSERT_INTO_SALE = " INSERT into sales (sales.client_count,sales.Employee_id) VALUES (?,?)";
	private final static String DELETE_SALE_BY_ID = "DELETE from sales WHERE id=?";
	private final static String UPDATE_SALEBY_ID = "UPDATE sales SET sales.client_count=? WHERE sales.id=?";

	@Override
	public List<Sale> getSaleByItId(int id) {

		ArrayList<Sale> sales = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_SALE_BY_ITID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sale sl = new Sale();
				sl.setId(rs.getInt("ID"));
				sl.setName(rs.getString("NAME"));
				sl.setLastName(rs.getString("LAST_NAME"));
				sl.setSalary(rs.getDouble("SALARY"));
				sl.setClientCount(rs.getInt("CLIENT_COUNT"));
				sales.add(sl);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return sales;
	}

	@Override
	public void insert(Sale sale, List<Integer> itcId) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			EmloyeeJDBC emp = new EmloyeeJDBC();
			int emplId = emp.insertEmployee(sale, itcId);
			ps = connection.prepareStatement(INSERT_INTO_SALE, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1, sale.getClientCount());
			ps.setInt(2, emplId);
			int rowAffected = ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next())
				sale.setId(rs.getInt(1));
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
	public void update(Sale sale) {
		try {
			connection = getConpool().getConnection();
			connection.setAutoCommit(false);
			ps = connection.prepareStatement(UPDATE_SALEBY_ID);
			ps.setInt(1, sale.getClientCount());
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
			ps = connection.prepareStatement(DELETE_SALE_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			connection.commit();
			LOGGER.info("Sale with " + id + " was deleted from Sales table");
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
	public Sale get(int id) {
		Sale sale = new Sale();
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_SALE_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			while (rs.next()) {
				sale.setName(rs.getString("NAME"));
				sale.setLastName(rs.getString("LAST_NAME"));
				sale.setSalary(rs.getDouble("SALARY"));
				sale.setClientCount(rs.getInt("ClIENT_COUNT"));
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return sale;
	}

	@Override
	public List<Sale> get() {
		List<Sale> sales = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_SALES_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Sale sale = new Sale();
				sale.setName(rs.getString("NAME"));
				sale.setLastName(rs.getString("LAST_NAME"));
				sale.setSalary(rs.getDouble("SALARY"));
				sale.setClientCount(rs.getInt("ClIENT_COUNT"));
				sales.add(sale);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			// TODO connection.setAutoCommit(true);
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return sales;
	}

	@Override
	public void insert(Sale entity) {
		// TODO Auto-generated method stub

	}
}
