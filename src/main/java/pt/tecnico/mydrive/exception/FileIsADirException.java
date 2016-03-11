package pt.tecnico.mydrive.exception;


public class FileIsADirException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public FileIsADirException(String name) {
		super();
  		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File " + _name + " is a Directory.";
	}
}