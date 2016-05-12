package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;

public abstract class MyDriveCommand extends Command {

	public MyDriveCommand(Shell sh, String n) { super(sh, n); }
	public MyDriveCommand(Shell sh, String n, String h) { super(sh, n, h); }


	protected void login(String username, long token){
        shell().setToken(username, token);
    }

    protected Long getTokenByUsername(String username){
        return shell().getTokenByUsername(username);
    }

    protected String getCurrentUsername(){
        return shell().getCurrentUsername();
    }

    protected long login(){
        if(shell().getCurrentToken()==null){
            LoginService service = new LoginService("nobody","");
            service.execute();

            long token = service.result();
            shell().setToken("nobody",token);

            return token;
        }        
        return shell().getCurrentToken();
    }
}