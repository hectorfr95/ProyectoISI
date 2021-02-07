package urjc.isi.algoritmoCopia;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;



public class recuFichCommit {
	  public void checkout(String path, String nombreFich) throws IOException, NoHeadException,GitAPIException {
		  Path repoPath = Paths.get(path);
		 
		  try (Git git = Git.open(repoPath.toFile())) {
			  git.checkout().addPath(nombreFich).call();
		  }
		  
		}
}
