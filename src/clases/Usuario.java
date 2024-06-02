package clases;

import java.sql.SQLException;

public class Usuario {
	private String usuario;
	private String password;

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Usuario(String usuario, String password) {
		this.usuario = usuario;
		this.password = password;
	}

	// Metodo que valida el login del usuario
	public String login(Database base) throws SQLException {
		if (this.buscaUsuario(base)) {
			base.ejecutaQuery("select * from usuario where usuario='" + this.usuario + "' and password='"
					+ this.password + "'");			
			if(base.getRs().next())
				return "OK";
			else
				return "La contraseña no es correcta";
		} else
			return "El usuario no existe";
	}

	//metodo que se encarga de registrar al usuario
	public String registrar(Database base) throws SQLException{
		if (!this.buscaUsuario(base)) {
			base.ejecutaQuery("insert into usuario values('" + this.usuario + "','"
					+ this.password + "')");
			return "OK";
		} else
			return "El usuario ya existe";
	}

	// metodo que se encarga ver si el usuario ya existe
	public boolean buscaUsuario(Database base) throws SQLException{
		base.ejecutaQuery("select * from usuario where usuario='" + this.usuario + "'");
		if (base.getRs().next()) {
			return true;
		} else
			return false;
	}
}
