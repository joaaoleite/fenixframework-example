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
    protected void dispatch() throws  LoginFailedException{
        Login login = Login.signIn(user,pass);
        token = login.getToken();
    }

    public long getToken(){
        super.execute();
        return token;
    }
}



