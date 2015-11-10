package poof;
public class EntryNotFoundException extends Exception {
	String _name ="";
	EntryNotFoundException(String name){
		_name = name;
	}
	public String getName(){ return _name; }
}
