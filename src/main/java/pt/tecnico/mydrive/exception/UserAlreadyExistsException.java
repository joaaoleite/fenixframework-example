package pt.tecnico.mydrive.exception;




public class UserAlreadyExistsException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	private final String _username;



	/**
	 * @param username
	 */
	public UserAlreadyExistsException(String username) {
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The user '" + _username + "' already exists.";
	}
}
