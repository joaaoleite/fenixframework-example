package pt.tecnico.mydrive.exception;


public class InsufficientPermissionsException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public InsufficientPermissionsException(String name) {
		super();
  		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "You dont have permission to access " + _name + ".";
	}
}
