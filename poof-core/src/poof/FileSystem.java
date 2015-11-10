package poof;
import java.util.TreeMap;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Implements the FileSystem that controls the
 * directory structure and its Users
 */
public class FileSystem implements Serializable{
	Directory _root = null;
	TreeMap <String, User> _users = new TreeMap<String, User>();
	
	public FileSystem(){
		User root = new Root(); //Add root user
		_users.put(root.getUsername(), root);
		
		//Add root directory
		_root = new Directory("/", (Directory) null, root, false);
		_root.setEntry(new Directory("home", _root, root, false));
		

		
		_root.changeOwner(root); //Change Ownership of root
		
		
		try{//Add root user directory
			Directory home = new Directory("root", (Directory) _root.getEntry("home"), root, false);
			((Directory) _root.getEntry("home")).setEntry(home);
			root.changeDirectory(home);
		}catch(EntryNotFoundException e){ e.printStackTrace(); }
	}
	
	/**
	 * @param String username
	 * @return User
	 */
	public User getUser(String username){ return _users.get(username); }
	
	/**
	 * @return RootDirectory
	 */
	public Directory getRoot(){ return _root; }
	
	/**
	 * @param String username
	 * @param String name
	 */
	public void addUser(String username, String name){
		//Cria Utilizador
		User user = new User(username, name);
		_users.put(username,user);
		
		//Get diretoria /home
		try{
			Directory home = (Directory) _root.getEntry("home");
			//Cria diretoria do user
			Directory user_home = new Directory(username, (Directory) _root.getEntry("home"), user, false);
			user.changeDirectory(user_home);
			home.setEntry(user_home);
		
		}catch(EntryNotFoundException e){ e.printStackTrace(); }
		
	}
	
	/**
	 * @param Directory current
	 * @param String entryname
	 */
	public void removeEntry(Directory current, String entryname) throws IllegalRemovalException{
		current.removeEntry(entryname);
	}
	
	/**
	 * @param Directory current
	 * @param User owner
	 * @param String dirname
	 */
	public Entry newDirectory(Directory current, User owner, String dirname){
		Entry entry = new Directory(dirname, current, owner,false);
		current.setEntry(entry);
		return entry;
	}
	
	/**
	 * @param Directory current
	 * @param String entryname
	 * @param String newuser
	 */
	public void changeOwner(Directory current, String entryname, String newuser, User user) throws EntryNotFoundException, AccessDeniedException, UserNotFoundException{
		if(_users.get(newuser) == null) throw new UserNotFoundException();
		Entry aux = current.getEntry(entryname);
		if(aux == null) throw new EntryNotFoundException(entryname);
		if(aux.getOwner() != user && !(user instanceof Root)) throw new AccessDeniedException();
		aux.changeOwner(_users.get(newuser));
	}
	
	/**
	 * @param Directory current
	 * @param String entryname
	 * @return boolean
	 */
	public boolean isCDable(Directory current, String entryname){
		return (current.isCDable(entryname));
	}
	
	/**
	 * @param Directory current
	 * @param String entryname
	 */
	public boolean HasEntry(Directory current, String entryname){
		try{
			return current.getEntry(entryname) != null;
		}catch(EntryNotFoundException e){ return false; }
	}
	
	/**
	 * @param Directory current
	 * @return ArrayList<String>
	 */
	public ArrayList<String>  list(Directory current){ return current.list(); }
	
	/**
	 * @param Directory current
	 * @param String entryname
	 * @return string
	 */
	public String listEntry(Directory current, String entryname) throws EntryNotFoundException {
		if((current.getEntry(entryname)) == null) throw new EntryNotFoundException(entryname);
		Entry a = current.getEntry(entryname);
		return a.toString();
	}

	/**
	 * @param Directory dir
	 * @return string with path
	 */
	public String getPath(Directory dir){
		String path = "";
		Directory aux = dir;
		while(aux.getName() != "/"){
			path = "/" + aux.getName() + path;
			aux = aux.getFather();
		}
		return path;
	}

	/**
	 * @return User's ArrayList<String> username:name:path/to/home
	 */
	public ArrayList<String> listUsers(){
		ArrayList<String> list = new ArrayList<String>();
		for(User u : _users.values()){
			String path = "";
			Directory aux = u.getHome();
			while(aux != _root){
				path = "/" + aux.getName() + path;
				aux = aux.getFather();
			}
			list.add(u.getUsername() + ":" + u.getName() + ":" + path);
		}
		return list;
	}
	
	/**
	 * @param Directory current
	 * @param String filename
	 * @param String content
	 */
	public void appendDataToFile(Directory current, String filename, String content, User user) throws EntryNotFoundException, AccessDeniedException, NotAFileException{
		Entry entry = current.getEntry(filename);
		if(entry == null) throw new EntryNotFoundException(filename);
		if(!(user instanceof Root) && ! entry.getPermission() && entry.getOwner() != user) throw new AccessDeniedException();
		entry.appendData(content);
	}
	
	/**
	 * @param String dirname
	 * @param User owner
	 * @param Directory parent
	 * @param boolean permission
	 */
	public void newDirectory(String dirname, User owner, Directory parent, boolean permission){
		parent.setEntry(new Directory(dirname, parent, owner, permission));
	}
	
	public void newFile(String filename, String content, Directory current, User owner){
		File file = new File(filename, owner, false);
		if(!content.equals(""))
			file.appendData(content);
		current.setEntry(file);
		
	}
	
	public String openFile(String filename, Directory current) throws EntryNotFoundException, NotAFileException{
		return current.openFile(filename);
	}
}
