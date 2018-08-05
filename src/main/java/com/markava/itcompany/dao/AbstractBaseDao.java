package com.markava.itcompany.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.markava.itcompany.connectionpool.ConnectionPool;


public abstract class AbstractBaseDao {
	private final static Logger LOGGER = LogManager.getLogger();
	private ConnectionPool myConpool;
	
	public void closePrStatement(PreparedStatement ps){
		if (ps != null){
			try {
				ps.close();
			} catch (SQLException e) {
				LOGGER.error(e.getCause());
			}
		}
	}
	public void closeRSet(ResultSet rs){
		if (rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				LOGGER.error(e.getCause());
			}
		} 
	}
	public void closepsAssignment(PreparedStatement psAssignment){
		if (psAssignment != null){
			try {
				psAssignment.close();
			} catch (SQLException e) {
				LOGGER.error(e.getCause());
			}
		} 
	}
	
	
	
	
	

	public ConnectionPool getConpool() {
		myConpool = ConnectionPool.getInstance();
		return myConpool;
	}

	public void setConpool(ConnectionPool myConpool) {
		this.myConpool = myConpool;
	}

}

