package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;

public class ChangeDirectoryTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        Dir rootdir = mydrive.getRootDir();
        
        mydrive.createUser("Halibio", "halib", "uht");
        mydrive.
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(getUserByUsername("halib"), "test");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti");
    }
    
    @Test
    public void successUserToHisDirRel(){
        final int token = login("halib", "uht");
        final String path = "test";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home/halib/test", newPath);
    }

    @Test
    public void successSuperUserToOtherDirAbs(){
        final int token = login("root", "***");
        final String path = "/home/halib/test";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home/halib/test", newPath);
    }

    @Test
    public void successUserToSuperUserDirRel(){
        final int token = login("zetrigo", "tetetiti");
        final String path = "..";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();
        
        assertNotNull("Directory was not changed", newPath);
        assertEquals("Wrong current directory", "/home", newPath);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidUserToOtherDirAbs(){
        final int token = login("halib", "uht");
        final String path = "/home/zetrigo";
        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();
    }
}
