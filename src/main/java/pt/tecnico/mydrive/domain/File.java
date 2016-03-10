package pt.tecnico.mydrive.domain;

public abstract class File extends File_Base {
    
    protected File(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        if (checkFilename (name)== false ){
             throw new FilenameInvalidException();

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

    }
    protected abstract int getSize();
 
    protected abstract void remove(){
        setMydrive(null);
        setParent(null);
        setOwner(null);
        deleteDomainObject();
    }

    private Boolean isDir(){
        return this instanceof Dir;
    }
}

