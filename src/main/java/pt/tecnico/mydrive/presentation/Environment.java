package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.AddVariableService;
import pt.tecnico.mydrive.service.dto.EnvDto;

import java.util.ArrayList;
import java.util.List;



public class Environment extends MyDriveCommand {

    public Environment(Shell sh) { super(sh, "env", "list or set a environment var"); }
    public void execute(String[] args) {

		    if (args.length > 2)
		        throw new RuntimeException("USAGE: "+name()+" [<name> [<value>]]");
		    
        else{
			      long token = login();
			      AddVariableService service;
			      List<EnvDto> list;

			      System.out.println("Environment variables:");

			      if (args.length == 0) {
			          service = new AddVariableService(token, null, null);
			          service.execute();
			          list = service.result();
			      } 
			      else if (args.length == 1) {
			          service = new AddVariableService(token, args[0], null);
			          service.execute();
			          list = service.result();
			          if(list.size()==0){
			    	        list = new ArrayList<EnvDto>();
			    	        list.add(new EnvDto(args[0],null));
			          }
            } else {
                service = new AddVariableService(token, args[0], args[1]);
                service.execute();
                list = service.result();
            }			
		    
            for(EnvDto env : list)
                System.out.println(env.getName()+" = "+env.getValue());
        }
    }
}

