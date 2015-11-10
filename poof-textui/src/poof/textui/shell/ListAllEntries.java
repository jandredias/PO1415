/** @version $Id: ListAllEntries.java,v 1.7 2014/11/25 11:20:39 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.util.ArrayList;
import java.io.IOException;
import poof.FileSystemManager;


/**
 * §2.2.1.
 */
public class ListAllEntries extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ListAllEntries(FileSystemManager manager ) {
		super(MenuEntry.LS, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		ArrayList<String> list = _receiver.list();
		for(String a : list)
			IO.println(a);
	}

}
