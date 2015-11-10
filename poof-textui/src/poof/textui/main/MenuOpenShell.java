/** @version $Id: MenuOpenShell.java,v 1.8 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;

/**
 * Open shell menu.
 */
public class MenuOpenShell extends Command<FileSystemManager>  {

	/**
	 * @param receiver
	 */
	public MenuOpenShell(FileSystemManager manager) {
		super(MenuEntry.MENU_SHELL, manager, new ValidityPredicate<FileSystemManager>(manager){
			public boolean isValid(){
				return _receiver.HasFileSystemOpen() && _receiver.HasUserLoggedIn();
			}
		});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		poof.textui.shell.MenuBuilder.menuFor(_receiver);
	}

}
