package urjc.isi.servidor;

public class realizaExamen {
	
	private int idExamen;
	private int idAlumno;

	public realizaExamen(int idExamen, int idAlumno) {
		this.idExamen = idExamen;
		this.idAlumno = idAlumno;
	}
	
	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}
	
	public int getIdAlumno() {
		return idAlumno;
	}
	
	public void setIdAlumno(int idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idExamen;
		result = prime * result + idAlumno;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		realizaExamen other = (realizaExamen) obj;
		if (idExamen != other.idExamen) 
			return false;
		if (idAlumno != other.idAlumno)
			return false;
		return true;
	}	
}
