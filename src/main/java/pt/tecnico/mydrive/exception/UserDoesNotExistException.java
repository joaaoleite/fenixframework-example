package pt.tecnico.mydrive.exception;




public class UserDoesNotExistException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	private final String _username;



	/**
	 * @param username
	 */
	public UserDoesNotExistException(String username) {
		super();
		_username = username;
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The user '" + _username + "' does not exist.";
	}
}
