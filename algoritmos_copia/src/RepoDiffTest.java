package urjc.isi.pruebasJGit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.lib.Repository;
import org.junit.Test;

public class RepoDiffTest {

    @Test
    public void testCreateFolder() throws IOException{
    	Repository result = RepoDiff.createNewRepository();
    	assertTrue(result.exists());
    }

}
