package urjc.isi.pruebasJGit;

import java.nio.charset.Charset;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;
import java.io.*;


public class GitDiff {

	
	public static void main(String[] args) throws FileNotFoundException {

		
		//String text1 = "This is a simple test";
		//String text2 = "This is a test";
		
		String sample1 = "Some text here for the testing example."  
				+ System.lineSeparator() + System.lineSeparator() +"Another text for doing some testing to the functionality."
				+ System.lineSeparator();
		
		String sample2 = "Some text here for the testing example."  
				+ System.lineSeparator() + System.lineSeparator() + "Another text for doing some testing to the functionality."
				+ System.lineSeparator() + System.lineSeparator() + "Another text for doing some testing to the functionality."
				+ System.lineSeparator();

		byte[] text1ByteArray = sample1.getBytes(Charset.forName("UTF-8"));
		byte[] text2ByteArray = sample2.getBytes(Charset.forName("UTF-8"));

		
		
		System.out.println("#############");
		System.out.println("Plaintext");
		System.out.println("#############");
		System.out.println();
		
		StringDiff stringDiff = new StringDiff();
		stringDiff.compare(text1ByteArray, text2ByteArray);
		
		System.out.println("#############");
		System.out.println("Repository");
		System.out.println("#############");
		System.out.println();

		RepoDiff repoTest = new RepoDiff();
		repoTest.compare(text1ByteArray, text2ByteArray);
		
		//escribir la salida de la consola en un fichero .txt
		String fileName = "whatever.txt";
		final boolean append = true, autoflush = true;
		PrintStream printStream = new PrintStream(new FileOutputStream(fileName, append), autoflush);
		System.setOut(printStream);
		//System.setErr(printStream);
		
		
		/*
		try {
		//create a buffered reader that connects to the console, we use it so we can read lines
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

		//read a line from the console
			String lineFromInput = in.readLine();

		//create an print writer for writing to a file
			PrintWriter out = new PrintWriter(new FileWriter("output.txt"));

		//output to the file a line
			out.println(lineFromInput);

		//close the file (VERY IMPORTANT!)
			out.close();
		} catch(IOException e) {
		System.out.println("Error during reading/writing");
		}
		*/
	}

}
			
		
