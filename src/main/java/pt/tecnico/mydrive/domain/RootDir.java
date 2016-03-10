package pt.tecnico.mydrive.domain;

public class RootDir extends RootDir_Base {
    
    public RootDir(MyDrive mydrive){
        super(mydrive, null, null, "/", "");
        
        createDir(null, "home", "");

    }
    
    @Override
    private Dir getParent(){
        return this;            
    }
    
    @Overrride
    private void setOwner(SuperUser user){
        super.setOwner(user);
        Dir home = getDir("home");
        removeFile(home);
        home.setOwner(SuperUser user);
        addFile(home);
    
}
