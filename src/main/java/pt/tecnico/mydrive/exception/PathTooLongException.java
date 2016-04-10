package pt.tecnico.mydrive.exception;


public class PathTooLongException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public PathTooLongException(String name) {
		super();
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The path of the file to be created with name'" + _name + "' is going to be too long.";
	}
}
