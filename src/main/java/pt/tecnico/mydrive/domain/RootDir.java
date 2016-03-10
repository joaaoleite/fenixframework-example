package pt.tecnico.mydrive.domain;

public class RootDir extends RootDir_Base {
    
    public RootDir(MyDrive mydrive, String mask){
        super(mydrive, null, null, "/", mask);
        
        createDir(null, "home", mask);

    }
    
    @Override
    protected Dir getParent(){
        return this;            
    }

}
