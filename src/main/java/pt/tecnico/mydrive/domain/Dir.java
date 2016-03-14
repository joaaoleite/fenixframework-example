package pt.tecnico.mydrive.domain;

import org.jdom2.Element;

import java.util.HashSet;
import java.util.Set;

import pt.tecnico.mydrive.exception.*;

public class Dir extends Dir_Base {

    public Dir(){
        super();
    }
    
    protected Dir(Dir parent, User owner, String name, String mask) {
        super();
        init(parent, owner, name, mask);
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

    protected boolean exists(String name){
        return getFileByName(name) != null;
    }

    public Dir createDir(User owner, String name, String mask) throws FileAlreadyExistsException{
        if(exists(name) == false){
            Dir newDir = new Dir(this, owner, name, mask);
            addFile(newDir);
            return newDir;  
        }
        throw new FileAlreadyExistsException(name);
    }

    public String listDir(){
        String list = ".\n..";
        for (File f : getFileSet())
            list += ("\n" + f.getName());
        
        return list;
    }

    public PlainFile createPlainFile(User owner, String name, String mask) throws FileAlreadyExistsException{
        if(exists(name) == false){
            return new PlainFile( this, owner, name, mask);
        }    
        else
            throw new FileAlreadyExistsException(name);
    }

    public PlainFile createPlainFile(User owner, String name, String mask, String content) throws FileAlreadyExistsException{
        PlainFile newPlainFile = createPlainFile(owner, name, mask);
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

    protected void removeR() throws DirectoryIsNotEmptyException{
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
    protected boolean isDir(){
        return true;    
    }
    
    public void print(){
        System.out.println("The mydrive has a dir with path: " + getPath() + " , name: " + getName() + ", owner: " + getOwner().getUsername() + ", perm: " + getMask());
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
