package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;
import org.jdom2.Element;

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
        String[] lines = getContent().split("\n");
        //Implement Logic
    }

    protected boolean isDir(){
        return false;
    }

    protected int getSize(){
        // TODO (length of linked file) !!!
        return getContent().length();
    }

    @Override
    protected void remove(){
        super.remove();
    }

    protected String type(){
        return "Plain File";
    }

    public void xmlImport(Element fileElement) throws ImportDocException{

        try{
            super.xmlImport(fileElement);
            setContent(new String(fileElement.getAttribute("content").getValue().getBytes("UTF-8")));
        } catch(UnsupportedEncodingException e){
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
