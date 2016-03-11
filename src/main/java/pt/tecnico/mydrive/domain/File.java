package pt.tecnico.mydrive.domain;

import java.io.UnsupportedEncodingException;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.*;

public abstract class File extends File_Base {

    protected File(){}
    
    protected File(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }


    protected void init(MyDrive mydrive, Dir parent, User owner, String name, String mask) throws FilenameInvalidException {
        if (!checkFilename(name)){
             throw new FilenameInvalidException(name);

        }
        
    createFile(mydrive, parent, owner, name, mask);
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
        
        return path
    }

    protected  void remove(){
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

    public void xmlImport(Element fileElement) throws UnsupportedEncodingException, DataConversionException {
        String name = new String(fileElement.getAttribute("name").getValue().getBytes("UTF-8"));
        User owner = getMydrive().getUserByUsername(new String(fileElement.getAttribute("owner").getValue().getBytes("UTF-8")));
        String mask = new String(fileElement.getAttribute("mask").getValue().getBytes("UTF-8"));
        int id = fileElement.getAttribute("id").getIntValue();
        //DataType to do ... Exception too

        setName(name);
        setOwner(owner);
        setMask(mask);
        setId(id);
    }

    public abstract Element xmlExport();

    public Element xmlExportAttributes(Element element) {

        element.setAttribute("name", getName());
        element.setAttribute("owner",getOwner().getUsername());
        element.setAttribute("mask",getMask());
        element.setAttribute("lastMofification", getLastModification());
        element.setAttribute("id", getId().toString());

        return element;
    }
}

