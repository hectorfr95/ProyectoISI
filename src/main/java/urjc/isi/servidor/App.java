package urjc.isi.servidor;

import static spark.Spark.*;
//import spark.Request;
//import spark.Response;
import urjc.isi.servidor.App;


//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
//import java.sql.Statement;
//import java.sql.PreparedStatement;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.MultipartConfigElement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;


public class App
{
	private static httprequest requestToClient = new httprequest();

	public static void main(String[] args) throws
	ClassNotFoundException, SQLException, URISyntaxException {
		port(getHerokuAssignedPort());

		examenDao examenDao = new examenDao();
		alumnoDao alumnoDao = new alumnoDao();
		realizaExamenDao realizaExamenDao = new realizaExamenDao();

	

	redirect.get("/", "/profesor");

	get("/profesor", (req, res) -> {

		String result = "<form action='/profesor' method='post'>"
		+ "<fieldset>"
		+ "<p>INTRODUZCA LOS DATOS:</p>\n"

		+ "<p>Asignatura:  "
		+ "<input type='text' name='asignatura' required='true'><br><br>"
		+ "<input type=\"submit\" value=\"Comenzar examen\">"
			+ "</fieldset>"
			+ "</form></p>";

		return result;
	});

	post("/alumno", (req, res) -> {
		
		String dni = req.queryParams("dni");
		String nombre = req.queryParams("nombre");
		int id_ex = Integer.parseInt(req.queryParams("idex"));
		String ip = req.ip(); //IP de la petición
		int port = req.port(); //PUERTO de la petición
		
		
		
		alumno alumnoObject = new alumno(dni,nombre, port, ip);
		alumnoDao.save(alumnoObject);
		realizaExamen realizaExamenObject = new realizaExamen(id_ex,dni, null);
		realizaExamenDao.save(realizaExamenObject);
		return null;
	});


	


	post("/profesor", (req, res) -> { // Revisar si es get o post
		int id_examen = (int) (Math.random()*1000000000 +1);


		//Añadido

		String asignatura = req.queryParams("asignatura");
		//-Añadido
		String result ="<h1> Examen de la asignatura <strong style='color:red'>"+ asignatura + "</strong> creado con <u>éxito</u></h1>"
		+ "<form action='/"+id_examen+"' method='get'>"
			+ "<input type=\"submit\" value=\"Finalizar examen\">"
			+ "</form><br>"
			+"<h2>Se ha generado el examen en la url "+id_examen+"</h2>";


		//Añadido
		Date fecha = new Date();
		long lnMilisegundos = fecha.getTime();
		java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);

		examen examenObject = new examen(id_examen, sqlDate, asignatura);
		examenDao.save(examenObject);
		//-Añadido


		return result;
	});
	
	get("/prueba", (req, res) -> {	//URL DE PRUEBA PARA HACER GET /profesor	
		requestToClient.sendGetprueba();
		return "EXITO";
	});
	
	get("/:random", (req, res) -> {
		//COMPROBAR SI EL RECURSO :RANDOM SE ENCUENTRA EN LA BD, SI NO ES ASI, DEVOLVER 404 NOT FOUND
		
		//BUCLE QUE RECORRA LOS ALUMNOS DEL ID DE EXAMEN HACIENDO GET IP+PUERTO/fin
		// requestToClient.sendGetAlumno(ip, puerto);
		
		String result = "<h1>Examen con id "+req.params(":random")+" finalizado!</h1>"
				+"<h2>Espera unos minutos hasta que se genere el informe de copias.</h2>";

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
