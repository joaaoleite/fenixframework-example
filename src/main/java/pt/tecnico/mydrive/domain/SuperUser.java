package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;

public class SuperUser extends SuperUser_Base {
    public SuperUser(MyDrive myDrive) { 
        super();
        init(myDrive,"root", "SuperUser", "***","rwxdr-x-"); 
    } 
    @Override
    public void remove(){
        throw new SuperUserCannotBeRemovedException();
    }
    
    public Element xmlExport(Element xmlmydrive){
        return xmlmydrive;
    }
    
}
