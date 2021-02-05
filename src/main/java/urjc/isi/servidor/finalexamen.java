package urjc.isi.servidor;
public class finalexamen {
	
	private int idExamen;
	private String idAlumno;
	private String nombreAlumno;
	private String path;
	
	public finalexamen(int idExamen, String idAlumno, String nombreAlumno, String path) {
		this.idExamen = idExamen;
		this.idAlumno = idAlumno;
		this.nombreAlumno = nombreAlumno;
		this.path = path;
	}
	
	
	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}
	
	public String getNombreAlumno() {
		return nombreAlumno;
	}

	public void setNombreAlumno(String nombreAlumno) {
		this.nombreAlumno = nombreAlumno;
	}
	
	public String getIdAlumno() {
		return idAlumno;
	}
	
	public void setIdAlumno(String idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	public String getPath() {
		return path;
	}
	
	public void setPath(String path) {
		this.path = path;
	}

	

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((Path == null) ? 0 : Path.hashCode());
//		result = prime * result + ((idAlumno == null) ? 0 : idAlumno.hashCode());
//		result = prime * result + idExamen;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		realizaExamen other = (realizaExamen) obj;
//		if (Path == null) {
//			if (other.Path != null)
//				return false;
//		} else if (!Path.equals(other.Path))
//			return false;
//		if (idAlumno == null) {
//			if (other.idAlumno != null)
//				return false;
//		} else if (!idAlumno.equals(other.idAlumno))
//			return false;
//		if (idExamen != other.idExamen)
//			return false;
//		return true;
//	}
	
}