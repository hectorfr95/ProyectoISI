package urjc.isi.pruebasJGit;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.HistogramDiff;
import org.eclipse.jgit.diff.MyersDiff;
import org.eclipse.jgit.diff.RawText;
import org.eclipse.jgit.diff.RawTextComparator;

public class StringDiff {

	public void compare(byte[] fileContentOld, byte[] fileContentNew) {
		try {


			RawText rt1 = new RawText(fileContentOld);
			RawText rt2 = new RawText(fileContentNew);

		
			
			EditList diffList = new EditList();
			diffList.addAll(new HistogramDiff().diff(RawTextComparator.DEFAULT,
					rt1, rt2));

			ByteArrayOutputStream diff = new ByteArrayOutputStream();
			DiffFormatter formatter = new DiffFormatter(diff);
			formatter.format(diffList, rt1, rt2);

			System.out.println(diff.toString());

			System.out.println("-----------------------------");

			Iterator<Edit> editIte = diffList.iterator();
			while (editIte.hasNext()) {
				Edit edit = editIte.next();
				System.out.println(edit.getType().toString() + " at: "
						+ edit.getEndB());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}