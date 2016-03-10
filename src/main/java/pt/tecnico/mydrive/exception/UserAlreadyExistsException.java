package pt.tecnico.mydrive.domain;


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
