import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

class Test {
	
	private static final String RUTA_SALIDA = "/home/alumno/Escritorio/ejemplo";

        public static void main( String[] args ) {

            String path = RUTA_SALIDA;


            String[] files = getFiles( path );

            if ( files != null ) {

                int size = files.length;

                for ( int i = 0; i < size; i ++ ) {

                    System.out.println( files[ i ] );
                    List listaArchivos;
                    String ARCHIVO_ZIP = files[i];
                    Test Test = new Test();
                    Test.DescomprimirZip(ARCHIVO_ZIP,RUTA_SALIDA);
                }
            }
        }


        public static String[] getFiles( String dir_path ) {

            String[] arr_res = null;

            File f = new File( dir_path );

            if ( f.isDirectory( )) {

                List<String> res   = new ArrayList<>();
                File[] arr_content = f.listFiles();

                int size = arr_content.length;

                for ( int i = 0; i < size; i ++ ) {

                    if ( arr_content[ i ].isFile( ))
                    res.add( arr_content[ i ].toString( ));
                }


                arr_res = res.toArray( new String[ 0 ] );

            } else
                System.err.println( "¡ Path NO válido !" );


            return arr_res;
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
