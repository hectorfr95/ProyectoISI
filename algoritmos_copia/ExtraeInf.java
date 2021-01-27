package com.ExtraeInf.eclipse.jgitexamples;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.Iterator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

public class ExtraeInf {

    private static Repository repo;

	public static void main(String[] args) throws IOException, IllegalStateException, GitAPIException {

		// Git que clona un repositorio completo en una carpeta en local
		
		//Git git = Git.cloneRepository()
		// 		.setURI("https://github.com/abmadrid19/repo-ISI.git")
       // 		.setDirectory(new File("///home/ana/Documentos/prueba")) 
       // 		.call();
       
    	FileRepositoryBuilder builder = new FileRepositoryBuilder();
    	
    	//Abre el repositorio en local donde queremos obtener la lista de commit
    	
    	Git git = Git.open(new File("///home/ana/Documentos/prueba"));
    	
    	Repository repo = git.getRepository();
    	
    	git = new Git(repo);
    	//Obtenemos la lista completa de commits
    	
    	Iterable<RevCommit> log = git.log().call();
    	for (RevCommit commit : log) {
    	    //Muestra el commit completo por pantalla con su identificador
    	    System.out.println("LogCommit: " + commit);
    	    //Muestra el contenido completo del mensaje de todos los commit que hacen los alumnos
    	    String logMessage = commit.getFullMessage();
    	    System.out.println("LogMessage: " + logMessage);
    	}
    	git.close();
    }
}
