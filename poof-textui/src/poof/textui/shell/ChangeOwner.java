/** @version $Id: ChangeOwner.java,v 1.6 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.textui.EntryUnknownException;
import poof.textui.AccessDeniedException;
import poof.textui.UserUnknownException;

import poof.FileSystemManager;

/**
 * §2.2.11.
 */
public class ChangeOwner extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ChangeOwner(FileSystemManager manager ) {
		super(MenuEntry.CHOWN, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String entryname = IO.readString(Message.nameRequest());
		String user = IO.readString(Message.usernameRequest());
		try{
			_receiver.changeOwner(entryname, user);			
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(entryname);
		}catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}catch(poof.UserNotFoundException e){
			throw new UserUnknownException(user);
		}
	}

}
