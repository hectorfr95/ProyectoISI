package urjc.isi.servidor;


import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class alumnoDao {
	
	private static Connection c;
	
    // Este método abre la conexion con la bbdd
    public void abrirConexion() throws SQLException {
    	
        c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
        c.setAutoCommit(false);
    }

	// Con este método creamos la conexión con la bbdd
	// Se crea la tabla Alumnos (si ya estaba creada se elimina)
    public alumnoDao() throws URISyntaxException {
        try {
            if(c!=null) return;
        	abrirConexion();

            c.prepareStatement("drop table if exists Alumnos").execute();
            c.prepareStatement("CREATE TABLE Alumnos (idAlumno	VARCHAR(50) NOT NULL UNIQUE,Nombre	VARCHAR(50) NOT NULL,Puerto	INTEGER,IP	VARCHAR(50),PRIMARY KEY(idAlumno))").execute();
	
            c.commit();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Este método cierra las conexiones con la bbdd, para que no surjan bloqueos.
    public void cerrarConexion(Connection conn, PreparedStatement ps, ResultSet rs) throws SQLException {
    	
    	if(rs != null) {
    		rs.close();
    	}
        ps.close();
        c.close();
    }

    // Con este método vamos a poder obtener todos los alumnos de la tabla Alumnos.
    public List<alumno> all() {

        List<alumno> allAlumnos = new ArrayList<alumno>();

        try {
        	abrirConexion();
        	// Esta es la query de sql
            PreparedStatement ps = c.prepareStatement("select * from Alumnos");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                String idAlumno = rs.getString("idAlumno");
                String nombre = rs.getString("nombre");
                int puerto = rs.getInt("puerto");
                String ip = rs.getString("ip");
                allAlumnos.add(new alumno(idAlumno, nombre, puerto, ip));
            }
            cerrarConexion(c, ps, rs);
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
        	abrirConexion();        	// Esta es la query de sql
        	String query = "SELECT * from Alumnos WHERE idAlumno = " + idAlumno;
            PreparedStatement ps = c.prepareStatement(query);
            	
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	nombre = rs.getString("nombre");
                ip = rs.getString("Ip");
                puerto = rs.getInt("puerto");
            }
            cerrarConexion(c, ps, rs);
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
        	abrirConexion();
        	// Esta es la query de sql
            PreparedStatement ps = c.prepareStatement("insert into Alumnos(idAlumno, nombre , puerto, ip) VALUES(?,?,?,?)");
            ps.setString(1, alumno.getIdAlumno());
            ps.setString(2, alumno.getNombre());
            ps.setInt(3, alumno.getPuerto());
            ps.setString(4, alumno.getIp());
            
            ps.execute();
            // trazas
            System.out.println("sql.DNI alumno: "+  alumno.getIdAlumno());
            System.out.println("sql.NOMBRE alumno: "+  alumno.getNombre());
            System.out.println("sql.IP: "+  alumno.getIp());
            System.out.println("sql.PUERTO: "+  alumno.getPuerto());
            c.commit();
            cerrarConexion(c, ps, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
