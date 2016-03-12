package pt.tecnico.mydrive.domain;

public class SuperUser extends SuperUser_Base {
    public SuperUser(MyDrive myDrive) { 
        super();
        init(myDrive,"root", "SuperUser", "***","rwxdr-x-"); 
    } 
    @Override
    public void remove(){
    }
    
    
}
