package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ExecuteFileService extends MyDriveService{

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

    protected final void dispatch() throws NoAppforExtensionException,FileDoesNotHaveExtension, TokenDoesNotExistException, ExpiredTokenException, FileDoesNotExistException, InsufficientPermissionsException{
        
        Login login = getMyDrive().getLoginByToken(token);
        File file = getMyDrive().getFileByPath(path);

        if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(2) == 'x')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(6) == 'x')
            && !(login.getUser().getUsername().equals("root"))){
            
            throw new InsufficientPermissionsException(file.getName());
        }

        App app; 
        String extension;
        
        try{
            if(file instanceof App){
                app = (App) file;
            }else{
                extension = file.getName().substring(file.getName().lastIndexOf(".")+1);
                app = login.getUser().getAppByExtension(extension);
            }
        }catch(Exception e){
            throw new FileDoesNotHaveExtension(file.getName());
        } 
        this.res = app.execute(args).toString();
    }
    
}
