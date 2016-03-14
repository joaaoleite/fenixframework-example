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
	          for (String s: args) xmlScan(new File(s));
            //print();
            xmlPrint();
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
        

        System.out.println("1"); 
        // 1. create File /home/README
        PlainFile plain = rootdir.getDir("home").createPlainFile(rootdir.getOwner(),"README",""); 
        plain.write(mydrive.getUserSet().toString());
           
        System.out.println("2"); 
        // 2. create Dir /usr/local/bin
        Dir bin = rootdir.createDir(mydrive.getUserByUsername("root"),"usr","mask").createDir(mydrive.getUserByUsername("root"),"local","mask").createDir(mydrive.getUserByUsername("root"),"bin","mask");
        
        System.out.println("3"); 
        // 3. print /home/README
        System.out.println(((PlainFile)rootdir.getDir("home").getFileByName("README")).read());
        
    
        System.out.println("4"); 
       // 4. remove /usr/local/bin
        rootdir.getDir("usr").getDir("local").getDir("bin").remove();

        
        System.out.println("5"); 
        // 5. print xmlExport()
        xmlPrint();

        System.out.println("6"); 
        // 6. remove /home/README
        rootdir.getDir("home").getFileByName("README").remove();
        

        System.out.println("7"); 
        // 7. list /home
        System.out.println(rootdir.getDir("home").listDir());
    }

    @Atomic
    public static void print() {
        log.trace("Print: " + FenixFramework.getDomainRoot());
        MyDrive mydrive = MyDrive.getInstance();

        for (User u: mydrive.getUserSet()) {
            System.out.println("The Mydrive has a user with username:" + u.getUsername() + " , password: " + u.getPassword() + ", name: " + u.getName() + "and mask:" + u.getUmask());
        }
        for (File f: mydrive.getRootDir().getChilds()){
            if (f instanceof Dir){
                System.out.println("The mydrive has a dir with path: " + f.getPath() + " , name: " + f.getName() + ", owner: " + f.getOwner() + ", perm: " + f.getMask();
            }
            if (f instanceof App){
                System.out.println("The mydrive has a app with path: " + f.getPath() + " , name: " + f.getName() + ", owner: " + f.getOwner() + ", perm: " + f.getMask() + ", method: " + f.getContent();
            }
            if (f instanceof Link){
                System.out.println("The mydrive has a link with path: " + f.getPath() + " , name: " + f.getName() + ", owner: " + f.getOwner() + ", perm: " + f.getMask() + ", value: " + f.getContent();
            }
            if (f instanceof PlainFile){
                System.out.println("The mydrive has a plain with path: " + f.getPath() + " , name: " + f.getName() + ", owner " + f.getOwner() + ", perm: " + f.getMask() + ", contents: " + f.getContent();
            }
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
