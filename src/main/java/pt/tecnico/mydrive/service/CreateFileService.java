package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class CreateFileService extends MyDriveService{

    private String filename;
    private String type;
    private String content;
    private Dir workingDir;
    private Login login;
    
    public CreateFileService(long token, String filename, String type, String content){
        super();
        
        this.login = Login.getLoginByToken(token);
        this.workingDir = login.getWorkingDir();
        this.filename = filename;
        this.type = type;
        this.content = content;
    }    

    public CreateFileService(long token, String filename, String type){
        super();
        
        this.login = Login.getLoginByToken(token);
        this.workingDir = login.getWorkingDir();
        this.filename = filename;
        this.type = type;
        this.content = null;
    }    
    
    protected final void dispatch() throws MyDriveException, InsufficientPermissionsException{
        
        File newFile;

        if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(1) == 'w')
            && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(5) == 'w')
            && !(login.getUser().getUsername().equals("root")))
            throw new InsufficientPermissionsException(filename);

        switch(type){
            case "dir":
                newFile = workingDir.createDir(login.getUser(), filename);
                break;
            case "plain":
                if(content == null)
                    newFile = workingDir.createPlainFile(login.getUser(), filename);
                else
                    newFile = workingDir.createPlainFile(login.getUser(), filename, content);
                break;
            case "link":
                if(content == null)
                    newFile = workingDir.createLink(login.getUser(), filename);
                else
                    newFile = workingDir.createLink(login.getUser(), filename, content);
                break;
            case "app":
                if(content == null)
                    newFile = workingDir.createApp(login.getUser(), filename);
                else
                    newFile = workingDir.createApp(login.getUser(), filename, content);
                break;
        }
    }
}


