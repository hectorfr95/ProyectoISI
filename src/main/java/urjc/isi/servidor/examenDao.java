package urjc.isi.servidor;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class examenDao {
	
	private static Connection c;
	
    // Este método abre la conexion con la bbdd
    public void abrirConexion() throws SQLException {
    	
        c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
        c.setAutoCommit(false);
    }

	// Con este método creamos la conexión con la bbdd.
    // Creamos la tabla Examenes (Si ya existe, la elimina y la vuelve a crear)
    public examenDao() throws URISyntaxException {
        try {
            if(c!=null) return;
            abrirConexion();
            
            c.prepareStatement("drop table if exists Examenes").execute();
            c.prepareStatement("CREATE TABLE Examenes (IdExamen	INTEGER NOT NULL UNIQUE,Fecha	DATE NOT NULL,Asignatura VARCHAR(50) NOT NULL,PRIMARY KEY(IdExamen))").execute();
            
            c.commit();
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    // Este método cierra las conexiones con la bbdd, para que no surjan bloqueos.
    public void cerrarConexion(PreparedStatement ps, ResultSet rs) throws SQLException {
    	
    	if(rs != null) {
    		rs.close();
    	}
        ps.close();
        c.close();
    }
    
    // Con este método vamos a poder obtener todos exámenes de la tabla Exámen.
    public List<examen> all() {

        List<examen> allExamenes = new ArrayList<examen>();

        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("select * from Examenes");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                Date Fecha = rs.getDate("Fecha");
                String Asignatura = rs.getString("Asignatura");
                allExamenes.add(new examen(idExamen, Fecha, Asignatura));
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allExamenes;
        }
    }
    
    // Método de comprobación para ver si se añade bien la asigantura
    public String getAsignatura(int idExamen) {
    	String asignatura=null;
    	try {
    		abrirConexion();
        	// Query sql
        	String query = "select * from Examenes Where idExamen=" + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	
            	asignatura = rs.getString("Asignatura");
            }
            cerrarConexion(ps, rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	return asignatura;
        }
    	
    }
    
    // Método que devuelve un 1 si está el examen en la bbdd, y 0 si no está
    public int comprobar_examen(int idExamen) {
    	int aux=0;
        try {
        	abrirConexion();
        	// Query sql
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	int idex = rs.getInt("idExamen");
            	if(idex == idExamen)
            		aux=1;
            }
            cerrarConexion(ps, rs);
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
        	return aux;
        }
    }

    // Metodo de comprobación de la insercción de la fecha
    public Date getFecha(int idExamen) {
    	Date fecha = null;

        try {
        	abrirConexion();
        	// Query sql
        	String query = "SELECT * from Examenes WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
            	fecha = rs.getDate("Fecha");
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return fecha;
        }
    }
    
    // Con este método insertamos el objeto examen recibido en la tabla de nuestra bbdd Examenes.
    public void save(examen examen) {
        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("insert into Examenes(idExamen, Fecha , Asignatura) VALUES(?,?,?)");
            ps.setInt(1, examen.getIdExamen());    		
            ps.setDate(2, examen.getFecha());
            ps.setString(3, examen.getAsignatura());
            System.out.println("sql.Examen ID: "+  examen.getIdExamen());
            System.out.println("sql.Asignatura: "+  examen.getAsignatura());
            System.out.println("sql.Date insert: "+  examen.getFecha());
            ps.execute();
            c.commit();
            cerrarConexion(ps, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
}
