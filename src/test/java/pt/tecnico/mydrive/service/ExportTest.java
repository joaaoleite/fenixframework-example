package pt.tecnico.mydrive.service;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.domain.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;

public class ExportTest extends AbstractServiceTest {

    protected void populate() {
        MyDrive mydrive = MyDrive.getInstance();

        User toni = mydrive.createUser("Toni","antonio","antonio","rwdx----");
        User ze = mydrive.createUser("Ze","zezinho","password","rwdx----");

        mydrive.getRootDir().getDir("home").getDir("antonio").createPlainFile(toni,"toni.txt");
        mydrive.getRootDir().getDir("home").getDir("antonio").createDir(toni,"folder").createPlainFile(toni,"plain.txt");
        mydrive.getRootDir().getDir("home").getDir("zezinho").createPlainFile(ze,"test.txt","content");
        mydrive.getRootDir().getDir("home").getDir("zezinho").createLink(ze,"link","/home/zezinho");
        mydrive.getRootDir().getDir("home").getDir("zezinho").createApp(ze,"app");
        mydrive.getRootDir().getDir("home").getDir("zezinho").createDir(ze,"foldertest");

    }

    @Test
    public void success() throws Exception {
        ExportMyDriveService service = new ExportMyDriveService();
        service.execute();
        Document doc = service.result();

        Element e = doc.getRootElement();
        assertEquals("Exported 2 Users", 2, e.getChildren("user").size());
        assertEquals("Exported 4 Dir", 4, e.getChildren("dir").size());
        assertEquals("Exported 2 PlainFile", 2, e.getChildren("plain").size());
        assertEquals("Exported 1 Link", 1, e.getChildren("link").size());
        assertEquals("Exported 1 App", 1, e.getChildren("app").size());
        //User
        for(Element u: e.getChildren("user")){
            
            if(u.getAttribute("username").getValue()=="antonio"){
                assertEquals("User Password", "antonio", u.getChildText("password"));
                assertEquals("User name", "Toni", u.getChildText("name"));
                assertEquals("User homefolder", "/home/antonio", u.getChildText("home"));
                assertEquals("User mask", "rwdx----", u.getChildText("mask"));
            }
            else if(u.getAttribute("username").getValue()=="zezinho"){
                assertEquals("User Password", "password", u.getChildText("password"));
                assertEquals("User name", "Ze", u.getChildText("name"));
                assertEquals("User homefolder", "/home/zezinho", u.getChildText("home"));
                assertEquals("User mask", "rwdx----", u.getChildText("mask"));
            }
            else{
                fail("invalid username");
            }
        }
        
        boolean dirTest = false;
        for(Element d: e.getChildren("dir")){

            if(d.getChildText("path")=="/home/antonio"){
                dirTest=true;
                assertEquals("Dir owner", "antonio", d.getChildText("owner"));
                assertEquals("Dir perm", "rwdx----", d.getChildText("perm"));
            }
        }
        if(!dirTest)
            fail("Invalid Dir path");

        boolean plainTest = false;
        for(Element d: e.getChildren("plain")){

            if(d.getChildText("path")=="/home/antonio/folder/plain.txt"){
                plainTest=true;
                assertEquals("Invalid file owner", "antonio", d.getChildText("owner"));
                assertEquals("Dir perm", "rwdx----", d.getChildText("perm"));
            }
            if(d.getChildText("path")=="/home/zezinho/test.txt"){
                plainTest=true;
                assertEquals("Invalid file owner", "zezinho", d.getChildText("owner"));
                assertEquals("Dir perm", "rwdx----", d.getChildText("perm"));
                assertEquals("Invalid file contents", "content", d.getChildText("content"));
            }
        }
        if(!plainTest)
            fail("Invalid PlainFile path");

        boolean linkTest = false;
        for(Element d: e.getChildren("plain")){

            if(d.getChildText("path")=="/home/zezinho/link"){
                linkTest=true;
                assertEquals("Invalid file owner", "zezinho", d.getChildText("owner"));
                assertEquals("Link perm", "rwdx----", d.getChildText("mask"));
                assertEquals("Link value", "/home/zezinho", d.getChildText("value"));
            }
        }
        if(!linkTest)
            fail("Invalid Link path");

        boolean appTest = false;
        for(Element d: e.getChildren("plain")){

            if(d.getChildText("path")=="/home/zezinho/link"){
                appTest=true;
                assertEquals("App perm", "rwdx----", d.getChildText("mask"));
            }
        }
        if(!appTest)
            fail("Invalid App path");
    }
}
