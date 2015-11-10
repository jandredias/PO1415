package poof;

import java.io.Serializable;

public abstract class Entry implements Serializable{
	private String _name = "";
	private User _owner = null;
	private boolean _public = true;
	
	public Entry(String name, User owner, boolean permission){
		_name = name;
		_public = permission;
		_owner = owner;
	}
	
	//Getters
	public boolean getPermission(){ return _public; }
	public String getName(){ return _name; }
	public User getOwner(){ return _owner; }
	abstract public int getSize();
	abstract public String toString();
	
	//Setters
	public void changeOwner(User owner){ _owner = owner; }
	public void changeName(String name){ _name = name; }
	public void changePermission(boolean p){ _public = p; }
	
	public void appendData(String content) throws NotAFileException{ throw new NotAFileException(); }
	public String getContent() throws NotAFileException { throw new NotAFileException(); }	
	public boolean isCDable(){ return false; }
}
