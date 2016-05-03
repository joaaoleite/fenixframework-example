package pt.tecnico.mydrive.exception;




public class GuestUserCannotSetPasswordException  extends MyDriveException {

	private static final long serialVersionUID = 1L;


	/**
	 * @param username
	 */
	public GuestUserCannotSetPasswordException() {
		super();
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The guest user cannot set Password.";
	}
}
