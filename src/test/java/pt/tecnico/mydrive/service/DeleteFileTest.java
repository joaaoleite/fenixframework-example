package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertNull;


import pt.tecnico.mydrive.domain.*;

public class DeleteFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User laura = mydrive.createUser("laura", "laurinha","laurinha", "rwxd----");
        User joana = mydrive.createUser("joana", "joaninha","joaninha","rwxd----");
        
        Dir roodir =mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("laurinha").createPlainFile(laura,"laura.txt","rwxd----"); 
        Link link = rootdir.getDir("home").getDir("laurinha").createLink(laura,"link","rwxd----");
        App app = rootdir.getDir("home").getDir("laurinha").createApp(laura,"app","rwxd----");
        Dir dir = rootdir.getDir("home").getDir("laurinha").createDir(laura,"Dir","rwxd----");
    }   

    @Test
	public void successDeletePlainFile() {
    	final String filename = "laura.txt";  
        final int token = login("laurinha", "laurinha");

    	DeleteFileService service = new DeleteFileService(filename);
    	service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteLink() {
        final String filename = "link";  
        final int token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteApp() {
        final String filename = "app";  
        final int token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test
    public void successDeleteDir() {
        final String filename = "Dir";  
        final int token = login("laurinha", "laurinha");

        DeleteFileService service = new DeleteFileService(filename);
        service.execute();

        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void DeleteFileWithoutPermissions() {
        final String filename = "laura.txt";  
        final int token = login("laurinha", "laurinha");
        final String path = "/home/ze/";

        ChangeDirectoryService service = new ChangeDirectoryService(token, path);
        service.execute();
        
        DeleteFileService service = new DeleteFileService(filename);
        service.execute();
    }

    @Test(expected = ExpiredTokenException.class)
    public void tokenExpired(){
        final int token = 765437263578354;
        final String filename = "link";

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();

    }

    @Test(expected = FileDoesNotExistException.class)
    public void DeleteNonExistingFile() {
        final String filename = "manel.txt";

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();
    }

} 