package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;

import org.jdom2.Element;

public class App extends App_Base {
    
    public App(){
        super();
    }

    public App(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }
    
    protected void execute(String[] args){
        String method = getContent();
        //INCOMPLETE!!!
        // @tiagofbfernandes
    }

    protected String type(){
        return "App";
    }

    public void xmlImport(Element fileElement) {
        super.xmlImport(fileElement);
    }

    public Element xmlExport() throws ExportDocException{
        return super.xmlExport();
    }
}
