package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.AddVariableService;
import pt.tecnico.mydrive.service.dto.EnvDto;



public class Environment extends MyDriveCommand {

    public Environment(Shell sh) { super(sh, "env", "list or set a environment var"); }
    public void execute(String[] args) {
		if (args.length > 2)
		    throw new RuntimeException("USAGE: "+name()+" [<name> [<value>]]");
		else{
			long token = login();
			AddVariableService service;

			if (agrs.length == 0) {
			    service = new AddVariableService(token, null, null);
			} 
			else if (agrs.length == 1) {
			    service = new AddVariableService(token, args[0], null);
			} else {
			    service = new AddVariableService(token, args[0], args[1]);
			}
			service.execute();
			
		    System.out.println("Environment variables:");
		    for(EnvDto env : service.result())
		    	System.out.println(env.getName()+" = "+env.getValue());
		}
    }
}