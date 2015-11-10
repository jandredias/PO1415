package poof;
import java.util.ArrayList;
import java.io.IOException;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;

/**
 * It implements the Manager of FileSystem.
 * Every action that is must be delivered to a filesystem, is handled
 * by the manager and this will do the necessary changes or tell
 * the filesystem to execute them
 */
public class FileSystemManager implements Serializable{
    private FileSystem _currentFileSystem = null;
    private User _currentUser = null;
    private Directory _workingDirectory = null;
    private String _openfile = "";
    private boolean _unsavedchanges = false;
    public FileSystemManager(){}

    public void newFileSystem(){
			_currentFileSystem = new FileSystem();
			try{ login("root"); }catch(UserNotFoundException e){}
			_unsavedchanges = true;
	}
	
	/**
	 * @param user	the username to loggin
	 */
	public void setCurrentUser(String user){
		_currentUser = _currentFileSystem.getUser(user);
		_unsavedchanges = true;
	}
	
	/**
	 * @return	return true is the filesystem has unsaved changes
	 */
	public boolean HasUnsavedChanges(){ return _unsavedchanges; }

	/**
	 * @param name	opens the filesystem that is saved in file name
	 * @throws IOException
	 * @throws ClasNotFoundException
	 */
    public void openFileSystem(String name) throws IOException, ClassNotFoundException{
		_currentUser = null;
		_workingDirectory = null;
		_openfile = name;
		ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(_openfile)));
		_currentFileSystem = (FileSystem) in.readObject();
		_currentUser = (User) in.readObject();
		try{ login(_currentUser.getUsername()); }catch(UserNotFoundException e){}
	}
	
	/**
	 * Saves the current filesystem to the its file
	 * @throws NoFileSpecifiedException
	 * @throws IOException
	 */
    public void save() throws NoFileSpecifiedException, IOException{
		if(_openfile.equals("")) throw new NoFileSpecifiedException();
		ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(_openfile)));
		out.writeObject(_currentFileSystem);
		out.writeObject(_currentUser);
		out.close();
		_unsavedchanges = false;
    }
    
    /**
     * @param filename	the file name where to save the filesystem
     * @throws IOException
     */
    public void save(String filename) throws IOException{
	    _openfile = filename;
	    try{ save(); }catch(NoFileSpecifiedException e){}
    }
    
    /**
     * @param username	Username to login
     * @throws UserNotFoundException
     */
    public void login(String username) throws UserNotFoundException{
		User a = _currentFileSystem.getUser(username);
		if(a == null) throw new UserNotFoundException();
		_currentUser = a;
		_workingDirectory = _currentUser.getHome();
	}
	
	/**
	 * Logout the current user
	 */
    public void logout(){
		_workingDirectory = null;
		_currentUser = null;
	}
	
	/**
	 * Changes the current working directory to filesystem root directory
	 */
	public void goToRoot(){
		_workingDirectory = _currentFileSystem.getRoot();
	}
	
	/**
	 * @return boolean 	if it has a filesystem loaded
	 */
    public boolean hasFileSystemOpen(){
      return (_currentFileSystem!= null);
    }
    
    /**
     * @return ArrayList<String> with entries details
     */
    public ArrayList<String> list(){
		return _currentFileSystem.list(_workingDirectory);
	}
	
	/**
	 * @param String name
	 */
    public String listEntry(String name) throws EntryNotFoundException{
		return _currentFileSystem.listEntry(_workingDirectory,name);
	}
	
	/**
	 * @param String name
	 */
    public void removeEntry(String name) throws EntryNotFoundException, AccessDeniedException, IllegalRemovalException{
		if(_workingDirectory.getEntry(name) == null) throw new EntryNotFoundException(name);		
		
		if(!((_currentUser instanceof Root ||
			_workingDirectory.getOwner() == _currentUser ||
			_workingDirectory.getPermission()) && 
		   (_currentUser instanceof Root ||
		    _workingDirectory.getEntry(name).getOwner() == _currentUser ||
		    _workingDirectory.getEntry(name).getPermission())
		    
		    
		    
		    )
		    
		    
		    ){
				throw new AccessDeniedException();		
			}
		_currentFileSystem.removeEntry(_workingDirectory, name);
		_unsavedchanges = true;	
	}
	
	/**
	 * @param String name  new working directory
	 * @throws DirectoryNotFoundException
	 * @throws  NotADirectoryException
	 */
    public void changeDirectory(String name) throws EntryNotFoundException,DirectoryNotFoundException, NotADirectoryException{
		if(_workingDirectory.getEntry(name) == null) throw new EntryNotFoundException(name);
		if(!_currentFileSystem.isCDable(_workingDirectory, name)) throw new NotADirectoryException();
		_workingDirectory = (Directory) _workingDirectory.getEntry(name);
		
    }
    
    /**
     * Resets login. Sets current User to null
     */
    public void resetLogin(){
		_currentUser = null;
	}
	
	/**
	 * @return current working directory path to root
	 */
    public String getPath(){
		return _currentFileSystem.getPath(_workingDirectory);
    }
    
    /**
     * @param String entryname
     * @param boolean permission
     */
    public void changePermission(String entryname, boolean permission) throws EntryNotFoundException, AccessDeniedException{
		if((Entry) _workingDirectory.getEntry(entryname) == null)
			throw new EntryNotFoundException(entryname);
		if(_currentUser != _workingDirectory.getEntry(entryname).getOwner() &&
			(!(_currentUser instanceof Root))) throw new AccessDeniedException();
		
		_workingDirectory.changeEntryPermission(entryname, permission);
		_unsavedchanges = true;
	}
	
	
	/**
	 * @return returns true if a filesystem is loaded
	 */
    public boolean HasFileSystemOpen(){
		return (_currentFileSystem != null);
	}
	
	/**
	 * @return boolean	returns true if a user is logged in
	 */
	public boolean HasUserLoggedIn(){
		return (_currentUser != null);
	}
	
	public boolean HasEntry(String name){
		return _currentFileSystem.HasEntry(_workingDirectory, name);
	}
	/**
	 * @return string	returns the current user loggedin
	 */
	public String getUserLoggedin(){ return _currentUser.getUsername(); }
	
	
    /**
     * @param String username
     * @param String name
     */
    public void newUserImport(String username, String name) throws UserExistsOnFileSystemException{
		if(_currentFileSystem.getUser(username) != null) throw new UserExistsOnFileSystemException();
		_currentFileSystem.addUser(username, name);
		_unsavedchanges = true;
	}
    
    /**
     * @param String username
     * @param String name
     */
    public void newUser(String username, String name) throws AccessDeniedException, UserExistsOnFileSystemException {
		if(!(_currentUser instanceof Root)) throw new AccessDeniedException();
		if(_currentFileSystem.getUser(username) != null) throw new UserExistsOnFileSystemException();
		_currentFileSystem.addUser(username, name);
		_unsavedchanges = true;
	}
	
    /**
     * @return ArrayList<String>	List users by this format: username:name:path/to/root
     */
    public ArrayList<String> listUsers(){
		return _currentFileSystem.listUsers();
	}
	
	/**
	 * @param dirname
	 */
    public void newDirectory(String dirname) throws EntryExistsException, AccessDeniedException{
		try{
			if(_workingDirectory.getEntry(dirname) != null) throw new EntryExistsException();
		}catch(EntryNotFoundException e){}
		
		if(!(_currentUser instanceof Root) && !_workingDirectory.getPermission() && _workingDirectory.getOwner() != _currentUser) throw new AccessDeniedException();
		_currentFileSystem.newDirectory(_workingDirectory, _currentUser, dirname);
		_unsavedchanges = true;
	}
    
    /**
     * @param String dirname
     * @param boolean Permission
     */
    public void newDirectory(String dirname, boolean permission) {
		try{
			_currentFileSystem.newDirectory(_workingDirectory, _currentUser, dirname);
			changePermission(dirname, permission);
		}catch(EntryNotFoundException e){ e.printStackTrace(); }
		catch(AccessDeniedException e){ e.printStackTrace(); }
	}
	
	/**
	 * @param filename	String with the file name
	 * @param content	String with the file's content
	 */
	public void newFile(String filename, String content) throws EntryExistsException, AccessDeniedException{
		try{
			if(_workingDirectory.getEntry(filename) != null) throw new EntryExistsException();
		}catch(EntryNotFoundException e){}
		if(!(_currentUser instanceof Root) && !_workingDirectory.getPermission() && _workingDirectory.getOwner() != _currentUser)
			throw new AccessDeniedException();
		_currentFileSystem.newFile(filename, content, _workingDirectory, _currentUser);
		_unsavedchanges = true;
	}
	/**
	 * @param filename	String with the file name
	 * @param content	String with the content to append to file
	 */
    public void appendtoFile(String filename, String content) throws EntryNotFoundException, AccessDeniedException, NotAFileException{
		_currentFileSystem.appendDataToFile(_workingDirectory, filename, content, _currentUser);
		_unsavedchanges = true;
    }
    /**
     * @param filename	String with the file name
     * @return string	String with the file's content
     */
    public String openFile(String filename) throws EntryNotFoundException, NotAFileException {
		return _workingDirectory.openFile(filename); }
		
	/**
	 * @param	String entryname
	 * @param 	String newuser
	 */
    public void changeOwner(String entryname, String newuser) throws EntryNotFoundException, AccessDeniedException, UserNotFoundException{
		_currentFileSystem.changeOwner(_workingDirectory, entryname, newuser, _currentUser);
		_unsavedchanges = true;
	}
	
}
 
