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
public class EnvironmentLinkTest extends AbstractServiceTest{    


    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        
        User halib = mydrive.createUser("Halibio", "halib", "uhtuhtuht", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("halib").createPlainFile(halib,"document","content");
        mydrive.getRootDir().getDir("home").getDir("halib").createLink(halib, "TestLink", "/home/$USER/document");

    }
    
    @Test
    public void successUserToHisDir() throws LoginFailedException, FileDoesNotExistException, FileIsADirException{
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
}
