package com.github.hayataka.prepareForGitExample.initializer;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbInitializer implements Closeable {

	// ~/db名および、ファイルパスを指定する
	// DB名だけ書くとカレントディレクトリにDBができる
	// http://www.h2database.com/html/cheatSheet.html 参考
	private final static String DRIVER_URL = "jdbc:h2:mem:test";

	private final static String DRIVER_NAME = "org.h2.Driver";

	private final static String USER_NAME = "sa";

	private final static String PASSWORD = "";
	
	private Connection _con;

	public BufferedReader fileToReader(String classPath) {

		InputStream test = this.getClass().getResourceAsStream(classPath);		
		if (test == null) {
				throw new RuntimeException("fileが存在しません：" + classPath);
		}
		BufferedReader fr = new BufferedReader(	new InputStreamReader(test));
		BufferedReader br = new BufferedReader(fr);
		return br;
	}
	
	public String toString(List<String> lines, String delimiter) {
		
		String one = String.join(delimiter, lines);
		return one;
		
		
	}
	
	public List<String> toLine(BufferedReader br) {
		
		
		String line;
		List<String> list = new ArrayList<>();
		try {
			line = br.readLine();
			while (line != null) {
				list.add(line);
				line = br.readLine();
			}
		} catch (IOException e) {
			throw new RuntimeException("readline Error.", e);
		}
		return list;
	}

	public Connection createConnection() {
		try {
			Class.forName(DRIVER_NAME);
			Connection con = DriverManager.getConnection(DRIVER_URL, USER_NAME, PASSWORD);
			this._con = con;
			return con;
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("Can't Find H2 Driver.", e);
		} catch (SQLException e) {
			throw new RuntimeException("Connection Error.", e);
		}
	}

	public PreparedStatement prepareStatement(String sql) {
		PreparedStatement prepareStatement;
		try {
			prepareStatement = _con.prepareStatement(sql);
		} catch (SQLException e) {
			throw new RuntimeException("prepareStatement Error.", e);
		}
		return prepareStatement;
	}
	public void close() {
		if (_con == null) {
			return;
		}
		try {
			_con.close();
		} catch (Exception e) {
			throw new RuntimeException("close Error.", e);
		}
	}

	public void close(PreparedStatement ps) {

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				throw new RuntimeException("ps close Error.", e);
			}
		}
		
	}

	public void prepare() throws IOException, SQLException  {
		try (DbInitializer init = new DbInitializer()) {
			init.createConnection();

			try (BufferedReader br = init.fileToReader("/schema.sql")) {
				List<String> line = init.toLine(br);
				String delimiter = System.getProperty("line.separator");
				String sql = init.toString(line, delimiter);
				PreparedStatement ps = init.prepareStatement(sql);
				ps.executeUpdate();
				init.close(ps);
			}

			List<String> line;
			try (BufferedReader br = init.fileToReader("/data.sql")) {
				line = init.toLine(br);
			}
			for (String sql : line) {
				PreparedStatement ps = init.prepareStatement(sql);
				ps.executeUpdate();
				// System.out.println(ret);
				init.close(ps);
			}

		}
	}
}
