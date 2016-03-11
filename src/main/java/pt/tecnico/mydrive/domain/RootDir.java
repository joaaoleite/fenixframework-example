package pt.tecnico.mydrive.domain;

public class RootDir extends RootDir_Base {
    
    public RootDir(MyDrive mydrive){
        super();
        init(mydrive, null, null, "/", "");
        
        createDir(null, "home", "");
    }
    public RootDir(){
        super();
    }
    
    @Override
    public Dir getParent(){
        return this;            
    }
    
    @Override
    public void setOwner(User user){
        super.setOwner(user);
        Dir home = getDir("home");
        removeFile(home);
        home.setOwner(user);
        addFile(home);
    }
    
}
