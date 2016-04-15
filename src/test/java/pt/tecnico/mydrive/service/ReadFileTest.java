package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;

public class ReadFileTest extends AbstractServiceTest{    
    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        User antonio = mydrive.createUser("antonio", "toni","toni","rwxd----");
        
        Dir rootdir =mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("toni").createPlainFile(antonio,"text.txt"); 
        plain.write("texto de teste");

        rootdir.getDir("home").getDir("toni").createLink(antonio,"link","/home/toni/text.txt"); 

        rootdir.getDir("home").getDir("toni").createDir(antonio,"teste");     
   } 
    @Test
    public void successReadFile(){
        final long token = login("toni", "toni");
        final String filename = "link";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test
    public void readLinkToPlainFile(){
        final long token = login("toni", "toni");
        final String filename = "link";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test
    public void successReadFileFromLink(){
        final long token = login("toni", "toni");
        final String filename = "text.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void cantFindFile(){
        final long token = login("toni", "toni");
        final String filename = "tex.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
    }
    @Test(expected = FileIsADirException.class)
    public void isDir(){
        final long token = login("toni", "toni");
        final String filename = "teste";
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();

    } 
    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String filename = "teste";
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();

    }
}
