package urjc.isi.pruebasJGit;

import java.nio.charset.Charset;

public class GitDiff {

	
	public static void main(String[] args) {

		
		String text1 = "This is a simple test";
		String text2 = "This is a test";
		
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
	}

}
		
		