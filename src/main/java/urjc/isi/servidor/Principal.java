package urjc.isi.servidor;


import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.Random;
import java.util.StringTokenizer;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;




public class Principal /*extends Repository */{
	
	//private static final String ARCHIVO_ZIP = "https://wordpress.org/latest.zip";
	private static final String PATH_LOCAL = "C:\\Users\\javie\\Documents\\URJC\\4\\ISI\\algoritmoCopia\\GIT";
	//private static final String DIRECTORIO = "/home/alumno/Escritorio/prueba/gits";
	
	public static void CrearDirectorio (String args){
        File directorio = new File(args);
        
        if (!directorio.exists()) {
            if (directorio.mkdirs()) {
                //System.out.println("Directorio creado\n");
            } else {
                //System.out.println("Error al crear directorio\n");
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
                System.err.println("�Path NO v�lido!");
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
		  }

		}
	}
    
    public static void checkout(String path, String nombreFich) throws IOException, NoHeadException,GitAPIException {
		  Path repoPath = Paths.get(path);
		 
		  try (Git git = Git.open(repoPath.toFile())) {
			  git.checkout().addPath(nombreFich).call();
		  }
		  
		}
	
	public static String NombreAleatorio() {
		char n;
		
		Random rnd = new Random();
		String cadena = new String();
		n = (char)(rnd.nextDouble()*26.0 + 65.0);
		cadena += n;
		return cadena;
	}
	
	private static String getFileNameWithoutExtension(File file) {
        String fileName = "";
 
        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                fileName = name.replaceFirst("[.][^.]+$", "");
            }
        } catch (Exception e) {
            e.printStackTrace();
            fileName = "";
        }
        return fileName;
    }
	
	public static void Ultimocommit(File flee1, File flee2) throws IOException
	{
		try
		{
			BufferedReader qlee1 = new BufferedReader(new FileReader(flee1));
			BufferedReader qlee2 = new BufferedReader(new FileReader(flee2));
			String l1="";
			String l2="";
			String linedis = "";
			int contador1=0;
			int contador2=0;
			int contador=0;
			while((l1!=null) || (l2 != null))
			{
				l1=qlee1.readLine();
				l2=qlee2.readLine();
				// comparar que el contador de lineas no sea igual
				if(l1!=null)
				{
					contador1++;
				}
				if(l2!=null)
				{
					contador2++;
				}		
				if((l1!=null)||(l2!=null)) {
				  if (l1.equals(l2))
				  {
					  	linedis=l1;
				  		contador++;
				  }
				}
			}
			if(contador1==contador2){
				System.out.println("probabilidad de copia por numero total de lineas\n");
			}else{
				System.out.println("NO probabilidad de copia por numero total de lineas\n");
			}
			qlee1.close();
			qlee2.close();
		}finally{
			System.out.println("");
		}
	}
	
	private static void Ultimocommitpal(File slee1, File slee2) throws IOException {
		String s1;
		String s2;
		String s3;
		String s4;
		int pal_igual = 0;
		BufferedReader br1 = new BufferedReader(new FileReader(slee1));
		s1=br1.readLine();
		int numtokens1 =0;
		StringTokenizer st =new StringTokenizer(s1);
		while(st.hasMoreTokens())
		{
			s2=st.nextToken();
			numtokens1++;
			BufferedReader br2 = new BufferedReader(new FileReader(slee2));
			s3=br2.readLine();
			int numtokens2=0;
			StringTokenizer st2 = new StringTokenizer(s3);
			while(st2.hasMoreElements()) {
				s4=st2.nextToken();
				numtokens2++;
				if(s2.equals(s4)) {
					pal_igual++;
				}
			}
		}
		if(pal_igual!=0) {
			System.out.println("Hay palabras igual\n" + pal_igual);
		}else {
			System.out.println("No hay palabras igual\n");
		}
	}
	
	public static String getDiff(String file1, String file2) {
	    OutputStream out = new ByteArrayOutputStream();
	    try {
	        RawText rt1 = new RawText(new File(file1));
	        RawText rt2 = new RawText(new File(file2));
	        EditList diffList = new EditList();
	        //new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2);	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2));
	        //new DiffFormatter(out).format(diffList, rt1, rt2);
	        DiffFormatter diffFormatter = new DiffFormatter(out);
	        diffFormatter.format(diffList, rt1, rt2);
	        System.out.println(out.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return out.toString();
	}
	

	public void Ejecutar(String path, int id_examen) throws Exception{
    	System.setOut(new PrintStream(new FileOutputStream("upload/"+id_examen+"/"+"output.txt")));
		String DIRECTORIO = path.concat("/gits");
 
    	String [] list_fich = null;
        String[] files = ObtenerFich(path);
        
		if (files != null) {
			int size = files.length;
            for (int i = 0; i < size; i++) {
                String ARCHIVO_ZIP = files[i];
                String cadena = NombreAleatorio();
                String CARPETA = DIRECTORIO.concat("/" + cadena);
                //System.out.println("carpeta: "+CARPETA);
                CrearDirectorio(CARPETA);
                Descomprimir(ARCHIVO_ZIP,CARPETA);
            }
            
            String list = null;
            String [] l = null;
            ArrayList<String> ficheros = new ArrayList<String>();
            String aux = null;
            File carp = new File(DIRECTORIO);
            list_fich = carp.list();
           // System.out.println("AQUII:"+list_fich.length);
            for(int k = 0; k< list_fich.length;k++) {
            	//System.out.println("DIR: "+DIRECTORIO);
            	list = DIRECTORIO.concat("/" + list_fich[k] + "/examen");
            	//System.out.println("list:"+list);
            	File c  = new File(list);
            	l = c.list();
            	if(l==null)
            		continue;
            	else
            	{
            		for(int h = 0; h< l.length; h++) {
                		
                		if(!(l[h].equals(".git"))){
                			
                			aux = list.concat("/" + l[h]);
                			ficheros.add(aux);
                		}
                		//System.out.println("TRAZAA"+ l[h]);
                	}
            	}
            	
            }
            String fichero1 = null;
            String fichero2 = null;
            Object[] lista = ficheros.toArray();
            for(int q =1; q< ficheros.size();q++) {
            	for(int n=1; n<ficheros.size();n++) {
            		System.out.println("Comparacion de fichero:");
            		fichero1 = (String)lista[q-1];
        			fichero2 = (String)lista[n];
        			System.out.println(fichero1);
        			System.out.println(fichero2);
        			File lee1 = new File(fichero1);
        			File lee2 = new File(fichero2);
        			Ultimocommit(lee1,lee2);
        			Ultimocommitpal(lee1,lee2);
            		getDiff(fichero1, fichero2);
            	}
            	System.out.println("---------------------------------------");
        	}
		}
	
	}
	
    
}