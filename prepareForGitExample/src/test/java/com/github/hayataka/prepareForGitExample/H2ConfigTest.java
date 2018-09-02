package com.github.hayataka.prepareForGitExample;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.github.hayataka.prepareForGitExample.initializer.DbInitializer;

public class H2ConfigTest {

	@Test
	public void testCreate() {
		DbInitializer init = new DbInitializer();
		init.createConnection();
		init.close();
	}

	@Test
	public void testInitialData() throws IOException, SQLException {

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
