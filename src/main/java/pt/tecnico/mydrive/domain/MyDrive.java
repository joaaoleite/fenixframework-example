package pt.tecnico.mydrive.domain;

import java.io.File;

import org.jdom2.Element;
import org.jdom2.Document;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.FenixFramework;

public class MyDrive extends MyDrive_Base {

    static final Logger log = LogManager.getRootLogger();

    public static MyDrive getInstance() {
        MyDrive mydrive = FenixFramework.getDomainRoot().getMydrive();
        if (mydrive != null)
	          return mydrive;

	      log.trace("new MyDrive");
        return new MyDrive();
    }

    private MyDrive() {
        setRoot(FenixFramework.getDomainRoot());
    }

    public void init(){
        RootDir rootdir = new RootDir(this);
        setRootDir(rootdir);
        SuperUser superuser = new SuperUser(this);
        rootdir.setOwner(superuser);
    }

    public void cleanup() {
        for(User u: getUserSet())
            u.remove();

        // clean Files
    }

    public void xmlImport(Element element) {
        // implement code here!!!
    }

    public Document xmlExport() {
        Element element = new Element("mydrive");
	      Document doc = new Document(element);

        return doc;
    }

    public File resourceFile(String filename) {
	      log.trace("Resource: "+filename);
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(filename) == null) return null;
        return new java.io.File(classLoader.getResource(filename).getFile());
    }

    public User createUser(String name, String username, String password, String mask){
        if(getUserByUsername(username)!=null){
            throw new UserAlreadyExists(username);
        }
        User user = new User(this,name,username,password,mask);
        addUser(user);
    }

}
