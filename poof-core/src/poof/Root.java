package poof;
import java.io.Serializable;
public class Root extends User implements Serializable{
	public Root(){
		super("root","Super User");
	}
}
