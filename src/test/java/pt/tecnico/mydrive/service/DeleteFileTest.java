package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;

public class DeleteFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        User laura = mydrive.createUser("laura", "laurinha","laurinha", "rwxd----");
        User ze = mydrive.createUser("ze", "zezinho","zezinho","rwxd----");

        
        Dir roodir =mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("laurinha").createPlainFile(laura,"text.txt","rwxd----"); 

        File file =  rootdir.getDir("home").getDir("laurinha").createFile(laura,"text.txt","rwxd----"); 

    }   

    @Test
	public void success() {
    	final String filename = "text.txt";  
        final int token = login("laurinha", "laurinha");
    	DeleteFileService service = new DeleteFileService(filename);
    	service.execute();
        assertNull("File was not deleted", MyDriveService.getMyDrive().getRootDir().getDir("home").getFileByName(filename));
    }



    @Test(expected = InsufficientPermissionsException.class)
    public void DeleteFileWithoutPermissions() {
        final String filename = "text.txt";  
        final int token = login("laurinha", "laurinha");
        final String filename = "NewDir";

        ChangeDirectoryService service = new ChangeDirectoryService(token, filename);
        String newToken = service.execute();
        
        DeleteFileService service = new DeleteFileService(filename);
        service.execute();
    }


    @Test(expected = ExpiredTokenException.class)
    public void tokenExpired(){
        final int token = 234257263578354;
        final String filename = "link";

        DeleteFileService service = new DeleteFileService(token, filename);
        service.execute();

    }



    @Test(expected = FileDoesNotExistException.class)
    public void removeNonexistingPerson() {
        DeleteFileService service = new DeleteFileService("other.txt");
        service.execute();
    }



   } 