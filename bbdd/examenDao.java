package urjc.isi.servidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class examenDao {
	
	private static Connection c;

    public examenDao() {
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);

//            c.prepareStatement("drop table if exists Examenes").execute();
//            c.prepareStatement("create table Examenes (idExamen integer, Fecha varchar(100), Asignatura varchar(100), Path varchar(100))").execute();
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<examen> all() {

        List<examen> allExamenes = new ArrayList<examen>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from Examenes");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                String Fecha = rs.getString("Fecha");
                String Asignatura = rs.getString("Asignatura");
                String Path = rs.getString("Path");
                allExamenes.add(new examen(idExamen, Fecha, Asignatura, Path));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allExamenes;
        }
    }
    
    public String pathCommits(int idExamen) {
    	String Path = null;
    	
        try {
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Path = rs.getString("Path");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Path;
        }
    }

    public void save(examen examen) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into Examenes(idExamen, Fecha , Asignatura, Path) VALUES(?,?,?,?)");
            ps.setInt(1, examen.getIdExamen());
            ps.setString(2, examen.getFecha());
            ps.setString(3, examen.getAsignatura());
            ps.setString(4, examen.getPath());
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
