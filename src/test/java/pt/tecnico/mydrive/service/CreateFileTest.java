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
        
        Link link = rootdir.getDir("home").getDir("luis").createLink(luis,"link","rwxd----");
        App app = rootdir.getDir("home").getDir("luis").createApp(luis,"app","rwxd----");
        rootdir.getDir("home").getDir("luis").createDir(luis,"NewDir","rwxd----");
        PlainFile plain = rootdir.getDir("home").getDir("luis").createPlainFile(luis,"new.txt","rwxd----"); 

   }

    @Test
    public void successCreateLinkFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "link";
        final String filename = "link";
        final String content = "";

        CreateFileService service = new CreateFileService(token, filename, type , content);
        service.execute();
        File file = service.result(); 
        
        assertNotNull("LinkFile not created", file);
        assertEquals("No match in filenameame", "link", file.getFileName());
        assertEquals("No match in type", "link", file.getType());
    }

    @Test
    public void successCreateAppFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "App"; 
        final String filename = "link";
        final String content = "";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        File file = service.execute();
        
        assertNotNull("AppFile not created", file);
        assertEquals("No match in filename", "app", file.getFileName());
        assertEquals("No match in type", "App", getClass().getName());
    }

    @Test
    public void successCreateDirFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "link";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        File file = service.execute();
        
        assertNotNull("DirFile not created", file);
        assertEquals("No match in filename", "NewDir", file.getFileName());
        assertEquals("No match in type", "Dir", file.isDir());
    }

    @Test
    public void successCreatePlainFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Plain"; 
        final String filename = "new.txt";
        final String content = "Projecto de ES";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        File file = service.execute();
        
        assertNotNull("AppFile not created", file);
        assertEquals("No match in filename", "new.txt", file.getFileName());
        assertEquals("No match in type", "Plain", file.getType());
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
