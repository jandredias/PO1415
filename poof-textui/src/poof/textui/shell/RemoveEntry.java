/** @version $Id: RemoveEntry.java,v 1.8 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;
import poof.textui.EntryUnknownException;
import poof.textui.AccessDeniedException;
import poof.textui.IllegalRemovalException;
import poof.FileSystemManager;

/**
 * §2.2.3.
 */
public class RemoveEntry extends Command<FileSystemManager> /* FIXME: select core type for receiver */ {
	/**
	 * @param receiver
	 */
	public RemoveEntry(FileSystemManager manager /*FIXME: add receiver declaration: type must agree with the above*/) {
		super(MenuEntry.RM, manager /*FIXME: receiver argument*/);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String entry = IO.readString(Message.nameRequest());
		try{
			_receiver.removeEntry(entry);
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(entry);
		}catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}catch(poof.IllegalRemovalException e){
			throw new IllegalRemovalException();
		}
	}
}
