package urjc.isi.myapp;

import java.util.Objects;

public class Alumno {
	//nombre y apellidos del alumno que está ejecutando este programa
	private String nombre;
	//DNI del alumno que está ejecutando este programa
	private String dni;
	//mail dl alumno, necesario solo para rellenar un campo en los commits
	private String mail;
	
	public Alumno(String n, String d, String m) {
		this.nombre = n;
		this.dni = d;
		this.mail = m;
	}
	
	 public void setName(String s) {
		 this.nombre = s;
	 }
	 public void setMail(String s) {
		 this.mail = s;
	 }
	 public void setDni(String s) {
    	this.dni = s;
	 }
	 public String getName() {
	   	return this.nombre;
	 }
	 public String getMail() {
	   	return this.mail;
	 }
	 public String getDni() {
	   	return this.dni;
	 }
	 
	 public boolean equals(Object otherObject) {
		// Test rapido para ver si son identicos
		 if(this== otherObject)return true;
		 // Se devuelve false si el parámetro es null
		 if(otherObject ==null)return false;
		 // Comprobar si otherObject no es subclase de Alumno
		 if(!(otherObject instanceof Alumno))return false;
		 // Comprobar si los objetos tienen valores identicos
		 Alumno other = (Alumno) otherObject;
		 return Objects.equals(nombre, other.nombre)&& Objects.equals(dni, other.dni)
				 && Objects.equals(mail, other.mail);
	 }
	 public int hashCode() {
		 return Objects.hash(nombre, dni, mail);
	 }
	    
}
