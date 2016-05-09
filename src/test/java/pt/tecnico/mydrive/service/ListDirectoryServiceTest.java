package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import pt.tecnico.mydrive.service.dto.FileDto;

public class ListDirectoryServiceTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        
        mydrive.createUser("Halibio", "halib", "uhtuhtuht","rwxd----");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti","rwxd----");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "test");
        mydrive.getRootDir().getDir("home").getDir("halib").createPlainFile(mydrive.getUserByUsername("halib"), "loans");
        mydrive.getRootDir().getDir("home").getDir("halib").createDir(mydrive.getUserByUsername("halib"), "photos");

        mydrive.getRootDir().createDir(mydrive.getSuperUser(),"test");
        mydrive.getRootDir().getDir("test").createPlainFile(mydrive.getSuperUser(), "doc.txt");
   }
    
    @Test
    public void successUserOnHisDir(){
        final long token = login("halib", "uhtuhtuht");
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        ArrayList<FileDto> list = (ArrayList)service.result();
       
        assertNotNull("Directory was not listed", list);
        assertEquals("Directory was not listed", 5, list.size());

        assertEquals("Wrong directory list 1", "Dir", list.get(0).getType());
        assertEquals("Wrong directory list 1", "rwxd----", list.get(0).getPerm());
        assertEquals("Wrong directory list 1", 5, list.get(0).getSize());
        assertEquals("Wrong directory list 1", "halib", list.get(0).getOwner());
        assertEquals("Wrong directory list 1", 4, list.get(0).getId());
        assertEquals("Wrong directory list 1", ".", list.get(0).getName());

        assertEquals("Wrong directory list 2", "Dir", list.get(1).getType());
        assertEquals("Wrong directory list 2", "rwxdr-x-", list.get(1).getPerm());
        assertEquals("Wrong directory list 2", 6, list.get(1).getSize());
        assertEquals("Wrong directory list 2", "root", list.get(1).getOwner());
        assertEquals("Wrong directory list 2", 1, list.get(1).getId());
        assertEquals("Wrong directory list 2", "..", list.get(1).getName());
        
        assertEquals("Wrong directory list 3", "PlainFile", list.get(2).getType());
        assertEquals("Wrong directory list 3", "rwxd----", list.get(2).getPerm());
        assertEquals("Wrong directory list 3", 0, list.get(2).getSize());
        assertEquals("Wrong directory list 3", "halib", list.get(2).getOwner());
        assertEquals("Wrong directory list 3", 7, list.get(2).getId());
        assertEquals("Wrong directory list 3", "loans", list.get(2).getName());

        assertEquals("Wrong directory list 4", "Dir", list.get(3).getType());
        assertEquals("Wrong directory list 4", "rwxd----", list.get(3).getPerm());
        assertEquals("Wrong directory list 4", 2, list.get(3).getSize());
        assertEquals("Wrong directory list 4", "halib", list.get(3).getOwner());
        assertEquals("Wrong directory list 4", 8, list.get(3).getId()); 
        assertEquals("Wrong directory list 4", "photos", list.get(3).getName());

        assertEquals("Wrong directory list 5", "Dir", list.get(4).getType());
        assertEquals("Wrong directory list 5", "rwxd----", list.get(4).getPerm());
        assertEquals("Wrong directory list 5", 2, list.get(4).getSize());
        assertEquals("Wrong directory list 5", "halib", list.get(4).getOwner());
        assertEquals("Wrong directory list 5", 6, list.get(4).getId());
        assertEquals("Wrong directory list 5", "test", list.get(4).getName());
    }
    
    @Test
    public void successUserOnSuperUserDir(){
        final long token = login("halib", "uhtuhtuht");
        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/root");
        cd.execute();
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
        ArrayList<FileDto> list = (ArrayList) service.result();
       
        assertNotNull("Directory was not listed", list);
        assertEquals("Directory was not listed", 2, list.size());

        assertEquals("Wrong directory list", "Dir", list.get(0).getType());
        assertEquals("Wrong directory list", "rwxdr-x-", list.get(0).getPerm());
        assertEquals("Wrong directory list", 2, list.get(0).getSize());
        assertEquals("Wrong directory list", "root", list.get(0).getOwner());
        assertEquals("Wrong directory list", 2, list.get(0).getId());
        assertEquals("Wrong directory list", ".", list.get(0).getName());
        
        assertEquals("Wrong directory list", "Dir", list.get(1).getType());
        assertEquals("Wrong directory list", "rwxdr-x-", list.get(1).getPerm());
        assertEquals("Wrong directory list", 6, list.get(1).getSize());
        assertEquals("Wrong directory list", "root", list.get(1).getOwner());
        assertEquals("Wrong directory list", 1, list.get(1).getId());
        assertEquals("Wrong directory list", "..", list.get(1).getName());
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidUserOnOtherUserDir(){
        final long token = login("zetrigo", "tetetiti");
        
        MyDrive.getInstance().getRootDir().getDir("home").getDir("halib").setMask("rwxdr-x-");

        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/home/halib");
        cd.execute();
        
        MyDrive.getInstance().getRootDir().getDir("home").getDir("halib").setMask("rwxd----");

        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void invalidListOnPrivateFolder(){
        final long token = login("zetrigo", "tetetiti");

        ChangeDirectoryService cd = new ChangeDirectoryService(token, "/test");
        cd.execute();
        
        MyDrive.getInstance().getRootDir().getDir("test").setMask("rwxd----");

        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String filename = "teste";
        
        ListDirectoryService service = new ListDirectoryService(token);
        service.execute();

    }
}