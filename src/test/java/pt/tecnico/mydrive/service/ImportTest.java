package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;

public class ImportTest extends AbstractServiceTest {
    
    private final String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
        + "<myDrive>"   
        + " <user username=\"jtb\">"
        + "     <password>fernandes</password>"
        + "     <name>Joaquim Teófilo Braga</name>"
        + "     <home>/home/jtb</home>"
        + "     <mask>rwxdr-x-</mask>"
        + " </user>"
        + " <user username=\"mja\">"
        + "     <name>Manuel José de Arriaga</name>"
        + "     <password>Peyrelongue</password>"
        + " </user>"
        + " <plain id=\"3\">"
        + "     <path>/home/jtb</path>"
        + "     <name>profile</name>"
        + "     <owner>jtb</owner>"
        + "     <perm>rwxdr-x-</perm>"
        + "     <contents>Primeiro chefe de Estado do regime republicano (acumulando com a chefia do governo), numa capacidade provisória até à eleição do primeiro presidente da República.</contents>"
        + " </plain>"
        + " <dir id=\"4\">"
        + "     <path>/home/jtb</path>"
        + "     <name>documents</name>"
        + "     <owner>jtb</owner>"
        + "     <perm>rwxdr-x-</perm>"
        + " </dir>"
        + " <link id=\"5\">"
        + "     <path>/home/jtb</path>"
        + "     <name>doc</name>"
        + "     <owner>jtb</owner>"
        + "     <perm>rwxdr-x-</perm>"
        + "     <value>/home/jtb/documents</value>"
        + " </link>"
        + " <dir id=\"7\">"
        + "     <path>/home/jtb</path>"
        + "     <owner>jtb</owner>"
        + "     <name>bin</name>"
        + "     <perm>rwxdr-x-</perm>"
        + " </dir>"
        + " <app id=\"9\">"
        + "     <path>/home/jtb/bin</path>"
        + "     <name>hello</name>"
        + "     <owner>jtb</owner>"
        + "     <perm>rwxdr-x-</perm>"
        + "     <method>pt.tecnico.myDrive.app.Hello</method>"
        + " </app>"
        + "</myDrive>";

    protected void populate() {
        MyDrive mydrive = MyDrive.getInstance();
    }

    @Test
    public void success() throws Exception {
		
        Document doc = new SAXBuilder().build(new StringReader(xml));

        ImportMyDriveService service = new ImportMyDriveService(doc);
        service.execute();

        // check imported data
        MyDrive mydrive = MyDrive.getInstance();
        
        assertEquals("created 2 Users", 4, mydrive.getUserSet().size());
        assertTrue("created jtb", mydrive.hasUser("jtb"));
		assertTrue("created mja", mydrive.hasUser("mja"));
        assertEquals("Dir is empty", 0, mydrive.getRootDir().getDir("home").getDir("mja").getFileSet().size());
        assertEquals("Check mask created correctly", "rwxd----", mydrive.getUserByUsername("mja").getUmask());
        assertEquals("Check home folder created correctly", "mja" , mydrive.getRootDir().getDir("home").getDir("mja").getName());
        assertEquals("jtb has a Dir", "bin",mydrive.getRootDir().getDir("home").getDir("jtb").getDir("bin").getName());
        assertEquals("Permissions are the same?", mydrive.getUserByUsername("jtb").getUmask(), mydrive.getRootDir().getDir("home").getDir("jtb").getMask());
        assertNotNull("PlainFile created", mydrive.getRootDir().getDir("home").getDir("jtb").getFileByName("profile"));
        assertEquals("Content correct","Primeiro chefe de Estado do regime republicano (acumulando com a chefia do governo), numa capacidade provisória até à eleição do primeiro presidente da República.", ((PlainFile)(mydrive.getRootDir().getDir("home").getDir("jtb").getFileByName("profile"))).getContent());
        assertNotNull("Link created", mydrive.getRootDir().getDir("home").getDir("jtb").getFileByName("doc"));
        assertEquals("value content created correctly", "/home/jtb/documents", ((Link)(mydrive.getRootDir().getDir("home").getDir("jtb").getFileByName("doc"))).getContent());
        assertTrue("Dir created", mydrive.getRootDir().getDir("home").getDir("jtb").getFileByName("bin").isDir());
        assertNotNull("App created", mydrive.getRootDir().getDir("home").getDir("jtb").getDir("bin").getFileByName("hello"));
        assertEquals("Permissions correct", "rwxdr-x-", mydrive.getRootDir().getDir("home").getDir("jtb").getDir("bin").getFileByName("hello").getMask());
        assertEquals("method content created correctly","pt.tecnico.myDrive.app.Hello", ((App)(mydrive.getRootDir().getDir("home").getDir("jtb").getDir("bin").getFileByName("hello"))).getContent());
    }
}
