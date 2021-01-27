package urjc.isi.pruebaCommits;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.Test;

public class AccessCommitsTest {

	@Test
	public void CheckAllCommits() {
		AccessCommits accessCommits = new AccessCommits();
		String result = accessCommits.listCommits("/home/alumno/Desktop/.git");
		assertFalse(result.isEmpty());
	}

}
