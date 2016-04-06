package pt.tecnico.mydrive.exception;


public class ExpiredTokenException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _token;

	/**
	 * @param name
	 */
	public ExpiredTokenException(String token) {
		super();
		_token = token;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The Token " + _token + " is expired.";
	}
}
