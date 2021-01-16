package urjc.isi.myapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;


import static org.mockito.Mockito.*;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.lib.Repository;
import java.io.File;


import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class Tests {
	
	@Test
	public void testCreateRepository()
			throws IOException, GitAPIException {
		Git git = Main.createRepo();
		
		
			assertNotNull(git.getRepository());
		
	}
	
	public void testCreateDirectory()
			throws IOException, GitAPIException {
		File directory = new File("../examen/.git/");
		Main.createRepo();
		
		
			assertEquals(directory.exists(), true);
		
	}
	
	public void testCreateZip()
			throws IOException{
		File directory =Main.compressRepo();
		
		
		assertEquals(directory.exists(), true);
		
	}


}
