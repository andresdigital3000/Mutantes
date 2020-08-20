package util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

public class Configuration {

	public Connection getConnection() throws SQLException, IOException {
		String secretBinaryString = SecretsManagerUtil.obtenerSecret();
	    final ObjectMapper objectMapper = new ObjectMapper();

	    @SuppressWarnings("unchecked")
		final HashMap<String, String> secretMap = objectMapper.readValue(secretBinaryString, HashMap.class);

		String url = String.format("jdbc:mysql://%s:%s", secretMap.get("host"), secretMap.get("port"));//"jdbc:mysql://phdb-instance-1.cfjaowrl2xkp.us-east-1.rds.amazonaws.com:3306";
		String username = secretMap.get("username");
		String password = secretMap.get("password");

		Connection conn = DriverManager.getConnection(url, username, password);
		return conn;
	}
}
