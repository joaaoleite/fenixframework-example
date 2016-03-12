package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;


public class User extends User_Base {

    public User(){ super();}

    public User(MyDrive myDrive ,String username ) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, username,"rwxd----");
    }
    
    public User(MyDrive myDrive ,String username, String password) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, password,"rwxd----");
    }
    public User(MyDrive myDrive, String username, String password,String umask ) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, password,umask);
    }    
    
    public void remove(){
        setMydrive(null);
        setUsername(null);
        setUmask(null);
        setName(null);
        setHomedir(null);
        setPassword(null);
        deleteDomainObject();
    }
 
    protected void init(MyDrive myDrive,String username, String name, String password,String umask) {
        setMydrive(myDrive);
        setUsername(username);
        setName(name);
        setPassword(password);
        setUmask(umask);
        setHomedir(myDrive.getRootDir().getDir("home").createDir(this,username,umask));
    }
    public boolean checkUsername (String username) {
        if (username.equals("")){
            return false;
        }
        String pattern= "^[a-zA-Z0-9]*$";
        if(username.matches(pattern)){
            return true;
        }
        return false;

    }
    
    public void xmlImport(Element userElemet) throws ImportDocException {

        try {
            setPassword(new String(userElemet.getAttribute("password").getValue().getBytes("UTF-8")));
            setName(new String(userElemet.getAttribute("name").getValue().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) { 
            System.err.println(e); 
            throw new ImportDocException(); 
        }
    }

    public Element xmlExport(Element xmlmydrive){
        Element user = new Element("user");
        user.setAttribute("username", getUsername());
        user.setAttribute("password", getPassword());
        user.setAttribute("name", getName());
        user.setAttribute("umask", getUmask());
        xmlmydrive.addContent(user);
        return xmlmydrive;
    } 
}
