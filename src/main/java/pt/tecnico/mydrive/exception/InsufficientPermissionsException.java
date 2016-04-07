package pt.tecnico.mydrive.exception;


public class InsufficientPermissionsExceptions extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String _name;

	/**
	 * @param name
	 */
	public InsufficientPermissionsExceptions(String name) {
		super();
  		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The User:" + _name + " does not have permissions.";
	}
}
