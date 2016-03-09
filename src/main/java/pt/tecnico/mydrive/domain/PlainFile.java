package pt.tecnico.mydrive.domain;

public class PlainFile extends PlainFile_Base {
    
    public PlainFile() {
        super();
    }
  
    protected String read(){
        return getContent();
    }

    protected void write(String content){
        setContent(content);
    }

    protected void execute(){
        String[] lines = getContent().split("\n");
        //Implement Logic
    }

    @Override
    protected void remove(){
        super.remove();
    }
}
