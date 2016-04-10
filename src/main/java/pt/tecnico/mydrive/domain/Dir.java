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

    public String listDir(){
        String output = "";
        
        ArrayList<String> str = new ArrayList<String>();

        for(File file : getFileSet()){
            str.add(file.getName());
        }

        Collections.sort(str);

        for(String filename : str){
            output += getFileByName(filename).print();   
        }

        output = getClass().getSimpleName()+" "+getMask()+" "+getSize()+" "+getOwner().getUsername()+" "+getId()+" "+getLastModification().toString()+" "+"."+"\n"
        +getParent().getClass().getSimpleName()+" "+getParent().getMask()+" "+getParent().getSize()+" "+getParent().getOwner().getUsername()+" "+getParent().getId()+" "+getParent().getLastModification().toString()+" "+".."+"\n"
        +output;

        return output;
        
    }

    public App createApp(User owner, String name) throws FileAlreadyExistsException{
        return null;
    }

    public App createApp(User owner, String name, String content) throws FileAlreadyExistsException{
        return null;
    }

    public Link createLink(User owner, String name) throws FileAlreadyExistsException{
        return null;
    }

    public Link createLink(User owner, String name, String content) throws FileAlreadyExistsException{
        return null;
    }
    public PlainFile createPlainFile(User owner, String name) throws FileAlreadyExistsException, PathTooLongException{
        if(exists(name) == false){
            if(getPath().length() + name.length() > 1023) throw new PathTooLongException(name);
            return new PlainFile( this, owner, name);
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    public PlainFile createPlainFile(User owner, String name, String content) throws FileAlreadyExistsException, PathTooLongException{
        PlainFile newPlainFile = createPlainFile(owner, name);
        newPlainFile.write(content);
        return newPlainFile;    
    }
    
    @Override
    public void remove() throws DirectoryIsNotEmptyException{
        if(!getFileSet().isEmpty()){
            throw new DirectoryIsNotEmptyException(getName());
        }
        super.remove();
    }

    public void removeR() throws DirectoryIsNotEmptyException{
        for(File f: getFileSet()){
            f.removeR();
        }
        remove();
    }
    @Override
    protected int getSize(){
        return (2+ getFileSet().size());
    }

    @Override
    public boolean isDir(){
        return true;    
    }
    
    public Set<File> getChildren(Set<File> list){
        for(File f: getFileSet()){
            list = f.getChildren(list);
            list.add(f);
        }
        return list;
    }

    public Set<File> getChildren(){
        return getChildren(new HashSet<File>());
    }

    public Element xmlExport(Element xmlmydrive) {
        Element dir = new Element("dir");
        dir = super.xmlExportAttributes(dir);
        xmlmydrive.addContent(dir);
        for(File f: getFileSet()){
            xmlmydrive = f.xmlExport(xmlmydrive);
        }
        return xmlmydrive;
    }
}
