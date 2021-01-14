package urjc.isi.servidor;

import java.sql.*;

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
    // Con este método obtenemos el Path, dado el idExamen 
    public String pathCommits(int idExamen) {
    	String Path = null;
    	
        try {
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen");
            ResultSet rs = ps.executeQuery(String.valueOf(idExamen));

            while(rs.next()) {
                Path = rs.getString("Path");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Path;
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
