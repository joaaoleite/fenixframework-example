package pt.tecnico.mydrive.exception;




public class GuestUserCannotSetException  extends MyDriveException {

	private static final long serialVersionUID = 1L;


	/**
	 * @param username
	 */
	public GuestUserCannotSetException() {
		super();
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The guest user cannot set Password.";
	}
}
