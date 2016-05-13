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

    
        Login login = getMyDrive().getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();

        File file = workingDir.getFileByName(filename);
        if(filename==null)
            throw new FilenameInvalidException("");
        if (file==null)
            throw new FileDoesNotExistException(filename);

        if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(0) == 'r')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(4) == 'r')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(file.getName());
            }

        if (file instanceof Dir) throw new FileIsADirException(filename);

        else if(file.getClass().getSimpleName().equals("PlainFile") || file.getClass().getSimpleName().equals("App")){
            res = ((PlainFile)(file)).read();
        }
        else if(file instanceof Link){
            File link = ((Link)(file)).findFile(login.getToken());

            if(!(link.getOwner().equals(login.getUser()) && link.getMask().charAt(0) == 'r')
                && !(!link.getOwner().equals(login.getUser()) && link.getMask().charAt(4) == 'r')
                && !(login.getUser().getUsername().equals("root"))){
                    throw new InsufficientPermissionsException(link.getName());
                }
            res = ((PlainFile)link).read();
        }
        else throw new FileDoesNotExistException(filename);


    }
}
