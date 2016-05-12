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

    public WriteFileService(long token, String filename, String text){
        this.token = token;
        this.filename = filename;
        this.text = text;
    }

    protected final void dispatch() throws LoginFailedException, FileIsADirException{
        Login login = getMyDrive().getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();
        
        if(filename==null){
            throw new FilenameInvalidException("");
        }

        File tmp = workingDir.getFileByName(filename);

        if (tmp==null)
            throw new FileDoesNotExistException(filename);

        if(tmp.isDir()){
            throw new FileIsADirException(tmp.getName());
        }
        PlainFile file = ((PlainFile) tmp);

        if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(1) == 'w')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(5) == 'w')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(file.getName());
            }

        
        if(text==null){
            file.write("");
        }else{
            file.write(text);
        }
        
    }
}
