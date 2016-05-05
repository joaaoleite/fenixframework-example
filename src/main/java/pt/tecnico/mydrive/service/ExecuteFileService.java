package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ExecuteFileService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private String[] args;
    private String path;
    private long token;
    private String res;


    public ExecuteFileService(long token, String path, String[] args){
    	super();

    	this.token = token;
    	this.path = path;
        this.args = args;
    }
    
    public final String result(){
        return res;
    }

    protected final void dispatch() throws TokenDoesNotExistException, ExpiredTokenException, FileDoesNotExistException, InsufficientPermissionsException{
        
        Login login = Login.getLoginByToken(token);
        File file = md.getFileByPath(path);

        if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(2) == 'x')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(6) == 'x')
            && !(login.getUser().getUsername().equals("root"))){
                throw new InsufficientPermissionsException(file.getName());
            }

        String extension = file.getName().split(".")[1];

        App app = getAppByExtension(extension);
        this.res = app.execute(args);
    
    }
    
}
