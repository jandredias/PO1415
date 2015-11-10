package poof;

import java.util.TreeMap;
import java.util.ArrayList;
import java.io.Serializable;

/**
 * Implements the directory that saves another entries
 */
public class Directory extends Entry implements Serializable{
    private TreeMap<String, Entry> _entries = new TreeMap<String, Entry>();
    
    /**
     * @param name
     * @param parent
     * @param owner
     * @param permission
     */
	public Directory(String name, Directory parent, User owner, boolean permission){
		super(name,owner, false); //permission: 0 private, 1 public	
		_entries.put("..",(parent != null) ? parent : this );
		_entries.put(".", this );
		try{changeEntryPermission(".", permission);}catch(EntryNotFoundException e){}
    }
    
    /**
     * @return size
     */
    public int getSize(){ return (_entries.size()) * 8; }
    
    /**
     * @return entries
     */
    public TreeMap<String, Entry> getEntries(){ return _entries; }
	
	
	/**
	 * @return ParentDirectory
	 */
	public Directory getFather(){ return (Directory) _entries.get(".."); }
	
	/**
	 * @return StringDetails, format: type permission owner size name
	 */
	@Override
	public String toString(){
		return "d " + (getPermission() ? "w" : "-") + " " +
			   getOwner().getUsername() + " " + getSize() +
			   " " + getName();
	}
	
	/**
	 * @param entry
	 */
    public void setEntry(Entry entry){ _entries.put(entry.getName(), entry); }
    
    /**
     * @param name
     */
    public void removeEntry(String name) throws IllegalRemovalException{
		if(name.equals(".") || name.equals("..")) throw new IllegalRemovalException();
		_entries.remove(name);
	}
	
	/**
	 * @return ArrayList<String>
	 */
    public ArrayList<String> list(){
		ArrayList<String> aux = new ArrayList<String>();
		for(Entry entry : _entries.values()){
			if(entry == getFather())
				aux.add( "d " + (entry.getPermission() ? "w" : "-") + " " + entry.getOwner().getUsername() + " " + getFather().getSize() + " " + "..");
			else if(entry == this)
				aux.add( "d " + (getPermission() ? "w" : "-") + " " + getOwner().getUsername() + " " + getSize() + " " + ".");
			else
			aux.add(entry.toString());
		}
		return aux;
	}
	
	/**
	 * @param String	entryname
	 * @throws EntryNotFoundException
	 * @return	Entry
	 */
	public Entry getEntry(String entryname) throws EntryNotFoundException{
			Entry a = _entries.get(entryname);
			if(a == null) throw new EntryNotFoundException(entryname);
			return a;
	}
	/**
	 * @param filename
	 * @return String (file's content)
	 */
	public String openFile(String filename)throws EntryNotFoundException, NotAFileException{
		return getEntry(filename).getContent();
	}
	
	/**
	 * @param entryname
	 * @param newpermission
	 */
	public void changeEntryPermission(String entryname, boolean permission) throws EntryNotFoundException{
		Entry aux = _entries.get(entryname);
		if(aux == null) throw new EntryNotFoundException(entryname);
		aux.changePermission(permission);	
	}
	
	/**
	 * @return	true if is cdable
	 */
	@Override
	public boolean isCDable(){ return true; }
	
	public boolean isCDable(String name){
		try{
			return getEntry(name).isCDable();
		}catch(EntryNotFoundException e){ return false; }
	}
}
