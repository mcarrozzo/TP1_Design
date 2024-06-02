package pantallas;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JPasswordField;
import clases.Usuario;
import javax.swing.SwingConstants;

public class RegistroUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField usuario;
	private JPasswordField password;
	private JButton Cancel;
	private Login parent;
	private JButton Registro;
	private JLabel lblNewLabel;
	private Usuario user;
	private JPasswordField password2;
	private JLabel lblPassword_1;

	public RegistroUsuario(Login padre) {
		this.parent = padre;
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUsuario.setBounds(28, 85, 143, 14);
		contentPane.add(lblUsuario);

		JLabel lblPassword = new JLabel("Password");
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(28, 127, 143, 14);
		contentPane.add(lblPassword);

		usuario = new JTextField();
		usuario.setBounds(181, 82, 143, 20);
		contentPane.add(usuario);
		usuario.setColumns(10);

		password = new JPasswordField();
		password.setBounds(181, 124, 143, 20);
		contentPane.add(password);
		password.setColumns(10);

		password2 = new JPasswordField();
		password2.setColumns(10);
		password2.setBounds(181, 165, 143, 20);
		contentPane.add(password2);

		lblPassword_1 = new JLabel("Re ingresar Password");
		lblPassword_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword_1.setBounds(28, 168, 143, 14);
		contentPane.add(lblPassword_1);

		Registro = new JButton("Registro");
		Registro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (usuario.getText().length() == 0) {
					JOptionPane.showMessageDialog(null, "El campo Usuario es obligatorio");
					usuario.setBackground(Color.red);
					usuario.requestFocus();
				}
				if (new String(password.getPassword()).length() == 0) {
					JOptionPane.showMessageDialog(null, "El campo Password es obligatorio");
					password.setBackground(Color.red);
					password.requestFocus();
				}
				if (!new String(password.getPassword()).equals(new String(password2.getPassword())))
					JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden");
				String resultado = validaPassword();
				if (resultado != "OK")
					JOptionPane.showMessageDialog(null, resultado);
				if (usuario.getText().length() > 0 && new String(password.getPassword()).length() >= 0
						&& new String(password.getPassword()).equals(new String(password2.getPassword()))
						&& resultado == "OK") {
					try {
						user = new Usuario(usuario.getText(), new String(password.getPassword()));
						resultado = user.registrar(Login.getBase());
						if (resultado == "OK") {
							JOptionPane.showMessageDialog(null, "Usuario registrado");
							parent.clear();
							parent.visible(true);
							dispose();
						} else {
							JOptionPane.showMessageDialog(null, resultado);
							usuario.setText("");
							password.setText("");
						}
					} catch (Exception error) {
						JOptionPane.showMessageDialog(null, error.getMessage());
					}
				}
			}
		});
		Registro.setBounds(214, 203, 89, 23);
		contentPane.add(Registro);

		Cancel = new JButton("Cancel");
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.clear();
				parent.visible(true);
				dispose();
			}
		});
		Cancel.setBounds(121, 203, 89, 23);
		contentPane.add(Cancel);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		lblNewLabel = new JLabel("Registro de usuario");
		lblNewLabel.setBounds(20, 11, 151, 14);
		contentPane.add(lblNewLabel);
	}

	private String validaPassword() {
		if (new String(password.getPassword()).contains(usuario.getText()))
			return "La contraseña no puede contener al usuario";
		return "ok";
	}

}
