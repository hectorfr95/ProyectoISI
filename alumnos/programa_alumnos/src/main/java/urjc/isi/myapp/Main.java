package urjc.isi.myapp;

import static spark.Spark.*;
import spark.Request;
import spark.Response;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;

import java.util.StringTokenizer;

import javax.servlet.MultipartConfigElement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import java.io.File;
import java.io.IOException;
import org.eclipse.jgit.api.InitCommand;
import java.nio.file.Paths;
import java.nio.file.Path;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.revwalk.RevCommit;
import java.util.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

// This code is quite dirty. Use it just as a hello world example 
// to learn how to use JDBC and SparkJava to upload a file, store 
// it in a DB, and do a SQL SELECT query
public class Main {
    
    // Connection to the SQLite database. Used by insert and select methods.
    // Initialized in main
    private static Connection connection;
    
    //nombre y apellidos del alumno que está ejecutando este programa
    private static String nombre;
  	//DNI del alumno que está ejecutando este programa
    private static String dni;
    //nombre del fichero con el que se identifica el alumno
    private static String fichName = "identificacion_alumno.txt";
    
    //objeto con el que haré los commits
    private static Git git;
    
    //necesito un timer para ejecutar commits de manera periodica
    private static Timer timer;
    
    //tiempo entre commit y commit en minutos
    private static int rateCommit;
    // Used to illustrate how to route requests to methods instead of
    // using lambda expressions
	/*
	 * public static String doSelect(Request request, Response response) { return
	 * select (connection, request.params(":table"), request.params(":film")); }
	 * 
	 * public static String select(Connection conn, String table, String film) {
	 * String sql = "SELECT * FROM " + table + " WHERE film=?";
	 * 
	 * String result = new String();
	 * 
	 * try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	 * pstmt.setString(1, film); ResultSet rs = pstmt.executeQuery(); // Commit
	 * after query is executed connection.commit();
	 * 
	 * while (rs.next()) { // read the result set result += "film = " +
	 * rs.getString("film") + "\n";
	 * System.out.println("film = "+rs.getString("film") + "\n");
	 * 
	 * result += "actor = " + rs.getString("actor") + "\n";
	 * System.out.println("actor = "+rs.getString("actor")+"\n"); } } catch
	 * (SQLException e) { System.out.println(e.getMessage()); }
	 * 
	 * return result; }
	 * 
	 * 
	 * public static void insert(Connection conn, String film, String actor) {
	 * String sql = "INSERT INTO films(film, actor) VALUES(?,?)";
	 * 
	 * try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	 * pstmt.setString(1, film); pstmt.setString(2, actor); pstmt.executeUpdate(); }
	 * catch (SQLException e) { System.out.println(e.getMessage()); } }
	 */
    
    //leo un fichero
    //fuente: https://javiergarciaescobedo.es/programacion-en-java/15-ficheros/44-leer-un-fichero-de-texto-linea-a-linea
    public static void findIdfile(String fich) {
    	
        //Declarar una variable BufferedReader
        BufferedReader br = null;
        try {
           //Crear un objeto BufferedReader al que se le pasa 
           //   un objeto FileReader con el nombre del fichero
           br = new BufferedReader(new FileReader(fich));
           //Leer la primera línea, guardando en un String
           String texto = br.readLine();
           //Repetir mientras no se llegue al final del fichero
           
               //Hacer lo que sea con la línea leída
               System.out.println("Línea leída: "+ texto);
               dni = texto;
               //Leer la siguiente línea
               texto = br.readLine();
               System.out.println("Línea leída: "+ texto);
               nombre = texto;
               //Leer la siguiente línea
               texto = br.readLine();
           
        }
        catch (FileNotFoundException e) {
            System.out.println("Error: Fichero no encontrado");
            dni = "????";
            nombre = "????";
            System.out.println(e.getMessage());
        }
        catch(Exception e) {
            System.out.println("Error de lectura del fichero");
            System.out.println(e.getMessage());
        }
        finally {
            try {
                if(br != null)
                    br.close();
            }
            catch (Exception e) {
                System.out.println("Error al cerrar el fichero");
                System.out.println(e.getMessage());
            }
        }
    	
    }
    
    public static void wait(int tiempo) {
    	//espero 60 segundos a que alumno haga el fichero de identificacion
    	try {
			 Thread.sleep(tiempo*1000);
			 
    	}catch(InterruptedException e){ 
    		System.out.println(e);
    	}
		 
    }
    public static void doCommit(String fichs, Git git, String comen, String nombre) {
    	try {
	    	if(fichs.equals("all")) {
	    		//hago un commit de todo
	    		git.add().addFilepattern("*").call();
	    	}else {
	    		git.add().addFilepattern(fichs).call();
	    	}
	    	git.commit().setMessage(comen).setAuthor(nombre, nombre+"@gmail.com").call();
    	}catch(GitAPIException e) {
    		System.out.println("An error occurred.");
	        e.printStackTrace();
    	}
    }
   
    //este metodo borra un repositorio si ya lo hubiera
    //fuente: https://www.journaldev.com/833/java-delete-directory-folder
    public static void deleteRepo(File file) {
		//to end the recursive loop
        if (!file.exists())
            return;
        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
            	deleteRepo(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
    }
    
    public static Git createRepo() {
    
    	Path repoPath = Paths.get("../");
        InitCommand init = Git.init();
        deleteRepo(new File("../.git"));
        init.setDirectory(repoPath.toFile());
        Git git2 = null;
        try{
        	git2 = init.call() ;
        }catch(GitAPIException e) {
        	System.out.println("An error occurred.");
	        e.printStackTrace();
        }
        return git2;
    }
    
    //metodo para comprobar que los commits se vayan haciendo correctamente
    public static void checkCommits(Git git) {
    	Iterable<RevCommit> logs = null;
    	try{
    		logs = git.log().all().call();
    		//logs = git.log().addPath("identificacion_alumno.txt").call();
    	}catch(Exception e) {
    		System.out.println("An error occurred.");
	        e.printStackTrace();
	        return;
    	}
    	for (RevCommit rev : logs) {
            //System.out.print(Instant.ofEpochSecond(rev.getCommitTime()));
            System.out.print(": ");
            System.out.print(rev.getFullMessage());
            System.out.println();
            System.out.println(rev.getId().getName());
            System.out.print(rev.getAuthorIdent().getName());
            System.out.println(rev.getAuthorIdent().getEmailAddress());
            System.out.println("-------------------------");
          }
    	
    }
    
    public static String currentDay() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	return dateFormat.format(date); //2016/11/16 12:08:43
    }
    
    public static void setAlarm(Timer timer, Git git, String nombre, int minutes) {
	    	timer.scheduleAtFixedRate(new TimerTask(){
	      	 @Override
	      	 public void run() {
	      		 System.out.println("Executed...");
			   	
	      		doCommit("*", git, "commit a las:"+ currentDay(), nombre);
	      		//checkCommits(git);
	      		//timer.cancel();
			  	 
	      	 }
  		 },1000*60*minutes, 1000*60*minutes);
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	port(getHerokuAssignedPort());
    	//me creo el objeto timer
    	timer = new Timer();
    	rateCommit = 10;
    	//busco si el fichero con el que se identifica el alumno se encuentra
    	findIdfile("../"+fichName);
    	if(dni.equals("????") || nombre.equals("????")) {
    		System.out.println("Notifico al servidor de que un alumno no ha generado el fichero");
    		//espero 60 segundos a que el alumno cree el fichero
    		wait(60);
    		findIdfile("../"+fichName);
    	}
    	System.out.println("Notifico al servidor de que el alumno con nombre " 
				+ nombre + " se ha conectado correctamente");
    	git = createRepo();
    	System.out.println("creo repositorio"+git);
    	doCommit(fichName, git, "primer commit, alumno: "+nombre+" dni: "+dni, nombre);
    	//llamo al siguiente metodo para comprobr que los commits se hacen correctamente
    	checkCommits(git);
		
    	//configuro el timer
    	//setAlarm(timer, git, nombre, rateCommit);
    	
    	
    	
    	// Connect to SQLite sample.db database
		// connection will be reused by every query in this simplistic example
		//connection = DriverManager.getConnection("jdbc:sqlite:sample.db");
	
		// SQLite default is to auto-commit (1 transaction / statement execution)
	        // Set it to false to improve performance
		//connection.setAutoCommit(false);
	
	
		// In this case we use a Java 8 method reference to specify
		// the method to be called when a GET /:table/:film HTTP request
		// Main::doWork will return the result of the SQL select
		// query. It could've been programmed using a lambda
		// expression instead, as illustrated in the next sentence.
		//get("/:table/:film", Main::doSelect);
	
		// In this case we use a Java 8 Lambda function to process the
		// GET /upload_films HTTP request, and we return a form
		//get("/upload_films", (req, res) -> 
		//    "<form action='/upload' method='post' enctype='multipart/form-data'>" 
		//    + "    <input type='file' name='uploaded_films_file' accept='.txt'>"
		//    + "    <button>Upload file</button>" + "</form>");
		// You must use the name "uploaded_films_file" in the call to
		// getPart to retrieve the uploaded file. See next call:
	
	
		// Retrieves the file uploaded through the /upload_films HTML form
		// Creates table and stores uploaded file in a two-columns table
		//post("/upload", (req, res) -> {
		//	req.attribute("org.eclipse.jetty.multipartConfig", new MultipartConfigElement("/tmp"));
		//	String result = "File uploaded!";
		//	try (InputStream input = req.raw().getPart("uploaded_films_file").getInputStream()) { 
				// getPart needs to use the same name "uploaded_films_file" used in the form
	
				// Prepare SQL to create table
		/*
		 * Statement statement = connection.createStatement();
		 * statement.setQueryTimeout(30); // set timeout to 30 sec.
		 * statement.executeUpdate("drop table if exists films");
		 * statement.executeUpdate("create table films (film string, actor string)");
		 * 
		 * 
		 * 
		 * // Read contents of input stream that holds the uploaded file
		 * InputStreamReader isr = new InputStreamReader(input); BufferedReader br = new
		 * BufferedReader(isr); String s; while ((s = br.readLine()) != null) {
		 * System.out.println(s);
		 * 
		 * // Tokenize the film name and then the actors, separated by "/"
		 * StringTokenizer tokenizer = new StringTokenizer(s, "/");
		 * 
		 * // First token is the film name(year) String film = tokenizer.nextToken();
		 * 
		 * 
		 * // Now get actors and insert them while (tokenizer.hasMoreTokens()) {
		 * insert(connection, film, tokenizer.nextToken()); } // Commit only once, after
		 * all the inserts are done // If done after each statement performance degrades
		 * connection.commit();
		 * 
		 * 
		 * } input.close(); } return result; });
		 */

    }

    static int getHerokuAssignedPort() {
		ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
		    return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4567; // return default port if heroku-port isn't set (i.e. on localhost)
    }
}
