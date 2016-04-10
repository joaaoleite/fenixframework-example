package pt.tecnico.mydrive.exception;


public class TokenDoesNotExistException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final long token;

	/**
	 * @param name
	 */
	public TokenDoesNotExistException(long token) {
		super();
		this.token = token;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The token " + token + " does not exist.";
	}
}
