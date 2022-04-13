package com.database.mysql.actions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class SqlConstants {

	private static Connection connection;
	
	static Statement getStatement(){
		Statement statement = null;
		// TODO : create and close connection for session
		connection = createConnection();
		try {
			statement = connection.createStatement();
		} catch (SQLException e) {
			System.out.println(e + " ERROR in getStatement()");
//			e.printStackTrace();
		}
		return statement;
		
	}
	
	private static Connection createConnection() {
		String connectionUrl = "jdbc:mysql://192.168.0.105:3306/sp_database";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(connectionUrl, "suraj", "suraj");
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println(e + " ERROR in createConnection()");
//			e.printStackTrace();
		}
		return connection;
	}

	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			System.out.println(e + " ERROR in closeConnection()");
		}
	}
}
