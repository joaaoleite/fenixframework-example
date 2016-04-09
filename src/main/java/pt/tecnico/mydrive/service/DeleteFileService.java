package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class DeleteFileService extends MyDriveService {

    private Sting token;
    private String fileName;

    public DeleteContactService(String token, String fileName) {
        this.token = token;
        this.fileName = fileName;
    }

    @Override
    public final void dispatch() throws MyDriveException, ExpiredTokenException, FileDoesNotExistException {

    	File f = mydrive.getLogin().getWorkingDir(token).getFileByFileName(fileName);
		if (f == null){
			throw new FileDoesNotExistException();
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
