package pt.tecnico.mydrive.exception;




public class UserInvalidException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	private final String _username;


	/**
	 * @param username
	 */
	public UserInvalidException(String username) {
		super();
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The username '" + _username + "' is invalid.";
	}
}
