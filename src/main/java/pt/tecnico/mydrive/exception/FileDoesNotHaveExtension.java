package pt.tecnico.mydrive.exception;


public class FileDoesNotHaveExtension extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String ext;

	/**
	 * @param name
	 */
	public FileDoesNotHaveExtension(String ext) {
		super();
		this.ext = ext;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The File " + ext + " does not have extension.";
	}
}
