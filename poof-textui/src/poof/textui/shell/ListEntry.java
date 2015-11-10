/** @version $Id: ListEntry.java,v 1.12 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
/**
 * §2.2.2.
 */
public class ListEntry extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ListEntry(FileSystemManager manager) {
		super(MenuEntry.LS_ENTRY, manager, new ValidityPredicate<FileSystemManager>(manager){
			public boolean isValid() {
				return _receiver.HasFileSystemOpen();
			}
		});
}
	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String entryname = IO.readString(Message.nameRequest());
		try {
			IO.println(_receiver.listEntry(entryname));
		} catch(poof.EntryNotFoundException e) {
		  throw new EntryUnknownException(entryname);
		}
      }
}
