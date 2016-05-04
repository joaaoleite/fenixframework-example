package pt.tecnico.mydrive.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;
import mockit.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

@RunWith(JMockit.class)
public class EnvironmentLinkTest extends AbstractServiceTest{    
    
    @Test
    public void successUserToHisDir(){
        new MockUp<Link>(){
            @Mock
            String resolve(long token, String path){
                assertEquals("resolve arg", path, "/home/$USER");
                return "/home/halib";
            }
        };

    MyDrive mydrive = MyDrive.getInstance();
    
    mydrive.createUser("Halibio", "halib", "uht", "rwxd----");
    final long token = login("halib", "uht");
    File file = mydrive.getRootDir().getDir("home").getDir("halib").createLink(mydrive.getUserByUsername("halib"), "TestLink", "/home/$USER").findFile(token);
    
    assertEquals("Wrong file", "halib", file.getName());
    }
}
