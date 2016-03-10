package pt.tecnico.mydrive.domain;


public class User extends User_Base {
    public User(MyDrive myDrive ,String username ) {
        init(username, username, username,"rwxd----",myDrive.getRootDir());
    }
    public User(MyDrive myDrive ,String username, String umask ) {
        init(username, username, username,umask,myDrive.getRootDir());
    }
    
    public User(MyDrive myDrive ,String username, String password) {
        init(username, username, password,"rwxd----",myDrive.getRootDir());
    }
    public User(MyDrive myDrive, String username, String password,String umask ) {
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
    
    public void xmlImport(Element userElemet) throws ImportDocumentException {

        try {
            setPassword(new String(userElemet.getAttribute("password").getValue().getBytes("UTF-8")));
            setName(new String(userElemet.getAttribute("name").getValue().getBytes("UTF-8")));
            //setUsername(new String(userElemet.getAttribute("username").getValue().getBytes("UTF-8")));
            //setUmask(new String(userElemet.getAttribute("umask").getValue().getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) { 
            System.err.println(e); 
            throw new ImportDocumentException; 
        }
    }

    public Element xmlExport() {
        Element element = new Element("user");
        element.setAttribute("username", getUsername());
        element.setAttribute("password", getPassword());
        element.setAttribute("name", getName());
        element.setAttribute("umask", getUmask());

        return element;
    } 
}   

