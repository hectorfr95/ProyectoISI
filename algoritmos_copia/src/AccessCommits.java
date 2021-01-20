package urjc.isi.pruebaCommits;

/*
 * Con esta clase creamos un objeto que tiene un método, que accede a un path (directorio .git) y del que lista
 * los commits del directorio
 */


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.revwalk.RevCommit;


public class AccessCommits {

  //Metodo que recibe el path (ruta al directorio .git) de tipo String y lista todos los commits del directorio 	
  public void listCommits(String path)
      throws IOException, NoHeadException, GitAPIException {

    Path repoPath = Paths.get(path);

    try (Git git = Git.open(repoPath.toFile())) {
      // all
      Iterable<RevCommit> logs = git.log().all().call();
      for (RevCommit rev : logs) {
        System.out.print(Instant.ofEpochSecond(rev.getCommitTime()));
        System.out.print(": ");
        System.out.print(rev.getFullMessage());
        System.out.println();
        System.out.println(rev.getId().getName());
        System.out.print(rev.getAuthorIdent().getName());
        System.out.println(rev.getAuthorIdent().getEmailAddress());
        System.out.println("-------------------------");
      }

      System.out.println("======================");
    }
  }

}