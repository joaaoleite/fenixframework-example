package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;

public class Login extends MyDriveCommand {

    public Login(Shell sh) { super(sh, "login", "login a user"); }
    public void execute(String[] args) {
		
        if (args.length > 2 || args.length < 1)
		        throw new RuntimeException("USAGE: "+name()+" <username> <password>");
		
        else{

            String password;
               
            if(args[0].equals("nobody")){
                if(shell().getCurrentUsername().equals("nobody"))
                    return;
                password="";
            }
            else if (args.length!=2)
                throw new RuntimeException("USAGE: "+name()+" <username> <password>");
            else password=args[1];
        	
            LoginService service = new LoginService(args[0],password);
            service.execute();
            login(args[0], service.result());
        }
	}
}
