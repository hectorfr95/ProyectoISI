import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.StringTokenizer;
public class Compararult {
	public static void main (String[] args) throws IOException
	{
		File lee1 = new File("/home/alumno/Escritorio/fichero1.txt");
		File lee2 = new File("/home/alumno/Escritorio/fichero2.txt");
		Ultimocommit(lee1,lee2);
		Ulitmocommitpal(lee1,lee2);
	}
	private static void Ulitmocommitpal(File slee1, File slee2) throws IOException {
		String s1;
		String s2;
		String s3;
		String s4;
		int pal_igual = 0;
		BufferedReader br1 = new BufferedReader(new FileReader(slee1));
		s1=br1.readLine();
		int numtokens1 =0;
		StringTokenizer st =new StringTokenizer(s1);
		while(st.hasMoreTokens())
		{
			s2=st.nextToken();
			numtokens1++;
			BufferedReader br2 = new BufferedReader(new FileReader(slee2));
			s3=br2.readLine();
			int numtokens2=0;
			StringTokenizer st2 = new StringTokenizer(s3);
			while(st2.hasMoreElements()) {
				s4=st2.nextToken();
				numtokens2++;
				if(s2.equals(s4)) {
					pal_igual++;
				}
			}
		}
		if(pal_igual!=0) {
			System.out.println("pal igual\n");
		}
	}
	public static void Ultimocommit(File flee1, File flee2) throws IOException
	{
		try
		{
			BufferedReader qlee1 = new BufferedReader(new FileReader(flee1));
			BufferedReader qlee2 = new BufferedReader(new FileReader(flee2));
			String l1="";
			String l2="";
			String linedis = "";
			int contador1=0;
			int contador2=0;
			int contador=0;
			while((l1!=null) || (l2 != null))
			{
				l1=qlee1.readLine();
				l2=qlee2.readLine();
				// comparar que el contador de lineas no sea igual
				if(l1!=null)
				{
					contador1++;
				}
				if(l2!=null)
				{
					contador2++;
				}		
				if((l1!=null)||(l2!=null)) {
				  if (l1.equals(l2))
				  {
					  	linedis=l1;
				  		contador++;
				  }
				}
				/*if(contador!=0) {
					System.out.println("linea igual:");
					System.out.println(linedis);
					System.out.println();
				}*/
			}
			if(contador1==contador2){
				System.out.println("probabilidad de copia por numero total de lineas\n");
			}else{
				System.out.println("NO probabilidad de copia por numero total de lineas\n");
			}
			qlee1.close();
			qlee2.close();
		}finally{
			System.out.println("fin");
		}
	}
}
