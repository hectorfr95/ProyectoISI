package urjc.isi.myapp;

import static spark.Spark.*;

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


// Programa que ejecuta commits periodicos sobre una carpeta que se crea en el directorio
// donde se ejecute este programa, de nombre /examen

public class Main {
    //objeto alumno:
	static Alumno alumno;
    //ID examen
    private static String idEx;
    //Puerto del alumno
	private static int puerto = 4568;
    
    //objeto con el que haré los commits
    private static Git git;
    
    //necesito un timer para ejecutar commits de manera periodica
    private static Timer timer = new Timer();
    
    //tiempo entre commit y commit en minutos
    private static int rateCommit = 2;
    
    //url a la que hacemos las peticiones:
    private static String url = "http://servidor-hectorfr95.herokuapp.com";//"http://localhost:4567";//"https://servidor-proyecto1.herokuapp.com";
    
    //objeto para hacer peticiones
    private static HttpRequests requestToServer = new HttpRequests(url);
    
    //timer para hacer consultas periodicas al server:
    private static Timer periodicRequests = new Timer();
    
  //tiempo entre consulta y consulta en minutos
    private static int rateRequest = 1;
    
   
    public static void setIdEx(String s) {
    	idEx = s;
    }
    public static void setGit(Git g) {
    	git = g;
    }
    
    public static String getIdEx() {
    	return idEx;
    }
    public static Git getGit() {
    	return git;
    }
    public static Timer getTimer() {
    	return timer;
    }
    
    public static void setCommitRate(int rate) {
    	rateCommit = rate;
    }
    
    public static int getCommitRate() {
    	return rateCommit;
    }
    
    public static Timer getTimerReq() {
    	return periodicRequests;
    }
    
    public static HttpRequests getReqToServer() {
    	return  requestToServer;
    }
    
    public static void setReqToServer(String link) {
    	requestToServer = new HttpRequests(link);
    }
    
    
    //metodo que espera a que el alumno acabe de rellenar el formulario
    public static void waitAl(Form f) {
    	while(f.getName() == null || f.getDni() == null 
    			|| f.getIdEx() == null || f.getMail() == null){
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
	    	git.commit().setMessage(comen).setAuthor(alumno.getName(), alumno.getMail()).call();
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
    
    //el repo se crea en una carpeta /examen en el lugar donde se encuentra el prog
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
    		System.out.println("-------------------------");
    		System.out.println("Commit id: "+rev.getId().getName());
            System.out.print("Comentario: "+rev.getFullMessage());
            System.out.println();
            System.out.println("Nombre: "+rev.getAuthorIdent().getName());
            System.out.println("Email: "+ rev.getAuthorIdent().getEmailAddress());
            System.out.println("-------------------------");
          }
    	
    }
    
    public static String currentDay() {
    	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    	Date date = new Date();
    	return dateFormat.format(date);
    }
    
    public static void setAlarm() {
	    timer.scheduleAtFixedRate(new TimerTask(){
		    @Override
		  	public void run() {
		    	System.out.println("***********************************************");
	      		System.out.println("Hago commit...");
	      		System.out.println("***********************************************");
	      		doCommit( "commit a las:"+ currentDay());
		     }
  		 },1000*60*rateCommit, 1000*60*rateCommit);
    }
    
    public static void setPeriodicRequests(){
    	periodicRequests.scheduleAtFixedRate(new TimerTask(){
		    @Override
		  	public void run() {
		    	System.out.println("***********************************************");
	        	System.out.println("Consulta al servidor para comprobar si se ha terminado el ex");
	      		try {	
	      			if(requestToServer.sendGet(idEx) == 1) {
	      				System.out.println("Se ha terminado el examen");
	      				System.out.println("***********************************************");
	      				finEx();
	      			}else {
	      				System.out.println("No se ha terminado el examen");
	      				System.out.println("***********************************************");
	      			}
	      		}catch(Exception e) {
	      			System.out.println("An error occurred.");
	    	        e.printStackTrace();
	      		}
		     }
  		 },1000*60*rateRequest, 1000*60*rateRequest);
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
    
    public static void sendInfoAl() throws Exception{
    	//POST con informacion del alumno
    	try {
    		requestToServer.sendPostAlumno(alumno.getName(), alumno.getDni(), idEx, Integer.toString(puerto));
    		System.out.println("***********************************************");
        	System.out.println("Notifico al servidor de que el alumno con nombre: " 
    							+ alumno.getName() + " ,dni: "+alumno.getDni() + ", idex: "+idEx 
    							+ " se ha conectado correctamente");
        	System.out.println("***********************************************");
        } finally {
        	requestToServer.close();
        }
    }
    
    public static File compressRepo() throws FileNotFoundException, IOException{
    	String sourceFile = "../examen/";
        FileOutputStream fos = new FileOutputStream("../"+idEx+"_"+alumno.getDni()+".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        File fileToZip = new File(sourceFile);
    	
    	zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
        
        //POST enviar .ZIP
        File file = new File("../"+idEx+"_"+alumno.getDni()+".zip");
        return file;
    	
    }
    
    public static void finEx() throws Exception{
    	timer.cancel();
    	periodicRequests.cancel();
    	doCommit("ultimo commit del examen");
    	
    	//Comprimir .git a zip 
        try {
    		requestToServer.sendPostExamen(compressRepo(), alumno.getName(), alumno.getDni(), idEx);
    		System.out.println("***********************************************");
        	System.out.println("Envio al server el zip del alumno: " + alumno.getName() 
        							+ alumno.getDni() + idEx);
        	System.out.println("***********************************************");
        } finally {
        	requestToServer.close();
        }
        //imprimir todos los commits realizados en el examen
        checkCommits();
        
        //terminar el programa
        System.exit(0);
    }
    
    
    public static void main(String[] args) throws ClassNotFoundException, Exception {    	
    	//formulario para que el alumno inserte sus datos
    	Form f = new Form();
    	waitAl(f);
    	
    	alumno = new Alumno(f.getName(), f.getDni(), f.getMail());
    	idEx = f.getIdEx();
    	
    	//mando info inicial del alumno
    	sendInfoAl();
    	
    	git = createRepo();
    	System.out.println("***********************************************");
    	System.out.println("creo repositorio"+git);
    	System.out.println("***********************************************");
    	
    	//hacemos el primer commit nada más que el alumno se ha registrado
    	doCommit("primer commit, alumno: "+alumno.getName()+" dni: "+alumno.getDni());
		
    	//configuro el timer de los commits
    	setAlarm();
    	
    	//configuro el timer de consultas periodicas al server
    	setPeriodicRequests();

    }


}
