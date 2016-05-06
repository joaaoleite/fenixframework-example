
package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ExecuteFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uhtuhtuht", "rwxd----");
        User zetrigo = mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createPlainFile(mydrive.getUserByUsername("zetrigo"), "file.exe");
        App app = mydrive.getRootDir().getDir("home").getDir("zetrigo").createApp(mydrive.getUserByUsername("zetrigo"), "app", "pt.tecnico.mydrive.apps.HelloWorld.hello");
        zetrigo.createDefaultApp("exe", app);
        
   }
    
    @Test
    public void successPlainFile(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/file.exe";
        String[] args = new String[2];
        args[0] = "tete";
        args[1] = "titi";
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
        String result = service.result(); 
        
        assertNotNull("No result from service", result);
        assertEquals("Wrong result from service", "Hello tete and titi.", result);
    }

    @Test
    public void successApp(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/app";
        
        String[] args = new String[2];
        args[0] = "tete";
        args[1] = "titi";
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
        String result = service.result(); 
        
        assertNotNull("No result from service", result);
        assertEquals("Wrong result from service", "Hello tete and titi.", result);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void FileDoesNotExist(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/start.exe";
        String[] args = new String[2];
        args[0] = "a";
        args[1] = "b";
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void tokenInsufficientPermissions(){
        final long token = login("halib", "uhtuhtuht");
        final String path = "/home/zetrigo/file.exe";
        String[] args = new String[2];
        args[0] = "a";
        args[1] = "b";
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String path = "/home/zetrigo/file.exe";
        String[] args = new String[2];
        args[0] = "a";
        args[1] = "b";
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }
}


