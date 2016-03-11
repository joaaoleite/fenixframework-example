package pt.tecnico.mydrive.exception;


public class FileIsAPlainFileException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public FileIsAPlainFileException(String name) {
		super();
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File " + _name + " is a PlainFile.";
	}
}
