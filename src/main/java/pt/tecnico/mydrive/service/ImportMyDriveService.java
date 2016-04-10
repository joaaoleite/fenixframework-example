package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ImportMyDriveService extends MyDriveService {
    private final Document doc;

    public ImportMyDriveService(Document doc) {
        this.doc = doc;
    }

    @Override
    protected void dispatch() throws ImportDocumentException {
        getMyDrive().xmlImport(doc.getRootElement());
    }
}