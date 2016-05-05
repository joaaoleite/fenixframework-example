package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.LoginService;

public class Login extends PbCommand {

    public Login(Shell sh) { super(sh, "login", "login a user"); }
    public void execute(String[] args) {
	if (args.length < 2)
	    throw new RuntimeException("USAGE: "+name()+" <username> <password>");
	else
	    login(args[0],args[1]);
    }
}