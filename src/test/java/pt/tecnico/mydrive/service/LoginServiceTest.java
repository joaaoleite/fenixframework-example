package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;

public class LoginServiceTest extends AbstractServiceTest{
    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        Dir rootdir = mydrive.getRootDir();
        mydrive.createUser("Halibio", "halib", "uht");
    }
    
    @Test
    public void successLogin(){
        LoginService service = new LoginService();

        service.login("halib","uht");
        int token = service.execute();
        
        assertNotNull("Login failed", token);
    }

    @Test(expected = UserDoesNotExistsException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService();

        service.login("eunaoexisto","123");
        service.execute();
    }

    @Test(expected = InvalidPasswordException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService();

        service.login("halib","esquecimedapassword");
        service.execute();
    }

    @Test(expected = IUserDoesNotExistsException.class)
    public void userDoesNotExists(){
        LoginService service = new LoginService();

        service.login(null,null);
        service.execute();
    }

}
