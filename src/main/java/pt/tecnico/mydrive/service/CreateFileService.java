package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class CreateFileService extends MyDriveService{

    private String _filename;
    private String _type;
    private String _content;
    private Dir _workingDir;
    private Login _login;
    
    public CreateFileService(int token, String filename, String type, String content){
        super();
        
        _login = Login.getLoginByToken(token);
        _workingDir = _login.getWorkingDir();
        _filename = filename;
        _type = type;
        _content = content;
    }    
    
    private void dispatch() throws MyDriveException, InsufficientPermissionsException{
        
        File newFile;

        if(!(_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getUmask().charAt(1) == 'w')
            && !(!_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getUmask().charAt(5) == 'w')
            && !(_login.getUser().getUsername().equals("root")))
            throw new InsufficientPermissionsException();

        switch(_type){
            case "dir":
                newFile = _workingDir.createDir(_login.getUser(), filename);
                break;
            case "plain":
                if(content == null)
                    newFile = _workingDir.createPlainFile(_login.getUser(), filename);
                else
                    newFile = _workingDir.createPlainFile(_login.getUser(), filename, content);
                break;
            case "link":
                if(content == null)
                    newFile = _workingDir.createLink(_login.getUser(), filename);
                else
                    newFile = _workingDir.createLink(_login.getUser(), filename, content);
                break;
            case "app":
                if(content == null)
                    newFile = _workingDir.createApp(_login.getUser(), filename);
                else
                    newFile = _workingDir.createApp(_login.getUser(), filename, content);
                break;
        }
    }
}


