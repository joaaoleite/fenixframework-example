package pt.tecnico.mydrive.domain;


public class ImportDocException extends MyDriveException {

    public ImportDocException() {
        super("Error in importing person from XML");
    }
}
