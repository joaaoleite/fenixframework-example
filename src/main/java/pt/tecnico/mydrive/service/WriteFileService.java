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
        
        if(filename==null)
            throw new FilenameInvalidException("");
        String content;
        if(text==null) content="";
        else content=text;

        File file = workingDir.getFileByName(filename);

        if (file==null)
            throw new FileDoesNotExistException(filename);


        if (file.isDir()) throw new FileIsADirException(filename);
        else if(file.getClass().getSimpleName().equals("PlainFile") || file.getClass().getSimpleName().equals("App")){

            if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(1) == 'w')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(5) == 'w')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(file.getName());
            }
            ((PlainFile)(file)).write(content);
        }
        else if(file instanceof Link){
            
            if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(0) == 'r')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(4) == 'r')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(file.getName());
            }
            File link = ((Link)(file)).findFile(login.getToken());

            if(link.isDir()) throw new FileIsADirException(filename);

            if(!(link.getOwner().equals(login.getUser()) && link.getMask().charAt(1) == 'w')
                && !(!link.getOwner().equals(login.getUser()) && link.getMask().charAt(5) == 'w')
                && !(login.getUser().getUsername().equals("root"))){
                    throw new InsufficientPermissionsException(link.getName());
                }

            ((PlainFile)link).write(content);
        }
        else throw new FileDoesNotExistException(filename);
        
    }
}
