package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class LoginServiceTest extends AbstractServiceTest{
    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        Dir rootdir = mydrive.getRootDir();
        mydrive.createUser("John", "john", "johnmilk","rwxd----");
    }
    
    @Test
    public void successLogin(){
        LoginService service = new LoginService("john","johnmilk");

        service.execute();
        long token = service.result();
        
        assertNotNull("Login failed", token);
    }

    @Test(expected = LoginFailedException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService("alice","123123123");

        service.execute();
    }

    @Test(expected = LoginFailedException.class)
    public void userDoesNotExists1(){
        LoginService service = new LoginService("john","dontknowmypassword");

        service.execute();
    }

    @Test(expected = LoginFailedException.class)
    public void userDoesNotExists2(){
        LoginService service = new LoginService(null,null);
        service.execute();
    }

}
