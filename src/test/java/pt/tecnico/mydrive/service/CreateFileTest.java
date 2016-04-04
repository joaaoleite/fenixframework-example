package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import pt.tecnico.mydrive.domain.*;

public class CreateFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User luis = mydrive.createUser("luis", "luisinho","luisinho","rwxd----");
        User ze = mydrive.createUser("ze", "zezinho","zezinho","rwxd----");
        
        Dir roodir = mydrive.getRootDir();
        
        Link link = rootdir.getDir("home").getDir("luis").createLink(luis,"link","rwxd----");
        App app = rootdir.getDir("home").getDir("luis").createApp(luis,"app","rwxd----");
        Dir dir = rootdir.getDir("home").getDir("luis").createDir(luis,"NewDir","rwxd----");
        PlainFile plain = rootdir.getDir("home").getDir("luis").createPlainFile(luis,"new.txt","rwxd----"); 

   }

    @Test
    public void successCreateLinkFile(){
        final int token = login("luisinho", "luisinho");
        final String type = "link";
        final String filename = "link";
        final String content = "";

        CreateFileService service = new CreateFileService(token, filename, type , content);
        File file = service.execute();
        
        assertNotNull("LinkFile not created", file);
        assertEquals("No match in filenameame", "link", file.getFilename());
        assertEquals("No match in type", "link", file.getType());
    }

    @Test
    public void successCreateAppFile(){
        final int token = login("luisinho", "luisinho");
        final String type = "App"; 
        final String filename = "link";
        final String content = "";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        File file = service.execute();
        
        assertNotNull("AppFile not created", file);
        assertEquals("No match in filename", "app", file.getFilename());
        assertEquals("No match in type", "app", file.getType());
    }

    @Test
    public void successCreateDirFile(){
        final int token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "link";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        File file = service.execute();
        
        assertNotNull("DirFile not created", file);
        assertEquals("No match in filename", "NewDir", file.getFilename());
        assertEquals("No match in type", "Dir", file.isDir());
    }

    @Test
    public void successCreatePlainFile(){
        final int token = login("luisinho", "luisinho");
        final String type = "Plain"; 
        final String filename = "new.txt";
        final String content = "Projecto de ES";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        File file = service.execute();
        
        assertNotNull("AppFile not created", file);
        assertEquals("No match in filename", "new.txt", file.getFilename());
        assertEquals("No match in type", "Plain", file.getType());
    }

    @Test(expected = FileDoesNotExistException.class)
    public void cantFindFile(){
        final int token = login("luisinho", "luisinho");
        final String filename = "ze.txt";
        
        CreateFileService service = new CreateFileService(token, filename);
        service.execute();
    }

    @Test(expected = FileIsADirException.class)
    public void DirWithoutContent(){
        final int token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "NewDir";
        final String content = "Directory with content";

        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();

    }

    @Test(expected = ExpiredTokenException.class)
    public void tokenExpired(){
        final int token = 234257263578354;
        final String filename = "link";

        CreateFileService service = new CreateFileService(token, filename);
        service.execute();

    }

    @Test(expected = InsufficientPermissionsException.class)
    public void FileInAnotherUserDir(){
        final int token = login("luisinho", "luisinho");
        final String path = "/home/ze";
        final String type = "Dir"; 
        final String filename = "NewDir";
        final String content = "Directory with content";

        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        String newPath = service.execute();

        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();

    } 
}
