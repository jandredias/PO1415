/** @version $Id: MenuBuilder.java,v 1.5 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.user;

import ist.po.ui.Command;
import ist.po.ui.Menu;

import poof.FileSystemManager;

/**
 * Menu builder for search operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystemManager manager) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new CreateUser(manager),
				new ListAllUsers(manager),
				});
		menu.open();
	}

}
