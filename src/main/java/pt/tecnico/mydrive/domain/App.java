package pt.tecnico.mydrive.domain;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ImportDocException;

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
    }

    protected String type(){
        return "App";
    }

    public Element xmlExport(Element xmlmydrive){
        Element app = new Element("app");
        app = super.xmlExportAttributes(app);
        app.addContent(new Element("method").setText(getContent()));
        xmlmydrive.addContent(app);
        return xmlmydrive;
    }

    public void print(){
        System.out.println("The mydrive has a app with path: " + getPath() + " , name: " + getName() + ", owner: " + getOwner().getUsername() + ", perm: " + getMask() + ", method: " + getContent());
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        String content = new String(fileElement.getChildText("method"));
        setContent(content);
        super.xmlImport(fileElement);
    }
}
