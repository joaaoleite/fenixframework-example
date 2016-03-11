package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

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
    
    @Override
    public Element xmlExport() {
        Element element = new Element("rootdir");

        element.setAttribute("name", getName());
        element.setAttribute("owner",getOwner().getUsername());
        element.setAttribute("mask",getMask());
        element.setAttribute("lastMofification", getLastModification());
        element.setAttribute("id", getId().toString());

        for(File f: getFileSet()){
            element.addContent(f.xmlExport());
        }
        return element;
    }
}
