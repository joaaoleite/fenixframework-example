package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

public class SuperUser extends SuperUser_Base {
    public SuperUser(MyDrive myDrive) { 
        super();
        init(myDrive,"root", "SuperUser", "***","rwxdr-x-"); 
    } 
    @Override
    public void remove(){
    }
    
    public Element xmlExport(Element xmlmydrive){
        return xmlmydrive;
    }
    
}
