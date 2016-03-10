package pt.tecnico.mydrive.domain;

public class Link extends Link_Base {
    
    protected Link(MyDrive mydrive, Dir parent, User owner, String name, String mask, String content) {
    	super(mydrive, parent, owner, name, mask);
    	setContent(content);
    }

    protected File findFile(){
        String[] path = getContent().split("/");

        Dir actual;

        if(getContent.charAt(0) == '/'){

            actual = getMydrive().getRootDir();   
        }
        else{
            actual = getParent();
        }

        for(int i=1; i<path.lenght(); i++){
            if(actual.getFileByName(path[i]).isDir())
                actual = actual.getDir(path[i]);
            else
                return actual;
        }
    }

    protected String readFile(){
    	return findFile().read();
    }    
    
    protected void writeFile(String content){
        findFile().write(content);
    }

    protected void execute(){
        findFile().execute();
    }

}
