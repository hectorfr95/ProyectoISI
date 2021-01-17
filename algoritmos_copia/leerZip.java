import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class leerZip {
    List listaArchivos;
    private static final String ARCHIVO_ZIP = "/home/alumno/Escritorio/ejemplo.zip";
    private static final String RUTA_SALIDA = "/home/alumno/Escritorio/ejemplo";
    public static void main(String[] args) {
        leerZip leerZip = new leerZip();
        leerZip.DescomprimirZip(ARCHIVO_ZIP, RUTA_SALIDA);
    }
    public void DescomprimirZip(String fZip, String salida) {
        byte[] buffer = new byte[1024];
        try {
            File folder = new File(RUTA_SALIDA);
            if (!folder.exists()) {
                folder.mkdir();
            }
            ZipInputStream zz = new ZipInputStream(new FileInputStream(fZip));
            ZipEntry ze = zz.getNextEntry();
            while (ze != null) {
                String nf = ze.getName();
                File archivo = new File(salida + File.separator + nf);
                //System.out.println("archivo descomprimido : " + archivo.getAbsoluteFile());
                new File(archivo.getParent()).mkdirs();
                FileOutputStream fos = new FileOutputStream(archivo);
                int len;
                while ((len = zz.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
                ze = zz.getNextEntry();
            }
            zz.closeEntry();
            zz.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
        	System.out.println("Listo");
        }
    }
}
