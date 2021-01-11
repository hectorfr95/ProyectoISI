package urjc.isi.servidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class alumnoDao {
	
	private static Connection c;

    public alumnoDao() {
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);

//            c.prepareStatement("drop table if exists Alumnos").execute();
//            c.prepareStatement("create table Alumnos (idAlumno integer, nombre varchar(100), puerto integer, ip varchar(100))").execute();
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<alumno> all() {

        List<alumno> allAlumnos = new ArrayList<alumno>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from Alumnos");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int idAlumno = rs.getInt("idAlumno");
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
    
    public String[] IpPuerto(int idAlumno) {
    	String ip = null;
    	Integer puerto = null;
    	
        try {
            PreparedStatement ps = c.prepareStatement("select * from Alumnos");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                ip = rs.getString("Ip");
                puerto = rs.getInt("puerto");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	String[] ethernet_alumno = {String.valueOf(puerto), ip};
            return [ethernet_alumno;
        }
    }

    
    
    public void save(alumno alumno) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into Examenes(idAlumno, nombre , puerto, ip) VALUES(?,?,?,?)");
            ps.setInt(1, alumno.getIdAlumno());
            ps.setString(2, alumno.getNombre());
            ps.setInt(3, alumno.getPuerto());
            ps.setString(4, alumno.getIp());
            ps.execute();

            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
