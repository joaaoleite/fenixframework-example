package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;

public class GuestUser extends GuestUser_Base {
    public GuestUser(MyDrive myDrive) { 
        super();
        init(myDrive,"Guest", "Guest", "","rwxdr-x-"); 

    } 
    @Override
    public void remove(){
        throw new GuestUserCannotBeRemovedException();
    } 
    @Override
    public void setPassword(String password){
        throw new GuestUserCannotSetPasswordException();
    }

    
    public Element xmlExport(Element xmlmydrive){
        return xmlmydrive;
    }
    
}
