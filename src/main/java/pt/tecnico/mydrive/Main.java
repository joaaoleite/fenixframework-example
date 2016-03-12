package pt.tecnico.mydrive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.File;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.ist.fenixframework.FenixFramework;

import pt.tecnico.mydrive.domain.*;

public class Main {
    static final Logger log = LogManager.getRootLogger();

    public static void main(String[] args) throws IOException {
        System.out.println("*** Welcome to the MyDrive application! ***");
	      try {
	          setup();
	          //for (String s: args) xmlScan(new File(s));
            //print();
            //xmlPrint();
	      } finally { FenixFramework.shutdown(); }
    }

    @Atomic
    public static void init() { // empty mydrive
        log.trace("Init: " + FenixFramework.getDomainRoot());
	      MyDrive mydrive = MyDrive.getInstance();
        mydrive.cleanup();
        mydrive.init();
    }

    @Atomic
    public static void setup() { // mydrive with debug data
        log.trace("Setup: " + FenixFramework.getDomainRoot());
	      MyDrive mydrive = MyDrive.getInstance();
        Dir rootdir = mydrive.getRootDir();
        

         
        // 1. create File /home/README
        PlainFile plain = rootdir.getDir("home").createPlainFile(rootdir.getOwner(),"README",""); 
        plain.write(mydrive.getUserSet().toString());
           
        // 2. create Dir /usr/local/bin
        Dir bin = rootdir.createDir(mydrive.getUserByUsername("root"),"usr","mask").createDir(mydrive.getUserByUsername("root"),"local","mask").createDir(mydrive.getUserByUsername("root"),"bin","mask");
        
        // 3. print /home/README
        System.out.println(((PlainFile)rootdir.getDir("home").getFileByName("README")).read());
        
    
       // 4. remove /usr/local/bin
        rootdir.getDir("usr").getDir("local").getDir("bin").remove();


        // 5. print xmlExport()
        xmlPrint();

        // 6. remove /home/README
        rootdir.getDir("home").getFileByName("README").remove();
        

        // 7. list /home
        System.out.println(rootdir.getDir("home").listDir());
    }
     
    @Atomic
    public static void xmlPrint() {
        log.trace("xmlPrint: " + FenixFramework.getDomainRoot());
	      Document doc = MyDrive.getInstance().xmlExport();
	      XMLOutputter xmlOutput = new XMLOutputter(Format.getPrettyFormat());
	      try { xmlOutput.output(doc, new PrintStream(System.out));
	      } catch (IOException e) { System.out.println(e); }
    }

    @Atomic
    public static void xmlScan(File file) {
        log.trace("xmlScan: " + FenixFramework.getDomainRoot());
	      MyDrive mydrive = MyDrive.getInstance();
	      SAXBuilder builder = new SAXBuilder();
	      try {
	          Document document = (Document)builder.build(file);
	          mydrive.xmlImport(document.getRootElement());
	      } catch (JDOMException | IOException e) {
	          e.printStackTrace();
	      }
    }
}
