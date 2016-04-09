package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ChangeDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private Dir workingDir;
    private Login login;
    private String path;


    public ChangeDirectoryService(long token, String path){
    	super();

    	this.login = Login.getLoginByToken(token);
    	this.path = path;

    }
    
    public final String result(){
        return workingDir.getPath();
    }

    protected final void dispatch() throws FileIsAPlainFileException, FileDoesNotExistException, InsufficientPermissionsException{
        String[] path = path.split("/");

        Dir actual;
        int i;

        if(path.charAt(0) == '/'){

            actual = md.getRootDir();
            i = 1;
        }
        else{
            actual = login.getWorkingDir();
            i = 0;
        }

        for(; i<path.length-1; i++){
            actual = actual.getDir(path[i]);
        }

        if(actual.exists(path[i++])){
        	if (actual.getFileByName(path[i]).isDir()){
        		workingDir = actual.getDir(path[i]);

                if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(0) == 'r')
                && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(4) == 'r')
                && !(login.getUser().getUsername().equals("root")))
                throw new InsufficientPermissionsException(path);

        		login.setWorkingDir(workingDir);
        	}
        	else
            	throw new FileIsAPlainFileException(path[i]);
        }

        throw new FileDoesNotExistException(path[i]);
    }
}
