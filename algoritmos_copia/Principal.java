package Proyecto2.Principla2;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Random;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.lib.Repository;

import org.eclipse.jgit.revwalk.RevCommit;




public class Principal /*extends Repository */{
	
	//private static final String ARCHIVO_ZIP = "https://wordpress.org/latest.zip";
	private static final String PATH_LOCAL = "/home/alumno/Escritorio/prueba";
	private static final String DIRECTORIO = "/home/alumno/Escritorio/prueba/gits";
	
	public static void CrearDirectorio (String args){
        File directorio = new File(args);
        
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                System.out.println("Directorio creado\n");
            } else {
                System.out.println("Error al crear directorio\n");
            }
        }
    }
    /*
    public static void EjecutarWget(){
		try {
			String[] cmd = {"wget","-P",RUTA_SALIDA,ARCHIVO_ZIP};
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultOfExecution = null;
            while((resultOfExecution = br.readLine()) != null){
                System.out.println(resultOfExecution);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	*/
	public static String[] ObtenerFich(String dir_path) {

		String[] arr_res = null;
        File f = new File(dir_path);
        if (f.isDirectory()) {
			List<String> res = new ArrayList<>();
			File[] arr_content = f.listFiles();
            int size = arr_content.length;
            for (int i = 0; i < size; i++) {
				if(arr_content[i].isFile())
                res.add(arr_content[i].toString());
			}
			
			arr_res = res.toArray(new String[0]);
            } else {
                System.err.println("¡Path NO válido!");
			}
			
            return arr_res;
        }
        
    public static void Descomprimir(String ficheroZip, String directorioSalida) throws Exception {
		
		final int TAM_BUFFER = 4096;
		byte[] buffer = new byte[TAM_BUFFER];

		ZipInputStream flujo = null;
		try {
		  flujo = new ZipInputStream(new BufferedInputStream(new FileInputStream(ficheroZip)));
		  
		  ZipEntry entrada;
		  while ((entrada = flujo.getNextEntry()) != null) {
			String nombreSalida = directorioSalida + File.separator + entrada.getName();
			if (entrada.isDirectory()) {
			  File directorio = new File(nombreSalida);
			  directorio.mkdir();
			} else {
			  BufferedOutputStream salida = null;
			  try {
				int leido;
				salida = new BufferedOutputStream(new FileOutputStream(nombreSalida), TAM_BUFFER);
				while ((leido = flujo.read(buffer, 0, TAM_BUFFER)) != -1) {
				  salida.write(buffer, 0, leido);
				}
			  } finally {
				if (salida != null) {
				  salida.close();
				}
			  }
			}
		  }
		} finally {
		  if (flujo != null) {
			flujo.close();
			System.out.println("Listo");
		  }

		}
	}
    
    public static void Diffs(String commit1, String commit2, String file) throws IOException, GitAPIException {
    	 try (Repository repo = CookbookHelper.openJGitCookbookRepository()) {
			DiffRenamed diffrenamed = new DiffRenamed();
			diffrenamed.runDiff(repo, commit1, commit2, file);
    	 }
    }
    
    //Metodo que recibe el path (ruta al directorio .git) de tipo String y lista todos los commits del directorio 	
	public static void listCommits(String path) throws IOException, NoHeadException, GitAPIException {
	
		String commitId;
		String author = null;
		ArrayList<String> lista = new ArrayList<String>();
	    Path repoPath = Paths.get(path);
	
	    try (Git git = Git.open(repoPath.toFile())) {
		  // all
		  Iterable<RevCommit> logs = git.log().all().call();
		  for (RevCommit rev : logs) {
		    commitId = rev.getId().getName();
		    lista.add(commitId);
		    author = rev.getAuthorIdent().getName();
		  }/*
		  Iterator i = lista.iterator();
		  while(i.hasNext()) {
			  System.out.println(i.next());
			  
		  }*/
		  Object[] list = lista.toArray();
		  for(int j = 1; j < list.length; j++) {
			String str1 = (String)list[j-1];
			String str2 = (String)list[j];
			Diffs(str1, str2, "f2");
		  }
	    }
	}
	
	public static String NombreAleatorio() {
		char n;
		
		Random rnd = new Random();
		String cadena = new String();
		n = (char)(rnd.nextDouble()*26.0 + 65.0);
		cadena += n;
		System.out.println("Random: " + cadena);
		return cadena;
	}

    public static void main(String[] args) throws Exception{
    	CrearDirectorio(DIRECTORIO);
        //EjecutarWget();
        String[] files = ObtenerFich(PATH_LOCAL);
        
		if (files != null) {
			int size = files.length;
            for (int i = 0; i < size; i++) {
				System.out.println("Imprimiendo: "+ files[i]);
                String ARCHIVO_ZIP = files[i];
                //String cadena = NombreAleatorio();
                //String CARPETA = DIRECTORIO.concat("/" + cadena);
                //CrearDirectorio(DIRECTORIO);
                Descomprimir(ARCHIVO_ZIP,DIRECTORIO);
				
				File carpeta = new File(DIRECTORIO);
				String[] listado = carpeta.list();
				
				for (i = 0; i < listado.length; i++){
					System.out.println("Lista: "+ listado[i]);
					String DIRECTORIO2 = DIRECTORIO.concat("/" + listado[i]);
					System.out.println(DIRECTORIO2);
					listCommits(DIRECTORIO2);
					System.out.println("Fin " + i + DIRECTORIO2);
					
				}

			}
		}
    }
}
