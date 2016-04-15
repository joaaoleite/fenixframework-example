package pt.tecnico.mydrive;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.PrintStream;
//import java.io.File;

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
	          for (String s: args) xmlScan(new java.io.File(s));
            print();
            xmlPrint();
	      } finally { FenixFramework.shutdown(); }
    }

    @Atomic
    public static void init() { // empty mydrive
        log.trace("Init: " + FenixFramework.getDomainRoot());
	      MyDrive mydrive = MyDrive.getInstance();
        mydrive.cleanup();
    }

    @Atomic
    public static void setup() { // mydrive with debug data
        log.trace("Setup: " + FenixFramework.getDomainRoot());
	      MyDrive mydrive = MyDrive.getInstance();
    }

    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
        MyDrive mydrive = MyDrive.getInstance();

        for (User u: mydrive.getUserSet()) {
            u.print();
        }
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
    public static void xmlScan(java.io.File file) {
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
