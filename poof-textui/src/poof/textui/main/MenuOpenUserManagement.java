/** @version $Id: MenuOpenUserManagement.java,v 1.8 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;

/**
 * Open user management menu.
 */
public class MenuOpenUserManagement extends Command<FileSystemManager>  {

	/**
	 * @param receiver
	 */
	public MenuOpenUserManagement(FileSystemManager manager) {
		super(MenuEntry.MENU_USER_MGT, manager, new ValidityPredicate<FileSystemManager>(manager){
			public boolean isValid(){
				return _receiver.HasUserLoggedIn();
			}
		});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() {
		poof.textui.user.MenuBuilder.menuFor(_receiver);
	}

}
