package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class LoginServiceTest extends AbstractServiceTest{
    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        Dir rootdir = mydrive.getRootDir();
        mydrive.createUser("John", "john", "john","rwxd----");
    }
    
    @Test
    public void successLogin(){
        LoginService service = new LoginService("john","uht");

        service.execute();
        long token = service.result();
        
        assertNotNull("Login failed", token);
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService("alice","123");

        service.execute();
    }

    @Test(expected = InvalidPasswordException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService("john","dontknowmypassword");

        service.execute();
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService(null,null);
        service.execute();
    }

}
