package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ChangeDirectoryTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uht");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(getUserByUsername("halib"), "test");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createPlainFile(getUserByUsername("zetrigo"), "loans");
   }
    
    @Test
    public void successUserToHisDirRel(){
        final long token = login("halib", "uht");
        final String path = "test";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        String newPath = service.result(); 
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home/halib/test", newPath);
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
        final long token = login("halib", "uht");
        final String path = "/home/zetrigo";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }

    @Test(expected = FileDoesNotExistsException.class)
    public void invalidPathRel(){
        final int token = login("halib", "uht");
        final String path = "photos";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }
    
    @Test(expected = FileIsAPlainFileException.class)
    public void invalidPathPointsToPlainFileAbs(){
        final int token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/loans";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
    }
}
