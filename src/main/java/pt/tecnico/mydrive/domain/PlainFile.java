package pt.tecnico.mydrive.domain;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ImportDocException;

public class PlainFile extends PlainFile_Base {

    public PlainFile(){
        super();
    }

    
    public PlainFile(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }
  
    protected String read(){
        return getContent();
    }

    protected void write(String content){
        setContent(content);
    }

    protected void execute(){
    }
    
    @Override
    protected int getSize(){
        return getContent().length();
    }

    @Override
    protected String type(){
        return "PlainFile";
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        String content = new String(fileElement.getAttribute("content").getValue());
        setContent(content);
        super.xmlImport(fileElement);
    }
    
    public Element xmlExport(Element xmlmydrive){

        Element plainfile = new Element("plainfile");
        plainfile = super.xmlExportAttributes(plainfile);
        xmlmydrive.addContent(plainfile);
        return xmlmydrive;
    }
}
