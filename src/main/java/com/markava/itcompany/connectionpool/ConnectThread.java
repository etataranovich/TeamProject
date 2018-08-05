package com.markava.itcompany.connectionpool;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectThread extends Thread {

	private final static Logger LOGGER = LogManager.getLogger(ConnectThread.class);

	private LazyConnectionPool conPool;
	private static Logger logger = LogManager.getLogger();

	public ConnectThread(LazyConnectionPool conPool) {
		super();
		this.conPool = conPool;
	}

	public void run() {
		logger.log(Level.INFO, "ConThread is created");
		conPool.getConnection();
	}

	public static void main(String[] args) {

		LazyConnectionPool conPool = LazyConnectionPool.getInstance();
		for (int i = 0; i < 7; i++) {
			ConnectThread thread = new ConnectThread(conPool);
			thread.start();
		}
	}

	// =======================================================
}
