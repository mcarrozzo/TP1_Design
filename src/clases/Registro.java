package clases;

import java.sql.SQLException;

public class Registro {
	private String nombre;
	private String apellido;
	private String materia;
	private String nota;

	public Registro(String nombre, String apellido, String materia, String nota) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.materia = materia;
		this.nota = nota;
	}
	
	//metodo que se encargar de guardar en la base los registros
	public void insertaRegistro(Database base) throws SQLException {
		base.ejecutaQuery("insert into materias_notas values ('"+this.nombre+"','"+this.apellido+"','"+this.materia+"',"+this.nota+")");
	}
}
