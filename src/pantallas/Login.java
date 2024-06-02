package pantallas;

import java.awt.EventQueue;
import javax.swing.JFrame;
import clases.Database;
import clases.Usuario;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;
import javax.swing.JRootPane;
import javax.swing.JPasswordField;
import javax.swing.JDesktopPane;
import java.awt.Color;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Login {
	private JFrame frame;
	private static Database base = new Database();// declaro e inicializo la conexion a la base de datos
	private Usuario user;

	// Declaro los input
	private JTextField Usuario;
	private JPasswordField Password;
	// declaro los botones
	private JButton Registro;
	private JButton Login;
	private JButton Salir;
	// Declaro las pantallas
	private SubirArchivos archivo; // Pantalla de carga de archivo
	private RegistroUsuario registro; // Pantalla de registro

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window = new Login();
					
					window.frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	// metodo que inicializa el componente
	private Login() {
		archivo = new SubirArchivos(this);// Inicializa la pantalla de subir archivos
		registro = new RegistroUsuario(this);
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setUndecorated(true);
		frame.getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		frame.getContentPane().setLayout(null);
		frame.setLocationRelativeTo(null);
		/*******************************************/
		/*******************************************/
		// campo usuario
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(125, 85, 46, 14);
		frame.getContentPane().add(lblUsuario);
		Usuario = new JTextField();
		Usuario.setBounds(181, 82, 86, 20);
		frame.getContentPane().add(Usuario);
		Usuario.setColumns(10);
		/*******************************************/
		// campo password
		Password = new JPasswordField();
		Password.setBounds(181, 124, 86, 20);
		frame.getContentPane().add(Password);
		Password.setColumns(10);
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(125, 127, 46, 14);
		frame.getContentPane().add(lblPassword);
		/*******************************************/
		// Boton de login
		Login = new JButton("Login");
		Login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//valida que el campo usuario este completo
				if (Usuario.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "El campo Usuario es obligatorio");
					Usuario.setBackground(Color.red);
					Usuario.requestFocus();
				}
				//valida que el campo password este completo
				if (new String(Password.getPassword()).length() == 0) {
					JOptionPane.showMessageDialog(null, "El campo Password es obligatorio");
					Password.setBackground(Color.red);
					Password.requestFocus();
				}
				//si los campos estan completos valida el login
				if (Usuario.getText().length() > 0 && new String(Password.getPassword()).length() > 0) {
					try {	
						user = new Usuario(Usuario.getText(),new String(Password.getPassword()));
						//verifica si el login es correcto
						//String resultado = base.login(Usuario.getText(), Password.getPassword());
						String resultado = user.login(base);
						if (resultado == "OK") {
							//si es correcto genera el usuario y muestra la pantalla de archivo
							archivo.inicializa(user);
							archivo.setVisible(true);
							frame.setVisible(false);
							frame.dispose();
						} else {
							JOptionPane.showMessageDialog(null, resultado);
							clear();
						}
					} catch (Exception error) {
						JOptionPane.showMessageDialog(null, error.getMessage());
					}
				}
			}
		});
		Login.setBounds(178, 161, 89, 23);
		frame.getContentPane().add(Login);
		/*******************************************/
		// Botón de registro
		Registro = new JButton("Registro");
		Registro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				registro.setVisible(true);
				frame.setVisible(false);
				frame.dispose();
			}
		});
		Registro.setBounds(10, 227, 89, 23);
		frame.getContentPane().add(Registro);
		/*******************************************/
		// Botón de salir
		Salir = new JButton("Salir");
		Salir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		});
		Salir.setBounds(317, 227, 89, 23);
		frame.getContentPane().add(Salir);
		// Recuadro rojo
		JDesktopPane desktopPane = new JDesktopPane();
		desktopPane.setBackground(Color.CYAN);
		desktopPane.setBounds(74, 48, 295, 168);
		frame.getContentPane().add(desktopPane);/*******************************************/
	}

	// metodo que sirve para limpiar los campos
	public void clear() {
		Usuario.setText("");
		Password.setText("");
	}

	// metodo que sirve apra manejar la visibilidad del componente
	public void visible(boolean visible) {
		frame.setVisible(visible);
	}

	public static Database getBase() {
		return base;
	}
}
