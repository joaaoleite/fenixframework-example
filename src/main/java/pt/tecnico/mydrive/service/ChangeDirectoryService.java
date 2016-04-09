package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ChangeDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private Dir _workingDir;
    private Login _login;
    private String _path;


    public ChangeDirectoryService(long token, String path){
    	super();

    	_login = Login.getLoginByToken(token);
    	_path = path;

    }
    
    public String result{
        return _workingDir.getPath();
    }

    protected void dispatch() throws FileIsAPlainFileException, FileDoesNotExistException, InsufficientPermissionsException{
        String[] path = _path.split("/");

        Dir actual;
        int i;

        if(_path.charAt(0) == '/'){

            actual = md.getRootDir();
            i = 1;
        }
        else{
            actual = _login.getWorkingDir();
            i = 0;
        }

        for(; i<path.length-1; i++){
            actual = actual.getDir(path[i]);
        }

        if(actual.exists(path[i++])){
        	if (actual.getFileByName(path[i]).isDir()){
        		_workingDir = actual.getDir(path[i]);

                if(!(_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getMask().charAt(0) == 'r')
                && !(!_workingDir.getOwner().equals(_login.getUser()) && _workingDir.getMask().charAt(4) == 'r')
                && !(_login.getUser().getUsername().equals("root")))
                throw new InsufficientPermissionsException(_path);

        		_login.setWorkingDir(_workingDir);
        	}
        	else
            	throw new FileIsAPlainFileException(path[i]);
        }

        throw new FileDoesNotExistException(path[i]);
    }
}
