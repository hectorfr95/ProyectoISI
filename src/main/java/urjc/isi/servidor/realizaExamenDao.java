package urjc.isi.servidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class realizaExamenDao {
	
	private static Connection c;

	// Con este método creamos la conexión con la bbdd.
    public realizaExamenDao() {
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);

//            c.prepareStatement("drop table if exists RealizaExamen").execute();
//            c.prepareStatement("create table RealizaExamen (idExamen integer, idAlumno integer))").execute();
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<realizaExamen> all() {

        List<realizaExamen> allRealiza = new ArrayList<realizaExamen>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                String idAlumno = rs.getString("idAlumno");
                String path = rs.getString("Path");
                allRealiza.add(new realizaExamen(idExamen, idAlumno, path));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allRealiza;
        }
    }
//    // Con este método vamos a obtener los idExamen de todos los examenes que haya realizado
//    // el alumno con este id.
//    public int ExamenesAlumno(String idAlumno) {
//    	int idExamen = 0;
//    	
//        try {
//        	String query = "SELECT * from RealizaExamen WHERE idAlumno = " + idAlumno;
//            PreparedStatement ps = c.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//                idExamen = rs.getInt("idExamen");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            return idExamen;
//        }
//    }
    
//    // Con este método vamos a obtener los idAlumno de todos los alumnos que hayan realizado
//    // el examen con este id.
//    public String AlumnosExamen(int idExamen) {
//    	String idAlumno = null;
//    	
//        try {
//        	String query = "SELECT * from RealizaExamen WHERE idExamen = " + idExamen;
//            PreparedStatement ps = c.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while(rs.next()) {
//            	idAlumno = rs.getString("idAlumno");
//            }
//
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        } finally {
//            return idAlumno;
//        }
//    }
//    
    // Con este método obtenemos el Path de todos los alumnos que han realizado este examen (idExamen)
    public List<String> pathCommits(int idExamen) {
    	List<String> Paths = new ArrayList<String>();
    	
        try {
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen where idExamen = " + idExamen);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Paths.add(rs.getString("Path"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Paths;
        }
    }
    
    // Con este método insertamos el objeto realizaExamen recibido en la tabla de nuestra bbdd RealizaExamenes.
    public void save(realizaExamen realizaExamen) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into RealizaExamen(idExamen, idAlumno, Path) VALUES(?,?, ?)");
            ps.setInt(1, realizaExamen.getIdExamen());
            ps.setString(2, realizaExamen.getIdAlumno());
            ps.setString(3, realizaExamen.getPath());
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
