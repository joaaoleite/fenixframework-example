package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryServiceTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uht","rwxd----");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti","rwxd----");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "test");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "photos");
        mydrive.getRootDir().getDir("home").getDir("halib").createPlainFile(mydrive.getUserByUsername("halib"), "loans");
   }
    
    @Test
    public void successUserOnHisDir(){
        final long token = login("halib", "uht");
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        String[] list = service.result().split("\\r?\\n");
       
        assertNotNull("Directory was not listed", list);
       
        String[] line1 = list[0].split(" ");
        String[] line2 = list[1].split(" ");
        String[] line3 = list[2].split(" ");
        String[] line4 = list[3].split(" ");
        String[] line5 = list[4].split(" ");

        assertEquals("Wrong directory list 1", "Dir", line1[0]);
        assertEquals("Wrong directory list 1", "rwxd----", line1[1]);
        assertEquals("Wrong directory list 1", "5", line1[2]);
        assertEquals("Wrong directory list 1", "halib", line1[3]);
        assertEquals("Wrong directory list 1", "3", line1[4]);
        assertEquals("Wrong directory list 1", ".", line1[6]);

        assertEquals("Wrong directory list 2", "Dir", line2[0]);
        assertEquals("Wrong directory list 2", "rwxdr-x-", line2[1]);
        assertEquals("Wrong directory list 2", "5", line2[2]);
        assertEquals("Wrong directory list 2", "root", line2[3]);
        assertEquals("Wrong directory list 2", "1", line2[4]);
        assertEquals("Wrong directory list 2", "..", line2[6]);
        
        assertEquals("Wrong directory list 3", "PlainFile", line3[0]);
        assertEquals("Wrong directory list 3", "rwxd----", line3[1]);
        assertEquals("Wrong directory list 3", "0", line3[2]);
        assertEquals("Wrong directory list 3", "halib", line3[3]);
        assertEquals("Wrong directory list 3", "7", line3[4]);
        assertEquals("Wrong directory list 3", "loans", line3[6]);

        assertEquals("Wrong directory list 4", "Dir", line4[0]);
        assertEquals("Wrong directory list 4", "rwxd----", line4[1]);
        assertEquals("Wrong directory list 4", "2", line4[2]);
        assertEquals("Wrong directory list 4", "halib", line4[3]);
        assertEquals("Wrong directory list 4", "6", line4[4]); 
        assertEquals("Wrong directory list 4", "photos", line4[6]);

        assertEquals("Wrong directory list 5", "Dir", line5[0]);
        assertEquals("Wrong directory list 5", "rwxd----", line5[1]);
        assertEquals("Wrong directory list 5", "2", line5[2]);
        assertEquals("Wrong directory list 5", "halib", line5[3]);
        assertEquals("Wrong directory list 5", "5", line5[4]);
        assertEquals("Wrong directory list 5", "test", line5[6]);
    }
    
    @Test
    public void successUserOnSuperUserDir(){
        final long token = login("halib", "uht");
        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/root");
        cd.execute();
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        String[] list = service.result().split("\\r?\\n");
       
        assertNotNull("Directory was not listed", list);
       
        String[] line1 = list[0].split(" ");
        String[] line2 = list[1].split(" ");

        assertEquals("Wrong directory list", "Dir", line1[0]);
        assertEquals("Wrong directory list", "rwxdr-x-", line1[1]);
        assertEquals("Wrong directory list", "2", line1[2]);
        assertEquals("Wrong directory list", "root", line1[3]);
        assertEquals("Wrong directory list", "2", line1[4]);
        assertEquals("Wrong directory list", ".", line1[6]);
        
        assertEquals("Wrong directory list", "Dir", line2[0]);
        assertEquals("Wrong directory list", "rwxdr-x-", line2[1]);
        assertEquals("Wrong directory list", "5", line2[2]);
        assertEquals("Wrong directory list", "root", line2[3]);
        assertEquals("Wrong directory list", "1", line2[4]);
        assertEquals("Wrong directory list", "..", line2[6]);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidUserOnOtherUserDir(){
        final long token = login("zetrigo", "tetetiti");
        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/halib");
        cd.execute();
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
    }
}
