package urjc.isi.servidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class realizaExamenDao {
	
	private static Connection c;

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
    
    public String ExamenesAlumno(int idAlumno) {
    	String Examen = null;
    	
        try {
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen");
            ResultSet rs = ps.executeQuery(String.valueOf(idAlumno));

            while(rs.next()) {
                Path = rs.getString("idExamen");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Examen;
        }
    }
    
    public String AlumnosExamen(int idExamen) {
    	String Alumno = null;
    	
        try {
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen");
            ResultSet rs = ps.executeQuery(String.valueOf(idExamen));

            while(rs.next()) {
                Path = rs.getString("idAlumno");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Alumno;
        }
    }
    
    public void save(realizaExamen realizaExamen) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into RealizaExamen(idExamen, idAlumno) VALUES(?,?)");
            ps.setInt(1, realizaExamen.getIdExamen());
            ps.setString(2, realizaExamen.getIdAlumno());
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