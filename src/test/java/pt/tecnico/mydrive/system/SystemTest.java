package pt.tecnico.mydrive.system;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;

import java.io.StringReader;

import pt.tecnico.mydrive.service.*;
import pt.tecnico.mydrive.presentation.*;

public class SystemTest extends AbstractServiceTest {

    private MyDriveShell sh;

    protected void populate() {
        sh = new MyDriveShell();

        String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
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

        try{
        	Document doc = new SAXBuilder().build(new StringReader(xml));
        	ImportMyDriveService import_service = new ImportMyDriveService(doc);
        	import_service.execute();
        }catch(Exception e){}
    }

    @Test
    public void success() {

   	 	
      new Login(sh).execute(new String[] { "mja", "Peyrelongue" });
   	 	new Login(sh).execute(new String[] { "jtb", "fernandes" });
   	 	
   	 	new ListDirectory(sh).execute(new String[] { });
   	 	new ListDirectory(sh).execute(new String[] { "/home" });

   	 	new Environment(sh).execute(new String[] { "editor", "vim" });
   	 	new Environment(sh).execute(new String[] { "editor" });
   	 	new Environment(sh).execute(new String[] { "other" });
   	 	new Environment(sh).execute(new String[] { });

   	 	new ChangeDirectory(sh).execute(new String[] { "/home/jtb/bin" });
   	 	new ChangeDirectory(sh).execute(new String[] { ".." });
   	 	new ChangeDirectory(sh).execute(new String[] { "." });

   	 	new WriteFile(sh).execute(new String[] { "/home/jtb/profile", "duas palavras" });
   	 	new WriteFile(sh).execute(new String[] { "./profile", "tres palavras pois" });

   	 	new Token(sh).execute(new String[] { "mja" });
   	 	new Token(sh).execute(new String[] { });
   	 	
    }
}
