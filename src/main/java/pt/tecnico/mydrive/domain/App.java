package pt.tecnico.mydrive.domain;

public class App extends App_Base {
    
    public App(Mydrive mydrive, Dir parent, User owner, String name, String mask) {
        super();
        init(mydrive, parent, owner, name, mask);
    }
    
    protected void execute(String[] args){
        String method = getContent();

        //INCMPLETE!
    }

    protected String type(){
        return "App";
    }
}
