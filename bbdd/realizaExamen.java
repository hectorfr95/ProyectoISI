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
	
	
	
}
