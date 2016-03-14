package pt.tecnico.mydrive.domain;


import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.*;

public abstract class File extends File_Base {

    public File(){}
    
    public File( Dir parent, User owner, String name, String mask) {
        super();
        init( parent, owner, name, mask);
    }


    protected void init(Dir parent, User owner, String name, String mask) throws FilenameInvalidException {
        if (!checkFilename(name)){
             throw new FilenameInvalidException(name);

        }
        MyDrive mydrive=parent.getMydrive();
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

    public boolean checkFilename(String name) {
        return !(name.contains("/") || name.contains("\0") || name.equals("..") || name.equals("."));
    }

    protected void removeR(){
        remove();
    }
    
    protected String getPath(){
        String path = "/" + getName();

        for(Dir dir = getParent();
            dir.equals(getParent());
            dir = dir.getParent()){

            path = "/" + dir.getName() + path;
        }
        
        return path;
    }

    public void remove(){
        setMydrive(null);
        setParent(null);
        setOwner(null);
        deleteDomainObject();
    }

    protected boolean isDir(){
        return false;
    }

    protected abstract int getSize();
    
    protected abstract String type();

    public void xmlImport(Element fileElement) throws DataConversionException {
        String name = new String(fileElement.getAttribute("name").getValue());
        String path = new String(fileElement.getAttribute("path").getValue());
        User owner = getMydrive().getUserByUsername(new String(fileElement.getAttribute("owner").getValue()));
        String mask = new String(fileElement.getAttribute("mask").getValue());
        int id = fileElement.getAttribute("id").getIntValue();

        setName(name);

        String[] parts = path.split("/");

        Dir actual = getMydrive().getRootDir();

        for(int i = 1; i < parts.length - 1; i++){
            if(actual.exists(parts[i])){
                actual = actual.getDir(parts[i]);
            }
            else{
                actual = actual.createDir(getMydrive().getSuperUser(), parts[i], "");
            }
        }

        setParent(actual);
        actual.addFile(this);
        setOwner(owner);
        setMask(mask);
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

