package nxtgen.keycoak.user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	String driverClassName = "com.mysql.cj.jdbc.Driver";
	String connectionUrl = "jdbc:mysql://localhost:3306/keycloak?allowPublicKeyRetrieval=true&useSSL=false";
	String dbUser = "keycloak-user";
	String dbPwd = "1h7rHhfy3";

	private static ConnectionFactory connectionFactory = null;

	private ConnectionFactory() {
		try {
			System.out.println("here 1");
			Class.forName(driverClassName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		conn = DriverManager.getConnection(connectionUrl, dbUser, dbPwd);
		System.out.println("here 2");
		return conn;
	}

	public static ConnectionFactory getInstance() {
		if (connectionFactory == null) {
			connectionFactory = new ConnectionFactory();
		}
		return connectionFactory;
	}

}
