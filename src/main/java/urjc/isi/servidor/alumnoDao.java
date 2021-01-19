package urjc.isi.servidor;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class alumnoDao {
	
	private static Connection c;

	// Con este método creamos la conexión con la bbdd.
    public alumnoDao() throws URISyntaxException {
        try {
            if(c!=null) return;

            String username = "yaaaxjmixpfybt";
            String password = "85f576abb87479453ec379a2b68dc7695af7ca2188f1b93c2d6ca6f72a2aa71c";
	        String host = "ec2-52-2-6-71.compute-1.amazonaws.com";
	        String port = "5432";
	        String database = "d52kb40627alpm";
            String dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            
        	c = DriverManager.getConnection(dbUrl,username,password);
            c.setAutoCommit(false);

            c.prepareStatement("drop table if exists Alumnos").execute();
            c.prepareStatement("CREATE TABLE Alumnos (idAlumno	VARCHAR(50) NOT NULL UNIQUE,Nombre	VARCHAR(50) NOT NULL,Puerto	INTEGER,IP	VARCHAR(50),PRIMARY KEY(idAlumno))").execute();
	
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Con este método vamos a poder obtener todos alumnos de la tabla Alumnos.
    public List<alumno> all() {

        List<alumno> allAlumnos = new ArrayList<alumno>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from Alumnos");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String idAlumno = rs.getString("idAlumno");
                String nombre = rs.getString("nombre");
                int puerto = rs.getInt("puerto");
                String ip = rs.getString("ip");
                allAlumnos.add(new alumno(idAlumno, nombre, puerto, ip));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allAlumnos;
        }
    }
 
    // Con este método vamos a poder obtener todos los atributos de la entidad
    // Alumnos, al recibir el idAlumno.
    public alumno getAtributosAlumno(String idAlumno) {
    	String nombre = null;
    	String ip = null;
    	int puerto = 0;
    	
        try {
        	String query = "SELECT * from Alumnos WHERE idAlumno = " + idAlumno;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	nombre = rs.getString("nombre");
                ip = rs.getString("Ip");
                puerto = rs.getInt("puerto");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	alumno alumno = new alumno(idAlumno, nombre , puerto, ip);
            return alumno;
        }
    }

    // Con este método insertamos el objeto alumno recibido en la tabla de nuestra bbdd Alumnos.
    public void save(alumno alumno) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into Alumnos(idAlumno, nombre , puerto, ip) VALUES(?,?,?,?)");
            ps.setString(1, alumno.getIdAlumno());
            ps.setString(2, alumno.getNombre());
            ps.setInt(3, alumno.getPuerto());
            ps.setString(4, alumno.getIp());
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Cerramos la conexión con la bbdd
    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
