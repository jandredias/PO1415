/** @version $Id: CreateFile.java,v 1.6 2014/11/19 13:01:09 ist175741 Exp $ */
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
 * §2.2.5.
 */
public class CreateFile extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public CreateFile(FileSystemManager manager) {
		super(MenuEntry.TOUCH, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String filename = IO.readString(Message.fileRequest());
		try{
			_receiver.newFile(filename,"");
		}catch(poof.EntryExistsException e){
			throw new EntryExistsException(filename);
		}catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}
	}

}
