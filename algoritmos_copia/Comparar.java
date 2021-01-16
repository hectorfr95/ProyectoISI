import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.util.StringTokenizer;

// Contar nº de palabras por commit

public class Comparar {
	
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
			System.out.println("No existe el fichero "); //
		} //
		
		return contador;
	}
	
	public static void main(String[] args) throws IOException {
		int counter1;
		int counter2;
	
		counter1 = contador("texto.txt");	//counter = contador("/home/juanmanuel/Descargas/02-JUnit-code/Proyecto/texto.txt");
		
		counter2 = contador("textito.txt"); 
		
		if(counter1 != 0){
			System.out.println(counter1);
		}
		
		if(counter2 != 0){
			System.out.println(counter2);
		}
	}
}
