package pt.tecnico.mydrive.exception;


public class FilenameInvalidException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public FilenameInvalidException(String name) {
		super();
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The name of the file'" + _name + "' is invalid.";
	}
}
