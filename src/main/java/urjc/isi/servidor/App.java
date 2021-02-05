package urjc.isi.servidor;

import static spark.Spark.*;
//import spark.Request;
//import spark.Response;
import urjc.isi.servidor.App;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
//import java.sql.Statement;
//import java.sql.PreparedStatement;
import java.util.Random;
import java.util.StringTokenizer;

import javax.servlet.MultipartConfigElement;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import static spark.Spark.*;
import spark.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;
import spark.utils.IOUtils;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import spark.*;

import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.nio.file.*;
import static spark.Spark.*;
public class App
{
	private static final Map<String, Object> settings = new HashMap<String, Object>();

	  public static String parse(String pattern, String text, Map<String, Object> locals) {
	    Matcher regexp = Pattern.compile(pattern).matcher(text);
	    while (regexp.find()) {
	      text = regexp.replaceFirst(locals.get(regexp.group(1)).toString());
	    }
	    return text;
	  }

	  public static String parseFile(String file, String pattern, Map<String, Object> locals) {
	    StringBuffer content = new StringBuffer("");
	    try {
	      BufferedReader buffer = new BufferedReader(new FileReader(file));
	      String line = null;

	      while ((line = buffer.readLine()) != null) {
	        content.append(parse(pattern, line, locals) + "\n");
	      }

	      buffer.close();
	    }
	    catch (Exception exception) {
	      System.out.printf("ERROR: %s\n", exception.getMessage());
	    }
	    finally {
	      return content.toString();
	    }
	  }

	  public static String render(String file, Map<String, Object> locals) {
	    return layout(file, parseFile(file, "\\$\\{(\\w.*?)\\}", locals));
	  }

	  public static String layout(String file, String content) {
	    HashMap<String, Object> layout = new HashMap<String, Object>();
	    layout.put("content", content);
	    return parseFile("views/layout.html", "@\\{(content)\\}", layout);
	  }

	  public static void set(String key, Object value) {
	    settings.put(key, (String) value);
	  }

	  public static Object settings(String key) {
	    return settings.get(key);
	  }














	  


















	  static int comprobar_examen(String param, examenDao examenDao) throws IOException {
		  int id_examen=0;
			try {
				id_examen = Integer.parseInt(param);
			//COMPROBAR SI EL RECURSO :RANDOM SE ENCUENTRA EN LA BD, SI NO ES ASI, DEVOLVER 404 NOT FOUND
			}catch (NumberFormatException e) {
				return -1;
	        }
	
				if(examenDao.comprobar_examen(id_examen)==0)
					return -1;
				else
					return id_examen;
		}







	private static httprequest requestToClient = new httprequest();

	public static void main(String[] args) throws
	ClassNotFoundException, SQLException, URISyntaxException {

		port(getHerokuAssignedPort());
		File uploadDir = new File("upload/");
		uploadDir.mkdir(); // create the upload directory if it doesn't exist
		staticFiles.externalLocation("upload");
		staticFiles.location("/views"); // Static files
		examenDao examenDao = new examenDao();
		realizaExamenDao realizaExamenDao = new realizaExamenDao();
		alumnoDao alumnoDao = new alumnoDao();

		
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		redirect.get("/", "/profesor");
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		get("/views/css/style.css", (req, res) -> {
			return render("views/css/style.css", settings);
		});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		get("/favicon.png", (req, res) -> {
			res.raw().setContentType("image/png");
	        File file = new File("favicon.png"); 
			 FileInputStream input = new FileInputStream(file);
			 ServletOutputStream out = res.raw().getOutputStream();			 			
			 byte[] outputByte = new byte[4096];
			 while(input.read(outputByte, 0, 4096) != -1)
			 {
			    out.write(outputByte, 0, 4096);
			 }
			input.close();
			out.flush();
			out.close();
			return out;
});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		get("/profesor", (req, res) -> {
			String result;
			List<examen> allExamenes = new ArrayList<examen>();
			allExamenes = examenDao.all();
			
			
			
			
			
			if(allExamenes.size()==0)
			{
				result = "<p style='text-align: center; font-weight: bold;'>No hay Examenes registrados</p>";
			}
			else {
				result = "<p style='text-align: center; font-weight: bold;font-size: 20px;'>Examenes en la BD:</p>";


			for (int i=0;i<allExamenes.size();i++) {

				result = result + "&nbsp &nbsp <strong>"+(i+1)+" - <u>ID:</u></strong> "+allExamenes.get(i).getIdExamen()+" <u style='font-weight: bold;'>ASIGNATURA:</u> "+allExamenes.get(i).getAsignatura()+"<br>";
			    }
			}
			set("bloque_bd", result);
			return render("views/index.html", settings);
		});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		post("/profesor", (req, res) -> {

			int id_examen = (int) (Math.random()*1000000000 +1); // Numero aleatorio que se asignará al examen

			String asignatura = req.queryParams("asignatura"); // Sacamos la query string del campo asignatura
			Date fecha = new Date();
			long lnMilisegundos = fecha.getTime();
			java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);

			examen examenObject = new examen(id_examen, sqlDate, asignatura, 0);
			examenDao.save(examenObject); //Inserción del examen en la BD

			System.out.println("*******************************************************************");
			System.out.println("POST recibido para iniciar examen de la asignatura "+asignatura+" con ID: "+id_examen);
			System.out.println("*******************************************************************");

			res.redirect("http://"+req.host()+"/"+id_examen);
			return null;
		});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		post("/examen", (req, res) -> {

	        req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/temp"));
	        String dni = req.raw().getParameter("dni");
	        String nombre = req.raw().getParameter("nombre");
	        int id_ex = Integer.parseInt(req.raw().getParameter("idex"));
	        System.out.println("*******************************************************************");
	        System.out.println("POST recibido del alumno: "+nombre+" para finalizar el examen: "+id_ex);
	        System.out.println("*******************************************************************");
	        File aux = new File("upload/"+id_ex);
	        aux.mkdir();
	
	        Path tempFile = Files.createTempFile(aux.toPath(), nombre+"_"+dni+"_"+id_ex+"_", ".zip");
	        try (InputStream input = req.raw().getPart("file").getInputStream()) 
	        { 	
	        	Files.copy(input, tempFile, StandardCopyOption.REPLACE_EXISTING);
	            Files.move(tempFile, tempFile.resolveSibling(nombre+"_"+dni+"_"+id_ex+".zip"), StandardCopyOption.REPLACE_EXISTING);
	            realizaExamenDao.verificacion_zip(dni, id_ex);
	        }

	        return "EXITO";
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
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
	
			System.out.println("*******************************************************************");
			System.out.println("POST recibido del alumno: "+nombre+" para el examen: "+id_ex+" con IP: "+ip+":"+port);
			System.out.println("*******************************************************************");
	
			return "EXITO";
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		get("/:random", (req, res) -> {
			int id_examen = comprobar_examen(req.params(":random"), examenDao);
			if(id_examen==-1)
			{
				res.status(404);
				return render("views/404.html", settings);
			}
			
	
	
	
			List<finalexamen> allFinalExamen = new ArrayList<finalexamen>();
			allFinalExamen = realizaExamenDao.alumnos_examen(Integer.parseInt(req.params(":random")));
			String result1 = "";
			if(allFinalExamen.size()==0)
			{
				result1 = "<p style='text-align: center; font-weight: bold;'>No hay Alumnos registrados todavia.</p>";
			}
			else {
				result1 = "<p style='text-align: center; font-weight: bold;font-size: 20px;'>Alumnos registrados en este examen:</p>";
	
	
			for (int i=0;i<allFinalExamen.size();i++) {
	
				result1 = result1 + "&nbsp &nbsp <strong>"+(i+1)+" - <u>Nombre:</u></strong> "+allFinalExamen.get(i).getNombreAlumno()+" <u style='font-weight: bold;'>ID:</u> "+allFinalExamen.get(i).getIdAlumno()+"<br>";
			    }
			}
	
	
	
			String asignatura = examenDao.getAsignatura(id_examen);
			String result = "Examen iniciado con id <strong style='color:red'> "+ id_examen + "</strong><br><br> <strong style='color:red;'> "+asignatura+ "</strong>";
			set("titulo", result);
			set("alumnos_bd", result1);
			set("id", req.params(":random"));
			return render("views/random.html", settings);
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
		get("/:random/finalizar", (req, res) -> {
			int id_examen = comprobar_examen(req.params(":random"), examenDao);
			if(id_examen==-1)
			{
				res.status(404);
				return render("views/404.html", settings);
			}
	
			
			
			examenDao.finalizar_examen(id_examen);
	
	
			List<finalexamen> allFinalExamen = new ArrayList<finalexamen>();
			allFinalExamen = realizaExamenDao.alumnos_examen(Integer.parseInt(req.params(":random")));
			String result1 = "";
			
			for (int i=0;i<allFinalExamen.size();i++) {
	
				result1 = result1 + "<div class=\"col-auto\" id=\"index\" style=\"text-align: center;\"><strong>Alumno:</strong> "+allFinalExamen.get(i).getNombreAlumno()+" <strong>DNI:</strong> "+allFinalExamen.get(i).getIdAlumno()+"<br>";
			    if(allFinalExamen.get(i).getPath()==null)
			    	result1 = result1 + "<i class=\"bi bi-x-circle-fill\" style=\"color: red;\"></i> <strong>ZIP no registrado...</strong>";
			    else
			    {
			    	result1 = result1 + "<i class=\"bi bi-check-circle-fill\" style=\"color: green;\"></i> <strong>ZIP guardado!</strong>";
			    	result1 = result1 + "<br><i style=\"color: blue;\" class=\"bi bi-file-earmark-arrow-down-fill\"> </i><a style=\"color: blue; text-decoration: underline;\" href=\"/"+id_examen+"/"+allFinalExamen.get(i).getNombreAlumno()+"/"+allFinalExamen.get(i).getIdAlumno()+"\">" +allFinalExamen.get(i).getNombreAlumno()+"_"+allFinalExamen.get(i).getIdAlumno()+"_"+id_examen+".zip"+"</a>";
			    }
			    
			    result1 = result1 + "</div>";
			}
			
			
			int todos_fin=1;
			for (int i=0;i<allFinalExamen.size();i++) {
				
				
				if(allFinalExamen.get(i).getPath()==null)
				{
					todos_fin=0;
					break;
				}			
			}
			
			String boton_disable;
			String error_msg="";
			if(todos_fin==0)
			{
				error_msg="<div class=\"col-auto\"><text style='color: red; font-weight: bold;font-size: 13px;'>&nbsp &nbspTodavia no se han registrado los ZIPS de TODOS los alumnos</text></div>";
				boton_disable = "<a href=\"\" class=\"btn btn-danger disabled\" role=\"button\" aria-disabled=\"true\">Algoritmo deshabilitado</a>";
			}
			else if(allFinalExamen.size()==0)
			{
				error_msg="<div class=\"col-auto offset-1\"><text style='color: red; font-weight: bold;font-size: 13px;margin-left: 25px;'>&nbsp &nbspExamen sin alumnos registrados</text></div>";
				boton_disable = "<a href=\"\" class=\"btn btn-danger disabled\" role=\"button\" aria-disabled=\"true\">Algoritmo deshabilitado</a>";
			}
			else
				boton_disable = "<a href=\"/"+id_examen+"/algoritmo\" class=\"btn btn-danger\" role=\"button\" >Algoritmo</a>";
			
			
			
			set("error_msg", error_msg);
			set("boton_disabled", boton_disable);
			set("result1", result1);
			set("id_examen", String.valueOf(id_examen));
			
			return render("views/finalizado.html", settings);
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		get("/:random/algoritmo", (req, res) -> {
			int id_examen = comprobar_examen(req.params(":random"), examenDao);
			if(id_examen==-1)
			{
				res.status(404);
				return render("views/404.html", settings);
			}
			
			//String respuesta = ejecutaralgoritmo("/ulouad/"+id_examen);
			
			
			return "PAGINA DEL ALGORITMO";
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		get("/fin/:random", (req, res) -> {
			int es_fin = 0;
			int id_examen = comprobar_examen(req.params(":random"), examenDao);
			if(id_examen==-1)
			{
				res.status(404);
				return render("views/404.html", settings);
			}
			
			if(examenDao.comprobar_final(id_examen)==1)
				es_fin = 1;
	
			System.out.println("*******************************************************************");
			System.out.println("get recibido con: "+id_examen);
	
	
	
			return es_fin;
	});
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////		
	get("/:random/:name/:dni", (req, res) -> {
		int id_examen = comprobar_examen(req.params(":random"), examenDao);
		if(id_examen==-1)
		{
			res.status(404);
			return render("views/404.html", settings);
		}
			
		String nombre = req.params(":name");
		String dni = req.params(":dni");
		 res.raw().setContentType("application/zip");
	        res.header("Content-Disposition", "attachment; filename=\""+nombre+"_"+dni+"_"+id_examen+".zip\"");
	        File file = new File("upload/"+id_examen+"/"+nombre+"_"+dni+"_"+id_examen+".zip"); 
			 FileInputStream input = new FileInputStream(file);
			 ServletOutputStream out = res.raw().getOutputStream();
			 
			 			//contents = IOUtils.toString(input); 
			            //System.out.println(contents);
			 byte[] outputByte = new byte[4096];
			//copy binary contect to output stream
			while(input.read(outputByte, 0, 4096) != -1)
			{
			    out.write(outputByte, 0, 4096);
			}
			input.close();
			out.flush();
			out.close();
		return null;
	});
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	
	
//	get("/aux", (req, res) -> {
//		
//		String sCarpAct = System.getProperty("user.dir");
//		File carpeta = new File(sCarpAct+"/upload");
//		String[] listado = carpeta.list();
//		if (listado == null || listado.length == 0) 
//		{
//		    System.out.println("No hay elementos dentro de la carpeta actual");
//		    return "ERROR";
//		}
//		else 
//		{
//		    for (int i=0; i< listado.length; i++) {
//		        System.out.println("Encontrada esta carpeta: "+listado[i]);
//		        File carpeta_aux = new File(sCarpAct+"/upload/"+listado[i]);
//				String[] listado_aux = carpeta_aux.list();
//				if (listado_aux == null || listado_aux.length == 0)
//				{
//				    System.out.println("		No hay elementos dentro de la carpeta actual");
//				    return "ERROR";
//				}
//				else
//				{
//				    for (int j=0; j< listado_aux.length; j++) {
//				        System.out.println("		Encontrado este archivo: "+listado_aux[i]);					        
//				    }
//				}
//		    }
//		}
//		return "EXITO";
//	});
	
	
	
	
	
	
	
	
	
	
	
	
	get("*", (req, res) -> {
		res.status(404);
		return render("views/404.html", settings);
	});
	}

	static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
		    return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
	    }
}
