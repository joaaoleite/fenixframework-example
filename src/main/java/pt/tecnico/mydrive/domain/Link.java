package pt.tecnico.mydrive.domain;
import pt.tecnico.mydrive.exception.*;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

public class Link extends Link_Base {

    public  Link(){
        super();
    }
    
    protected Link(MyDrive mydrive, Dir parent, User owner, String name, String mask, String content) {
        super();
    	init(parent, owner, name);
    	setContent(content);
    }

    private File findFile() throws FileIsAPlainFileException, FileDoesNotExistException{
        String[] path = getContent().split("/");

        Dir actual;
        int i;

        if(getContent().charAt(0) == '/'){

            actual = getMydrive().getRootDir();
            i = 1;
        }
        else{
            actual = getParent();
            i = 0;
        }

        for(; i<path.length-1; i++){
            actual = actual.getDir(path[i]);
        }

        if(actual.exists(path[i++])){
            return actual.getFileByName(path[i]);
        }

        throw new FileDoesNotExistException(path[i]);
    }
    
    @Override
    public String read() throws FileIsADirException{
        if(isDir()) throw new FileIsADirException(getName());
    	  return ((PlainFile)findFile()).read();
    }    
    
    @Override
    public void write(String content){
        ((PlainFile)findFile()).write(content);
    }

    @Override
    protected void execute(){
    }

    @Override
    public void print(){
        System.out.println("The mydrive has a link with path: " + getPath() + " , name: " + getName() + ", owner: " + getOwner().getUsername() + ", perm: " + getMask() + ", value: " + getContent());
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        String content = new String(fileElement.getChildText("value"));
        setContent(content);
        super.xmlImport(fileElement);
    }

    @Override
    public Element xmlExport(Element xmlmydrive){
        Element link = new Element("link");
        link = super.xmlExportAttributes(link);
        link.addContent(new Element("value").setText(getContent()));
        xmlmydrive.addContent(link);
        return xmlmydrive;
    }
}
