package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;


public class UserAlreadyExists extends MyDriveException {

	private final String _username;


	/**
	 * @param username
	 */
	public UserAlreadyException(String username) {
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The user '" + _username + "' already exists.";
	}
}
