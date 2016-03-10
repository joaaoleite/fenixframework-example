package pt.tecnico.mydrive.domain;

public class Dir extends Dir_Base {
    
    private Dir(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super(mydrive, parent, owner, name, mask);
    }

    protected File getFileByName(String name) throws FileDoesNotExistException{
        for (File file: getFileSet())
            if (file.getName().equals(name))
                return file;
        throw new FileDoesNotExistException();
    } 

    protected Dir getDir(String name) throws FileDoesNotExistException, FileIsAPlainFileException{
        if (!exists(name))
            throw FileDoesNotExistException;
        
        File file = getFileByName(name);
        if (file.isDir())
            return file;

        return FileIsAPlainFileException();
    }

    protected Boolean exists(String name){
        try{
            File file = getFileByName(name);
            return true;
        }
        catch(FileDoesNotExistException e){
            return false;
        }
    }

    protected Dir createDir(User owner, String name, String mask) throws FileAlreadyExistsException{
        if (exists(name) == false){
            Dir newDir = new Dir(getMydrive(), this, owner, name, mask);
            Dir selfDir = newDir;
            selfDir.setName(".");
            Dir parentDir = this;
            parentDir.setName("..");
            newDir.addFile(selfDir);
            newDir.addFile(parentDir);
            addFile(newDir);
            return newDir;  
        }
        throw new FileAlreadyExistsException();
    }

    protected PlainFile createPlainFile(User owner, String name, String mask) throws FileAlreadyExistsException{
        if (!exists(name)){
            return new PlainFile(getMydrive(), this, owner, name, mask);
        }    
        else
            throw new FileAlreadyExistsException();
    }

    protected PlainFile createPlainFile(User owner, String name, String mask, String content) throws FileAlreadyExistsException{
        Plainfile newPlainFile = createPlainFile(getMydrive(), this, owner, name, mask);
        newPlainFile.write(content);
        return newPlainFile;    
    }
    
    @Override
    protected void remove() throws DirectoryIsNotEmptyException{
    	if(getFileSet().isEmpty()){
    		throw new DirectoryIsNotEmptyException();
    	}
    	super.remove();
    }  

    private Dir xmlCreateDir(User owner, String name, String mask){
        if(exists(name)){
            File file = getFileByName(name);
            if(file.isDir()){
                return file;
            }
            else{
                throw new  FileAlreadyExistsException();
            }  
        }
        return createDir(owner,name,mask);
    }

}
