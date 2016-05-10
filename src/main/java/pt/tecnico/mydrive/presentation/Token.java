package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;

public class Token extends MyDriveCommand {

    public Token(Shell sh) { super(sh, "token", "change logged user"); }
    public void execute(String[] args) {
		
        if (args.length > 1)
		        throw new RuntimeException("USAGE: "+name()+" <username>");
		else if (args.length < 1){
            Long token = login();
            String username = getCurrentUsername();
            System.out.println(token + " -> " + username);
        }
        else{
            Long temp = getTokenByUsername(args[0]); 
            if(temp != null){
                login(args[0],temp);
                Long token = login();
                System.out.println(token);
            }
            else{
                throw new RuntimeException("Please login first: " + args[0]);
            }
	 	}
    }
}
