/** @version $Id: Login.java,v 1.9 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;
import poof.UserNotFoundException;
import poof.textui.UserUnknownException;
/**
 * §2.1.2.
 */
public class Login extends Command<FileSystemManager> {

	/**
	 * @param receiver
	 */
	public Login(FileSystemManager manager) {
		super(MenuEntry.LOGIN, manager, new ValidityPredicate<FileSystemManager>(manager){
			public boolean isValid(){
				return _receiver.HasFileSystemOpen();
			}
		});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String username = IO.readString(Message.usernameRequest());
		try{
			_receiver.login(username);
		}catch(UserNotFoundException e){
			throw new UserUnknownException(username);
		}
	}
}
