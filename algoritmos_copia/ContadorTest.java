import java.io.FileNotFoundException;
import java.io.IOException;
import static org.junit.Assert.*;
import org.junit.*;
import java.util.*;

public class ContadorTest
{
	private String string;

	@Test public void testForCounter() throws FileNotFoundException, IOException
		{
			string = "texto.txt";
			assertEquals("Text counter", 10, Contador.contador(string));
		}
		
	@Test (expected = FileNotFoundException.class)
	public void testForFileDoesNotExist() throws FileNotFoundException, IOException
		{
			string = "te.txt";
			Contador.contador(string);
		}
}
