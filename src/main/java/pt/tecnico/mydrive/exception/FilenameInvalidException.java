package pt.tecnico.mydrive.exception;


public class FilenameInvalidException extends MyDriveExcepion {


	private final String _name;

	/**
	 * @param name
	 */
	public FilenameInvalidException(String name) {
		_name = name;
	}

	
	@Override
	@SuppressWarnings("nls")
	public String getMessage() {
		return "The name of the file'" + _name + "' is invalid.";
	}
}
