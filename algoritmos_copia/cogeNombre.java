package algo.pruebashell;

import java.io.File;

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
    public static void main(String[] args) {
        String fileName = getFileNameWithoutExtension(new File("/home/razvan/Escritorio/algo.txt"));
        System.out.println(fileName);
    }
}