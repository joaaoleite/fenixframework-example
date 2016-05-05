package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import pt.tecnico.mydrive.exception.*;

public class Dir extends Dir_Base {

    public Dir(){
        super();
    }
    
    protected Dir(Dir parent, User owner, String name) {
        super();
        init(parent, owner, name);
    }

    public File getFileByName(String name){
        if (name.equals(".")) return this;
        if (name.equals("..")) return getParent();

        for (File file: getFileSet()){
            if (file.getName().equals(name))
                return file;
        }
        return null;
    } 

    public Dir getDir(String name) throws FileDoesNotExistException, FileIsAPlainFileException{
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

    public boolean exists(String name){
        return getFileByName(name) != null;
    }

    public Dir createDir(User owner, String name) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            if(getPath().length() + name.length() > 1023) throw new PathTooLongException(name);
            Dir newDir = new Dir(this, owner, name);
            addFile(newDir);
            return newDir;  
        }
        throw new FileAlreadyExistsException(name);
    }

    public App createApp(User owner, String name) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            if(getPath().length() + name.length() > 1023) throw new PathTooLongException(name);
            return new App(this, owner, name);
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    public App createApp(User owner, String name, String content) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            App app = createApp(owner, name);
            app.setContent(content);
            return app;
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    public Link createLink(User owner, String name, String content) throws FileAlreadyExistsException, PathTooLongException, LinkCantBeEmptyException{
        if(content==null || content.equals("")){
            throw new LinkCantBeEmptyException(name);
        }
        if(exists(name) == false){
            if(getPath().length() + name.length() > 1023) throw new PathTooLongException(name);
            return new Link(this, owner, name, content);
        }    
        else
            throw new FileAlreadyExistsException(name);
    }
    public PlainFile createPlainFile(User owner, String name) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            if(getPath().length() + name.length() > 1023) throw new PathTooLongException(name);
            PlainFile file = new PlainFile(this, owner, name);
            file.write("");
            return file;
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    public PlainFile createPlainFile(User owner, String name, String content) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            PlainFile newPlainFile = createPlainFile(owner, name);
            newPlainFile.write(content);
            return newPlainFile; 
        }    
        else
            throw new FileAlreadyExistsException(name);   
    }

    @Override
    public void remove(){
        for(File f: getFileSet()){
            f.remove();
        }
        super.remove();
    }
    @Override
    public int getSize(){
        return (2+ getFileSet().size());
    }

    @Override
    public boolean isDir(){
        return true;    
    }

    public Element xmlExport(Element xmlmydrive) {
        if(!(getName().equals("home") && getPath().equals("/")) &&
           !(getName().equals("root") && getPath().equals("/home"))){

            Element dir = new Element("dir");
            dir = super.xmlExportAttributes(dir);
            xmlmydrive.addContent(dir);
        }

        for(File f: getFileSet()){
            xmlmydrive = f.xmlExport(xmlmydrive);
        }
        return xmlmydrive;
    }
}
