package pt.tecnico.mydrive.exception;


public class FileDoesNotExistException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public FileDoesNotExistException(String name) {
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File:" + _name + " does not exist.";
	}
}
