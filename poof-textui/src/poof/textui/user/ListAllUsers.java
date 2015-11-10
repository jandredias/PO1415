/** @version $Id: ListAllUsers.java,v 1.8 2014/11/14 11:51:23 ist175741 Exp $ */
package poof.textui.user;
import java.util.ArrayList;
import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;

/**
 * §2.3.2.
 */
public class ListAllUsers extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ListAllUsers(FileSystemManager manager) {
		super(MenuEntry.LIST_USERS, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException  {
		ArrayList<String> users = _receiver.listUsers();
		for(String aux : users)
			IO.println(aux);
	}
}
