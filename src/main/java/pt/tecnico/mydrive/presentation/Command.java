package pt.tecnico.mydrive.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.tecnico.mydrive.service.LoginService;

public abstract class Command {

    protected static final Logger log = LogManager.getRootLogger();
    
    private String name;
    private String help;

    private Shell shell;


    public Command(Shell sh, String nm){
        this(sh,nm,"<no help>");
    }

    public Command(Shell shell, String name, String help){
        this.name = name;
        this.help = help;
        (this.shell = shell).add(this);
    }

    protected void help(String h){
        this.help = h;
    }

    public String name() { return name; }
    public String help() { return help; }
    public Shell shell() { return shell; }

    abstract void execute(String[] args);

    protected void login(String username, long token){
        shell.setToken(username, token);
    }

    protected long getTokenByUsername(String username){
        return shell.getTokenByUsername(username);
    }

    protected long login(){
        if(shell.getCurrentToken()==null){
            LoginService service = new LoginService("guest","");
            service.execute();

            long token = service.result();
            shell.setToken("guest",token);

            return token;
        }        
        return shell.getCurrentToken();
    }

    public void print(String s) { shell.print(s); }
    public void println(String s) { shell.println(s); }
    public void flush() { shell.flush(); }


}
