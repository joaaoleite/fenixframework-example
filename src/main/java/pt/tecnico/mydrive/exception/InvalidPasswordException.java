package pt.tecnico.mydrive.exception;


public class InvalidPasswordException extends MyDriveException {
    
    private static final long serialVersionUID = 1L;

	/**
	 * @param name
	 */
	public InvalidPasswordException() {
		super();
	}

	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The password needs at least 8 caracthers";
	}
}