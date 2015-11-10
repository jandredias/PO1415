/** @version $Id: ChangeWorkingDirectory.java,v 1.8 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotDirectoryException;

import poof.FileSystemManager;

/**
 * §2.2.4.
 */
public class ChangeWorkingDirectory extends Command<FileSystemManager>  {
	/**
	 * @param receiver
	 */
	public ChangeWorkingDirectory(FileSystemManager manager) {
		super(MenuEntry.CD, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String a = IO.readString(Message.directoryRequest());
		try{
		_receiver.changeDirectory(a);
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(e.getName());
		}catch(poof.NotADirectoryException e){
			throw new IsNotDirectoryException(a);
		}catch(poof.DirectoryNotFoundException e){
			throw new EntryUnknownException(a);
		}
	}
}
