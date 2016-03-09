package pt.tecnico.mydrive.domain;

public class File extends File_Base {
    
    public File(MyDrive mydrive, Dir parent, User owner, String name, DateTime last_mod, String mask) {
        super();

        setMydrive(mydrive);
        setParent(parent);
        setOwner(owner);

        int id = mydrive.getNfile();
        setId(id);
        
        //Implement name restrictions
        setName(name);
        setLast_modification(last_mod);
        setMask(mask);
    }

    protected abstract int getSize();
    protected abstract void remove(String name);
}

