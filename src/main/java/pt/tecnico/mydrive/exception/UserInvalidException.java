package pt.tecnico.mydrive.domain;




public class UserInvalidException extends MyDriveException {
	private static final long serialVersionUID = 1L;
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
