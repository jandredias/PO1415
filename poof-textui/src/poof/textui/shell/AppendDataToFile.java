/** @version $Id: AppendDataToFile.java,v 1.9 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;
import java.io.IOException;
import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;
import poof.textui.AccessDeniedException;
/**
 * §2.2.8.
 */
public class AppendDataToFile extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	 
	 
	public AppendDataToFile(FileSystemManager manager) {
		
		super(MenuEntry.APPEND, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String filename = IO.readString(Message.fileRequest());
		String content = IO.readString(Message.textRequest());
		try{
			_receiver.appendtoFile(filename, content);
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(filename);
		}catch(poof.AccessDeniedException e){
			throw new AccessDeniedException(_receiver.getUserLoggedin());
		}catch(poof.NotAFileException e){
			throw new IsNotFileException(filename);
		}
	}

}
