package pantallas;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.SQLException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import clases.Usuario;
import clases.Registro;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.Color;

public class SubirArchivos extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton Logout;
	private Login parent;
	private JFileChooser fc;
	private JButton upload;
	private JButton browser;
	private JButton guardar;
	private JTextField archivo;
	private JLabel lblUsuario;
	private Registro registro;
	private JTable tabla;
	@SuppressWarnings("serial")
	private DefaultTableModel model = new DefaultTableModel(new Object[][] {},
			new String[] { "", "Nombre", "Apellido", "Materia", "Nota" }) {
		@Override
		public Class<?> getColumnClass(int col) {
			return getValueAt(0, col).getClass();
		}
	};
	private JLabel lblNewLabel;

	public SubirArchivos(Login padre) {
		this.parent = padre;
		getContentPane().setLayout(null);
		setUndecorated(true);
		getRootPane().setWindowDecorationStyle(JRootPane.NONE);
		setBounds(100, 100, 500, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		/*************************************************************************************************/
		// Boton de logout
		Logout = new JButton("Logout");
		Logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.clear();
				parent.visible(true);
				dispose();
			}
		});
		Logout.setBounds(411, 3, 89, 23);
		contentPane.add(Logout);
		/*************************************************************************************************/
		// separador
		JSeparator separator = new JSeparator();
		separator.setBounds(0, 28, 500, 12);
		getContentPane().add(separator);
		/*************************************************************************************************/
		// inicializa el manejo de la subida del archivo
		archivo = new JTextField();
		archivo.setToolTipText("Solo CSV, PDF o EXCEL");
		archivo.setBounds(10, 31, 190, 30);
		getContentPane().add(archivo);
		browser = new JButton("Browse");
		browser.setBounds(210, 31, 80, 30);
		getContentPane().add(browser);
		browser.addActionListener(this);
		upload = new JButton("Upload");
		upload.setEnabled(false);
		upload.setBounds(300, 31, 80, 30);
		getContentPane().add(upload);
		upload.addActionListener(this);
		fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
		FileNameExtensionFilter csv = new FileNameExtensionFilter("Archivo CSV (.csv)", "csv");
		fc.addChoosableFileFilter(csv);
		FileNameExtensionFilter pdf = new FileNameExtensionFilter("Archivo PDF (.pdf)", "pdf");
		fc.addChoosableFileFilter(pdf);

		/*************************************************************************************************/
		// Inicializa la tabla
		tabla = new JTable();
		tabla.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tabla.setModel(model);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(tabla);
		scrollPane.setBounds(10, 85, 480, 270);
		contentPane.add(scrollPane);
		/*************************************************************************************************/
		/*************************************************************************************************/
		// boton Guardar en la base los registros seleccionados
		guardar = new JButton("Guardar");
		guardar.setEnabled(false);
		guardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < tabla.getRowCount(); i++) {
					if ((Boolean) tabla.getModel().getValueAt(i, 0)) {
						try {
							String nombre = tabla.getModel().getValueAt(i, 1).toString();
							String apellido = tabla.getModel().getValueAt(i, 2).toString();
							String materia = tabla.getModel().getValueAt(i, 3).toString();
							String nota = tabla.getModel().getValueAt(i, 4).toString();
							registro = new Registro(nombre, apellido, materia, nota);
							registro.insertaRegistro(Login.getBase());
							registro = null;
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}

				}
			}
		});
		guardar.setBounds(401, 366, 89, 23);
		contentPane.add(guardar);

		lblNewLabel = new JLabel("Solo se permiten CSV, PDF o XLS");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setBounds(10, 60, 190, 14);
		contentPane.add(lblNewLabel);
	}

	public void inicializa(Usuario user) {
		// this.base = base;
		lblUsuario = new JLabel("Usuario : " + user.getUsuario());
		lblUsuario.setBounds(2, 3, 100, 23);
		contentPane.add(lblUsuario);
	}

	// metodo qeu maneja los botones de browser y upload de archivo
	public void actionPerformed(ActionEvent e) {
		try {
			if (e.getSource() == browser) {
				fc.showOpenDialog(null);
				if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".csv")
						|| fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".pdf")
						|| fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xls")
						|| fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xlsx")) {
					File f1 = fc.getSelectedFile();
					archivo.setText(f1.getAbsolutePath());
					upload.setEnabled(true);
				} else {
					JOptionPane.showMessageDialog(null, "La extencion del archivo no esta soportada");
				}
			}
			if (e.getSource() == upload) {
				if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".csv"))
					csv();
				if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".pdf"))
					pdf();
				if (fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xls")
						|| fc.getSelectedFile().getAbsolutePath().toLowerCase().endsWith(".xlsx"))
					excel();
			}
		} catch (Exception ex) {
		}
	}

	// metodo que se encarga del manejo del archico si es un CVS
	public void csv() throws IOException {
		String line = "";
		String splitBy = ";";
		File f1 = fc.getSelectedFile();
		try (BufferedReader br = new BufferedReader(new FileReader(f1))) {
			while ((line = br.readLine()) != null) // returns a Boolean value
			{
				String[] alumno = line.split(splitBy); // use comma as separator
				model.addRow(new Object[] { false, alumno[0], alumno[1], alumno[2], alumno[3] });
			}
		}
		guardar.setEnabled(true);
	}

	// metodo que se encarga del manejo del archico si es un PDF
	public void pdf() throws IOException {
		System.out.println("pdf");
		//PdfDocument pdf = PdfDocument.fromFile(Paths.get("C:\\Users\\dcalandra\\Downloads\\materias.pdf"));
		guardar.setEnabled(true);
	}

	// metodo que se encarga del manejo del archico si es un EXCEL
	public void excel() {
		System.out.println("excel");
		guardar.setEnabled(true);
	}
}
