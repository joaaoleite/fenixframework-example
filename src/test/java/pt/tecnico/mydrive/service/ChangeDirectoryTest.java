package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ChangeDirectoryTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uhtuhtuht", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "test");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createPlainFile(mydrive.getUserByUsername("zetrigo"), "loans");
   }
    
    @Test
    public void successUserToHisDirRel(){
        final long token = login("halib", "uhtuhtuht");
        final String path = "test";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        String newPath = service.result(); 
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home/halib/test", newPath);
    }

    @Test
    public void changeToThisFolder(){
        final long token = login("halib", "uhtuhtuht");
        final String path = ".";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        String newPath = service.result(); 
        
        assertEquals("Wrong current directory", "/home/halib", newPath);
    }

    @Test
    public void successSuperUserToOtherDirAbs(){
        final long token = login("root", "***");
        final String path = "/home/halib/test";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        String newPath = service.result(); 
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home/halib/test", newPath);
    }

    @Test
    public void successUserToSuperUserDirRel(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "..";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        String newPath = service.result(); 
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home", newPath);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidUserToOtherDirAbs(){
        final long token = login("halib", "uhtuhtuht");
        final String path = "/home/zetrigo";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void invalidPathRel(){
        final long token = login("halib", "uhtuhtuht");
        final String path = "photos";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }
    
    @Test(expected = FileIsAPlainFileException.class)
    public void invalidPathPointsToPlainFileAbs(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/loans";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }
}
