package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public abstract class WriteFileTest extends AbstractServiceTest {
    
    private MyDrive mydrive;
    @Override
    protected void populate(){
        mydrive = MyDrive.getInstance();

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

        String result = ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successPlainWithNullContent() {
        final long token = login("marshall", "missy");
        final String namefile = "testplain";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain"))).read();

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

        String result = ((Link)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successLinkWithNullContent() {
        final long token = login("marshall", "missy");
        final String namefile = "testlink";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = ((Link)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testlink"))).read();

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

    @Test(expected = TokenDoesNotExistException.class)
    public void expiredToken() {
        final long token = login("marshall", "missy");
        final String namefile = "test";
        final String content = "teste123";
        final long fakeToken = 123456789;

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
