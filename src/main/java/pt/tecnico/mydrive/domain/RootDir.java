package pt.tecnico.mydrive.domain;

public class RootDir extends RootDir_Base {
    
    public RootDir(MyDrive mydrive, User owner, String mask){
        super(mydrive, null, owner, "/", mask);
    }
    
    @Override
    protected Dir getParent(){
        return this;            
    }

}
