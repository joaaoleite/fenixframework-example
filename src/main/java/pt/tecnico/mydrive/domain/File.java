package pt.tecnico.mydrive.domain;

import java.util.Collections;
import java.util.Comparator;
import java.util.Set;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.*;

public abstract class File extends File_Base{

    public File(){
        super();
    }
    
    protected File( Dir parent, User owner, String name) {
        super();
        init( parent, owner, name);
    }


    protected void init(Dir parent, User owner, String name) throws FilenameInvalidException {
        if (!checkFilename(name)){
             throw new FilenameInvalidException(name);

        }
        MyDrive mydrive=parent.getMydrive();

        String mask = null;
        if(owner!=null)
            mask = owner.getUmask();

        createFile(mydrive,parent, owner, name, mask);
    }

    protected void createFile(MyDrive mydrive, Dir parent, User owner, String name, String mask){
        setMydrive(mydrive);
        setParent(parent);
        setOwner(owner);
        
        int id = mydrive.generateId();
        setId(id);
        
        setName(name);
        DateTime last_mod = new DateTime();
        setLastModification(last_mod);
        setMask(mask);
    }
    
    private boolean checkFilename(String name) {
        return !(name.contains("/") || name.contains("\0") || name.equals("..") || name.equals("."));
    }

    public void removeR(){
        remove();
    }

    public Set<File> getChildren(Set<File> list){
        return list;
    }
    
    public String print(){
        return getClass().getSimpleName()+" "+getMask()+" "+getSize()+" "+getOwner().getUsername()+" "+getId()+" "+getLastModification().toString()+" "+getName()+"\n";
    }
    
    public String getPath(){
        String path = "";

        for(Dir dir = getParent();
            dir.getId()!=0;
            dir = dir.getParent()){

            path = "/" + dir.getName() + path;
        }
        if(path==""){ return "/"; } 
        return path;
    }

    public void remove(){
        setMydrive(null);
        setParent(null);
        setOwner(null);
        deleteDomainObject();
    }

    public boolean isDir(){
        return false;
    }

    protected abstract int getSize();
    
    public void xmlImport(Element element) throws DataConversionException {
        String name = element.getChildText("name");
        String path = element.getChildText("path");
        User owner = getMydrive().getUserByUsername(new String(element.getAttribute("owner").getValue()));
        if (element.getChildText("perm") != ""){
            setMask(element.getChildText("perm"));
        }else{
            setMask(owner.getUmask());
        }

        int id = element.getAttribute("id").getIntValue();
        
        setName(name);

        String[] parts = path.split("/");

        Dir actual = getMydrive().getRootDir();

        for(int i = 1; i < parts.length - 1; i++){
            if(actual.exists(parts[i])){
                actual = actual.getDir(parts[i]);
            }
            else{
                actual = actual.createDir(getMydrive().getSuperUser(), parts[i]);
            }
        }

        setParent(actual);
        actual.addFile(this);
        setOwner(owner);
        setId(id);
    }

    public abstract Element xmlExport(Element xmlmydrive);

    public Element xmlExportAttributes(Element element) {

        element.setAttribute("id", getId().toString());
        element.addContent(new Element("name").setText(getName()));
        element.addContent(new Element("path").setText(getPath()));
        element.addContent(new Element("owner").setText(getOwner().getUsername()));
        element.addContent(new Element("perm").setText(getMask()));
        element.addContent(new Element("lastModification").setText(getLastModification().toString()));
        return element;
    }
}

