package pt.tecnico.mydrive.exception;




public class LogoutFailedException extends MyDriveException {
	  
    private static final long serialVersionUID = 1L;

	  /**
	  * @param username
	  */
    public LogoutFailedException() {
		    super();
	  }


	  @Override
	  @SuppressWarnings("nls")
	  public String getMessage() {
		    return "Token is invalid.";
	  }
}
