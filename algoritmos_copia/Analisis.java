package urjc.isi;
import java.io.*;


public class Analisis {
	public static float porTotal(float p1, float p2, float p3) {
		float valpor=0.3333f;
		
		return p1*valpor + p2*valpor + p3*valpor;
	}
	
	public static void genTxt(int idExam, int dni, String alumno, float p1, float p2, float p3){
		float portot=porTotal(p1,p2,p3);
		String mssg="El alumno "+alumno+" con DNI: "+dni+" tine un porcentaje de copia del "+portot+"%";
		
		try {
			FileWriter txt = new FileWriter("Examen-"+idExam+"-"+dni+".txt");
			BufferedWriter bw = new BufferedWriter(txt);
			
			bw.write(mssg);
			bw.flush();
			
			bw.close();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		genTxt(1234, 11871245, "Antonio Velasco", 20, 0, 50);
	}
}
