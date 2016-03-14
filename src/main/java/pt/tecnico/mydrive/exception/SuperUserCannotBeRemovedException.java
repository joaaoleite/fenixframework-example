package pt.tecnico.mydrive.exception;




public class SuperUserCannotBeRemovedException extends MyDriveException {
	private static final long serialVersionUID = 1L;


	/**
	 * @param username
	 */
	public SuperUserCannotBeRemovedException() {
		super();
	}


	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The SuperUser cannot be removed.";
	}
}
