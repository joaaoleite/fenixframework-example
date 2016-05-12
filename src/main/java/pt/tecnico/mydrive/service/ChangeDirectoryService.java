package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ChangeDirectoryService extends MyDriveService{

    private String res;
    private String path;
    private long token;


    public ChangeDirectoryService(long token, String path){
    	super();

    	this.token = token;
    	this.path = path;
    }
    
    public final String result(){
        return res;
    }

    protected final void dispatch() throws TokenDoesNotExistException, ExpiredTokenException, FileIsAPlainFileException, FileDoesNotExistException, InsufficientPermissionsException{
        
        Login login = getMyDrive().getLoginByToken(token);

        String[] parts = path.split("/");
        Dir actual;
        int i;

        if(path.equals("/")){
            login.setWorkingDir(getMyDrive().getRootDir());
            res="/";
        }
        else{

            if(path.charAt(0) == '/'){

                actual = getMyDrive().getRootDir();
                i = 1;
            }
            else{
                actual = login.getWorkingDir();
                i = 0;
            }

            for(; i<parts.length-1; i++){
                actual = actual.getDir(parts[i]);
            }

            Dir workingDir = actual.getDir(parts[i]);
            
            if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(0) == 'r')
            && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(4) == 'r')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(workingDir.getName());
            }
            
            login.setWorkingDir(workingDir);
            res = workingDir.getFullPath();
            if(res.equals("//")) res="/";
        }
    }
}
