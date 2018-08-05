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
import com.markava.itcompany.dao.IItcompanyDao;
import com.markava.itcompany.model.Itcompany;

public class ItcompanyJDBC extends AbstractBaseDao implements IItcompanyDao {

	private final static Logger LOGGER = LogManager.getLogger(ItcompanyJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_ITCOMPANY_BY_ID = "SELECT itcompany.id, itcompany.name, itcompany.reg_number, itcompany.year from itcompany WHERE itcompany.id=?";
	private final static String GET_ITCOMPANY_ALL = "SELECT itcompany.id,itcompany.name, itcompany.reg_number, itcompany.year from itcompany";
	private final static String INSERT_ITCOMPANY = "INSERT into itcompany (itcompany.name, itcompany.reg_number, itcompany.year) VALUES (?,?,?)";
	private final static String DELETE_ITCOMPANY_BY_ID = "DELETE from itcompany WHERE id=?";
	private final static String UPDATE_ITCOMPANY_BY_ID = "UPDATE itcompany SET itcompany.name =?, itcompany.reg_number =?, itcompany.year=?  WHERE  itcompany.id=?";

	@Override
	public Itcompany get(int id) {

		Itcompany itc = new Itcompany();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ITCOMPANY_BY_ID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			rs.next();
			itc.setId(rs.getInt("ID"));
			itc.setName(rs.getString("NAME"));
			itc.setRegNumber(rs.getString("REG_NUMBER"));
			itc.setYear(rs.getString("YEAR"));
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return itc;
	}

	@Override
	public void insert(Itcompany itcompany) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(INSERT_ITCOMPANY, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, itcompany.getName());
			ps.setString(2, itcompany.getRegNumber());
			ps.setString(3, itcompany.getYear());
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				itcompany.setId(rs.getInt(1));
			}
			LOGGER.info("ID" + itcompany.getId() + " " + itcompany.toString() + " was added to Customer table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}

	@Override
	public void update(Itcompany itcompany) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(UPDATE_ITCOMPANY_BY_ID);
			ps.setString(1, itcompany.getName());
			ps.setString(2, itcompany.getRegNumber());
			ps.setString(3, itcompany.getYear());
			ps.executeUpdate();
			LOGGER.info(itcompany.toString() + " was updated at Itcompany table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}

	@Override
	public void delete(int id) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(DELETE_ITCOMPANY_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			LOGGER.info("Itcompany with " + id + " was deleted from Itcompany table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}

	@Override
	public List<Itcompany> get() {
		ArrayList<Itcompany> its = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_ITCOMPANY_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Itcompany it = new Itcompany();
				it.setId(rs.getInt("ID"));
				it.setName(rs.getString("NAME"));
				it.setRegNumber(rs.getString("REG_NUMBER"));
				it.setYear(rs.getString("YEAR"));
				// c.setAddress(rs.getString("ADDRESS"));
				its.add(it);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return its;
	}
}
