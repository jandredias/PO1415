/** @version $Id: ShowFileData.java,v 1.8 2014/11/27 18:27:53 ist175741 Exp $ */
package poof.textui.shell;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;

import java.io.IOException;

import poof.FileSystemManager;
import poof.textui.EntryUnknownException;
import poof.textui.IsNotFileException;
/**
 * §2.2.9.
 */
public class ShowFileData extends Command<FileSystemManager> {
	/**
	 * @param receiver
	 */
	public ShowFileData(FileSystemManager manager) {
		super(MenuEntry.CAT, manager );
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		String filename = IO.readString(Message.fileRequest());
		try{
			String content = _receiver.openFile(filename);
			if(!content.equals(""))
				IO.println(content);
		}catch(poof.EntryNotFoundException e){
			throw new EntryUnknownException(filename);
		}catch(poof.NotAFileException e){
			throw new IsNotFileException(filename);
		}
	}
}
