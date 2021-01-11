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
    
    public int ExamenesAlumno(int idAlumno) {
    	int idExamen = 0;
    	
        try {
        	String query = "SELECT * from RealizaExamen WHERE idAlumno = " + idAlumno;
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                idExamen = rs.getInt("idExamen");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return idExamen;
        }
    }
    
    public int AlumnosExamen(int idExamen) {
    	int idAlumno = 0;
    	
        try {
        	String query = "SELECT * from RealizaExamen WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	idAlumno = rs.getInt("idAlumno");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return idAlumno;
        }
    }
    
    public void save(realizaExamen realizaExamen) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into RealizaExamen(idExamen, idAlumno) VALUES(?,?)");
            ps.setInt(1, realizaExamen.getIdExamen());
            ps.setInt(2, realizaExamen.getIdAlumno());
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

        }
    }
}
