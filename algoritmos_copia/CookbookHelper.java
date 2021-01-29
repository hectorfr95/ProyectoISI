package Proyecto2.Principla2;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class CookbookHelper {

	public static Repository openJGitCookbookRepository() throws IOException {
		String path = "/home/alumno/Escritorio/prueba/gits/.git";
		Path repoPath = Paths.get(path);
		
	    Git git = Git.open(repoPath.toFile());
	    Repository repo = git.getRepository();
		return repo;
    }

    public static Repository createNewRepository() throws IOException {
        // prepare a new folder
        File localPath = File.createTempFile("TestGitRepository", "");
        if(!localPath.delete()) {
            throw new IOException("Could not delete temporary file " + localPath);
        }

        // create the directory
        Repository repository = FileRepositoryBuilder.create(new File(localPath, ".git"));
        repository.create();

        return repository;
    }
	
}
