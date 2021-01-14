package urjc.isi.servidor;

import static spark.Spark.*;
//import spark.Request;
//import spark.Response;
import urjc.isi.servidor.App;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
//import java.sql.PreparedStatement;
import java.util.Random;
//import java.util.StringTokenizer;
//
//import javax.servlet.MultipartConfigElement;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import urjc.isi.servidor.base_datos;

public class App 
{
	static String cosa = null;

	public static void main(String[] args) throws 
	ClassNotFoundException, SQLException {
		port(getHerokuAssignedPort());
		
		examenDao examenDao = new examenDao();
		alumnoDao alumnoDao = new alumnoDao();
		realizaExamenDao realizaExamenDao = new realizaExamenDao();
	//	int examen=0;//Si es 0 el examen esta finalizado, 1 está activo.

		Random rnd = new Random();
		
		get("/profesor", (req, res) -> {
			int random = rnd.nextInt()*(-1);
			String result = "<form action='/"+random+  "' method='post'>"
			+ "<fieldset>"
			+ "<p>INTRODUZCA LOS DATOS:</p>\n"
			+ "<p>ID_Examen</p>\n"
			+ "<input type='text' name='id_Examen' required='true'>"
		    + "<input type=\"submit\" value=\"Comenzar examen\">"
		    + "</fieldset>" 
		    + "</form>";
			
			return result;
		});
		
		post("/alumno", (req, res) -> {
			
			String result = req.queryParams("nombre")+ " " +
			req.queryParams("dni")+ " " +
			req.queryParams("idex");
			System.out.println(result);
			cosa = result;
			String dni = req.queryParams("dni");
			String nombre = req.queryParams("nombre");
			int id_ex = Integer.parseInt(req.queryParams("idex"));
			String ip = req.ip();
			
			alumno alumnoObject = new alumno(dni,nombre, 4568, ip);
			alumnoDao.save(alumnoObject);
			realizaExamen realizaExamenObject = new realizaExamen(id_ex,dni, null);
			realizaExamenDao.save(realizaExamenObject);
			return result;
		});
		
		
		get("/cosa", (req, res) -> 
			"<h1> El examen con ID "+ cosa + "</h1>"
		);
		
		
		post("/:random", (req, res) -> { // Revisar si es get o post
			String path = req.uri();
			String aux = path.substring(1,path.length());
			//Añadido
			int id_examen = Integer.parseInt(req.queryParams("id_Examen"));
			//-Añadido
			String result ="<h1> El examen con ID "+id_examen+" se ha iniciado con el numero generado: "+ aux + "</h1>"
			+ "<form action='/finalizar' method='post'>"		
		    + "<input type=\"submit\" value=\"Finalizar examen\">"	    
		    + "</form>";
			
			//Añadido
			examen examenObject = new examen(id_examen, "fecha", "asig");
			examenDao.save(examenObject);
			//-Añadido
			
			return result;
		});
	

	    }
		
//		get("/alumnos", (req, res) -> 
//			String result = "<form action='/examinar' method='post'>"
//			+ "<fieldset>"
//			+ "<p>INTRODUZCA LOS DATOS:</p>\n"
//			+ "<p>Nombre: <input type='text' name='nombre_alumno' required='true'></p>\n"
//			+ "<p>Apellidos: <input type='text' name='apellido_alumno' required='true'></p>\n"
//			+ "<p>ID de examen: <input type='text' name='id_examen_alumno' required='true'></p>\n"
//			+ "<input type=\"submit\" value=\"Comenzar examen\"></fieldset></form>";
//			
//			return result;
//		);
//		
//		
//		
//		post("/examinar", (req, res) -> {
//			//Añadido
//			String nombre = req.queryParams("nombre_alumno");
//			String apellido = req.queryParams("apellido_alumno");
//			String id_alumno = req.queryParams("id_examen_alumno");
//			//-Añadido
//			String result = nombre + " " + apellido  + " has iniciado el examen con ID: " +"<strong>"+ id_alumno +"</strong>";
//			String nombre_alumno = nombre + " " + apellido;
//			
//			//Añadido
//			String IP = "1";
//			String puerto = "22";
//			base_datos.insert_alumno(connection, nombre_alumno, IP, puerto);
//			//-Añadido
//			
//			//EJECUTAR CODIGO DE COMMITS
//			
//			return result;
//		    });
	
	
	
	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
		    return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
	    }
}
