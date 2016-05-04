package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

public class WriteFileTest extends AbstractServiceTest {
    
    private MyDrive mydrive;
    @Override
    protected void populate(){
        mydrive = MyDrive.getInstance();

        User marshall = mydrive.createUser("Titi", "marshall", "missymissy", "rwxd----");
        mydrive.createUser("Tony", "selecta", "rufusrufus", "rwxd----");
        mydrive.getRootDir().getDir("home").getDir("marshall").createDir(mydrive.getUserByUsername("marshall"), "test");
        mydrive.getRootDir().getDir("home").getDir("marshall").createPlainFile(mydrive.getUserByUsername("marshall"), "testplain", "abcdefghijklmnopqrstuvwxyz");
        mydrive.getRootDir().getDir("home").getDir("marshall").getDir("test").createLink(mydrive.getUserByUsername("marshall"), "testlink","/home/marshall/testplain");    
        mydrive.getRootDir().getDir("home").getDir("marshall").createLink(marshall,"link","/home/marshall/testplain"); 

    } 

    @Test
    public void successPlain() {
        final long token = login("marshall", "missymissy");
        final String namefile = "testplain";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void writeLinkToPlainFile() {
        final long token = login("marshall", "missymissy");
        final String namefile = "link";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("link"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successPlainWithNullContent() {
        final long token = login("marshall", "missymissy");
        final String namefile = "testplain";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("testplain"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

     @Test
    public void successLink() {
        final long token = login("marshall", "missymissy");
        final String namefile = "link";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();

        String result = ((Link)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("link"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "teste123",result);
    }

    @Test
    public void successLinkWithNullContent() {
        final long token = login("marshall", "missymissy");
        final String namefile = "link";

        WriteFileService service = new WriteFileService(token, namefile, null);
        service.execute();

        String result = ((Link)(mydrive.getRootDir().getDir("home").getDir("marshall").getFileByName("link"))).read();

        assertNotNull("Content is null", result);
        assertEquals("Write not successfull", "",result);
    }

    @Test(expected = FileIsADirException.class)
    public void dirAsArgument() {
        final long token = login("marshall", "missymissy");
        final String namefile = "test";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = FileDoesNotExistException.class)
    public void fileNotExists() {
        final long token = login("marshall", "missymissy");
        final String namefile = "wrongname";
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, namefile, content);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void expiredToken() {
        final long token = login("marshall", "missymissy");
        final String namefile = "test";
        final String content = "teste123";
        final long fakeToken = 123456789;

        WriteFileService service = new WriteFileService(fakeToken, namefile, content);
        service.execute();
    }

    @Test(expected = FilenameInvalidException.class)
    public void FilenameAsNull() {
        final long token = login("marshall", "missymissy");
        final String content = "teste123";

        WriteFileService service = new WriteFileService(token, null, content);
        service.execute();
    }
}
