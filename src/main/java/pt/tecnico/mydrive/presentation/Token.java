package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;

public class Token extends MyDriveCommand {

    public Token(Shell sh) { super(sh, "token", "change logged user"); }
    public void execute(String[] args) {
		
        if (args.length < 1)
		        throw new RuntimeException("USAGE: "+name()+" <username>");
		
        else{
            Long temp = getTokenByUsername(args[0]); 
            if(temp != null){
                login(args[0],temp);
            }
            else{
                throw new RuntimeException("Please login first: "+args[0]);
            }
	 	    }
    }
}
