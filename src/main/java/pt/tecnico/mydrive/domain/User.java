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
   
 
    protected void init(MyDrive myDrive,String username, String name, String password,String umask, Dir home ) {
        setUsername(username);
        setName(name);
        setPassword(password);
        setUmask(umask);
        setHomedir(home);

}     

