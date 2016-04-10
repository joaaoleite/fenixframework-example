package pt.tecnico.mydrive.exception;




public class ExportDocException extends MyDriveException {
	private static final long serialVersionUID = 1L;
    public ExportDocException() {
        super("Error in exporting person from XML");
    }
}
