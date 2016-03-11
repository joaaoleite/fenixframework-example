package pt.tecnico.mydrive.domain;

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

    protected File getFileByName(String name){
        if (name.equals(".")) return this;
        if (name.equals("..")) return getParent();

        for (File file: getFileSet()){
            if (file.getName().equals(name))
                return file;
        }
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

    protected boolean exists(String name){
        return getFileByName(name) != null;
    }

    protected Dir createDir(User owner, String name, String mask) throws FileAlreadyExistsException{
        if(exists(name) == false){
            Dir newDir = new Dir(getMydrive(), this, owner, name, mask);
            addFile(newDir);
            return newDir;  
        }
        throw new FileAlreadyExistsException(name);
    }

    protected String listDir(){
        String list = "";
        for (File f : getFileSet())
            list += (f.getName() + "\n");
        
        return list;
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
    @Override
    protected int getSize(){
        return (2+ getFileSet().size());
    }

    @Override
    protected boolean isDir(){
        return true;    
    }

    @Override
    protected String type(){
        return "Dir";    
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
