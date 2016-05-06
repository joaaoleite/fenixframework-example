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
