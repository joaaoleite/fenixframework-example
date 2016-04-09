package pt.tecnico.mydrive.exception;


public class ExpiredTokenException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final long date;

	/**
	 * @param name
	 */
	public ExpiredTokenException(long date) {
		super();
		this.date = date;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The Token " + date + " is expired.";
	}
}
