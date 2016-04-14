package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class DeleteFileService extends MyDriveService {

    private long token;
    private String fileName;

    public DeleteFileService(long token, String fileName) {
        this.token = token;
        this.fileName = fileName;
    }

    @Override
    public final void dispatch() throws MyDriveException, ExpiredTokenException, FileDoesNotExistException, InsufficientPermissionsException {
        
        Login login = Login.getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();
    	File f = workingDir.getFileByName(fileName);

        if(!(f.getOwner().equals(login.getUser()) && f.getMask().charAt(1) == 'w')
            && !(!f.getOwner().equals(login.getUser()) && f.getMask().charAt(5) == 'w')
            && !(login.getUser().getUsername().equals("root")))
            throw new InsufficientPermissionsException(fileName);

		else{
			if (f == null){
				throw new FileDoesNotExistException(fileName);
			}
			else{
				if (f.isDir() == true){
					f.removeR();
				}
				else{
					f.remove();
				}
			}
		}
	}
}
