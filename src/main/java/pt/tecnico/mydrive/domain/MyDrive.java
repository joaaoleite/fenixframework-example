package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;

import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.DataConversionException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Set;

import pt.ist.fenixframework.FenixFramework;

public class MyDrive extends MyDrive_Base {

    static final Logger log = LogManager.getRootLogger();
    

    public static MyDrive getInstance() {
        MyDrive mydrive = FenixFramework.getDomainRoot().getMydrive();
        if (mydrive != null)
	          return mydrive;

	      log.trace("new MyDrive");
        mydrive=new MyDrive();
        mydrive.init();
        return mydrive;
    }

    private MyDrive() {
        setRoot(FenixFramework.getDomainRoot());
        
    }

    protected int generateId(){
        int num = getNfile().intValue();
        setNfile(new Integer(num+1));
        return num;
    }

    public void init(){
        setNfile(new Integer(0));
        RootDir rootdir = new RootDir(this);
        rootdir.createDir(null,"home").setMask(rootdir.getMask());
        setRootDir(rootdir);
        SuperUser superuser = new SuperUser(this);
        superuser.setHomedir(getRootDir().getDir("home").createDir(superuser,"root"));
        setSuperUser(superuser);
        rootdir.setOwner(superuser);
        rootdir.getDir("home").setOwner(superuser);
        rootdir.getDir("home").setMask(superuser.getUmask());

    }

    public void cleanup() {
        /*for(User u: getUserSet()){
            u.remove();
        }

        getRootDir().removeR();*/
    }

    public void xmlImport(Element element) throws ImportDocException{

        try{
            // import users
            for(Element e: element.getChildren("user")){
                String username = e.getAttribute("username").getValue();
                User user = getUserByUsername(username);

                if(user==null){
                    user = new User(this,username);
                }
                user.xmlImport(e);
                addUser(user);
            }

            // import filesystem
            for(Element e: element.getChildren("dir")){
                new Dir().xmlImport(e);
            }
            for(Element e: element.getChildren("plain")){
                new PlainFile().xmlImport(e);
            }
            for(Element e: element.getChildren("link")){
                new Link().xmlImport(e);
            }
            for(Element e: element.getChildren("app")){
                new App().xmlImport(e);
            }
        }catch(DataConversionException e){
            e.printStackTrace();
            throw new ImportDocException();
        }
    }
   
    public Document xmlExport() {
        Element element = new Element("mydrive");
	      Document doc = new Document(element);
        
        for(User u: getUserSet()){
            element = u.xmlExport(element);
        }
        element = getRootDir().xmlExport(element);

        return doc;
    }
    
    public java.io.File resourceFile(String filename) {
	    log.trace("Resource: "+filename);
        ClassLoader classLoader = getClass().getClassLoader();
        if (classLoader.getResource(filename) == null) return null;
        return new java.io.File(classLoader.getResource(filename).getFile());
    }

    public User getUserByUsername(String username){
        for(User u: getUserSet()){
            if(u.getUsername()==username){
                return u;
            }
        }
        return null;
    }
    @Override
    public Set<Login> getLoginSet(){
        return null;
    }

    @Override
    public void addLogin(Login login){}

    protected void login(Login login){
        super.addLogin(login);
        for(Login u: super.getLoginSet()){
            u.verify();
        }
    }

    protected Login getLoginByToken(long token){
        for(Login u: super.getLoginSet()){
            if(u.getToken()==token){
                return u;
            }
        }
        return null;
    }

    public pt.tecnico.mydrive.domain.File getFileByPath(String pathMixed) throws FileDoesNotExistException{
        String[] path = pathMixed.split("/");

        Dir actual = getRootDir();
        int i = 1;

        for(; i<path.length-1; i++){
            actual = actual.getDir(path[i]);
        }

        if(actual.exists(path[i++])){
            return actual.getFileByName(path[i]);
        }

        throw new FileDoesNotExistException(path[i]);
    }

    public boolean hasUser(String name){
        return getUserByUsername(name) != null;
    }

    public User createUser(String name, String username, String password, String mask) throws UserAlreadyExistsException{
        if(getUserByUsername(username)!=null){
            throw new UserAlreadyExistsException(username);
        }
        User user = new User(this,username,password,mask);
        Dir tmpdir=getRootDir().getDir("home").createDir(user,username);
        user.setHomedir(tmpdir);
        addUser(user);
        return user;
    }

}
