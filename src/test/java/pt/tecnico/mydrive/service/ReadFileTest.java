package pt.tecnico.mydrive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

@RunWith(JMockit.class)
public class ReadFileTest extends AbstractServiceTest{    
    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        User antonio = mydrive.createUser("antonio", "toni","123456789","rwxd----");
        
        Dir rootdir =mydrive.getRootDir();
            
        PlainFile plain = rootdir.getDir("home").getDir("toni").createPlainFile(antonio,"text.txt"); 
        plain.write("texto de teste");

        rootdir.getDir("home").getDir("toni").createLink(antonio,"link","/home/toni/text.txt"); 

        rootdir.getDir("home").getDir("toni").createDir(antonio,"teste");     
        
        User halib = mydrive.createUser("Halibio", "halib", "uhtuhtuht", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("halib").createPlainFile(halib,"document","content");
        mydrive.getRootDir().getDir("home").getDir("halib").createLink(halib, "TestLink", "/home/$USER/document");

   } 
   @Test
    public void readLinkEnv() throws LoginFailedException, FileDoesNotExistException, FileIsADirException{
        new MockUp<Link>(){
            @Mock
            String resolve(long token, String path){
                assertEquals("resolve arg", path, "/home/$USER/document");
                return "/home/halib/document";
            }
        };

        final long token = login("halib", "uhtuhtuht");

        ReadFileService service = new ReadFileService(token,"TestLink");
        service.execute();
        String result = service.result();

        assertEquals("Wrong file", "content", result);
    }

    

    @Test
    public void successReadFile(){
        final long token = login("toni", "123456789");
        final String filename = "text.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test
    public void readLinkToPlainFile(){
        final long token = login("toni", "123456789");
        final String filename = "link";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test
    public void successReadFileFromLink(){
        final long token = login("toni", "123456789");
        final String filename = "text.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
        String content = service.result();
        assertNotNull("File doesn't exist", content);
        assertEquals("Wrong match", "texto de teste", content);
    }

    @Test(expected = FileDoesNotExistException.class)
    public void cantFindFile(){
        final long token = login("toni", "123456789");
        final String filename = "tex.txt";
        
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();
    }
    @Test(expected = FileIsADirException.class)
    public void isDir(){
        final long token = login("toni", "123456789");
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
