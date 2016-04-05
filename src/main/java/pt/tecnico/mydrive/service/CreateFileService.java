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
    
    public CreateFileService(int token, String filename, String type, String content){
        super();

        //criar login, verificar token e tal
        
        _workingDir = login.getWorkingDir();
        _filename = filename;
        _type = type;
        _content = content;
    }    
    
    private void dispatch() throws MyDriveException{
        
        File newFile;

        switch(_type){
            case "dir":
                newFile = _workingDir.createDir(login.getUser(), filename);
                break;
            case "plain":
                if(content == null)
                    newFile = _workingDir.createPlainFile(login.getUser(), filename);
                else
                    newFile = _workingDir.createPlainFile(login.getUser(), filename, content);
                break;
            case "link":
                if(content == null)
                    newFile = _workingDir.createLink(login.getUser(), filename);
                else
                    newFile = _workingDir.createLink(login.getUser(), filename, content);
                break;
            case "app":
                if(content == null)
                    newFile = _workingDir.createApp(login.getUser(), filename);
                else
                    newFile = _workingDir.createApp(login.getUser(), filename, content);
                break;
        }
    }
}


