package urjc.isi.servidor;

import static org.junit.Assert.*;


import org.junit.Test;

import urjc.isi.servidor.alumno;
import urjc.isi.servidor.alumnoDao;
import urjc.isi.servidor.examen;
import urjc.isi.servidor.examenDao;
import urjc.isi.servidor.realizaExamen;
import urjc.isi.servidor.realizaExamenDao;
import static org.mockito.Mockito.*;


import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class testsSelect {
	@Test
    public void AllAlumnos() {

		alumno juanito = new alumno("1247567", "Juanito Garcia", 1234, "172.16.4.205");
		alumno dani = new alumno("7654321", "Daniel Hernandez", 5678, "10.128.1.253");
		alumnoDao dao = mock(alumnoDao.class);

        List<alumno> results = Arrays.asList(juanito, dani);
        when(dao.all()).thenReturn(results);

        List<alumno> allAlumnos = dao.all();
        assertEquals(juanito, allAlumnos.get(0));
        assertEquals(2, allAlumnos.size());
    }
	
	public void SelectAlumnos() {

		alumno juanito = new alumno("1247567", "Juanito Garcia", 1234, "172.16.4.205");
		alumno dani = new alumno("7654321", "Daniel Hernandez", 5678, "10.128.1.253");
		alumnoDao dao = mock(alumnoDao.class);

        List<alumno> results = Arrays.asList(juanito, dani);
        when(dao.all()).thenReturn(results);

        List<alumno> allAlumnos = dao.all();
   
        alumno alumno_1 =  dao.getAtributosAlumno("1234567");
        assertEquals(alumno_1, allAlumnos.get(0));
    }
	
	
    public void AllExamenes() {
    	
    	Date fecha = new Date();
    	long lnMilisegundos = fecha.getTime();
    	java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);
		examen examen_juanito = new examen(1010, sqlDate, "ISI") ;
		examen examen_dani = new examen(1802, sqlDate, "ISI") ;
		examenDao dao = mock(examenDao.class);

        List<examen> results = Arrays.asList(examen_juanito, examen_dani);
        when(dao.all()).thenReturn(results);

        List<examen> allExamenes = dao.all();
        assertEquals(examen_juanito, allExamenes.get(0));
        assertEquals(2, allExamenes.size());
    }
    
    public void SelectExamenes() {
    	
    	Date fecha = new Date();
    	long lnMilisegundos = fecha.getTime();
    	java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);
		examen examen_juanito = new examen(1010, sqlDate, "ISI") ;
    	lnMilisegundos = fecha.getTime();
    	java.sql.Date sqlDate_2 = new java.sql.Date(lnMilisegundos);
		examen examen_dani = new examen(1802, sqlDate_2, "ISI") ;
		examenDao dao = mock(examenDao.class);

        List<examen> results = Arrays.asList(examen_juanito, examen_dani);
        when(dao.all()).thenReturn(results);

        java.sql.Date fecha_1 =  dao.getFecha(1010);
        assertEquals(fecha_1, results.get(0).getFecha());
    }
    
    public void AllRealizaEx() {
    
    	realizaExamen re_examen_juanito = new realizaExamen(1010, "1234567", "Path") ;
    	realizaExamen re_examen_dani = new realizaExamen(1802, "7654321", "Path_2") ;
    	realizaExamenDao dao = mock(realizaExamenDao.class);
    	
    	 List<realizaExamen> all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani);
         when(dao.all()).thenReturn(all_realiza_examenes);
   
         String path =  dao.pathCommits(1802);
         assertEquals(path, all_realiza_examenes.get(1).getPath());
    }
    
}
