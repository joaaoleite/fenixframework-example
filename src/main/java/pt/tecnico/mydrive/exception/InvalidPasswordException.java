package pt.tecnico.mydrive.exception;




public class InvalidPasswordException extends MyDriveException {
	private static final long serialVersionUID = 1L;


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
