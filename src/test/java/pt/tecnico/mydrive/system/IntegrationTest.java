package pt.tecnico.mydrive.system;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;

import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.StringReader;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import pt.tecnico.mydrive.domain.MyDrive; // Mockup
import pt.tecnico.mydrive.service.*;
import pt.tecnico.mydrive.service.dto.*;
import pt.tecnico.mydrive.exception.*;

@RunWith(JMockit.class)
public class IntegrationTest extends AbstractServiceTest {

	private final String u1 = "jtb";
    private final String p1 = "fernandes";
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


	protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
    }

    @Test
    public void success() throws Exception {
    	Document doc = new SAXBuilder().build(new StringReader(xml));
        ImportMyDriveService import_service = new ImportMyDriveService(doc);
        import_service.execute();

        LoginService login = new LoginService(u1,p1);
	    login.execute();
	    long token = login.result(); 

        ListDirectoryService list_1 = new ListDirectoryService(token); 
        list_1.execute();
        ArrayList<FileDto> list1 = (ArrayList)list_1.result();

        assertEquals("Directory was not listed", 6, list1.size());

        assertEquals("File 1 of list1 Wrong", "bin", list1.get(2).getName());
        assertEquals("File 1 of list1 Wrong", "Dir", list1.get(2).getType());
        assertEquals("File 1 of list1 Wrong", "rwxdr-x-", list1.get(2).getPerm());
        assertEquals("File 1 of list1 Wrong", 3, list1.get(2).getSize());
        assertEquals("File 1 of list1 Wrong", u1, list1.get(2).getOwner());
       
        assertEquals("File 2 of list1 Wrong", "doc", list1.get(3).getName());
        assertEquals("File 2 of list1 Wrong", "Link", list1.get(3).getType());
        assertEquals("File 2 of list1 Wrong", "rwxdr-x-", list1.get(3).getPerm());
        assertEquals("File 2 of list1 Wrong", 19, list1.get(3).getSize());
        assertEquals("File 2 of list1 Wrong", u1, list1.get(3).getOwner());
        
        assertEquals("File 3 of list1 Wrong", "documents", list1.get(4).getName());
        assertEquals("File 3 of list1 Wrong", "Dir", list1.get(4).getType());
        assertEquals("File 3 of list1 Wrong", "rwxdr-x-", list1.get(4).getPerm());
        assertEquals("File 3 of list1 Wrong", 2, list1.get(4).getSize());
        assertEquals("File 3 of list1 Wrong", u1, list1.get(4).getOwner());
        
        assertEquals("File 4 of list1 Wrong", "profile", list1.get(5).getName());
        assertEquals("File 4 of list1 Wrong", "PlainFile", list1.get(5).getType());
        assertEquals("File 4 of list1 Wrong", "rwxdr-x-", list1.get(5).getPerm());
        assertEquals("File 4 of list1 Wrong", 162, list1.get(5).getSize());
        assertEquals("File 4 of list1 Wrong", u1, list1.get(5).getOwner());
        

        CreateFileService create_d = new CreateFileService(token,"Pasta","Dir");
        create_d.execute();

        ChangeDirectoryService cd = new ChangeDirectoryService(token, "Pasta");
        cd.execute();

        CreateFileService create_p = new CreateFileService(token,"Ficheiro","Plain","O Tiago fez este teste");
        create_p.execute();
        CreateFileService create_l = new CreateFileService(token,"Atalho","Link","/home");
        create_l.execute();
        CreateFileService create_a = new CreateFileService(token,"Aplicacao","App","pt.tecnico.mydrive.apps.HelloWorld");
        create_a.execute();


        ListDirectoryService list_2 = new ListDirectoryService(token);
        list_2.execute();
        ArrayList<FileDto> list2 = (ArrayList)list_2.result();

        assertEquals("Directory was not listed", 5, list2.size());

        assertEquals("File 1 of list2 Wrong", "App", list2.get(2).getType());
        assertEquals("File 1 of list2 Wrong", "rwxdr-x-", list2.get(2).getPerm());
        assertEquals("File 1 of list2 Wrong", 34, list2.get(2).getSize());
        assertEquals("File 1 of list2 Wrong", u1, list2.get(2).getOwner());
        assertEquals("File 1 of list2 Wrong", "Aplicacao", list2.get(2).getName());

        assertEquals("File 2 of list2 Wrong", "Link", list2.get(3).getType());
        assertEquals("File 2 of list2 Wrong", "rwxdr-x-", list2.get(3).getPerm());
        assertEquals("File 2 of list2 Wrong", 5, list2.get(3).getSize());
        assertEquals("File 2 of list2 Wrong", u1, list2.get(3).getOwner());
        assertEquals("File 2 of list2 Wrong", "Atalho", list2.get(3).getName());

        assertEquals("File 3 of list2 Wrong", "PlainFile", list2.get(4).getType());
        assertEquals("File 3 of list2 Wrong", "rwxdr-x-", list2.get(4).getPerm());
        assertEquals("File 3 of list2 Wrong", 22, list2.get(4).getSize());
        assertEquals("File 3 of list2 Wrong", u1, list2.get(4).getOwner());
        assertEquals("File 3 of list2 Wrong", "Ficheiro", list2.get(4).getName());

        ReadFileService read = new ReadFileService(token, "Ficheiro");
        read.execute();
        String content = read.result();
        assertEquals("Wrong match", "O Tiago fez este teste", content);

        
    }
}
