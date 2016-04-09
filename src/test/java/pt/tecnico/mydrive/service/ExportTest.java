package pt.tecnico.mydrive.service;

import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pt.tecnico.mydrive.domain.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;

public class ExportTest extends AbstractServiceTest {

    protected void populate() {
        MyDrive mydrive = MyDrive.getInstance();

        User toni = mydrive.createUser("Toni");
        User ze = mydrive.createUser("Ze");

        mydrive.getRootDir().getDir("home").getDir("Toni").createPlainFile(toni,"toni.txt");
        mydrive.getRootDir().getDir("home").getDir("Toni").createDir(toni,"folder").createPlainFile(toni,"plain.txt");
        mydrive.getRootDir().getDir("home").getDir("Ze").createPlainFile(ze,"test.txt");

    }

    @Test
    public void success() throws Exception {
        ExportMyDriveService service = new ExportMyDriveService();
        service.execute();
		    Document doc = service.result();

    }
}
