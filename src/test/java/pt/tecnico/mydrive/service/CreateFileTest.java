package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class CreateFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User luis = mydrive.createUser("luis", "luisinho","luisinho","rwxd----");
        User ze = mydrive.createUser("ze", "zezinho","zezinho","rwxd----");
        
        Dir rootdir = mydrive.getRootDir();
        
        rootdir.getDir("home").getDir("luis").createLink(luis,"link","rwxd----");
        rootdir.getDir("home").getDir("luis").createApp(luis,"app","rwxd----");
        rootdir.getDir("home").getDir("luis").createDir(luis,"NewDir","rwxd----");
        rootdir.getDir("home").getDir("luis").createPlainFile(luis,"new.txt","rwxd----"); 

   }

    @Test
    public void successCreateLinkFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "link";
        final String filename = "link";
        final String content = "";

        CreateFileService service = new CreateFileService(token, filename, type , content);
        service.execute();
        
        assertNotNull("LinkFile not created", rootdir.getDir("home").getDir("luis").getFileByName("link"));
        assertEquals("Match in filename?", "link", rootdir.getDir("home").getDir("luis").getFileByName("link"));
        assertEquals("Match in type?", "link", rootdir.getDir("home").getDir("luis").getFileByName("link").getClass().getName());
    }

    @Test
    public void successCreateAppFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "App"; 
        final String filename = "app";
        final String content = "";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("AppFile not created", rootdir.getDir("home").getDir("luis").getFileByName("app"));
        assertEquals("Match in filename?", "app", rootdir.getDir("home").getDir("luis").getFileByName("app"));
        assertEquals("Match in type?", "App", rootdir.getDir("home").getDir("luis").getFileByName("app").getClass().getName());
    }

    @Test
    public void successCreateDirFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "newDir";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        service.execute();
        
        assertNotNull("DirFile not created", rootdir.getDir("home").getDir("luis").getDir("newDir"));
        assertEquals("Match in filename?", "NewDir", rootdir.getDir("home").getDir("luis").getDir("newDir"));
        assertEquals("Match in type?", "Dir", rootdir.getDir("home").getDir("luis").getDir("newDir").isDir());
    }

    @Test
    public void successCreatePlainFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Plain"; 
        final String filename = "new.txt";
        final String content = "Projecto de ES";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("PlainFile not created", rootdir.getDir("home").getDir("luis").getFileByName("new.txt"));
        assertEquals("Match in filename?", "new.txt", rootdir.getDir("home").getDir("luis").getFileByName("new.txt"));
        assertEquals("Match in type?", "Plain", rootdir.getDir("home").getDir("luis").getFileByName("new.txt").getClass().getName());
        assertEquals("Match in content?" , "Projecto de ES" , rootdir.getDir("home").getDir("luis").getFileByName("new.txt").getConten());
    }

    @Test(expected = FileDoesNotExistException.class)
    public void cantFindFile(){
        final long token = login("luisinho", "luisinho");
        final String filename = "ze.txt";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        service.execute();
    }

    @Test(expected = FileIsADirException.class)
    public void DirWithoutContent(){
        final long token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "NewDir";
        final String content = "Directory with content";

        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();

    }

    @Test(expected = ExpiredTokenException.class)
    public void tokenExpired(){
        final long token = 234257263;
        final String filename = "link";

        CreateFileService service = new CreateFileService(token, filename, type);
        service.execute();

    }

    @Test(expected = InsufficientPermissionsException.class)
    public void FileInAnotherUserDir(){
        final long token = login("luisinho", "luisinho");
        final String path = "/home/ze";
        final String type = "Dir"; 
        final String filename = "NewDir";
        final String content = "Directory with content";

        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();

        CreateFileService service2 = new CreateFileService(token, filename, type, content);
        service2.execute();

    } 
}
