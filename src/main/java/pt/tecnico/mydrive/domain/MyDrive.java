package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;

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

        //getRootDir().recursiveR();
    }

    public void xmlImport(Element element) {

        // import users
        Element users = element.getChild("users"); 
        for(Element e: users.getChildren("user")){
            String username = e.getAttribute("username").getValue();
            User user = getUserByUsername(username);

            if(user==null){
                user = new User(this,username);
            }
            user.xmlImport(e);
            addUser(user);
        }

        //import filesystem
        Element e = element.getChild("rootdir");
        getRootDir().xmlImport(e);

    }
    /*
    public Document xmlExport() {
        Element element = new Element("mydrive");
	      Document doc = new Document(element);

        return doc;
    }
    */
    public File resourceFile(String filename) {
	      log.trace("Resource: "+filename);
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(filename) == null) return null;
        return new java.io.File(classLoader.getResource(filename).getFile());
    }

    protected User getUserByUsername(String username){
        for(User u: getUserSet()){
            if(u.getUsername()==username){
                return u;
            }
        }
        return null;
    }

    public User createUser(String name, String username, String password, String mask) throws UserAlreadyExistsException{
        if(getUserByUsername(username)!=null){
            throw new UserAlreadyExistsException(username);
        }
        User user = new User(this,username,password,mask);
        addUser(user);
        return user;
    }

}
