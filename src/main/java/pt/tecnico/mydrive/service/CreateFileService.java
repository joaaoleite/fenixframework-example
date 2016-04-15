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
    private long token;
    
    public CreateFileService(long token, String filename, String type, String content){
        super();
        
        this.filename = filename;
        this.type = type;
        this.content = content;
        this.token = token;
    }    

    public CreateFileService(long token, String filename, String type){
        super();
        
        this.token = token;
        this.filename = filename;
        this.type = type;
        this.content = null;
    }    
    
    protected final void dispatch() throws TokenDoesNotExistException, ExpiredTokenException, MyDriveException, InsufficientPermissionsException, FileAlreadyExistsException, FileIsADirException, LinkCantBeEmptyException{
        
        Login login = Login.getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();

        System.out.println("\n\n\n\n WorkingDir: "+workingDir.getPath()+"\n\n\n");

        File newFile;

        if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(1) == 'w')
            && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(5) == 'w')
            && !(login.getUser().getUsername().equals("root")))
            throw new InsufficientPermissionsException(filename);

        switch(type){
            case "Dir":
                if(content!=null){
                    throw new FileIsADirException(filename);
                }
                newFile = workingDir.createDir(login.getUser(), filename);
                break;
            case "Plain":
                if(content == null)
                    newFile = workingDir.createPlainFile(login.getUser(), filename);
                else
                    newFile = workingDir.createPlainFile(login.getUser(), filename, content);
                break;
            case "Link":
                    newFile = workingDir.createLink(login.getUser(), filename, content);
                break;
            case "App":
                if(content == null)
                    newFile = workingDir.createApp(login.getUser(), filename);
                else
                    newFile = workingDir.createApp(login.getUser(), filename, content);
                break;
            default:
                throw new InvalidFileTypeException(type);
        }
    }
}


