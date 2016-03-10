package pt.tecnico.mydrive.domain;

public class Link extends Link_Base {
    
    protected Link(MyDrive mydrive, Dir parent, User owner, String name, String mask, String content) {
    	super(mydrive, parent, owner, name, mask);
    	setContent(content);
    }

    protected String readFile(){
    	//parse content link
    	String[] path = getContent().split("/");
        File last;
        last = getFileByName(part)
    	for (String part: path){ 
    	   if(last.isDir())
                last=last.getFileByName(part)
            else
                last.read();
    }    
}
