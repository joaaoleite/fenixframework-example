package pt.tecnico.mydrive.exception;




public class ImportDocException extends MyDriveException {
	private static final long serialVersionUID = 1L;
    public ImportDocException() {
        super("Error in importing person from XML");
    }
}
