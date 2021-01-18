package FrecuenciaTiempos;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class FrecuenciaTiempos {
	public static int primerFichero(File file1, String time) {
		// función que comprueba la hora a la que se ha creado el fichero
		
		BasicFileAttributes attrs1,attrs2;
		try {
			// Obtenemos la fecha de creación del primer fichero
		    attrs1 = Files.readAttributes(file1.toPath(), BasicFileAttributes.class);
		    FileTime time1 = attrs1.creationTime();
		    	    
		    String pattern = "yyyy-MM-dd HH:mm:ss";
		    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			
		    String formatted1 = simpleDateFormat.format( new Date( time1.toMillis() ) );

		    System.out.println( "La fecha y hora de creación del archivo 1 es: " + formatted1 );
		    
		    // Convertimos el minuto en número
		    char num1 = formatted1.charAt(14);
		    char num2 = formatted1.charAt(15);
		    
		    int minutoCreacion = Character.getNumericValue(num1)*10 + Character.getNumericValue(num2);
		    return minutoCreacion;
		    	    
		} catch (IOException e) {
		    e.printStackTrace();
		    return 1;
		}
	}
	
	public static int compararTiempos(int tiempoPrimero, int tiempoSegundo) {
	    System.out.println( "La fecha y hora de creación del archivo 1 es: " + tiempoPrimero );
	    System.out.println( "La fecha y hora de creación del archivo 2 es: " + tiempoSegundo );
	    if (tiempoSegundo - tiempoPrimero >= 60)
	    	return 90;
	    else
	    	return 0;
		
	}
	
	public static int ultimaModificacion(String [] arrayFicheros) {
		String dia, mes, annio, hora, minuto, segundo;
		long[] modificacion = null;
		long contador = 1;

		
		for (int i=0;i<arrayFicheros.length;i++) {
			File file1 = new File(arrayFicheros[i]);
			modificacion[i] = file1.lastModified();
		}
		
		for (int i=1;i<modificacion.length;i++){
			File file1 = new File(arrayFicheros[i-1]);
			File file2 = new File(arrayFicheros[i]);
			long ms = file1.lastModified();
			long ms2 = file2.lastModified();
			long valor =  ms2 - ms;
			contador = i;
			long valor2;
			if (i > 1) {
				valor2 = ms2 - ms;
				if (valor2<valor)
					contador =i;
			}
			
		}
		File file1 = new File(arrayFicheros[(int) (contador-1)]);
		File file2 = new File(arrayFicheros[(int) contador]);
	
// Aquí debería llamar al métido de valeria para hacer la comparación y devolver el porcentaje
		
	    
		return 1;
	}
	
	public static String[] listarFicherosPorCarpeta(final File carpeta) {
		String [] arrayFicheros = null;
		int i = 0;
	    for (final File ficheroEntrada : carpeta.listFiles()) {
	        if (ficheroEntrada.isDirectory()) {
	            listarFicherosPorCarpeta(ficheroEntrada);
	         
	            i++;
	            
	        } else {
	            System.out.println(ficheroEntrada.getName());
	        }
	        arrayFicheros[i] = ficheroEntrada.getName();
	    }
		return arrayFicheros;
	    
	}

	
	public static void main(String[] argv){
		
		int hayFichero = 1;
		String [] arrayFicheros = null;
		
		
		File file1 = new File("///home/ana/eclipse-workspace/FrecuenciaTiempos/src/FrecuenciaTiempos/ejemplo.txt");
	   	File file2 = new File("///home/ana/eclipse-workspace/FrecuenciaTiempos/src/FrecuenciaTiempos/ejemplo2.txt");    	
	    
	   	if (hayFichero == 1) {
	   		//Si no se ha creado ningun fichero en el commit no hay que hacer nada con ese commit
	   			int primero = primerFichero(file1, "2021-01-15 17:36:10");
	   			int segundo = primerFichero(file2, "2021-01-15 17:36:10");
	   			
	   			//Comparamos en primer lugar el primer fichero si se ha creado con un tiempo prudencial o no para considerar que pueda haber copia
	   			int porcentajeCopiaPrimero = compararTiempos(primero, segundo);
	   			
	   			long ms = ultimaModificacion(arrayFicheros);
	   			
	   			final File carpeta = new File("/home/ana/eclipse-workspace/FrecuenciaTiempos/src/FrecuenciaTiempos");
	   			

	   			listarFicherosPorCarpeta(carpeta);
	   	}

	}
}