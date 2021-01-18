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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.MultipartConfigElement;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import java.io.File;
import java.io.FileInputStream;
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
    //ID examen
    private static String idEx;
    //Puerto del alumno
    private static int puerto;
    //nombre del fichero con el que se identifica el alumno
    //private static String fichName = "identificacion_alumno.txt";
    
    //objeto con el que haré los commits
    private static Git git;
    
    //necesito un timer para ejecutar commits de manera periodica
    private static Timer timer = new Timer();
    
    //tiempo entre commit y commit en minutos
    private static int rateCommit = 10;
    
    //objeto para hacer peticiones
    private static HttpRequests requestToServer = new HttpRequests();
    
    
    //metodo que espera a que el alumno acabe de rellenar el formulario
    public static void waitAl(Form f) {
    	while(f.getName() == null || f.getDni() == null || f.getIdEx() == null){
    		System.out.println("");
        	/*System.out.println("Name : "
					+ f.getName() + "\n"
					+ "DNI : "
					+ f.getDni() + "\n"
					+ "ID examen : "
					+ f.getIdEx() + "\n");*/
    	}
    	return;
    }
    public static void doCommit(String comen) {
    	try {
	    	//hago un commit de todo
	    	git.add().addFilepattern(".").call();
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
    
    	Path repoPath = Paths.get("../examen/");
        InitCommand init = Git.init();
        deleteRepo(new File("../examen/"));
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
    public static void checkCommits() {
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
    
    public static void setAlarm() {
	    	timer.scheduleAtFixedRate(new TimerTask(){
	      	 @Override
	      	 public void run() {
	      		 System.out.println("Executed...");
			   	
	      		doCommit( "commit a las:"+ currentDay());
	      		//checkCommits(git);
	      		//timer.cancel();
			  	 
	      	 }
  		 },1000*60*rateCommit, 1000*60*rateCommit);
    }
    
    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
    
    public static void sendInfoAl(String puerto) throws Exception{
    	//POST con informacion del alumno
    	
    	try {
    		System.out.println("Send Http POST request");
    		requestToServer.sendPostAlumno(nombre, dni, idEx, puerto);
        } finally {
        	requestToServer.close();
        }
    }
    
    public static File compressRepo() throws FileNotFoundException, IOException{
    	String sourceFile = "../examen/.git/";
        FileOutputStream fos = new FileOutputStream("../"+idEx+"_"+dni+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
    	
    	zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        
        //POST enviar .ZIP
        File file = new File("../"+idEx+"_"+dni+".zip");
        return file;
    	
    }
    
    public static String finEx(Request request, Response response) throws Exception{
    	timer.cancel();
    	doCommit("ultimo commit del examen");
    	//Comprimir .git a zip 
        try {
    		System.out.println("Send Http POST request");
    		requestToServer.sendPostExamen(compressRepo(), nombre, dni, idEx);
        } finally {
        	requestToServer.close();
        }
      //System.exit(0);
        return "";
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException, Exception {
    	port(getHerokuAssignedPort());
    	
    	//formulario para que el alumno inserte sus datos
    	Form f = new Form();
    	System.out.println("Antes del formulario");
    	waitAl(f);
    	nombre = f.getName();
    	dni = f.getDni();
    	idEx = f.getIdEx();
    	
    	String puertoStr = Integer.toString(puerto);
    	
    	System.out.println("Notifico al servidor de que el alumno con nombre " 
				+ nombre + dni + idEx + " se ha conectado correctamente");
    	
    	//mando info inicial del alumno
    	sendInfoAl(puertoStr);
    	
    	git = createRepo();
    	System.out.println("creo repositorio"+git);
    	doCommit("primer commit, alumno: "+nombre+" dni: "+dni);
    	//llamo al siguiente metodo para comprobr que los commits se hacen correctamente
    	checkCommits();
		
    	//configuro el timer
    	setAlarm();
    	
    	//Recibir GET, probar con hector
    	get("/fin", Main::finEx);

    }
    static int getHerokuAssignedPort() {
    	ProcessBuilder processBuilder = new ProcessBuilder();
		if (processBuilder.environment().get("PORT") != null) {
		    return Integer.parseInt(processBuilder.environment().get("PORT"));
		}
		return 4568; // return default port if heroku-port isn't set (i.e. on localhost)
    }


}
