package poof;

import java.io.Serializable;

/**
 * Implements the User
 */
public class User implements Serializable{
		private String _username;
		private String _name;
		private Directory _home;
		
		/**
		 * @param username	the user's username
		 * @param name		the user's name
		 */
		public User(String username, String name){
			_username = username;
			_name = name;
		}
		
		/**
		 * @return String Username
		 */
		public String getUsername(){ return _username; }
		
		/**
		 * @return String name
		 */
		public String getName(){ return _name; }
		/**
		 * @return Directory home
		 */
		public Directory getHome(){ return _home; }
		
		/**
		 * @param String username
		 */
		public void changeUsername(String username){ _username = username; }
		
		/**
		 * @param String name
		 */
		public void changeName(String name){ _name = name; }
		
		/**
		 * @param Directory home
		 */
		public void changeDirectory(Directory home){ _home = home; }
}
