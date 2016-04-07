package pt.tecnico.mydrive.exception;




public class LoginFailedException extends MyDriveException {
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
		return "Username/Password is invalid.";
	}
}
