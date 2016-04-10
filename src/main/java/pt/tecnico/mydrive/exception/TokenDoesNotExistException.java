package pt.tecnico.mydrive.exception;


public class TokenDoesNotExistException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public TokenDoesNotExistException(String name) {
		super();
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The token " + _name + " does not exist.";
	}
}
