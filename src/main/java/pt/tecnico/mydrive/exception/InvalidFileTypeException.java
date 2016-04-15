package pt.tecnico.mydrive.exception;


public class InvalidFileTypeException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String type;

	/**
	 * @param name
	 */
	public InvalidFileTypeException(String type) {
		super();
		this.type = type;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File Type " + type + " is invalid.";
	}
}
