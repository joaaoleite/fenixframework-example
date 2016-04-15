package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ReadFileService extends MyDriveService{
    private final long token;
    private final String filename;
    private String res;

    public ReadFileService(long token, String filename){

        this.token = token;
        this.filename = filename;
    }

    public String result(){
    	return res;
    }

    protected final void dispatch() throws LoginFailedException, FileDoesNotExistException, FileIsADirException{

        Login login = Login.getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();

        File file = workingDir.getFileByName(filename);

        if (file==null) throw new FileDoesNotExistException(filename);
        if (file.isDir()) throw new FileIsADirException(filename);

        res = ((PlainFile)(file)).read();
    }
}
