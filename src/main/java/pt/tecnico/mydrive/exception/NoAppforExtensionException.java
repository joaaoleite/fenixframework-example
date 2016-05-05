package pt.tecnico.mydrive.exception;


public class NoAppforExtensionException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String ext;

	/**
	 * @param name
	 */
	public NoAppforExtensionException(String ext) {
		super();
		this.ext = ext;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "No default App for" + ext + " extension.";
	}
}
