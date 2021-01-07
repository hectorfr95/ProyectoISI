package urjc.isi.myapp;
//Java program to implement 
//a Simple Registration Form 
//using Java Swing 
//source: https://www.geeksforgeeks.org/java-swing-simple-user-registration-form/

import javax.swing.*; 
import java.awt.*; 
import java.awt.event.*; 

public class Form extends JFrame implements ActionListener { 

	// Components of the Form 
	private Container c; 
	private JLabel title; 
	private JLabel name; 
	private JTextField tname; 
	
	private JLabel dni; 
	private JTextField tdni; 
	
	//identificador de examen
	private JLabel id; 
	private JTextField tid; 
	
	
	//botones de envio 
	private JButton sub; 
	private JButton reset; 
	private JTextArea tout; 
	private JLabel res; 
	private JTextArea resadd; 
	
	//nombre, apellidos y id del examen
	private String nombreAl;
	private String dniAl;
	private String idExAl;

	public Form() {
		//inicalizo las variables a null
		nombreAl = null;
		String dniAl = null;
		String idExAl = null;
		
		setTitle("Identification Form"); 
		setBounds(300, 90, 900, 600); 
		setDefaultCloseOperation(HIDE_ON_CLOSE); 
		setResizable(false); 
	
		c = getContentPane(); 
		c.setLayout(null); 
	
		title = new JLabel("Formulario de identificación"); 
		title.setFont(new Font("Arial", Font.PLAIN, 30)); 
		title.setSize(450, 30); 
		title.setLocation(250, 30); 
		c.add(title); 
	
		name = new JLabel("Nombre y apellidos"); 
		name.setFont(new Font("Arial", Font.PLAIN, 20)); 
		name.setSize(200, 20); 
		name.setLocation(100, 180); 
		c.add(name); 
	
		tname = new JTextField(); 
		tname.setFont(new Font("Arial", Font.PLAIN, 15)); 
		tname.setSize(190, 20); 
		tname.setLocation(300, 180); 
		//tname.setText("hola");
		c.add(tname); 
	
		dni = new JLabel("DNI/NIE"); 
		dni.setFont(new Font("Arial", Font.PLAIN, 20)); 
		dni.setSize(100, 20); 
		dni.setLocation(100, 230); 
		c.add(dni); 
	
		tdni = new JTextField(); 
		tdni.setFont(new Font("Arial", Font.PLAIN, 15)); 
		tdni.setSize(150, 20); 
		tdni.setLocation(200, 230); 
		c.add(tdni); 
		
		id = new JLabel("ID examen"); 
		id.setFont(new Font("Arial", Font.PLAIN, 20)); 
		id.setSize(150, 20); 
		id.setLocation(100, 280); 
		c.add(id); 
	
		tid = new JTextField(); 
		tid.setFont(new Font("Arial", Font.PLAIN, 15)); 
		tid.setSize(150, 20); 
		tid.setLocation(220, 280); 
		c.add(tid); 
		
	
		sub = new JButton("Enviar"); 
		sub.setFont(new Font("Arial", Font.PLAIN, 15)); 
		sub.setSize(100, 20); 
		sub.setLocation(150, 450); 
		sub.addActionListener(this); 
		c.add(sub); 
	
		reset = new JButton("Reset"); 
		reset.setFont(new Font("Arial", Font.PLAIN, 15)); 
		reset.setSize(100, 20); 
		reset.setLocation(270, 450); 
		reset.addActionListener(this); 
		c.add(reset); 
	
		tout = new JTextArea(); 
		tout.setFont(new Font("Arial", Font.PLAIN, 15)); 
		tout.setSize(300, 400); 
		tout.setLocation(500, 100); 
		tout.setLineWrap(true); 
		tout.setEditable(false); 
		c.add(tout); 

		res = new JLabel(""); 
		res.setFont(new Font("Arial", Font.PLAIN, 20)); 
		res.setSize(500, 25); 
		res.setLocation(100, 500); 
		c.add(res); 
	
		resadd = new JTextArea(); 
		resadd.setFont(new Font("Arial", Font.PLAIN, 15)); 
		resadd.setSize(200, 75); 
		resadd.setLocation(580, 175); 
		resadd.setLineWrap(true); 
		c.add(resadd); 
	
		setVisible(true); 
} 

	public String getName(){
		return nombreAl;
	}
	public String getDni(){
		return dniAl;
	}
	public String getIdEx(){
		return idExAl;
	}
	
	// method actionPerformed() 
	// to get the action performed 
	// by the user and act accordingly 
	public void actionPerformed(ActionEvent e){ 
		if (e.getSource() == sub) { 
			//if (term.isSelected()) { 
				String data1; 
				String data 
					= "Name : "
					+ tname.getText() + "\n"
					+ "DNI : "
					+ tdni.getText() + "\n"
					+ "ID examen : "
					+ tid.getText() + "\n"; 
				
				tout.setText(data);
				tout.setEditable(false); 
				res.setText("Registro correcto, Buena suerte!"); 
				nombreAl = tname.getText();
				dniAl = tdni.getText();
				idExAl = tid.getText();
			/*} 
			else { 
				tout.setText(""); 
				resadd.setText(""); 
				res.setText("Please accept the"
							+ " terms & conditions.."); 
			} */
		} 
	
		else if (e.getSource() == reset) { 
			String def = ""; 
			tname.setText(def); 
			tdni.setText(def); 
			tid.setText(def); 
			res.setText(def); 
			resadd.setText(def); 
		} 
	} 
} 

