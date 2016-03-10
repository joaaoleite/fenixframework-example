package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;


public class User extends User_Base {
    public User(MyDrive myDrive ,String username ) {
        if (checkUsername(username)==false){
            throws new UserInvalidException();
        }
        init(username, username, username,"rwxd----",myDrive.getRootDir());
    }
    public User(MyDrive myDrive ,String username, String umask ) {
        if (checkUsername(username)==false){
            throws new UserInvalidException();
        }
        init(username, username, username,umask,myDrive.getRootDir());
    }
    
    public User(MyDrive myDrive ,String username, String password) {
        if (checkUsername(username)==false){
            throws new UserInvalidException();
        }
        init(username, username, password,"rwxd----",myDrive.getRootDir());
    }
    public User(MyDrive myDrive, String username, String password,String umask ) {
        if (checkUsername(username)==false){
            throw new UserInvalidException();
        }
        init(username, username, password,umask,home, myDrive.getRootDir());
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
            return false
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

    public Element xmlExport() throws ExportDocException{
        try{
            Element element = new Element("user");
            element.setAttribute("username", getUsername());
            element.setAttribute("password", getPassword());
            element.setAttribute("name", getName());
            element.setAttribute("umask", getUmask());
        } catch(UnsupportedEncodingException e) { 
            System.err.println(e); 
            throw new ExportDocException(); 
        }

        return element;
    } 
}
