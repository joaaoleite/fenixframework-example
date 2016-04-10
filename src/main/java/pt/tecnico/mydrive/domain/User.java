package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Element;
import org.jdom2.DataConversionException;

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
        setHomedir(myDrive.getRootDir().getDir("home").createDir(this,username));
    }
    public boolean checkUsername (String username) {
        if (username==null){
            return false;
        }
        if (username.equals("")){
            return false;
        }
        if (username.length()<3){
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
            setUsername(new String(userElemet.getAttribute("username").getValue()));
            setName(new String(userElemet.getChildText("name")));

            if (userElemet.getChildText("password") != ""){
                setPassword(new String(userElemet.getChildText("password")));
            }
            
            if (userElemet.getChildText("mask") != ""){
                setUmask(new String(userElemet.getChildText("mask")));
            }
        }catch(Exception e){
            e.printStackTrace();
            throw new ImportDocException();
        }

    }

    public void print(){
        System.out.println("The Mydrive has a user with username:" + getUsername() + " , password: " + getPassword() + ", name: " +getName() + "and mask:" + getUmask());
    }

    public Element xmlExport(Element xmlmydrive){
        Element user = new Element("user");
        user.setAttribute("username", getUsername());
        user.addContent(new Element("password").setText(getPassword()));
        user.addContent(new Element("name").setText(getName()));
        user.addContent(new Element("home").setText("/home/"+getUsername()));
        user.addContent(new Element("umask").setText(getUmask()));
        xmlmydrive.addContent(user);
        return xmlmydrive;
    } 
}
