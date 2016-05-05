package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.WriteFileService;

public class Environment extends PbCommand {

    public Environment(Shell sh) { super(sh, "env", "list or set a environment var"); }
    public void execute(String[] args) {
		if (args.length > 2)
		    throw new RuntimeException("USAGE: "+name()+" [<name> [<value>]]");
		else{
			long token = login();
		    AddVariableService service = new AddVariableService(token, args[0], args[1])
		    service.execute();

		    System.out.println("Environment variables:");
		    for(EnvDto env : service.result())
		    	System.out.println(env.getName()+" -> "+env.getValue());
		}
    }
}