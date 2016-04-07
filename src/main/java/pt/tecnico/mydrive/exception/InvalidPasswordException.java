package pt.tecnico.mydrive.exception;




public class InvalidPasswordException extends MyDriveException {
	private static final long serialVersionUID = 1L;
	private final String _username;


	/**
	 * @param username
	 */
	public InvalidPasswordException() {
		super();
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "Password is invalid.";
	}
}
