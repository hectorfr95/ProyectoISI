import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

// Contar nº de palabras por commit

public class Contador {
	
	public static int contador(String fichero) throws FileNotFoundException, IOException{
		FileReader fr;
		BufferedReader bf;
		String s;
		StringTokenizer st;
		int contador = 0;
	
		File file = new File(fichero);
		if(file.exists()){
			fr = new FileReader(file);
			bf = new BufferedReader(fr);
			
			while((s = bf.readLine()) != null){
				st = new StringTokenizer(s);
				contador = contador + st.countTokens();
			}
			bf.close();
		} else {
			throw new FileNotFoundException("El file no existe");
		}
		
		return contador;
	}
	
	public static void main(String[] args) throws IOException {
		int counter1[] = new int[30];
		int counter2[] = new int[30];
		int i;
		
		String dircarpeta = "/home/juanmanuel/Descargas/02-JUnit-code/Proyecto";
		File carpeta = new File(dircarpeta);
		String[] listado = carpeta.list();
	
		for (i = 0; i < listado.length; i++){
			counter1[i] = contador(listado[i]);
			counter2[i] = contador(listado[i]);
		}
		
		for (i = 0; i < counter1.length; i++){
			if((counter1[i] != 0) && (counter2[i] != 0)){
				System.out.println("\nEl examen 1 tiene en su commit"+ " "+ (i+1)+ ": "+ counter1[i]+ " "+ "palabras");
				System.out.println("Y el examen 2 tiene en su commit"+ " "+ (i+1)+ ": "+ counter1[i]+ " "+ "palabras");
			}
		 }
	}
}
