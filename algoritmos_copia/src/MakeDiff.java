/*
 * Prpgrama que realiza un git diff entre dos ficheros que se le pasan como argumento
 */

package urjc.isi.pruebasJGit;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;


public class MakeDiff {

	public static String getDiff(String file1, String file2) {
	    OutputStream out = new ByteArrayOutputStream();
	    try {
	        RawText rt1 = new RawText(new File(file1));
	        RawText rt2 = new RawText(new File(file2));
	        EditList diffList = new EditList();
	        //new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2);	
	        diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT, rt1, rt2));
	        //new DiffFormatter(out).format(diffList, rt1, rt2);
	        DiffFormatter diffFormatter = new DiffFormatter(out);
	        diffFormatter.format(diffList, rt1, rt2);
	        System.out.println(out.toString());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return out.toString();
	}
}
