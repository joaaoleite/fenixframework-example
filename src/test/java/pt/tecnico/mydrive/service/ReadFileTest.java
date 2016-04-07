package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import pt.tecnico.mydrive.domain.*;

public class ReadFileTest extends AbstractServiceTest{    
    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        User antonio = mydrive.createUser("antonio", "toni","toni","rwxd----");
        
        Dir roodir =mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("toni").createPlainFile(antonio,"text.txt","rwxd----"); 
        plain.write("texto de teste");
         
        rootdir.getDir("home").getDir("toni").createDir(antonio,"teste","rwxd----");     
   } 
    @Test
    public void successReadFile(){
        final int token = login("toni", "toni");
        final String filename = "text.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        String content = service.execute();
        
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }


    @Test(expected = FileDoesNotExistException.class)
    public void cantFindFile(){
        final int token = login("toni", "toni");
        final String filename = "tex.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
    }
    @Test(expected = FileIsADirException.class)
    public void isDir(){
        final int token = login("toni", "toni");
        final String filename = "teste";
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();

    } 
    @Test(expected = ExpiredTokenException.class)
    public void tokenExpired(){
        final int token = 111111111111111;
        final String filename = "teste";
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();

    }
}
