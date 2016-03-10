package pt.tecnico.mydrive.exception;


public class FileAlreadyExistsException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public FileAlreadyExistsException(String name) {
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File:" + _name + " is already exists.";
	}
}
