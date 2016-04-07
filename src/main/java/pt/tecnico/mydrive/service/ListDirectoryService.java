package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryService extends MyDriveService{
	MyDrive md = MyDrive.getInstance();

    private Dir _workingDir;
    private Login _login;
    private String _path;


    public ListDirectoryService(token){
    	super();

    	//criar login, verificar token e tal
    	_token = token;
    }

    private File dispatch() throws Exception{
        String output = (type()+" "+getMask()+" "+getSize()+" "+getOwner().getUsername()+" "+getId()+" "+getLastModification()+" "+getName()+"\n"+getParent().type()+" "+getParent().getMask()+" "+getParent().getSize()+" "+getParent().getOwner().getUsername()+" "+getParent().getId()+" "+getParent().getLastModification()+" "+getParent().getName()+"\n");
            for (File file: getFileSet()){
                if (file.isLink()){
                    output += (file.getName()+"->"+file.getContent());
                }
                else
                    output += (file.type()+" "+file.getMask()+" "+file.getSize()+" "+file.getOwner().getUsername()+" "+file.getId()+" "+file.getLastModification()+" "+file.getName()+"\n");
            }
        return output;
    }
}
