package pt.tecnico.mydrive.exception;


public class RootDirCannotBeRemovedException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	/**
	 * @param name
	 */
	public RootDirCannotBeRemovedException() {
		super();
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "Root Dir cannot be removed.";
	}
}