package urjc.isi.algoritmoCopia;
import java.io.*;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


public class Descomprimir {
	//Asignamos la ruta del primer archivo zip encontrado en la carpeta actual donde se lanza
	private static final String ARCHIVO_ZIP = Path_Zip();
	
	//Usamos el directorio actual como salida
    private static final String RUTA_SALIDA = Path_Salida();
    
  public static void main(String[] args) throws Exception {	
	 Descomprime(ARCHIVO_ZIP,RUTA_SALIDA);
  }
  
  //Funcion que te devuelve el path de la carpeta actual
  public static String Path_Salida() {
	  String path="";
	  String sCarpAct = System.getProperty("user.dir");
	  File carpeta = new File(sCarpAct);
	  path=carpeta.getAbsolutePath();
	  
	  return path;
  }
  
  //Funcion basica para identifiacr en la carpeta actual si es un fichero zip y devolver su path(DE MOMENTO EL PRIMERO QUE ENCUENTRA)
  public static String Path_Zip() {
	  String path="";
	  String sCarpAct = System.getProperty("user.dir");
	  File carpeta = new File(sCarpAct);
	  String[] listado = carpeta.list();
	  String extFich = "";
	  boolean encontrado=false;
	  
	  if (listado == null || listado.length == 0) {
	      System.out.println("No hay elementos dentro de la carpeta actual");
	  }else {
		  for(int i=0; i<listado.length; i++) {
			  File f = new File(listado[i]);
			  if(f.isDirectory()) {
				  continue;
			  }else {
				//Obtener extension del archivo
				  String nombreFich=f.toString();
				  int punto=nombreFich.lastIndexOf('.');
				  if(punto>0) {
					  extFich=nombreFich.substring(punto+1);
				  }
				  
				  //Comparamos la extension para comprobar que es un .zip y asignar el path para devolverlo
				  if(extFich.equals("zip")){
					  encontrado=true;
					  path=f.getAbsolutePath();
				  }  
			  }
			  if(encontrado==true) {
				  break;
			  }
		  }
	  }
	  return path;
  }
  
  //Funcion que descomprime un archivo con los parametros de zip de entrada y directorio de salida -> Hecho por Valeria
  public static void Descomprime(String ficheroZip, String directorioSalida)
          throws Exception {
    final int TAM_BUFFER = 4096;
    byte[] buffer = new byte[TAM_BUFFER];

    ZipInputStream flujo = null;
    try {
      flujo = new ZipInputStream(new BufferedInputStream(
              new FileInputStream(ficheroZip)));
      ZipEntry entrada;
      while ((entrada = flujo.getNextEntry()) != null) {
        String nombreSalida = directorioSalida + File.separator
                + entrada.getName();
        if (entrada.isDirectory()) {
          File directorio = new File(nombreSalida);
          directorio.mkdir();
        } else {
          BufferedOutputStream salida = null;
          try {
            int leido;
            salida = new BufferedOutputStream(
                    new FileOutputStream(nombreSalida), TAM_BUFFER);
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
  
}
