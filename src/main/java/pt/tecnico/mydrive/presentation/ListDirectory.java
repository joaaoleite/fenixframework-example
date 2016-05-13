package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ListDirectoryService;
import pt.tecnico.mydrive.service.ChangeDirectoryService;
import pt.tecnico.mydrive.service.dto.FileDto;

public class ListDirectory extends MyDriveCommand {

    public ListDirectory(Shell sh) { super(sh, "ls", "list the path directory"); }
    public void execute(String[] args) {

		    if (args.length > 1)
		        throw new RuntimeException("USAGE: "+ name() +" <path>");

        String actual="";
        long token = login();

        if(args.length == 1){
            ChangeDirectoryService cd = new ChangeDirectoryService(token, ".");
            cd.execute();
            actual = cd.result();
			      new ChangeDirectoryService(token, args[0]).execute();
			  }

		    ListDirectoryService list = new ListDirectoryService(token);
		    list.execute();
		    for(FileDto f : list.result()){
		        if (f.getType().equals("Link"))
		    		    System.out.println(f.getName()+" -> "+f.getContent());
		    	  else
		    		    System.out.println(f.getType()+" "+f.getPerm()+" "+f.getSize()+" "+f.getOwner()+" "+f.getId()+" "+f.getLastModification().toString()+" "+f.getName());
			  }

			  if(!actual.equals("")) new ChangeDirectoryService(token, actual).execute();
	  }
}
