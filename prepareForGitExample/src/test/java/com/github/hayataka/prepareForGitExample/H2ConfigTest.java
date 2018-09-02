package com.github.hayataka.prepareForGitExample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;

public class H2ConfigTest {

	// ~/db名および、ファイルパスを指定する
	// DB名だけ書くとカレントディレクトリにDBができる
	private final static String DRIVER_URL = "jdbc:h2:file:.target/awaretweet";

	private final static String DRIVER_NAME = "org.h2.Driver";

	private final static String USER_NAME = "sa";

	private final static String PASSWORD = "";

	@Test
	public void testCreate() {
		Connection con = this.createConnection();
		this.closeConnection(con);
	}
	
	public Connection createConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection con = DriverManager.getConnection(DRIVER_URL, USER_NAME, PASSWORD);
			return con;
		} catch (ClassNotFoundException e) {
			System.out.println("Can't Find H2 Driver.\n");
		} catch (SQLException e) {
			System.out.println("Connection Error.\n");
		}
		return null;
	}

	public void closeConnection(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
}
