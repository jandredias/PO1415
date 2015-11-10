/** @version $Id: CreateUser.java,v 1.9 2014/11/18 13:43:09 ist175741 Exp $ */
package poof.textui.user;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.AccessDeniedException;
import poof.UserExistsOnFileSystemException;
import poof.textui.UserExistsException;


/**
 * §2.3.1.
 */
public class CreateUser extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public CreateUser(FileSystemManager manager ) {
		super(MenuEntry.CREATE_USER, manager );
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String username = IO.readString(Message.usernameRequest());
		String name = IO.readString(Message.nameRequest());
		try{
			_receiver.newUser(username,name);
		}catch (UserExistsOnFileSystemException e){
			throw new UserExistsException(username);
		}catch (poof.AccessDeniedException e){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}
	}
}
