package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;


public class User extends User_Base {

    public User(){ }

    public User(MyDrive myDrive ,String username ) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, username,"rwxd----",myDrive.getRootDir());
    }
    
    public User(MyDrive myDrive ,String username, String password) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, password,"rwxd----",myDrive.getRootDir());
    }
    public User(MyDrive myDrive, String username, String password,String umask ) {
        if (checkUsername(username)==false){
            throw new UserInvalidException(username);
        }
        init(myDrive,username, username, password,umask, myDrive.getRootDir());
    }    
    
    public void remove(){
        setUsername(null);
        setUmask(null);
        setName(null);
        setHomedir(null);
        setPassword(null);
        deleteDomainObject();
    }
 
    protected void init(MyDrive myDrive,String username, String name, String password,String umask, Dir home ) {
        setUsername(username);
        setName(name);
        setPassword(password);
        setUmask(umask);
        setHomedir(home);
    }
    public boolean checkUsername (String username) {
        if (username.compareTo("")==0){
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
