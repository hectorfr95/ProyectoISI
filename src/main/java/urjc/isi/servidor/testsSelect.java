package urjc.isi.servidor;

import static org.junit.Assert.*;

import org.junit.Test;
import java.util.*;
import urjc.isi.servidor.alumno;
import urjc.isi.servidor.alumnoDao;
import urjc.isi.servidor.examen;
import urjc.isi.servidor.examenDao;
import urjc.isi.servidor.realizaExamen;
import urjc.isi.servidor.realizaExamenDao;

import java.net.URISyntaxException;

public class testsSelect {
	
	/* 
	 * CARACTERIZACIONES:
	 * Alumno.Dao
	 * 		C1: Devuelve todos los alumnos de la lista de manera correcta	
	 * 		B1.1: Tabla sin elementos (null)
	 * 		B1.2: Tabla con elementos
	 * 		C2: Seleción de un alumno segun su id
	 * 		B2.1: Entrada id correcto, (coincidente con el de un alumno)
	 * 		B2.2: Entrada id incorrecto, (no coincide con el de un alumno)
	 * Examen.Dao
	 * 		C1: Devuelve todos los examenes de la lista de manera correcta
	 * 		B1.1: Tabla sin elementos (null)
	 * 		B1.2: Tabla con elementos 
	 * 		C2: Comprobar si esta un examen en la lista
	 * 		B2.1: Entrada id correcto. 
	 *		B2.2: Entrada id incorrecto.
	 *		C3: Comprobar si un examen ha terminado
	 *		B3.1: No ha terminado.
	 *		B3.2: Si ha terminado. (se analiza también el metodo finalizar_examen
	 * Realizaexamen.dao
	 * 		C1: Seleción de un path de realizaExamen segun su id (solo uno)
	 * 		B1.1: Entrada id correcto
	 * 		B1.2: Entrada id correcto, varios paths (coincide con el de un examen)
	 *		B1.3: Entrada id incorrecto, (no coincide con el de un examen)
	 */
	// Set up - Called before every test method.

	/* Alumno.Dao */
	@Test
    public void AllAlumnos(){
		try {
			// C1.B1.1 
			alumnoDao alumnoDao_test = new alumnoDao();
			List<alumno> allAlumnos_null = alumnoDao_test.all();
			List<alumno> allAlumnos_vacia = new ArrayList<alumno>();
			assertEquals(allAlumnos_null, allAlumnos_vacia);
			
			// C1.B1.2 
			alumno juanito = new alumno("1234567", "Juanito Garcia", 1234, "172.16.4.205");
			alumno dani = new alumno("7654321", "Daniel Hernandez", 5678, "10.128.1.253");
			alumnoDao_test.save(juanito);
			alumnoDao_test.save(dani);
			List<alumno> allAlumnos = alumnoDao_test.all();
			assertEquals(juanito, allAlumnos.get(0));
		    assertEquals(2, allAlumnos.size());
		    
		    // C2.B2.1
		    alumno maria = new alumno("33333", "Maria Vazquez", 1234, "133.16.33.235");
			alumnoDao_test.save(maria);
			alumno alumno_1 =  alumnoDao_test.getAtributosAlumno("33333");
			assertEquals(alumno_1, maria);
			
			// C2.B2.2
			alumno alumno_null = new alumno("100000");
			alumno alumno_2 =  alumnoDao_test.getAtributosAlumno("100000");
			assertEquals(alumno_2, alumno_null);
			
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	
	/* Examen.Dao */
	@Test
    public void AllExamenes() {
		try {
			// C1.B1.1 
		 	examenDao examenDao_test = new examenDao();
		 	List<examen> allExamen_null = examenDao_test.all();
			List<examen> allExamen_vacia = new ArrayList<examen>();
			assertEquals(allExamen_null, allExamen_vacia);
			
			// C1.B1.2 
			Date fecha = new Date();
	    	long lnMilisegundos = fecha.getTime();
	    	java.sql.Date sqlDate = new java.sql.Date(lnMilisegundos);
			examen examen_juanito = new examen(1010, sqlDate, "ISI",0) ;
			examen examen_dani = new examen(1802, sqlDate, "ISI",0) ;
			examenDao_test.save(examen_juanito);
			examenDao_test.save(examen_dani);
			List<examen> results = Arrays.asList(examen_juanito, examen_dani);
			List<examen> allExamenes = examenDao_test.all();
			assertEquals(examen_juanito, allExamenes.get(0));
		    assertEquals(allExamenes, results);
		    assertEquals(2, allExamenes.size());
		    
		    // C2.B2.1;
	        int examen_1 =  examenDao_test.comprobar_examen(1010);
	        assertEquals(examen_1, 1);
	        
	        // C2.B2.2
	        examen_1 =  examenDao_test.comprobar_examen(1234);
	        assertEquals(examen_1, 0);
	        
	        // C3 B3.1
	        int finalizar_examen = examenDao_test.comprobar_final(1010);
	        assertEquals(finalizar_examen, 0);
	        
	        //C3 B3.2
	        examenDao_test.finalizar_examen(1010);
	        finalizar_examen = examenDao_test.comprobar_final(1010);
	        assertEquals(finalizar_examen, 1);
	        
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
    }
   
    /* C1.B1.1 */
	@Test
    public void AllRealizaEx() {
		try {
			// C1.B1.1 
			realizaExamenDao realizaExamenDao_test = new realizaExamenDao();
			realizaExamen re_examen_juanito = new realizaExamen(1010, "1234567", "Path") ;
			realizaExamen re_examen_dani = new realizaExamen(1802, "7654321", "Path_2") ;
			realizaExamenDao_test.save(re_examen_juanito);
			realizaExamenDao_test.save(re_examen_dani);
			List<realizaExamen> all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani);
			List<String> path_realiza_examenes =  Arrays.asList(all_realiza_examenes.get(1).getPath());
         	List<String> paths  =  realizaExamenDao_test.pathCommits(1802);
         	assertEquals(paths, path_realiza_examenes);
         	
         // C1.B1.2 
         	realizaExamen re_examen_pepe = new realizaExamen(1802, "58965478", "Path_3") ;
         	realizaExamenDao_test.save(re_examen_pepe);
         	all_realiza_examenes = Arrays.asList(re_examen_juanito, re_examen_dani,re_examen_pepe);
         	paths  =  realizaExamenDao_test.pathCommits(1802);
         	assertEquals(paths, Arrays.asList(all_realiza_examenes.get(2).getPath(),all_realiza_examenes.get(1).getPath()));
         	
         // C1.B1.3 
         	List<realizaExamen> results = Arrays.asList();
         	paths  =  realizaExamenDao_test.pathCommits(0000);
         	assertEquals(paths, results);
		}catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}

