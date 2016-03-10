package pt.tecnico.mydrive.domain;

public abstract class File extends File_Base {
    
    protected File(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super();

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

    protected abstract int getSize();
 
    protected abstract void remove(){
        setMydrive(null);
        setParent(null);
        setOwner(null);
        deleteDomainObject();
    }
}

