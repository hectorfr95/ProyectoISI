package urjc.isi.servidor;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class examenDao {
	
	private static Connection c;

	// Con este método creamos la conexión con la bbdd.
    public examenDao() throws URISyntaxException {
        try {
            if(c!=null) return;

            c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
            

            c.prepareStatement("drop table if exists Examenes").execute();
            c.prepareStatement("CREATE TABLE Examenes (IdExamen	INTEGER NOT NULL UNIQUE,Fecha	DATE NOT NULL,Asignatura VARCHAR(50) NOT NULL,PRIMARY KEY(IdExamen))").execute();
            
            c.commit();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // Con este método vamos a poder obtener todos exámenes de la tabla Exámen.
    public List<examen> all() {

        List<examen> allExamenes = new ArrayList<examen>();

        try {
        	c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement("select * from Examenes");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                Date Fecha = rs.getDate("Fecha");
                String Asignatura = rs.getString("Asignatura");
                allExamenes.add(new examen(idExamen, Fecha, Asignatura));
            }
            rs.close();
            ps.close();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allExamenes;
        }
    }
    
    public String getAsignatura(int idExamen) {
    	String asignatura=null;
    	try {
        	c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
        	String query = "select * from Examenes Where idExamen=" + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	
            	asignatura = rs.getString("Asignatura");
            }
            rs.close();
            ps.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	return asignatura;
        }
    	
    }
    public int comprobar_examen(int idExamen) {
    	int aux=0;
        try {
        	c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	int idex = rs.getInt("idExamen");
            	if(idex == idExamen)
            		aux=1;
            }
            rs.close();
            ps.close();
            c.close();
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	return aux;
        }
    }
    
    
    
    
    
    
    
    
    public Date getFecha(int idExamen) {
    	Date fecha = null;

        try {
        	c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	fecha = rs.getDate("Fecha");
            }
            rs.close();
            ps.close();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return fecha;
        }
    }
    
    // Con este método insertamos el objeto examen recibido en la tabla de nuestra bbdd Examenes.
    public void save(examen examen) {
        try {
        	c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
            c.setAutoCommit(false);
            PreparedStatement ps = c.prepareStatement("insert into Examenes(idExamen, Fecha , Asignatura) VALUES(?,?,?)");
            ps.setInt(1, examen.getIdExamen());    		
            ps.setDate(2, examen.getFecha());
            ps.setString(3, examen.getAsignatura());
            System.out.println("sql.Examen ID: "+  examen.getIdExamen());
            System.out.println("sql.Asignatura: "+  examen.getAsignatura());
            System.out.println("sql.Date insert: "+  examen.getFecha());
            ps.execute();
            c.commit();
            ps.close();
            c.close();
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
