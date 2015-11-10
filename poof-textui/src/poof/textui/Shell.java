/** @version $Id: Shell.java,v 1.20 2014/11/27 18:27:51 ist175741 Exp $ */
package poof.textui;

import static ist.po.ui.Dialog.IO;

import java.io.IOException;
import java.util.ArrayList;
import poof.FileSystemManager;
import poof.UserExistsOnFileSystemException;
import poof.UserNotFoundException;
import poof.DirectoryNotFoundException;
import poof.NotADirectoryException;

import java.io.BufferedReader;
import java.io.FileReader;
/**
 * Class that starts the application's textual interface.
 */
public class Shell {
	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException {
		FileSystemManager manager = new FileSystemManager();
		String datafile = System.getProperty("import"); //$NON-NLS-1$
		if (datafile != null) {
			manager.newFileSystem();
			BufferedReader in = new BufferedReader(new FileReader(datafile));
			String c;
			while((c = in.readLine()) != null){
					String parts[] = c.split("\\|");
					if(parts[0].equals("USER")){
						try{
							manager.newUserImport(parts[1],parts[2]);
						}catch(UserExistsOnFileSystemException e){}
					}
					else if(parts[0].equals("DIRECTORY")){
						
						//BEGIN OF IMPORT DIRECTORY
						try{
							manager.login(parts[2]);
							manager.goToRoot();
							String dirname[] = parts[1].split("/");
							int i;
							boolean p;
							for(i = 1; i < dirname.length; i++){
								if(i == dirname.length - 1) 
									p = (parts[3].equals("private")) ? false : true;
								else p = false;
								try{
									if(!manager.HasEntry(dirname[i])){
										manager.newDirectory(dirname[i],p);
									}
									manager.changeDirectory(dirname[i]);
								}catch(poof.EntryNotFoundException e){ e.printStackTrace(); }
								 catch(poof.DirectoryNotFoundException e){e.printStackTrace(); }
								 catch(poof.NotADirectoryException e){e.printStackTrace(); }
							}
						}
						catch(poof.UserNotFoundException e){ e.printStackTrace(); }
					}else if(parts[0].equals("FILE")){
						try{
							manager.login(parts[2]);
							manager.goToRoot();
							String dirname[] = parts[1].split("/");
							int i;
							boolean p;
							for(i = 1; i < dirname.length - 1; i++){
								if(i == dirname.length - 1) 
									p = (parts[3].equals("private")) ? false : true;
								else p = false;
								try{
									if(!manager.HasEntry(dirname[i])){
										manager.newDirectory(dirname[i],false);
									}
									manager.changeDirectory(dirname[i]);
								}catch(poof.EntryNotFoundException e){ e.printStackTrace(); }
								 catch(poof.DirectoryNotFoundException e){e.printStackTrace(); }
								 catch(poof.NotADirectoryException e){e.printStackTrace(); }
							}
							try{
								p = (parts[3].equals("private")) ? false : true;
								manager.newFile(dirname[i], parts[4]);
								manager.changePermission(dirname[i],p);
							}catch(poof.EntryExistsException e){e.printStackTrace();}
							 catch(poof.AccessDeniedException e){e.printStackTrace(); }
							 catch(poof.EntryNotFoundException e){ e.printStackTrace(); }
							 
						}catch(poof.UserNotFoundException e){ e.printStackTrace(); }
					}
					
						
			}
					try{
						manager.goToRoot();
						manager.changeDirectory("home");
						manager.login("root");
					}catch(poof.EntryNotFoundException e){ e.printStackTrace(); }
					 catch(poof.UserNotFoundException e){e.printStackTrace();}
					 catch(poof.DirectoryNotFoundException e){ e.printStackTrace();}
					 catch(poof.NotADirectoryException e){ e.printStackTrace();}
			
		}
		poof.textui.main.MenuBuilder.menuFor(manager);
		IO.closeDown();
	}

}
