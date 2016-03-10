package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;


public class ImportDocException extends MyDriveException {

    public ImportDocException() {
        super("Error in importing person from XML");
    }
}
