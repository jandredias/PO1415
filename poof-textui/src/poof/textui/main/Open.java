/** @version $Id: Open.java,v 1.10 2014/11/25 11:20:36 ist175741 Exp $ */
package poof.textui.main;

import static ist.po.ui.Dialog.IO;
import ist.po.ui.Command;
import ist.po.ui.DialogException;
import ist.po.ui.ValidityPredicate;
import java.io.IOException;
import poof.FileSystemManager;


/**
 * Open existing file.
 */
public class Open extends Command<FileSystemManager>  {

	/**
	 * @param receiver
	 */
	public Open(FileSystemManager manager) {
		super(MenuEntry.OPEN, manager);
	}

	/** @see ist.po.ui.Command#execute() */
	@Override
	public final void execute() throws DialogException, IOException {
		if(_receiver.hasFileSystemOpen() && _receiver.HasUnsavedChanges()){
			if(IO.readBoolean(Message.saveBeforeExit())){
				String name = IO.readString(Message.newSaveAs());
				_receiver.save(name);
			}
		}
		String a = IO.readString(Message.openFile());
		try{
			_receiver.openFileSystem(a);
		}catch(ClassNotFoundException e){ e.printStackTrace(); }
		catch(java.io.FileNotFoundException e){
			IO.println(Message.fileNotFound());
		}
	}

}
