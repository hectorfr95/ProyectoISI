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

            String username = "pnppwxvdpozvck";
            String password = "7528b8d1e443547a70ea96ca4d16af0d75debdda07550ceb861e0d597818873e";
	        String host = "ec2-52-205-145-201.compute-1.amazonaws.com";
	        String port = "5432";
	        String database = "dh952nv6jujp8";
            String dbUrl = "jdbc:postgresql://" + host + ":" + port + "/" + database;
            
        	c = DriverManager.getConnection(dbUrl,username,password);
            c.setAutoCommit(false);
            
            c.prepareStatement("drop table if exists Examenes").execute();
            c.prepareStatement("CREATE TABLE Examenes (IdExamen	INTEGER NOT NULL UNIQUE,Fecha	DATE NOT NULL,Asignatura VARCHAR(50) NOT NULL,PRIMARY KEY(IdExamen))").execute();
            
            c.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Con este método vamos a poder obtener todos exámenes de la tabla Exámen.
    public List<examen> all() {

        List<examen> allExamenes = new ArrayList<examen>();

        try {
            PreparedStatement ps = c.prepareStatement("select * from Examenes");

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                Date Fecha = rs.getDate("Fecha");
                String Asignatura = rs.getString("Asignatura");
                allExamenes.add(new examen(idExamen, Fecha, Asignatura));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allExamenes;
        }
    }
    
    public Date getFecha(int idExamen) {
    	Date fecha = null;

        try {
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	fecha = rs.getDate("Fecha");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return fecha;
        }
    }
    
    // Con este método insertamos el objeto examen recibido en la tabla de nuestra bbdd Examenes.
    public void save(examen examen) {
        try {
            PreparedStatement ps = c.prepareStatement("insert into Examenes(idExamen, Fecha , Asignatura) VALUES(?,?,?)");
            ps.setInt(1, examen.getIdExamen());
    		System.out.println("sql.Date insert: "+  examen.getFecha());
            ps.setDate(2, examen.getFecha());
            ps.setString(3, examen.getAsignatura());
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
