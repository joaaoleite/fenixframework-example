package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;
import org.jdom2.Element;

public class Link extends Link_Base {

    protected Link(){
        super();
    }
    
    protected Link(MyDrive mydrive, Dir parent, User owner, String name, String mask, String content) {
        super();
    	init(mydrive, parent, owner, name, mask);
    	setContent(content);
    }

    protected File findFile(){
        String[] path = getContent().split("/");

        Dir actual;

        if(getContent().charAt(0) == '/'){

            actual = getMydrive().getRootDir();   
        }
        else{
            actual = getParent();
        }

        for(int i=1; i<path.length; i++){
            if(actual.getFileByName(path[i]).isDir())
                actual = actual.getDir(path[i]);
            else
                return (File) actual;
        }
        return null;
    }
    
    protected boolean isDir(){
        return false;
    }

    protected String readFile(){
    	return ((Link)findFile()).read();
    }    
    
    protected void writeFile(String content){
        ((Link)findFile()).write(content);
    }

    protected void execute(){
        // TODO !!!
        // @tiagofbfernandes
        //((Link)findFile()).execute();
    }

    protected String type(){
        return "Link";
    }

    public void xmlImport(Element fileElement) throws UnsupportedEncodingException {
        super.xmlImport();
    }

    public Element xmlExport(){
        super.xmlExportAttributes();
    }
}
