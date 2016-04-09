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


    public ListDirectoryService(int token){
    	super();

    	_login = Login.getLoginByToken(token);
    	_token = token;
        _workingDir = Login.getWorkingDir();
    }

    private File dispatch() throws InsuffientPermissionsException{

        if(!(_workingDir.getOwner().equals(login.getUser()) && _workingDir.getUmask().charAt(0) == 'r')
        && !(!_workingDir.getOwner().equals(login.getUser()) && _workingDir.getUmask().charAt(4) == 'r')
        && !(login.getUser().getUsername().equals("root")))
        throw new InsufficientPermissionsException();

        _workingDir.listDir();
    }
}
