package pt.tecnico.mydrive.service;

import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;

import pt.ist.fenixframework.FenixFramework;
import pt.ist.fenixframework.core.WriteOnReadError;
import pt.tecnico.mydrive.Main;

public abstract class WriteFileTest extends AbstractServiceTest {
    protected static final Logger log = LogManager.getRootLogger();

    @override
    protected abstract void populate(){
        MyDrive mydrive = mydrive.getInstance();

        mydrive.createUser("Titi", "marshall", "missy");
        mydrive.createUser("Tony", "selecta", "rufus");
        mydrive.getRootDir().getDir("home").getDir("marshall").createDir(getUserByUsername("marshall"), "test");
        mydrive.getRootDir().getDir("home").getDir("marshall").createPlainFile(getUserByUsername("marshall"), "testplain", "abcdefghijklmnopqrstuvwxyz")
        mydrive.getRootDir().getDir("home").getDir("marshall").getDir("test").createLink(getUserByUsername("marshall"), "testlink","/home/marshall/testplain");
        /*mydrive.getRootDir().getDir("home").getDir("marshall").getDir("test").createApp(!!!);*/
    } 

    @Test
    public void successPlain() {
        final int token = login("marshall", "missy");
        final String namefile = "testplain";
        final String content = "teste123"

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successPlainWithNullContent() {
        final int token = login("marshall", "missy");
        final String namefile = "testplain";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

     @Test
    public void successLink() {
        final int token = login("marshall", "missy");
        final String namefile = "testlink";
        final String content = "teste123"

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successLinkWithNullContent() {
        final int token = login("marshall", "missy");
        final String namefile = "testlink";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink").read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

    @Test(expected = FileIsADirException.class)
    public void dirAsArgument() {
        final int token = login("marshall", "missy");
        final String namefile = "test";
        final String content = "teste123"

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void fileNotExists() {
        final int token = login("marshall", "missy");
        final String namefile = "wrongname";
        final String content = "teste123"

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = ExpiredTokenException.class)
    public void expiredToken() {
        final int token = login("marshall", "missy");
        final String namefile = "test";
        final String content = "teste123"
        final int fakeToken = "123456789";

        WriteFileService service = new WriteFileService(fakeToken, namefile, content);
        service.execute();
    }

    @Test(expected = FilenameInvalidException.class)
    public void FilenameAsNull() {
        final int token = login("marshall", "missy");
        final String content = "teste123"

        WriteFileService service = new WriteFileService(Token, null, content);
        service.execute();
    }
}