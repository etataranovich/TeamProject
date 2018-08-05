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
import com.markava.itcompany.dao.IOfficeDao;
import com.markava.itcompany.model.Office;

public class OfficeJDBC extends AbstractBaseDao implements IOfficeDao {

	private final static Logger LOGGER = LogManager.getLogger(OfficeJDBC.class);

	private Connection connection = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private final static String GET_OFFICE_BY_ITID = "SELECT office.id, office.phone from Office LEFT JOIN itcompany ON itcompany.id=office.ITcompany_id WHERE itcompany.id=?";
	private final static String GET_OFFICE_BY_ID = "SELECT office.id, office.phone from office WHERE office.id=?";
	private final static String GET_OFFICE_ALL = "SELECT office.id, office.phone from office";
	private final static String INSERT_OFFICE = "INSERT into office (adress_id,ITcompany_id) VALUES (?,?)";
	private final static String UPDATE_OFFICE_BY_ID = "UPDATE office SET office.phone =? WHERE  office.id=?";
	private final static String DELETE_OFFICE_BY_ID = "DELETE from office WHERE id=?";

	@Override
	public List<Office> getOfficeByItId(int id) {

		ArrayList<Office> offices = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OFFICE_BY_ITID);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Office off = new Office();
				off.setId(rs.getInt("ID"));
				off.setPhone(rs.getString("PHONE"));
				// off.setAdress();
				offices.add(off);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return offices;
	}

	@Override
	public void insert(Office office, List<Integer> itcId) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(INSERT_OFFICE, Statement.RETURN_GENERATED_KEYS);
			ps.setInt(1,office.getAdress().getId());
			for(Integer idIt:itcId){
				ps.setInt(2,idIt);
			}
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if (rs.next()) {
				office.setId(rs.getInt(1));
			}
			LOGGER.info("ID" + office.getId() + " " + office.toString() + " was added to Office table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}

	}

	@Override
	public void update(Office office) {
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(UPDATE_OFFICE_BY_ID);
			ps.setString(1, office.getPhone());
			ps.executeUpdate();
			LOGGER.info(office.toString() + " was updated at Office table");
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
			ps = connection.prepareStatement(DELETE_OFFICE_BY_ID);
			ps.setInt(1, id);
			ps.executeUpdate();
			LOGGER.info("Office with " + id + " was deleted from Office table");
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
	}

	@Override
	public Office get(int id) {
		Office off = new Office();
		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OFFICE_BY_ID);
			ps.setInt(1, id);
			rs = ps.executeQuery();
			rs.next();
			off.setId(rs.getInt("ID"));
			off.setPhone(rs.getString("PHONE"));
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return off;
	}

	@Override
	public List<Office> get() {
		ArrayList<Office> offices = new ArrayList<>();

		try {
			connection = getConpool().getConnection();
			ps = connection.prepareStatement(GET_OFFICE_ALL);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Office off = new Office();
				off.setId(rs.getInt("ID"));
				off.setPhone(rs.getString("PHONE"));
				// c.setAddress(rs.getString("ADDRESS"));
				offices.add(off);
			}
		} catch (SQLException | InterruptedException e) {
			LOGGER.error(e.getMessage());
		} finally {
			closeRSet(rs);
			closePrStatement(ps);
			getConpool().releaseConnection(connection);
		}
		return offices;
	}

	@Override
	public void insert(Office entity) {
		// TODO Auto-generated method stub
		
	}
}
