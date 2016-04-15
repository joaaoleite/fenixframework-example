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
        
        rootdir.getDir("home").getDir("luisinho").createLink(luis,"link","/home/zezinho");
        rootdir.getDir("home").getDir("luisinho").createApp(luis,"app");
        rootdir.getDir("home").getDir("luisinho").createDir(luis,"NewDir");
        rootdir.getDir("home").getDir("luisinho").createPlainFile(luis,"new.txt"); 

   }

    @Test
    public void successCreateLinkFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Link";
        final String filename = "link2";
        final String content = "/";

        CreateFileService service = new CreateFileService(token, filename, type , content);
        service.execute();
        
        assertNotNull("LinkFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename));
        assertEquals("Match in filename?", "link2", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getName());
        assertEquals("Match in type?", "pt.tecnico.mydrive.domain.Link", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getClass().getName());
        assertEquals("Match in Content", "/", ((Link)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename))).getContent());

    }

    @Test
    public void successCreateAppFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "App"; 
        final String filename = "app2";
        final String content = "";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("AppFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename));
        assertEquals("Match in filename?", "app2", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getName());
        assertEquals("Match in type?", "pt.tecnico.mydrive.domain.App", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getClass().getName());
        assertEquals("Match in Content", "", ((App)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename))).read());

    }

    @Test
    public void successCreateDirFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "newDir2";
        
        CreateFileService service = new CreateFileService(token, filename, type);
        service.execute();
        
        assertNotNull("DirFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getDir(filename));
        assertEquals("Match in filename?", "newDir2", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getDir(filename).getName());
        assertEquals("Match in type?","pt.tecnico.mydrive.domain.Dir" , MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getDir(filename).getClass().getName());
    }

    @Test
    public void successCreatePlainFile(){
        final long token = login("luisinho", "luisinho");
        final String type = "Plain";
        final String filename = "new2.txt";
        final String content = "Projecto de ES";
        
        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();
        
        assertNotNull("PlainFile not created", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename));
        assertEquals("Match in filename?", "new2.txt", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getName());
        assertEquals("Match in type?", "pt.tecnico.mydrive.domain.PlainFile", MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename).getClass().getName());
        assertEquals("Match in content?" , "Projecto de ES" , ((PlainFile)(MyDriveService.getMyDrive().getRootDir().getDir("home").getDir("luisinho").getFileByName(filename))).getContent());
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void createPlainFileAlreadyExists(){
        final long token = login("luisinho", "luisinho");
        final String filename = "new.txt";
        
        CreateFileService service = new CreateFileService(token, filename, "Plain");
        service.execute();
    }

    @Test(expected = InvalidFileTypeException.class)
    public void createFileWithInvalidType(){
        final long token = login("luisinho", "luisinho");
        final String filename = "other";
        
        CreateFileService service = new CreateFileService(token, filename, "Invalid");
        service.execute();
    }

    @Test(expected = LinkCantBeEmptyException.class)
    public void createAppWithNoContent(){
        final long token = login("luisinho", "luisinho");
        final String filename = "app2";
        
        CreateFileService service = new CreateFileService(token, filename, "App",null);
        service.execute();
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void createDirAlreadyExists(){
        final long token = login("luisinho", "luisinho");
        final String filename = "NewDir";
        
        CreateFileService service = new CreateFileService(token, filename, "Dir");
        service.execute();
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void createAppAlreadyExists(){
        final long token = login("luisinho", "luisinho");
        final String filename = "app";
        
        CreateFileService service = new CreateFileService(token, filename, "App");
        service.execute();
    }

    @Test(expected = FileAlreadyExistsException.class)
    public void cantFindFile(){
        final long token = login("luisinho", "luisinho");
        final String filename = "NewDir";
        
        CreateFileService service = new CreateFileService(token, filename, "Dir");
        service.execute();
    }

    @Test(expected = FileIsADirException.class)
    public void DirWithoutContent(){
        final long token = login("luisinho", "luisinho");
        final String type = "Dir"; 
        final String filename = "NewDir2";
        final String content = "Directory with content";

        CreateFileService service = new CreateFileService(token, filename, type, content);
        service.execute();

    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 234257263;
        final String filename = "link2";

        CreateFileService service = new CreateFileService(token, filename, "Plain");
        service.execute();
    } 
}
