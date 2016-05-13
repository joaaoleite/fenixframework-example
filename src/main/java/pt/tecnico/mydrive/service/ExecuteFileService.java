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


    public ExecuteFileService(long token, String path, String[] args){
    	super();

    	this.token = token;
    	this.path = path;
        this.args = args;
    }

    private String extension(String exe){
        return "";
    }


    @Override
    protected final void dispatch() throws NoAppforExtensionException,FileDoesNotHaveExtension, TokenDoesNotExistException, ExpiredTokenException, FileDoesNotExistException, InsufficientPermissionsException{
        
        Login login = getMyDrive().getLoginByToken(token);
        String[] parts = path.split("/");

        if(parts[parts.length - 1].contains(".")){
            String name = parts[parts.length-1];
            path = extension(name.substring(name.lastIndexOf(".")+1));    
        }

        File file = getMyDrive().getFileByPath(path);
        
        System.out.println("\n file.getOwner... \n");

        if(!(file.getOwner().equals(login.getUser()) && file.getMask().charAt(2) == 'x')
            && !(!file.getOwner().equals(login.getUser()) && file.getMask().charAt(6) == 'x')
            && !(login.getUser().getUsername().equals("root"))){
            
            throw new InsufficientPermissionsException(file.getName());
        }
        
        resolve(file);
    }

   private void resolve(File file){
      try{ 
        String[] content;
        if(file instanceof App){
            pt.tecnico.mydrive.presentation.Shell.run(((App)(file)).read(), this.args);
        }else if(file instanceof Dir){
            throw new FileIsADirException(file.getName());
        }else if(file instanceof Link){
            file = ((Link)(file)).findFile(this.token);
            resolve(file);
        }else if(file instanceof PlainFile){
            content = ((PlainFile)(file)).read().split("\\r?\\n");
            for(String line : content){
                pt.tecnico.mydrive.presentation.Shell.run(line, this.args);
            }       
        }else{
            throw new InvalidFileTypeException(file.getName());
        }
        }
        catch(Exception e){}
    }
    
}
