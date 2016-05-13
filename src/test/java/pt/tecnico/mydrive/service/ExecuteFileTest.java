package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.Arrays;

import org.junit.runner.RunWith;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
@RunWith(JMockit.class)
public class ExecuteFileTest extends AbstractServiceTest{    
    protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();
        mydrive.createUser("Halibio", "halib", "uhtuhtuht", "rwxd----");
        mydrive.createUser("Jose Trigo", "zetrigo", "tetetiti", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createPlainFile(mydrive.getUserByUsername("zetrigo"), "file", "/home/zetrigo/app Halib Jose");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createApp(mydrive.getUserByUsername("zetrigo"), "app", "pt.tecnico.mydrive.apps.HelloWorld");
        mydrive.getRootDir().getDir("home").getDir("zetrigo").createPlainFile(mydrive.getUserByUsername("zetrigo"), "program.exe", "/home/zetrigo/app Halib Jose");
   }
    
    @Test
    public void successExePlainFile(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/file";
       
        MyDrive mydrive = MyDrive.getInstance();
        String[] content = ((PlainFile)(mydrive.getFileByPath(path))).read().split(" ");
        String[] args = Arrays.copyOfRange(content, 1, content.length - 1);
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }

    @Test
    public void successExeApp(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/app";
        
        ExecuteFileService service = new ExecuteFileService(token, path, null);
        service.execute();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void FileDoesNotExist(){
        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/start";

        MyDrive mydrive = MyDrive.getInstance();
        String[] content = ((PlainFile)(mydrive.getFileByPath(path))).read().split(" ");
        String[] args = Arrays.copyOfRange(content, 1, content.length - 1);
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }

    @Test(expected = InsufficientPermissionsException.class)
    public void tokenInsufficientPermissions(){
        final long token = login("halib", "uhtuhtuht");
        final String path = "/home/zetrigo/file";
    
        MyDrive mydrive = MyDrive.getInstance();
        String[] content = ((PlainFile)(mydrive.getFileByPath(path))).read().split(" ");
        String[] args = Arrays.copyOfRange(content, 1, content.length - 1);
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String path = "/home/zetrigo/file";
   
        MyDrive mydrive = MyDrive.getInstance();
        String[] content = ((PlainFile)(mydrive.getFileByPath(path))).read().split(" ");
        String[] args = Arrays.copyOfRange(content, 1, content.length - 1);
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();
   }

    @Test
    public void MockOnExecuteAssociation(){
        new MockUp<ExecuteFileService>(){
            @Mock
            String extension(String exe){
                assertEquals("resolve arg", exe, "exe");
                return "/home/zetrigo/app";
            }
        };
    
        MyDrive mydrive = MyDrive.getInstance();

        final long token = login("zetrigo", "tetetiti");
        final String path = "/home/zetrigo/program.exe";

        String[] content = ((PlainFile)(mydrive.getFileByPath(path))).read().split(" ");
        String[] args = Arrays.copyOfRange(content, 1, content.length - 1);
        
        ExecuteFileService service = new ExecuteFileService(token, path, args);
        service.execute();

}
}
