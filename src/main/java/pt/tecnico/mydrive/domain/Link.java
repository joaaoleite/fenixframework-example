package pt.tecnico.mydrive.domain;

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
        int i;

        if(getContent().charAt(0) == '/'){

            actual = getMydrive().getRootDir();
            i = 1;
        }
        else{
            actual = getParent();
            i = 0
        }

        for(i; i<path.length-1; i++){
            actual = actual.getDir(path[i]);
        }

        if(actual.exists(path[i++])){
            return getFileByName(path[i]);
        }

        throw new FileDoesNotExistException(path[i]);
    }
    
    protected String readFile(){
        if(isDir) throw new FileIsADirException(getName());
    	  return ((PlainFile)findFile()).read();
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

    public void xmlImport(Element fileElement) {
        super.xmlImport(fileElement);
    }

    public Element xmlExport(){
        Element link = new Element("link");
        return super.xmlExportAttributes(link);
    }
}
