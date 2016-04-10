package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertNull;


import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class DeleteFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User laura = mydrive.createUser("laura", "laurinha","laurinha", "rwxd----");
        User joana = mydrive.createUser("joana", "joaninha","joaninha","rwxd----");
        
        Dir rootdir = mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("laurinha").createPlainFile(laura,"laura.txt"); 
        Link link = rootdir.getDir("home").getDir("laurinha").createLink(laura,"link","/");
        App app = rootdir.getDir("home").getDir("laurinha").createApp(laura,"app");
        Dir dir = rootdir.getDir("home").getDir("laurinha").createDir(laura,"Dir");
    }   

    @Test
	public void successDeletePlainFile() {
    	final String filename = "laura.txt";  
        final long token = login("laurinha", "laurinha");

    	DeleteFileService service = new DeleteFileService(token,filename);
    	service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteLink() {
        final String filename = "link";  
        final long token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteApp() {
        final String filename = "app";  
        final long token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(token,filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteDir() {
        final String filename = "Dir";  
        final long token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void DeleteFileWithoutPermissions() {
        final String filename = "laura.txt";  
        final long token = login("joaninha", "joaninha");
        final String path = "/home/laura";

        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        
        DeleteFileService service2 = new DeleteFileService(token, filename);
        service2.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 765437263;
        final String filename = "link";

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();

    }

    @Test(expected = FileDoesNotExistException.class)
    public void DeleteNonExistingFile() {
        final long token = login("laurinha", "laurinha");
        final String filename = "manel.txt";

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();
    }

} 
