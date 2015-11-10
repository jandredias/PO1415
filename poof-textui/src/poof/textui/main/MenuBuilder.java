/** @version $Id: MenuBuilder.java,v 1.7 2014/11/13 19:54:29 ist175973 Exp $ */
package poof.textui.main;

import ist.po.ui.Command;
import ist.po.ui.Menu;
import poof.FileSystemManager;

/**
 * Menu builder.
 */
public abstract class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystemManager manager) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new New(manager),
				new Open(manager),
				new Save(manager),
				new Login(manager),
				new MenuOpenShell(manager),
				new MenuOpenUserManagement(manager)});
		menu.open();
	}

}
