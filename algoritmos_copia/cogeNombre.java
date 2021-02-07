package algo.pruebashell;

//Estos dos imports son los necesarios para hacer el volcado de la consola a un fichero .txt
import java.io.FileOutputStream;
import java.io.PrintStream;
 

import java.io.File;
import java.io.FileNotFoundException;
import java.io.File;

public class cogeNombre {
	
	
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
	
    //Clase main para probar el metodo getFileWithoutExtension
    public static void main(String[] args) throws FileNotFoundException {
    	
    	//Esta linea es la importante para que el volcado de la consola se haga en un fichero .txt
    	System.setOut(new PrintStream(new FileOutputStream("output.txt")));
    	
    	
        String fileName = getFileNameWithoutExtension(new File("/home/razvan/Escritorio/algo.txt"));
        System.out.println(fileName);
        
        //Estas son pruebas para probar si funciona bien el volcado al .txt
        System.out.println("This is test output");
        System.out.println("This is test output1");
        System.out.println("This is test output2");
        System.out.println("This is test output3");
        System.out.println("This is test outpu4");
        System.out.println("This is test outpu5");
        System.out.println("This is test outpu6");
        System.out.println("This is test output7");

    }
}
