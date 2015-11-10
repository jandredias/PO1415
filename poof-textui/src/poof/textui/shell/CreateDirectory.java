/** @version $Id: CreateDirectory.java,v 1.8 2014/11/25 11:20:39 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.textui.EntryExistsException;
import poof.textui.AccessDeniedException;
import poof.FileSystemManager;

/**
 * §2.2.6.
 */
public class CreateDirectory extends Command<FileSystemManager>{
	/**
	 * @param receiver
	 */
	public CreateDirectory(FileSystemManager manager ) {
		super(MenuEntry.MKDIR, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String a = IO.readString(Message.directoryRequest());
		try{ _receiver.newDirectory(a); }
		catch(poof.EntryExistsException e){ throw new EntryExistsException(a); }
		catch(poof.AccessDeniedException e){ throw new AccessDeniedException(_receiver.getUserLoggedin()); }
	}

}
