package pt.tecnico.mydrive.domain;

public class SuperUser extends SuperUser_Base {
    public SuperUser(MyDrive myDrive) {
        RootDir rootDir =new RootDir (this);
        Dir home = rootDir.createDir("home",this,null)
        myDrive.setRootDir(rootDir); 
        init("root", "SuperUser", "***","rwxdr-x-",home ); 
    }
    
}
