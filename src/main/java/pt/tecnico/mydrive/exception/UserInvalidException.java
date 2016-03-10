package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;


public class UserInvalidException extends MyDriveException {

	private final String _username;


	/**
	 * @param username
	 */
	public UserInvalidException(String username) {
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The username '" + _username + "' is invalid.";
	}
}
