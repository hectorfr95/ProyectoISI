package urjc.isi.servidor;


public class examen {
	
	private int idExamen;
	private String Fecha;
	private String Asignatura;

	public examen(int idExamen, String Fecha, String Asignatura) {
		this.idExamen = idExamen;
		this.Fecha = Fecha;
		this.Asignatura = Asignatura;
	}

	public int getIdExamen() {
		return idExamen;
	}

	public void setIdExamen(int idExamen) {
		this.idExamen = idExamen;
	}

	public String getFecha() {
		return Fecha;
	}

	public void setFecha(String fecha) {
		Fecha = fecha;
	}

	public String getAsignatura() {
		return Asignatura;
	}

	public void setAsignatura(String asignatura) {
		Asignatura = asignatura;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Asignatura == null) ? 0 : Asignatura.hashCode());
		result = prime * result + ((Fecha == null) ? 0 : Fecha.hashCode());
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
		examen other = (examen) obj;
		if (Asignatura == null) {
			if (other.Asignatura != null)
				return false;
		} else if (!Asignatura.equals(other.Asignatura))
			return false;
		if (Fecha == null) {
			if (other.Fecha != null)
				return false;
		} else if (!Fecha.equals(other.Fecha))
			return false;
		if (idExamen != other.idExamen)
			return false;
		return true;
	}

}