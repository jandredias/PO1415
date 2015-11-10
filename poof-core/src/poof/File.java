package poof;

import java.io.Serializable;

public class File extends Entry implements Serializable{
	private String _content = "";
	
	public File(String name, User owner, boolean permission){
		super(name, owner, permission);
	}
	
	//Getters
	public int getSize(){ 
		int size = 0;
		String a = _content;
		return a.replace("\n","|").length(); }
	public String getContent(){ return _content; }
	public String toString(){
		return "- " + (getPermission() ? "w" : "-") + " " + 
				getOwner().getUsername() + " " + getSize() + " " + getName();
	}
	//Setters
	public void appendData(String content){ _content = _content + content + "\n"; }	
} 
