package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;

public class GuestUser extends GuestUser_Base {
    public GuestUser(MyDrive myDrive) { 
        super();
        init(myDrive,"nobody", "Guest", "","rwxdr-x-"); 

    } 
    @Override
    public void remove(){
        throw new GuestUserCannotBeRemovedException();
    } 
    @Override
    public void setPassword(String password){
        if(!password.equals(""))
            throw new GuestUserCannotSetPasswordException();
        super.setPassword(password);
    }

    @Override
    public boolean checkPassword(String password){
        if(password.equals(""))
            return true;
        return false;
    }

    
    public Element xmlExport(Element xmlmydrive){
        return xmlmydrive;
    }
    
}
