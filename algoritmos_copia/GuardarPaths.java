package urjc.isi;
import java.io.*;

public class GuardarPaths {
	public static String arrayPaths[];//Declaramos el array donde guardaremos los paths
	
	public static void listarCarp() {
		
		String sCarp = System.getProperty("user.dir");//Obtenemos la carpeta actual desde donde ejecutamos el código(de la carpeta del proyecto)
		File carpeta = new File(sCarp); //Con este File obtenemos información de la carpeta
		String[] listado = carpeta.list();
		arrayPaths = new String[listado.length];//Inicializo el array para que no salga error de NullPointerException
		
		if(listado==null || listado.length == 0) {
			System.out.println("No hay elementos en la carpeta actual");
			return;
		}else {
			for(int i=0; i<listado.length;i++) {
				File f = new File(listado[i]);
				arrayPaths[i]=f.getAbsolutePath();
			}
		}
	}

	
	public static void main(String[] args) {
		listarCarp();
	}

}
