package pt.tecnico.mydrive.presentation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public void print(String s) { shell.print(s); }
    public void println(String s) { shell.println(s); }
    public void flush() { shell.flush(); }


}
