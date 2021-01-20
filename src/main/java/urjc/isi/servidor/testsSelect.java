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
	
	/* 
	 * CARACTERIZACIONES:
	 * Alumno.Dao
	 * 		C1: Devuelve todos los alumnos de la lista de manera correcta	
	 * 		B1.1: Tabla con elementos
	 * 		B1.2: Tabla sin elementos (null)
	 * 		C2: Seleción de un alumno segun su id
	 * 		B2.1: Entrada id correcto, (coincidente con el de un alumno)
	 * 		B2.2: Entrada id incorrecto, (no coincide con el de un alumno)
	 * Examen.Dao
	 * 		C1: Devuelve todos los examenes de la lista de manera correcta
	 * 		B1.1: Tabla con elementos
	 * 		B1.2: Tabla sin elementos (null)
	 * 		C2: Seleción de un examen segun su id
	 * 		B2.1: Entrada id correcto. 
	 * Realizaexamen.dao
	 * 		C1: Seleción de un path de realizaExamen segun su id (solo uno)
	 * 		B1.1: Entrada id correcto
	 * 		B1.2: Entrada id correcto, varios paths (coincide con el de un examen)
	 *		B1.3: Entrada id incorrecto, (no coincide con el de un examen)
	 */
	
	/* C1.B1.1 */
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
	
	/* C1.B1.2 */
    public void AllAlumnos_null() {

		alumnoDao dao = mock(alumnoDao.class);

        List<alumno> results = Arrays.asList();
        when(dao.all()).thenReturn(results);

        List<alumno> allAlumnos = dao.all();
        assertEquals(null, allAlumnos);
        assertEquals(0, allAlumnos.size());
    }
	
	/* C2.B1 */
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
	
	
	/* C2.B2 */
	public void SelectAlumnos_null() {

		alumno juanito = new alumno("1247567", "Juanito Garcia", 1234, "172.16.4.205");
		alumno dani = new alumno("7654321", "Daniel Hernandez", 5678, "10.128.1.253");
		alumnoDao dao = mock(alumnoDao.class);

        List<alumno> results = Arrays.asList(juanito, dani);
        when(dao.all()).thenReturn(results);
   
        alumno alumno_1 =  dao.getAtributosAlumno("100000");
        assertEquals(alumno_1, null);
    }
	
	/* C2.B1.1 */
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
        assertEquals(allExamenes, Arrays.asList(examen_dani,examen_juanito));
        assertEquals(2, allExamenes.size());
    }
    
	/* C2.B1.2 */
    public void AllExamenes_null() {
    	
		examenDao dao = mock(examenDao.class);

        List<examen> results = Arrays.asList();
        when(dao.all()).thenReturn(results);

        List<examen> allExamenes = dao.all();
        assertEquals(null, allExamenes);
        assertEquals(0, allExamenes.size());
    }
    
    /* C2.B2.1 */
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
        
    
    /* C1.B1.1 */
    public void AllRealizaEx() {
    
    	realizaExamen re_examen_juanito = new realizaExamen(1010, "1234567", "Path") ;
    	realizaExamen re_examen_dani = new realizaExamen(1802, "7654321", "Path_2") ;
    	realizaExamenDao dao = mock(realizaExamenDao.class);
    	
    	 List<realizaExamen> all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani);
         when(dao.all()).thenReturn(all_realiza_examenes);
   
         List<String> paths  =  dao.pathCommits(1802);
         assertEquals(paths, all_realiza_examenes.get(1).getPath());
    }
    
    /* C1.B1.2 */
    public void AllRealizaEx_2() {
        
    	realizaExamen re_examen_juanito = new realizaExamen(1010, "1234567", "Path") ;
    	realizaExamen re_examen_dani = new realizaExamen(1802, "7654321", "Path_2") ;
    	realizaExamen re_examen_pepe = new realizaExamen(1802, "58965478", "Path_3") ;
    	realizaExamenDao dao = mock(realizaExamenDao.class);
    	
    	 List<realizaExamen> all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani,re_examen_pepe);
         when(dao.all()).thenReturn(all_realiza_examenes);
   
         List<String> paths  =  dao.pathCommits(1802);
         assertEquals(paths, Arrays.asList(all_realiza_examenes.get(2).getPath(),all_realiza_examenes.get(1).getPath()));
    }

    /* C1.B1.3 */
	public void AllRealizaEx_null() {
	    
		realizaExamen re_examen_juanito = new realizaExamen(1010, "1234567", "Path") ;
		realizaExamen re_examen_dani = new realizaExamen(1802, "7654321", "Path_2") ;
		realizaExamen re_examen_pepe = new realizaExamen(1802, "58965478", "Path_3") ;
		realizaExamenDao dao = mock(realizaExamenDao.class);
		
		 List<realizaExamen> all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani,re_examen_pepe);
	     when(dao.all()).thenReturn(all_realiza_examenes);
	
	     List<String> paths  =  dao.pathCommits(0000);
	     assertEquals(paths, null);
	}
}
