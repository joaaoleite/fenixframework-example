package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private Dir _workingDir;
    private Login _login;
    private String _path;


    public ListDirectoryService(token){
    	super();

    	//criar login, verificar token e tal
    	_token = token;
    }

    private File dispatch() throws InsuffientPermissionsException{
        _workingDir.listDir();
    }
}
