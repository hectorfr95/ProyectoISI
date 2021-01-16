package urjc.isi.servidor;

public class realizaExamen {
	
	private int idExamen;
	private String idAlumno;
	private String Path;
	
	public realizaExamen(int idExamen, String idAlumno, String path) {
		this.idExamen = idExamen;
		this.idAlumno = idAlumno;
		this.Path = path; 
	}
	
	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}
	
	public String getIdAlumno() {
		return idAlumno;
	}
	
	public void setIdAlumno(String idAlumno) {
		this.idAlumno = idAlumno;
	}
	
	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Path == null) ? 0 : Path.hashCode());
		result = prime * result + ((idAlumno == null) ? 0 : idAlumno.hashCode());
		result = prime * result + idExamen;
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
		if (Path == null) {
			if (other.Path != null)
				return false;
		} else if (!Path.equals(other.Path))
			return false;
		if (idAlumno == null) {
			if (other.idAlumno != null)
				return false;
		} else if (!idAlumno.equals(other.idAlumno))
			return false;
		if (idExamen != other.idExamen)
			return false;
		return true;
	}
	
}