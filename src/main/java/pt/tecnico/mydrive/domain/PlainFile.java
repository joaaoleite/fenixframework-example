package pt.tecnico.mydrive.domain;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ImportDocException;

public class PlainFile extends PlainFile_Base {

    public PlainFile(){
        super();
    }
    
    protected PlainFile(Dir parent, User owner, String name) {
        super();
        init(parent, owner, name);
    }
  
    public String read(){
        return getContent();
    }

    public void write(String content){
        setContent(content);
    }

    @Override
    public int getSize(){
        return getContent().length();
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        if(getContent()==null)
            setContent(new String(fileElement.getChildText("contents")));
        super.xmlImport(fileElement);
    }
    
    public Element xmlExport(Element xmlmydrive){

        Element plainfile = new Element("plain");
        plainfile = super.xmlExportAttributes(plainfile);
        plainfile.addContent(new Element("contents").setText(getContent()));
        xmlmydrive.addContent(plainfile);
        return xmlmydrive;
    }
}
