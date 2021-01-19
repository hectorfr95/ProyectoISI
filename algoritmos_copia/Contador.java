import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

// Contar nº de palabras por commit

public class Contador {
	
	public static int contador(String fichero) throws FileNotFoundException, IOException{ //contador(String file)
		FileReader fr;
		BufferedReader bf;
		String s;
		StringTokenizer st;
		int contador = 0;
	
		File file = new File(fichero); //
		if(file.exists()){ //
			fr = new FileReader(file);
			bf = new BufferedReader(fr);
			
			while((s = bf.readLine()) != null){
				st = new StringTokenizer(s);
				contador = contador + st.countTokens();
			}
			bf.close();
		} else { //
			throw new FileNotFoundException("El file no existe"); //
		} //
		
		return contador;
	}
	
	public static void main(String[] args) throws IOException {
		int counter[] = new int[15];
		int i;
		
		String dircarpeta = "/home/juanmanuel/Descargas/02-JUnit-code/Proyecto";
		File carpeta = new File(dircarpeta);
		String[] listado = carpeta.list();
		
		for (i = 0; i < listado.length; i++){
			System.out.println(listado[i]);
		}
	
		for (i = 0; i < listado.length; i++){
			counter[i] = contador(listado[i]);	//counter = contador("/home/juanmanuel/Descargas/02-JUnit-code/Proyecto/texto.txt");
		}
		
		for (i = 0; i < counter.length; i++){
			if(counter[i] != 0){
				System.out.println(counter[i]);
			}
		 }
	}
}

/*
 * En el programa principal hacer un bucle for, llamando 2 veces al contador pasando a cada uno un distinto array con los ficheros
 */
 
