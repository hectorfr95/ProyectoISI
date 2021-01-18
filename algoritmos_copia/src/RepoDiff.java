package urjc.isi.pruebasJGit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;


public class RepoDiff {

	public void compare(byte[] fileContentOld, byte[] fileContentNew) {

		try {

			Repository repository = createNewRepository();
			Git git = new Git(repository);
			
			
			commitFileContent(fileContentOld, repository, git, true);
			commitFileContent(fileContentNew, repository, git, true);
	    	
						
	        
	        ObjectId oldHead = repository.resolve("HEAD~1^{tree}");
	        ObjectId head = repository.resolve("HEAD^{tree}");

	        System.out.println("Printing diff between tree: " + oldHead + " and " + head);

	       
			ObjectReader reader = repository.newObjectReader();
			CanonicalTreeParser oldTreeIter = new CanonicalTreeParser();
			oldTreeIter.reset(reader, oldHead);
			CanonicalTreeParser newTreeIter = new CanonicalTreeParser();
			newTreeIter.reset(reader, head);

			
			List<DiffEntry> diffs= new Git(repository).diff()
			                    .setNewTree(newTreeIter)
			                    .setOldTree(oldTreeIter)
			                    .call();
	        for (DiffEntry entry : diffs) {
	            System.out.println("Entry: " + entry);
	        }
	        System.out.println("Done");
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	
	public static Repository createNewRepository() throws IOException {
		
		File localPath = File.createTempFile("TestGitRepository", "");
		localPath.delete();

	
		Repository repository = FileRepositoryBuilder.create(new File(
				localPath, ".git"));
		repository.create();

		return repository;
	}
	
	
	
	private void commitFileContent(byte[] fileContent, Repository repository, Git git, boolean commit) throws Exception {
		
		File myfile = new File(repository.getDirectory().getParent(), "testfile");
		myfile.createNewFile();

		FileOutputStream fos = new FileOutputStream (myfile); 
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(fileContent);
		baos.writeTo(fos);
		
		
		git.add().addFilepattern("testfile").call();

		if(commit) {
			
			git.commit().setMessage("Added fileContent").call();
		}
		
		fos.close();
	}
	
}