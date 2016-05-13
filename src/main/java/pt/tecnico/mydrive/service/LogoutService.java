package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class LogoutService extends MyDriveService{
    private long token;
    
    public LogoutService(long token){
        this.token= token;
      
    }



    public final void dispatch() throws  TokenDoesNotExistException{
        
        Login login = MyDriveService.getMyDrive().getLoginByToken(token);
        login.remove();
    }

    
}



