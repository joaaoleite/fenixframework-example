package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.Element;

import pt.tecnico.mydrive.exception.*;

public class Dir extends Dir_Base {

    public Dir(){
        super();
    }
    
    private Dir(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }

    protected File getFileByName(String name) throws FileDoesNotExistException{
        for (File file: getFileSet())
            if (file.getName().equals(name))
                return file;
        return null;
    } 

    protected Dir getDir(String name) throws FileDoesNotExistException, FileIsAPlainFileException{
        if (exists(name) == false)
            throw new FileDoesNotExistException(name);
        else{
            File file = getFileByName(name);
            if (file.isDir()){
                return (Dir) file;
            }
            else
                throw new FileIsAPlainFileException(name);
        }
    }

    protected Boolean exists(String name){
        if (getFileByName(name) == null)
            return false;
        return true;
    }

    protected Dir createDir(User owner, String name, String mask) throws FileAlreadyExistsException{
        if(exists(name) == false){
            Dir newDir = new Dir(getMydrive(), this, owner, name, mask);
            Dir selfDir = newDir;
            selfDir.setName(".");
            Dir parentDir = this;
            parentDir.setName("..");
            newDir.addFile(selfDir);
            newDir.addFile(parentDir);
            addFile(newDir);
            return newDir;  
        }
        File file = getFileByName(name);
        if (file.isDir())
            return (Dir) file;
        throw new FileAlreadyExistsException(name);
    }

    protected PlainFile createPlainFile(User owner, String name, String mask) throws FileAlreadyExistsException{
        if(exists(name) == false){
            return new PlainFile(getMydrive(), this, owner, name, mask);
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    protected PlainFile createPlainFile(User owner, String name, String mask, String content) throws FileAlreadyExistsException{
        PlainFile newPlainFile = createPlainFile(owner, name, mask);
        newPlainFile.write(content);
        return newPlainFile;    
    }
    
    @Override
    protected void remove() throws DirectoryIsNotEmptyException{
        if(!getFileSet().isEmpty()){
            throw new DirectoryIsNotEmptyException(getName());
        }
        super.remove();
    }

    protected int getSize(){
        return (2+ getFileSet().size());
    }
    
    @Override
    public void xmlImport(Element dirElement) throws ImportDocException{
        
        try {
            for (Element e: dirElement.getChildren("dir")){
                String name = new String(e.getAttribute("name").getValue().getBytes("UTF-8"));
                User owner = getMydrive().getUserByUsername(new String(e.getAttribute("owner").getValue().getBytes("UTF-8")));
                String mask = new String(e.getAttribute("mask").getValue().getBytes("UTF-8"));
                Dir dir = createDir(owner, name, mask);
                dir.xmlImport(e);
            }
            for(Element e: dirElement.getChildren("plainfile")){
                PlainFile plain = new PlainFile();
                plain.setParent(this);
                plain.xmlImport(e);
            }
            for(Element e: dirElement.getChildren("link")){
                Link link = new Link();
                link.setParent(this);
                link.xmlImport(e);
            }
            for(Element e: dirElement.getChildren("app")){
                App app = new App();
                app.setParent(this);
                app.xmlImport(e);
            }

        } catch (UnsupportedEncodingException e) { 
            System.err.println(e); 
            throw new ImportDocException();
        }
    }

    public Element xmlExport() {
        Element element = new Element("dir");
        super.xmlExportAttributes(element);
        for(File f: getFileSet()){
            element.addContent(f.xmlExport());
        }
        return element;
    }

   @Override
   protected boolean isDir(){
        return true;    
   }

   @Override
   protected String type(){
        return "Dir";    
   }
}
