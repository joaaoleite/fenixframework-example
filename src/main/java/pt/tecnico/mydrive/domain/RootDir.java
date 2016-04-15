package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;

public class RootDir extends RootDir_Base {
    
    protected RootDir(MyDrive mydrive){
        super();
        createFile(mydrive, null, null, "/", "rwxdr-x-");
    }
    public RootDir(){
        super();
    }

    @Override
    public void remove() throws RootDirCannotBeRemovedException{
        throw new RootDirCannotBeRemovedException();
    }

    @Override
    public Dir getParent(){
        return this;
    }
    
    @Override
    public void setOwner(User user){
        super.setOwner(user);
    }

    public void cleanup(){
        for(File f: getFileSet()){
            if(!f.getName().equals("home"))
                f.remove();
        }
    }

    @Override
    public Element xmlExport(Element xmlmydrive) {
        
        for(File f: getFileSet()){
            xmlmydrive = f.xmlExport(xmlmydrive);
        }
        return xmlmydrive;
    }
}
