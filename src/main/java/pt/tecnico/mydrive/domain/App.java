package pt.tecnico.mydrive.domain;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ImportDocException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class App extends App_Base {
    
    public App(){
        super();
    }

    protected App(Dir parent, User owner, String name) {
        super();
        init(parent, owner, name);
    }
    
    public Element xmlExport(Element xmlmydrive){
        Element app = new Element("app");
        app = super.xmlExportAttributes(app);
        app.addContent(new Element("method").setText(getContent()));
        xmlmydrive.addContent(app);
        return xmlmydrive;
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        String content = new String(fileElement.getChildText("method"));
        setContent(content);
        super.xmlImport(fileElement);
    }
}
