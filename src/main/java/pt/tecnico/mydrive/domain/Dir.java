package pt.tecnico.mydrive.domain;

public class Dir extends Dir_Base {
    
    public Dir() {
        super();
    }

    protected getFile() throws FileDoesNotExistException{

    }

    
    @Override
    protected void remove() throws DirectoryIsNotEmptyException{
    	if(getFileSet().isEmpty()){
    		throw new DirectoryIsNotEmptyException();
    	}
    	super.remove();
    }  
}
