package pt.tecnico.mydrive.domain;

public class Dir extends Dir_Base {
    
    protected Dir(MyDrive mydrive, Dir parent, User owner, String name, String mask) {
        super(mydrive, parent, owner, name, mask);
    }

    protected File getFileByName(String name) throws FileDoesNotExistException{
        for (File file: getFileSet())
            if (file.getName().equals("."))
                return this;
            if (file.getName().equals(".."))
                return getParent();

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
            addFile(newDir);
            return newDir;  
        }
        throw new FileAlreadyExistsException();
    }

    protected PlainFile createPlainFile(User owner, String name, String mask) throws FileAlreadyExistsException{
        if (!exists(name)){
            return new PlainFile(getMydrive(), this, owner, name, mask);
        }    
        throw new FileAlreadyExistsException();
    }

    protected PlainFile createPlainFile(User owner, String name, String mask, String content) throws FileAlreadyExistsException{
        Plainfile newPlainFile = createPlainFile(getMydrive(), this, owner, name, mask);
        newPlainFile.write(content);
        return newPlainFile;    
    }

    private String listDir(){
        String output = (type()+" "+getMask()+" "+getSize()+" "+getOwner().getUsername()+" "+getId()+" "+getLastModification()+" "+getName()+"\n"
            +getParent().type()+" "+getParent().getMask()+" "+getParent().getSize()+" "+getParent().getOwner().getUsername()+" "+getParent().getId()+" "+getParent().getLastModification()+" "+getParent().getName()+"\n");
        
        for (File file: getFileSet()){
            if (file.isLink()){
                output += (file.getName()+"->"+file.getContent());
            }
            else
                output += (file.type()+" "+file.getMask()+" "+file.getSize()+" "+file.getOwner().getUsername()+" "+file.getId()+" "+file.getLastModification()+" "+file.getName()+"\n");
        }
        return output;
    }

    private Int getDirSize(){
        return (2 + getFileSet.size());
    }
    
    @Override
    protected void remove() throws DirectoryIsNotEmptyException{
    	if(getFileSet().isEmpty()){
    		throw new DirectoryIsNotEmptyException();
    	}
    	super.remove();
    }  

    protected void removeR(){
        for(File f: getFileSet())
            f.removeR();
        f.remove();
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

    public void xmlImport(Element dirElement) {
        for (Element e: directory.getChildren("dir"){
            String name = new String(fileElement.getAttribute("name").getValue().getBytes("UTF-8"));
            User owner = getMydrive().getUserByUsername(new String(fileElement.getAttribute("owner").getValue().getBytes("UTF-8")));
            String mask = new String(fileElement.getAttribute("mask").getValue().getBytes("UTF-8"));
            Dir dir = createDir(owner, name, mask);
            dir.xmlImport(e);
        }
    }

    public Element xmlExport(){
        Element element = new Element("dir");
        element.setAttribute("name", getName());
        element.setAttribute("owner",getOwner());
        element.setAttribute("mask",getMask());

        for(File f: getFileSet())
            element.addContent(f.xmlExport());
        return element;
    }
}
