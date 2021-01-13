package urjc.isi.servidor;

public class realizaExamen {
	
	private int idExamen;
	private int idAlumno;
	private String Path;
	
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
	
	public String getPath() {
		return Path;
	}

	public void setPath(String path) {
		Path = path;
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
		if (idAlumno == null) {
			if (other.idAlumno != null)
				return false;
		} else if (!idAlumno.equals(other.idAlumno))
			return false;
		if (Path == null) {
			if (other.Path != null)
				return false;
		} else if (!Path.equals(other.Path))
			return false;
		if (idExamen != other.idExamen)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + idExamen;
		result = prime * result + idAlumno;
		result = prime * result + ((Path == null) ? 0 : Path.hashCode());
		return result;
	}
	
}
