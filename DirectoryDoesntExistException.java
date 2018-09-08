package home;

public class DirectoryDoesntExistException extends Exception {
	public DirectoryDoesntExistException (String s) {
		super(s);
	}
}
