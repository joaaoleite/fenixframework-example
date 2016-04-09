package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private Dir workingDir;
    private Login login;
    private String res;


    public ListDirectoryService(long token){
    	  super();
        login = Login.getLoginByToken(token);
        workingDir = login.getWorkingDir();
    }

    protected final void dispatch() throws InsufficientPermissionsException{

        if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(0) == 'r')
        && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(4) == 'r')
        && !(login.getUser().getUsername().equals("root")))
        throw new InsufficientPermissionsException(workingDir.getName());

        res = workingDir.listDir();
    }

    public final String result() {
        return res;
    }
}
