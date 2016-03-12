package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

public class App extends App_Base {
    
    public App(){
        super();
    }

    public App(Dir parent, User owner, String name, String mask) {
        super();
        init(parent, owner, name, mask);
    }
    
    protected void execute(String[] args){
        String method = getContent();
        //INCOMPLETE!!!
        // @tiagofbfernandes
    }

    protected String type(){
        return "App";
    }

    public Element xmlExport(Element xmlmydrive){
        Element app = new Element("app");
        app = super.xmlExportAttributes(app);
        xmlmydrive.addContent(app);
        return xmlmydrive;
    }
}
