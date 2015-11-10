/** @version $Id: New.java,v 1.9 2014/11/25 11:20:36 ist175741 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

// FIXME: import project-specific classes
//import poof.FileSystemManager;
import poof.FileSystemManager;
import java.io.IOException;

/**
 * Open a new file.
 */
public class New extends Command<FileSystemManager> {

	/**
	 * @param receiver
	 */
	public New(FileSystemManager manager) {
		super(MenuEntry.NEW, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		if(_receiver.HasUnsavedChanges())
			if(IO.readBoolean(Message.saveBeforeExit()))
				_receiver.save(IO.readString(Message.newSaveAs()));
				
		_receiver.newFileSystem();
	}
}
