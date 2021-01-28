package urjc.isi.servidor;
public class finalexamen {
	
	private int idExamen;
	private String idAlumno;
	private String ip;
	private int puerto;
	
	public finalexamen(int idExamen, String idAlumno, String ip, int puerto) {
		this.idExamen = idExamen;
		this.idAlumno = idAlumno;
		this.ip = ip;
		this.puerto = puerto;
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
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
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