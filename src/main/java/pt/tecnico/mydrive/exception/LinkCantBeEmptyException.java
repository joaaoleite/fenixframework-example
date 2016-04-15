package pt.tecnico.mydrive.exception;


public class LinkCantBeEmptyException extends MyDriveException {

    
    private static final long serialVersionUID = 1L;


	private final String name;

	/**
	 * @param name
	 */
	public LinkCantBeEmptyException(String name) {
		super();
		this.name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The Link " + name + " cant be empty.";
	}
}
