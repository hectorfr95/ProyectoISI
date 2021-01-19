package urjc.isi;
import java.io.*;

public class UltimoPath {
	public static String pathUltimos[];//Declaramos el array donde guardaremos los paths con los utlimos ficheros modificados
	
	//Funcion que saca la longitud necesaria para guardar los paths de los ultimos modificados(reutilizo codigo)
	public static int longArray() {
		String sCarp = System.getProperty("user.dir");//Obtenemos la carpeta actual desde donde ejecutamos el código(de la carpeta del proyecto)
		File carpeta = new File(sCarp); //Con este File obtenemos información de la carpeta
		File[] listado = carpeta.listFiles();//Asi nos devuelven objetos file y podemos sacar mas info de ellos

		int longi=0;
		
		if(listado==null || listado.length == 0) {
			System.out.println("No hay elementos en la carpeta actual");
		}else {
			longi++;//solo ponemos un longi++ ya que si tiene archivos solo nos va a extraer uno que es la pos que guardamos
			for(int i=0; i<listado.length;i++) {
				File archivo = listado[i];
				if(archivo.isDirectory()==true) {
					File[] listado2 = archivo.listFiles();
					if(listado2==null || listado2.length == 0) {
						System.out.println("No hay elementos en la carpeta actual");
					}else {
						longi++;
					}					
				}				
			}
		}
		return longi;
	}
		
	public static void listarCarp() {
		
		int longi=longArray();
			
		String sCarp = System.getProperty("user.dir");//Obtenemos la carpeta actual desde donde ejecutamos el código(de la carpeta del proyecto)
		File carpeta = new File(sCarp); //Con este File obtenemos información de la carpeta
		File[] listado = carpeta.listFiles();//Asi nos devuelven objetos file y podemos sacar mas info de ellos
		pathUltimos = new String[listado.length];//Inicializo el array para que no salga error de NullPointerException
		long aux;
		long aux2;
		int indaux=0;
		int indaux2=0;
		int idx=0;
		
		if(listado==null || listado.length == 0) {
			System.out.println("No hay elementos en la carpeta actual");
			return;
		}else {
			aux=0;//inicializamos a 0 por si tiene guardado de la anterior carpeta
			for(int i=0; i<listado.length;i++) {
				File archivo = listado[i];
				if(archivo.isDirectory()==true) {
					File[] listado2 = archivo.listFiles();
					if(listado2==null || listado2.length == 0) {
						System.out.println("No hay elementos en la carpeta actual");
						return;
					}else {
						aux2=0;//inicializamos a 0 por si tiene guardado de la anterior carpeta
						for(int j=0; j<listado2.length;j++) {
							
							File archivo2 = listado2[j];
							if(aux2<archivo2.lastModified()) {
								aux2=archivo2.lastModified();
								indaux2=j;
							}
						}
						pathUltimos[idx]=listado2[indaux2].getAbsolutePath();	
						idx++;
					}
					
				}else {
					if(aux<archivo.lastModified()) {
						aux=archivo.lastModified();
						indaux=i;
					}
				}
				
			}
			pathUltimos[idx]=listado[indaux].getAbsolutePath();//No ponemos idx++ porque en la carpeta actual no reitera más
		}
	}

	public static void main(String[] args) {
		listarCarp();
	}

}
