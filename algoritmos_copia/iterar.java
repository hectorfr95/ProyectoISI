package practica.practica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;

public class iterar {
	
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

	public static String NombreAleatorio() {
		char n;
		
		Random rnd = new Random();
		String cadena = new String();
		n = (char)(rnd.nextDouble()*26.0 + 65.0);
		cadena += n;
		System.out.println("Random: " + cadena);
		return cadena;
	}
	
	private static void Ulitmocommitpal(File slee1, File slee2) throws IOException {
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
	
	public static void main(String[] args) throws Exception{
		String DIRECTORIO = "/home/alumno/Escritorio/car";
		String[] files = ObtenerFich("/home/alumno/Escritorio/car");
		String [] list_fich = null;
		if (files != null) {
			int size = files.length;
			System.out.println(size);
            for (int i = 0; i < size; i++) {
                String ARCHIVO_ZIP = files[i];
                System.out.println("Files: " + files[i]);
                String cadena = NombreAleatorio();
                String CARPETA = DIRECTORIO.concat("/" + cadena);
                CrearDirectorio(CARPETA);
                Descomprimir(ARCHIVO_ZIP,CARPETA);
            }
            String DIRECTORIO2 = "/home/alumno/Escritorio/car/gits";
            String list = null;
            String [] l = null;
            ArrayList<String> ficheros = new ArrayList<String>();
            String aux = null;
            File carp = new File(DIRECTORIO2);
            list_fich = carp.list();
            for(int k = 0; k< list_fich.length;k++) {
            	list = DIRECTORIO2.concat("/" + list_fich[k]);
            	File c  = new File(list);
            	l = c.list();
            	for(int h = 0; h< l.length; h++) {
            		if(!(l[h].equals("papa"))){
            			//System.out.println("he entrado");
            			aux = list.concat("/" + l[h]);
            			ficheros.add(aux);
            			//System.out.println("p: " + l[h]);
            		}
            	}
            }
            String fichero1 = null;
            String fichero2 = null;
            Object[] lista = ficheros.toArray();
            for(int q =1; q< ficheros.size();q++) {
            	fichero1 = (String)lista[q-1];
    			fichero2 = (String)lista[q];
    			File lee1 = new File(fichero1);
    			File lee2 = new File(fichero2);
    			Ultimocommit(lee1,lee2);
    			Ulitmocommitpal(lee1,lee2);
        		//System.out.println("p: " + ficheros.get(q));
        		
        	}
		}
	}
}