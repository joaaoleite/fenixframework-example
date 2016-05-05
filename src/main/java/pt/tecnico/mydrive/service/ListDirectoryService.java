package pt.tecnico.mydrive.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.tecnico.mydrive.service.dto.FileDto;
import pt.tecnico.mydrive.domain.PlainFile;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryService extends MyDriveService{
    MyDrive md = MyDrive.getInstance();

    private List<FileDto> res;
    private long token;


    public ListDirectoryService(long token){
        super();
        this.token = token;      
    }


    public List<FileDto> result(){
        return res; 
    }

    protected final void  dispatch() throws InsufficientPermissionsException,TokenDoesNotExistException, ExpiredTokenException{

        Login login = MyDriveService.getMyDrive().getLoginByToken(token);
        Dir workingDir = login.getWorkingDir();

        if(!(workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(0) == 'r')
        && !(!workingDir.getOwner().equals(login.getUser()) && workingDir.getMask().charAt(4) == 'r')
        && !(login.getUser().getUsername().equals("root")))
        throw new InsufficientPermissionsException(login.getUser().getUsername());
        
        res = new ArrayList<FileDto>();
        
        for(File f : workingDir.getFileSet()){
            if(f.getClass().getSimpleName().equals("Dir"))
                res.add(new FileDto(f.getId(),f.getName(),f.getClass().getSimpleName(),f.getMask(),f.getSize(),f.getOwner().getUsername(),f.getLastModification()));
            else
                res.add(new FileDto(f.getId(),f.getName(),f.getClass().getSimpleName(),f.getMask(),f.getSize(),f.getOwner().getUsername(),f.getLastModification(),((PlainFile)f).getContent()));
        }
        
        res.add(new FileDto(workingDir.getId(),".",workingDir.getClass().getSimpleName(),workingDir.getMask(),workingDir.getSize(),workingDir.getOwner().getUsername(),workingDir.getLastModification()));

        res.add(new FileDto(workingDir.getParent().getId(),"..",workingDir.getParent().getClass().getSimpleName(),workingDir.getParent().getMask(),workingDir.getParent().getSize(),workingDir.getParent().getOwner().getUsername(),workingDir.getParent().getLastModification()));

        Collections.sort(res);
    }
}
