package pt.tecnico.mydrive.exception;


public class InvalidEnvValuesException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	  public InvalidEnvValuesException() {
		    super();
	  }

	
	  @Override
	  @SuppressWarnings("nls")
	  public String getMessage() {
        return "Invalid environment variable name or value!";
	  }
}
