package pt.tecnico.mydrive.domain;

import org.joda.time.DateTime;

import pt.tecnico.mydrive.exception.*;

public abstract class File extends File_Base {

    protected File(){}
    
    protected File(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }


    protected void init(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        if (checkFilename (name)== false ){
             throw new FilenameInvalidException(name);

        }

        setMydrive(mydrive);
        setParent(parent);
        setOwner(owner);

        int id = mydrive.getNfile();
        setId(id);
        
        //Implement name restrictions
        setName(name);
        DateTime last_mod = new DateTime();
        setLast_modification(last_mod);
        setMask(mask);
    }

    protected String getLastModification(){
        return "";
    }

    public boolean checkFilename (String username) {
        if (username.compareTo("/")==0){
            return false;
        }
        if (username.compareTo("\0")==0){
            return false;
        }
       return true;
    }

    protected void removeR(){
        remove();
    }

    protected abstract int getSize();
 
    protected  void remove(){
        setMydrive(null);
        setParent(null);
        setOwner(null);
        deleteDomainObject();
    }

    protected abstract boolean isDir();

    protected abstract String type();

        public void xmlImport(Element fileElement) throws UnsupportedEncodingException {
        String name = new String(fileElement.getAttribute("name").getValue().getBytes("UTF-8"));
        User owner = getMydrive().getUserByUsername(new String(fileElement.getAttribute("owner").getValue().getBytes("UTF-8")));
        String mask = new String(fileElement.getAttribute("mask").getValue().getBytes("UTF-8"));
        Int id = fileElement.getAttribute("id").getIntValue();
        //DataType to do ... Exception too

        setName(name);
        setOwner(owner);
        setMask(mask);
        setId(id);
    }
}

