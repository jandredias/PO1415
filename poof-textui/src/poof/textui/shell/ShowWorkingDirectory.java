/** @version $Id: ShowWorkingDirectory.java,v 1.6 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystemManager;

/**
 * §2.2.7.
 */
public class ShowWorkingDirectory extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ShowWorkingDirectory(FileSystemManager manager)  {
		super(MenuEntry.PWD, manager, new ValidityPredicate<FileSystemManager>(manager) {
			public boolean isValid() {
				return _receiver.HasFileSystemOpen();
			}
		});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		IO.println(_receiver.getPath());
	}

}
