package pt.tecnico.mydrive.exception;




public class GuestUserCannotBeRemovedException extends MyDriveException {
	private static final long serialVersionUID = 1L;


	/**
	 * @param username
	 */
	public GuestUserCannotBeRemovedException() {
		super();
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The guest user cannot be removed.";
	}
}
