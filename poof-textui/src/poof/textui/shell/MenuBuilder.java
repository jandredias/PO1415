/** @version $Id: MenuBuilder.java,v 1.6 2014/11/25 11:20:39 ist175741 Exp $ */
package poof.textui.shell;

import ist.po.ui.Command;
import ist.po.ui.Menu;
import poof.FileSystemManager;

/**
 * Menu builder for shell operations.
 */
public class MenuBuilder {

	/**
	 * @param receiver
	 */
	public static void menuFor(FileSystemManager manager) {
		Menu menu = new Menu(MenuEntry.TITLE, new Command<?>[] {
				new ListAllEntries(manager),
				new ListEntry(manager),
				new RemoveEntry(manager),
				new ChangeWorkingDirectory(manager),
				new CreateFile(manager),
				new CreateDirectory(manager),
				new ShowWorkingDirectory(manager),
				new AppendDataToFile(manager),
				new ShowFileData(manager),
				new ChangeEntryPermissions(manager),
				new ChangeOwner(manager),
				});
		menu.open();
	}

}
