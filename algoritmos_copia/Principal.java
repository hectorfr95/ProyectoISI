import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

public class Principal {
	
	private static final String ARCHIVO_ZIP = "https://wordpress.org/latest.zip";
	private static final String RUTA_SALIDA = "/home/juanmanuel/Escritorio/prueba";
	private static final String DIRECTORIO = "/home/juanmanuel/Escritorio/prueba";
	
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
    
    public static void EjecutarWget(){
		try {
			String[] cmd = {"wget","-P",RUTA_SALIDA,ARCHIVO_ZIP};
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String resultOfExecution = null;
            while((resultOfExecution = br.readLine()) != null){
                System.out.println(resultOfExecution);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
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
	
    public static void main(String[] args) throws Exception{
		CrearDirectorio(DIRECTORIO);
        EjecutarWget();
        String path = RUTA_SALIDA;
        String[] files = ObtenerFich(path);
		if (files != null) {
			int size = files.length;
            for (int i = 0; i < size; i++) {
				System.out.println(files[i]);
                List listaArchivos;
                String ARCHIVO_ZIP = files[i];
                Descomprimir(ARCHIVO_ZIP,RUTA_SALIDA);
            }
		}
    }
}

