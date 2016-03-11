package pt.tecnico.mydrive.exception;


public class DirectoryIsNotEmptyException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public DirectoryIsNotEmptyException(String name) {
		super();
  		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The Directory:" + _name + " is not empty.";
	}
}
