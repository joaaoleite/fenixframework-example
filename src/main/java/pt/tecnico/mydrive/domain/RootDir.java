package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

public class RootDir extends RootDir_Base {
    
    public RootDir(MyDrive mydrive){
        super();
        createFile(mydrive, null, null, "/", "");
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
    }
    
    @Override
    public Element xmlExport(Element xmlmydrive) {
        Element dir = new Element("rootdir");
        dir = super.xmlExportAttributes(dir);
        xmlmydrive.addContent(dir);
        for(File f: getFileSet()){
            xmlmydrive = f.xmlExport(xmlmydrive);
        }
        return xmlmydrive;
    }
}
