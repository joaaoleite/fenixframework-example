package pt.tecnico.mydrive.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class LoginService extends MyDriveService{
    private final String user;
    private final String pass;
    private long token;
    public LoginService(String username, String password){
        user= username;
        pass=password;
    }
    public final void dispatch() throws  LoginFailedException{
        Login login = MyDriveService.getMyDrive().signIn(user,pass);
        token = login.getToken();
        login.init();
    }

    public final long result(){
        return token;
    }
}



