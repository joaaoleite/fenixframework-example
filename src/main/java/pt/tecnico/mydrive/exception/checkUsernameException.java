package pt.tecnico.mydrive.domain;


public class checkUsernameException extends MyDriveException {

	private final String _username;


	/**
	 * @param username
	 */
	public checkUsernameException(String username) {
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The username '" + _username + "' has invalid characters.";
	}
}
