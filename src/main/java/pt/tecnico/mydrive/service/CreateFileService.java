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
    
    public CreateFileService(long token, String filename, String type, String content){
        super();
        
        _login = Login.getLoginByToken(token);
        _workingDir = _login.getWorkingDir();
        _filename = filename;
        _type = type;
        _content = content;
    }    

    public CreateFileService(long token, String filename, String type){
        super();
        
        _login = Login.getLoginByToken(token);
        _workingDir = _login.getWorkingDir();
        _filename = filename;
        _type = type;
        _content = null;
    }    
    
    protected void dispatch() throws MyDriveException, InsufficientPermissionsException{
        
        File newFile;

        if(!(_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getMask().charAt(1) == 'w')
            && !(!_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getMask().charAt(5) == 'w')
            && !(_login.getUser().getUsername().equals("root")))
            throw new InsufficientPermissionsException(_filename);

        switch(_type){
            case "dir":
                newFile = _workingDir.createDir(_login.getUser(), _filename);
                break;
            case "plain":
                if(_content == null)
                    newFile = _workingDir.createPlainFile(_login.getUser(), _filename);
                else
                    newFile = _workingDir.createPlainFile(_login.getUser(), _filename, _content);
                break;
            case "link":
                if(_content == null)
                    newFile = _workingDir.createLink(_login.getUser(), _filename);
                else
                    newFile = _workingDir.createLink(_login.getUser(), _filename, _content);
                break;
            case "app":
                if(_content == null)
                    newFile = _workingDir.createApp(_login.getUser(), _filename);
                else
                    newFile = _workingDir.createApp(_login.getUser(), _filename, _content);
                break;
        }
    }
}


