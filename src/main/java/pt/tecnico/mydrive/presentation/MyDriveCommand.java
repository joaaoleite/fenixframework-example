package pt.tecnico.mydrive.presentation;

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
        return shell().getCurrentToken();
    }
}