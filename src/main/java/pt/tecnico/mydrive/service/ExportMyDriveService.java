package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class ExportMyDriveService extends MyDriveService{
    private Document doc;
    
    public ExportMyDriveService(){}

    @Override
    protected void dispatch() throws ExportDocException{
	      doc = getMyDrive().xmlExport();
    }

    public final Document result() {
        return doc;
    }
}
