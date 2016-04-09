package pt.tecnico.mydrive.service;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import static org.junit.Assert.*;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;
import pt.tecnico.mydrive.Main;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;

public abstract class WriteFileTest extends AbstractServiceTest {
    protected static final Logger log = LogManager.getRootLogger();
    
    private Mydrive mydrive;
    @override
    protected void populate(){
        mydrive = mydrive.getInstance();

        mydrive.createUser("Titi", "marshall", "missy", "rwxd----");
        mydrive.createUser("Tony", "selecta", "rufus", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("marshall").createDir(mydrive.getUserByUsername("marshall"), "test");
        mydrive.getRootDir().getDir("home").getDir("marshall").createPlainFile(mydrive.getUserByUsername("marshall"), "testplain", "abcdefghijklmnopqrstuvwxyz");
        mydrive.getRootDir().getDir("home").getDir("marshall").getDir("test").createLink(mydrive.getUserByUsername("marshall"), "testlink","/home/marshall/testplain");
        /*mydrive.getRootDir().getDir("home").getDir("marshall").getDir("test").createApp(!!!);*/
    } 

    @Test
    public void successPlain() {
        final long token = login("marshall", "missy");
        final String namefile = "testplain";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successPlainWithNullContent() {
        final long token = login("marshall", "missy");
        final String namefile = "testplain";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

     @Test
    public void successLink() {
        final long token = login("marshall", "missy");
        final String namefile = "testlink";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successLinkWithNullContent() {
        final long token = login("marshall", "missy");
        final String namefile = "testlink";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

    @Test(expected = FileIsADirException.class)
    public void dirAsArgument() {
        final long token = login("marshall", "missy");
        final String namefile = "test";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void fileNotExists() {
        final long token = login("marshall", "missy");
        final String namefile = "wrongname";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = ExpiredTokenException.class)
    public void expiredToken() {
        final long token = login("marshall", "missy");
        final String namefile = "test";
        final String content = "teste123";
        final long fakeToken = "123456789";

        WriteFileService service = new WriteFileService(fakeToken, namefile, content);
        service.execute();
    }

    @Test(expected = FilenameInvalidException.class)
    public void FilenameAsNull() {
        final long token = login("marshall", "missy");
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, null, content);
        service.execute();
    }
}
