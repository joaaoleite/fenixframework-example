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
        User ze = mydrive.createUser("Ze","zezinho","zezinho","rwdx----");

        mydrive.getRootDir().getDir("home").getDir("Toni").createPlainFile(toni,"toni.txt");
        mydrive.getRootDir().getDir("home").getDir("Toni").createDir(toni,"folder").createPlainFile(toni,"plain.txt");
        mydrive.getRootDir().getDir("home").getDir("Ze").createPlainFile(ze,"test.txt");
        mydrive.getRootDir().getDir("home").getDir("Ze").createLink(ze,"link","/home/zezinho");
        mydrive.getRootDir().getDir("home").getDir("Ze").createApp(ze,"app");
        mydrive.getRootDir().getDir("home").getDir("Ze").createDir(ze,"foldertest");

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
        assertEquals("User username", "Toni", e.getChildren("user").getAttribute("username").getValue());
        assertEquals("User Password", "rwdx----", e.getChildren("user").getChildText("password"));
        assertEquals("User name", "Toni", e.getChildren("user").getChildText("name"));
        assertEquals("User homefolder", "rwdx----", e.getChildren("user").getChildText("home"));
        assertEquals("User mask", "rwdx----", e.getChildren("user").getChildText("mask"));
        //Dir
        assertEquals("Dir path", "/home/antonio", e.getChildren("dir").getChildText("path"));
        assertEquals("Dir name", "Toni", e.getChildren("dir").getChildText("name"));
        assertEquals("Dir owner", "antonio", e.getChildren("dir").getChildText("owner"));
        assertEquals("Dir perm", "rwdx----", e.getChildren("dir").getChildText("perm"));
        //PlainFile
        assertEquals("Plain path", "/home/antonio", e.getChildren("plain").getChildText("path"));
        assertEquals("Plain name", "Toni", e.getChildren("plain").getChildText("name"));
        assertEquals("Plain homefolder", "antonio", e.getChildren("plain").getChildText("home"));
        assertEquals("Plain perm", "rwdx----", e.getChildren("plain").getChildText("mask"));
        assertEquals("Plain content", "", e.getChildren("plain").getChildText("content"));
        //Link
        assertEquals("Link path", "/home/antonio", e.getChildren("link").getChildText("path"));
        assertEquals("Link name", "Toni", e.getChildren("link").getChildText("name"));
        assertEquals("Link homefolder", "antonio", e.getChildren("link").getChildText("home"));
        assertEquals("Link perm", "rwdx----", e.getChildren("link").getChildText("mask"));
        assertEquals("Link value", "/home/zezinho", e.getChildren("link").getChildText("value"));
        //App
        assertEquals("App path", "/home/antonio", e.getChildren("app").getChildText("path"));
        assertEquals("App name", "Toni", e.getChildren("app").getChildText("name"));
        assertEquals("App homefolder", "antonio", e.getChildren("app").getChildText("home"));
        assertEquals("App perm", "rwdx----", e.getChildren("app").getChildText("mask"));
        assertEquals("App content", "", e.getChildren("app").getChildText("method"));
    }
}