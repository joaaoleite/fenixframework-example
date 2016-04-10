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
        
        rootdir.getDir("home").getDir("luis").createLink(luis,"link","/home/zezinho");
        rootdir.getDir("home").getDir("luis").createApp(luis,"app");
        rootdir.getDir("home").getDir("luis").createDir(luis,"NewDir");
        rootdir.getDir("home").getDir("luis").createPlainFile(luis,"new.txt"); 

   }

    @Test
    public void successCreateLinkFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "link";
        final String filename = "link";
        final String content = "";

        CreateFileService service = new CreateFileService(token, filename, type , content);
        service.execute();
        
        assertNotNull("LinkFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename));
        assertEquals("Match in filename?", "link", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename));
        assertEquals("Match in type?", "Link", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(type).getClass().getName());
        assertEquals("Match in Content", "/home/zezinho", ((Link)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename))).getContent());

    }

    @Test
    public void successCreateAppFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "App"; 
        final String filename = "app";
        final String content = "";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("AppFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName("app"));
        assertEquals("Match in filename?", "app", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName("app"));
        assertEquals("Match in type?", "App", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName("app").getClass().getName());
        assertEquals("Match in Content", "", ((App)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename))).getContent());

    }

    @Test
    public void successCreateDirFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "dir"; 
        final String filename = "newDir";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        service.execute();
        
        assertNotNull("DirFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getDir(filename));
        assertEquals("Match in filename?", "NewDir", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getDir(filename));
        assertEquals("Match in type?", "Dir", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getDir(filename).getClass().getName());
    }

    @Test
    public void successCreatePlainFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "plain"; 
        final String filename = "new.txt";
        final String content = "Projecto de ES";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("PlainFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename));
        assertEquals("Match in filename?", "new.txt", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename));
        assertEquals("Match in type?", "plain", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename).getClass().getName());
        assertEquals("Match in content?" , "Projecto de ES" , ((PlainFile)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luis").getFileByName(filename))).getContent());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void cantFindFile(){
        final long token = login("luisinho", "luisinho");
        final String filename = "new.txt";
        
        CreateFileService service = new CreateFileService(token, filename, "PlainFile");
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

        CreateFileService service = new CreateFileService(token, filename, "PlainFile");
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
        service.execute();

        CreateFileService service2 = new CreateFileService(token, filename, type, content);
        service2.execute();

    } 
}
