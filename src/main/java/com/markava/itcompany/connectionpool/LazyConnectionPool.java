package com.markava.itcompany.connectionpool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LazyConnectionPool {

	private static Logger logger = LogManager.getLogger();
	private BlockingQueue<Connection> connections;
	private static LazyConnectionPool instance = null;
	private int poolSize = 5;
	private String url;
	private String user;
	private String password;
	private Properties prop;
	private static ReentrantLock locker = new ReentrantLock();

	private LazyConnectionPool() {
		prop = new Properties();
		try {
			InputStream ist = new FileInputStream("src/main/resources/database/db.properties");
			prop.load(ist);
			url = prop.getProperty("jdbc.url");
			user = prop.getProperty("jdbc.user");
			password = prop.getProperty("jdbc.password");
			connections = new ArrayBlockingQueue<Connection>(poolSize);
		} catch (Exception e) {
			logger.log(Level.ERROR, "file not found", e.getMessage());
		}
	}

	public static LazyConnectionPool getInstance() {
		if (instance == null)
			instance = new LazyConnectionPool();
		return instance;
	}

	private void init() {
		// driver = prop.getProperty("jdbc.driver");
		try {
			Connection conn = DriverManager.getConnection(url, user, password);
			connections.add(conn);
			poolSize--;
		} catch (SQLException e) {
			logger.log(Level.ERROR, e.getCause());
		}
	}

	public Connection getConnection() {
		Connection conn = null;
		try {
			locker.lock();
			if (connections.size() == 0 && poolSize > 0) {
				init();
			}
			conn = connections.take();
			logger.log(Level.INFO, "connection was taken");
		} catch (InterruptedException e) {
			logger.log(Level.ERROR, "ERROR take connection", e);
		} finally {
			locker.unlock();
		}
		return conn;
	}

	public void releaseConnection(Connection conn) {
		if (conn != null) {
			connections.add(conn);
			poolSize++;
		} else {
			logger.log(Level.ERROR, "ErrorCloseDBConnection");
		}
	}
}
