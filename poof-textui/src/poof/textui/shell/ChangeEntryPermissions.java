/** @version $Id: ChangeEntryPermissions.java,v 1.7 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.AccessDeniedException;


/**
 * §2.2.10.
 */
public class ChangeEntryPermissions extends Command<FileSystemManager>{
	/**
	 * @param receiver
	 */
	public ChangeEntryPermissions(FileSystemManager manager) {
		super(MenuEntry.CHMOD, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String a = IO.readString(Message.nameRequest());
		boolean p = IO.readBoolean(Message.publicAccess());
		try{
			_receiver.changePermission(a,p);
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(a);
		}catch(poof.AccessDeniedException e ){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}
	}
}
