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

	private final String u1 = "luisinho";


	protected void populate(){
        MyDrive mydrive = MyDrive.getInstance();        
        mydrive.createUser("luis", u1,u1,"rwxd----");
    }

    @Test
    public void success() throws Exception {
    	LoginService service = new LoginService(u1,u1);
	    service.execute();
	    long token = service.result(); 







    }
}
