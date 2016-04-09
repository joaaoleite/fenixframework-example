package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class WriteFileService extends MyDriveService{
    private final long token;
    private final String filename;
    private final String text;
    public WriteFileService(long session, String filename, String content){
        this.token = session;
        this.filename = filename;
        this.text = content;
    }
    protected void dispatch() throws LoginFailedException{
        Login login = Login.getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();
        PlainFile file = ((PlainFile) workingDir.getFileByName(filename));
        if (file==null){
            throw new FileDoesNotExistException(filename);
        }
        file.write(text);
        
    }
}
