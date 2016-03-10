package pt.tecnico.mydrive.domain;

public class SuperUser extends SuperUser_Base {
    public SuperUser(MyDrive myDrive) { 
        init("root", "SuperUser", "***","rwxdr-x-",myDrive.getRootDir()); 
    }
    
}
