package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private String res;
    private long token;


    public ListDirectoryService(long token){
    	super();

    	this.token = token; 
        
    }


    public String result(){
        return res; 
    }

    protected final void  dispatch() throws InsufficientPermissionsException,TokenDoesNotExistException, ExpiredTokenException{

        Login login = MyDriveService.getMyDrive().getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();

        if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(0) == 'r')
        && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(4) == 'r')
        && !(login.getUser().getUsername().equals("root")))
        throw new InsufficientPermissionsException(login.getUser().getUsername());
        

        res = workingDir.listDir();
    }
}
