/** @version $Id: Save.java,v 1.11 2014/11/25 11:20:36 ist175741 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;
import java.io.IOException;
import poof.NoFileSpecifiedException;
import poof.FileSystemManager;

/**
 * Save to file under current name (if unnamed, query for name).
 */
public class Save extends Command<FileSystemManager>  {
	/**
	 * @param receiver
	 */
	public Save(FileSystemManager manager) {
		super(MenuEntry.SAVE, manager, new ValidityPredicate<FileSystemManager>(manager){
			public boolean isValid(){
				return _receiver.HasFileSystemOpen();
			}
		});
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		try{
			_receiver.save();
		}catch(NoFileSpecifiedException e){
			_receiver.save(IO.readString(Message.newSaveAs()));
		}
	}
}
