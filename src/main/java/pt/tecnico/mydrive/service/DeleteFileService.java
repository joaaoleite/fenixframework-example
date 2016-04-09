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
    public final void dispatch() throws MyDriveException, ExpiredTokenException, FileDoesNotExistException {

    File f = Login.getLoginByToken(token).getWorkingDir().getFileByName(fileName);
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
