package clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	private String usuario = "poc";
	private String clave = "poc";
	private String server = "localhost";
	private Number puerto = 1521;
	private String service = "XEPDB1";
	private Connection con;
	private ResultSet rs;

	public ResultSet getRs() {
		return rs;
	}

	public Database() {
		try {
			con = DriverManager.getConnection("jdbc:oracle:thin:@" + server + ":" + puerto + "/" + service, usuario,
					clave);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Connection getCon() {
		return con;
	}

	// metodo que ejecuta el qeury
	public void ejecutaQuery(String query) throws SQLException {
		Statement stmt = con.createStatement();
		rs = stmt.executeQuery(query);
	}

}
