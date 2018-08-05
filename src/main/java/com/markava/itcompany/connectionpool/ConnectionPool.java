package com.markava.itcompany.connectionpool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {

	private final static Logger LOGGER = LogManager.getLogger();
	private static final String DB_URL = "jdbc:mysql://localhost:3306/itdb?serverTimezone=UTC&useSSL=false";
	private static final String USER = "root";
	private static final String PASS = "1234";
	private int poolSize = 5;
	private BlockingQueue<Connection> connections;

	private static ConnectionPool instance = null;

	private ConnectionPool() {
		init();
	}

	public static ConnectionPool getInstance() {
		if (instance == null)
			instance = new ConnectionPool();
		return instance;
	}

	private void init() {
		connections = new ArrayBlockingQueue<Connection>(poolSize);
		for (int i = 0; i < poolSize; i++) {
			try {
				Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				connections.add(conn);
			} catch (SQLException e) {
				LOGGER.error(e.getCause());
			}
		}
	}

	public Connection getConnection() throws InterruptedException {
		Connection conn = null;
		LOGGER.info("Try to get connection to db");
		try {
			conn = connections.poll(400, TimeUnit.MILLISECONDS);
		} catch (InterruptedException | NullPointerException e) {
			LOGGER.error("Something get wrong" + e);
		}
		LOGGER.info("Connected");
		poolSize--;
		return conn;
	}

	public void releaseConnection(Connection conn) {
		if (conn != null) {
			connections.add(conn);
			poolSize++;
		} else {
			LOGGER.error("ErrorCloseDBConnection");
		}
	}

	public int getPoolSize() {
		return poolSize;
	}
}