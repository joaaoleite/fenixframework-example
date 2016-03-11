package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

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

    public void xmlImport(Element fileElement) throws ImportDocException{

        try{
            super.xmlImport(fileElement);
            setContent(new String(fileElement.getAttribute("content").getValue().getBytes("UTF-8")));
        } catch(UnsupportedEncodingException | DataConversionException e){
            System.err.println(e);
            throw new ImportDocException();
        }
    }

    // could be an error
    public Element xmlExport(){

        Element element = new Element("plainfile");
        return super.xmlExportAttributes(element);
    }
}
