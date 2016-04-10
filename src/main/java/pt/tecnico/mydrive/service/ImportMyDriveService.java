package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

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