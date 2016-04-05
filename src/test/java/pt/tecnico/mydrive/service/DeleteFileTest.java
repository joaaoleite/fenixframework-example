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

        Link link = rootdir.getDir("home").getDir("laurinha").createLink(laura, "text.txt","rwxd----");
         

        @Test
    	public void success() {
        	final String filename = "text.txt";  
            final int token = login("laurinha", "laurinha");
        	DeleteFileService service = new DeleteFileService(personName);
        	service.execute();

        


        // check person was removed
        assertFalse("File was not deleted", MyDriveService.getDir().h(personName));
        







        //*Verify line 32


        @Test(expected = FileDoesNotExistException.class)
        public void removeNonexistingPerson() {
            DeleteFileService service = new DeleteFileService("toni");
            service.execute();
    }



   } 