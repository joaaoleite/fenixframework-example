package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class ListDirectoryServiceTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uht");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "test");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti","rwxd----");
   }
    
    @Test
    public void successUserOnHisDir(){
        final long token = login("halib", "uht");
        ListDirectoryService service = new ListDirectoryService(token);
        String[] list = service.execute();
        service.result().split("\\r?\\n");
       
        assertNotNull("Directory was not listed", list);
       
        String[] line1 = list[0].split(" ");
        String[] line2 = list[1].split(" ");
        String[] line3 = list[2].split(" ");

        assertEquals("Wrong directory list", "Dir", line1[0]);
        assertEquals("Wrong directory list", "rwxd----", line1[1]);
        assertEquals("Wrong directory list", "3", line1[2]);
        assertEquals("Wrong directory list", "halib", line1[3]);
        assertEquals("Wrong directory list", "3", line1[4]);
        assertEquals("Wrong directory list", ".", line1[5]);

        assertEquals("Wrong directory list", "Dir", line2[0]);
        assertEquals("Wrong directory list", "rwxdr-x-", line2[1]);
        assertEquals("Wrong directory list", "5", line2[2]);
        assertEquals("Wrong directory list", "root", line2[3]);
        assertEquals("Wrong directory list", "1", line2[4]);
        assertEquals("Wrong directory list", "..", line2[5]);

        assertEquals("Wrong directory list", "Dir", line3[0]);
        assertEquals("Wrong directory list", "rwxd----", line3[1]);
        assertEquals("Wrong directory list", "2", line3[2]);
        assertEquals("Wrong directory list", "halib", line3[3]);
        assertEquals("Wrong directory list", "4", line3[4]);
        assertEquals("Wrong directory list", "test", line3[5]);
    }
    
    @Test
    public void successUserOnSuperUserDir(){
        final long token = login("halib", "uht");
        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/root");
        cd.execute();
        ListDirectoryService service = new ListDirectoryService(token);
        String[] list = service.execute().split("\\r?\\n");
       
        assertNotNull("Directory was not listed", list);
       
        String[] line1 = list[0].split(" ");
        String[] line2 = list[1].split(" ");

        assertEquals("Wrong directory list", "Dir", line1[0]);
        assertEquals("Wrong directory list", "rwxdr-x-", line1[1]);
        assertEquals("Wrong directory list", "2", line1[2]);
        assertEquals("Wrong directory list", "root", line1[3]);
        assertEquals("Wrong directory list", "2", line1[4]);
        assertEquals("Wrong directory list", ".", line1[5]);
        
        assertEquals("Wrong directory list", "Dir", line2[0]);
        assertEquals("Wrong directory list", "rwxdr-x-", line2[1]);
        assertEquals("Wrong directory list", "5", line2[2]);
        assertEquals("Wrong directory list", "root", line2[3]);
        assertEquals("Wrong directory list", "1", line2[4]);
        assertEquals("Wrong directory list", "..", line2[5]);
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidUserOnOtherUserDir(){
        final long token = login("zetrigo", "tetetiti");
        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/halib");
        cd.execute();
        ListDirectoryService service = new ListDirectoryService(token);
        String[] list = service.execute().split("\\r?\\n");
    }
}
