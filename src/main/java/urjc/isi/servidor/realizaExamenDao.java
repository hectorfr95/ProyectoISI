package urjc.isi.servidor;

import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class realizaExamenDao {
	
	private static Connection c;

    // Este método abre la conexion con la bbdd
    public void abrirConexion() throws SQLException {
    	
        c = DriverManager.getConnection("jdbc:sqlite:proyecto.db");
        c.setAutoCommit(false);
    }
    
	// Con este método creamos la conexión con la bbdd.
    // Se crea la tabla RealizaExamen (si ya estaba se borra)
    public realizaExamenDao() throws URISyntaxException {
	
        try {
            if(c!=null) return;
            abrirConexion();

            c.prepareStatement("drop table if exists RealizaExamen").execute();
            c.prepareStatement("CREATE TABLE RealizaExamen (idExamen INTEGER NOT NULL,idAlumno varchar(50) NOT NULL,Path varchar(50),FOREIGN KEY(idExamen) REFERENCES Examenes(IdExamen),FOREIGN KEY(idAlumno) REFERENCES Alumnos(idAlumno),PRIMARY KEY(idExamen,idAlumno))").execute();
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
    
    // Lista de todos los examenes
    public List<finalexamen> alumnos_examen(int idExamen_) {

        List<finalexamen> allFinalExamen = new ArrayList<finalexamen>();

        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("select idExamen, RealizaExamen.idAlumno, ip, puerto from RealizaExamen left join Alumnos on RealizaExamen.idAlumno=Alumnos.idAlumno WHERE idExamen="+idExamen_);

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int idex = rs.getInt("idExamen");
                String idAlumno = rs.getString("idAlumno");
                String ip = rs.getString("IP");
                int puerto = rs.getInt("Puerto");
                allFinalExamen.add(new finalexamen(idex, idAlumno,ip, puerto));
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allFinalExamen;
        }
    }
   // select idExamen, RealizaExamen.idAlumno, ip, puerto from RealizaExamen left join Alumnos on RealizaExamen.idAlumno=Alumnos.idAlumno
    public List<realizaExamen> all() {

        List<realizaExamen> allRealiza = new ArrayList<realizaExamen>();

        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen");

            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int idExamen = rs.getInt("idExamen");
                String idAlumno = rs.getString("idAlumno");
                String path = rs.getString("Path");
                allRealiza.add(new realizaExamen(idExamen, idAlumno, path));
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return allRealiza;
        }
    }
    
    
    // Con este método vamos a obtener los idAlumno de todos los alumnos que hayan realizado
    // el examen con este id.
    public List<String> AlumnosExamen(int idExamen) {
    	List<String> idAlumnos = new ArrayList<String>();
    	
        try {
        	abrirConexion();
        	// Query sql
        	String query = "SELECT * from RealizaExamen WHERE idExamen = " + idExamen;
            PreparedStatement ps = c.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
            	idAlumnos.add(rs.getString("idAlumno"));
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return idAlumnos;
        }
    }
    
    // Con este método obtenemos el Path de todos los alumnos que han realizado este examen (idExamen)
    public List<String> pathCommits(int idExamen) {
    	List<String> Paths = new ArrayList<String>();
    	
        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("select * from RealizaExamen where idExamen = " + idExamen);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Paths.add(rs.getString("Path"));
            }
            cerrarConexion(ps, rs);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            return Paths;
        }
    }
    
    // Método de comprobacion del grupo servidor
    public void verificacion_zip(String id_alumno, int id_examen) {
        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("UPDATE RealizaExamen SET Path = '1' WHERE [idExamen] = "+id_examen+" AND [idAlumno] = '"+id_alumno+"'");
            ps.execute();
            c.commit();
            cerrarConexion(ps, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    // Con este método insertamos el objeto realizaExamen recibido en la tabla de nuestra bbdd RealizaExamenes.
    public void save(realizaExamen realizaExamen) {
        try {
        	abrirConexion();
        	// Query sql
            PreparedStatement ps = c.prepareStatement("insert into RealizaExamen(idExamen, idAlumno, Path) VALUES(?,?, ?)");
            ps.setInt(1, realizaExamen.getIdExamen());
            ps.setString(2, realizaExamen.getIdAlumno());
            ps.setString(3, realizaExamen.getPath());
            ps.execute();
            c.commit();
            cerrarConexion(ps, null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
}
