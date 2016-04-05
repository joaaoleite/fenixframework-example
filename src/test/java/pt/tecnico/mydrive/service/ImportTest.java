package pt.tecnico.mydrive.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import pt.tecnico.mydrive.domain.*;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;

public class ImportTest extends AbstractServiceTest {
    private final String xml = "<?xml version="1.0" encoding="UTF-8"?>"
		+ "<myDrive>"	
		+ "	<user username="jtb">"
    	+ "		<password>fernandes</password>"
		+ "   	<name>Joaquim Teófilo Braga</name>"
		+ "  	<home>/home/jtb</home>"
		+ "   	<mask>rwxdr-x-</mask>"
		+ "	</user>"
		+ "	<user username="mja">"
		+ "  	<name>Manuel José de Arriaga</name>"
		+ "   	<password>Peyrelongue</password>"
		+ "	</user>"
		+ "	<plain id="3">"
		+ "	    <path>/home/jtb</path>"
		+ "	    <name>profile</name>"
		+ "	    <owner>jtb</owner>"
		+ "	    <perm>rwxdr-x-</perm>"
		+ "	    <contents>Primeiro chefe de Estado do regime republicano (acumulando com a chefia do governo), numa capacidade provisória até à eleição do primeiro presidente da República.</contents>"
		+ "	</plain>"
		+ "	<dir id="4">"
		+ "	    <path>/home/jtb</path>"
		+ "	    <name>documents</name>"
		+ "	    <owner>jtb</owner>"
		+ "	    <perm>rwxdr-x-</perm>"
		+ "	</dir>"
		+ "	<link id="5">"
		+ "	    <path>/home/jtb</path>"
		+ "	    <name>doc</name>"
		+ "	    <owner>jtb</owner>"
		+ "	    <perm>rwxdr-x-</perm>"
		+ "	    <value>/home/jtb/documents</value>"
		+ "	</link>"
		+ "	<dir id="7">"
		+ "	    <path>/home/jtb</path>"
		+ "	    <owner>jtb</owner>"
		+ "	    <name>bin</name>"
		+ "	    <perm>rwxdr-x-</perm>"
		+ "	</dir>"
		+ "	<app id="9">"
		+ "	    <path>/home/jtb/bin</path>"
		+ "	    <name>hello</name>"
		+ "	    <owner>jtb</owner>"
		+ "	    <perm>rwxdr-x-</perm>"
		+ "	    <method>pt.tecnico.myDrive.app.Hello</method>"
		+ "	</app>"
		+ "</myDrive>"

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
        assertEquals("created 2 Users", 2, mydrive.getUserSet().size());
        assertTrue("created jtb", mydrive.hasUser("jtb"));
		assertTrue("created mja", mydrive.hasUser("mja"));
        assertEquals("Dir is empty", 0, mydrive.getRootDir().getDir("mja").getFileSet().size());
        assertEquals("Check mask created correctly", "rwxd----" , mydrive.getUserByUsername("mja").getUmask());
        assertEquals("Check home folder created correctly", "mja" , mydrive.getRootDir().getDir("home").getDir("mja"));
        assertEquals("jtb has 5 files", 5, mydrive.getUserByUsername("jtb").getFileSet().size());
        assertTrue("jtb has a Dir", mydrive.getRootDir().getDir("home").getDir("jtb").getDir("bin");
        assertEquals("Permissions are the same?", mydrive.getUserByUsername("jtb").getUmask(), mydrive.getRootDir().getDir("home").getDir("jtb").getMask());
        assertTrue("PlainFile created", mydrive.getRootDir().getDir("jtb").getFileByName("profile"));
        assertEquals("Content correct","Primeiro chefe de Estado do regime republicano (acumulando com a chefia do governo), numa capacidade provisória até à eleição do primeiro presidente da República.", mydrive.getRootDir().getDir("jtb").getFileByName("profile").getContent());
        assertTrue("Link created", mydrive.getRootDir().getDir("jtb").getFileByName("doc"));
        assertEquals("value content created correctly", "/home/jtb/documents", mydrive.getRootDir().getDir("jtb").getFileByName("doc").getContent());
        assertTrue("Dir created", mydrive.getRootDir().getDir("jtb").getFileByName("bin").isDir());
        assertTrue("App created", mydrive.getRootDir().getDir("jtb").getDir("bin").getFileByName("hello"));
        assertEquals("method content created correctly","pt.tecnico.myDrive.app.Hello", mydrive.getRootDir().getDir("jtb").getDir("bin").getFileByName("hello").getContent()));
    }
}