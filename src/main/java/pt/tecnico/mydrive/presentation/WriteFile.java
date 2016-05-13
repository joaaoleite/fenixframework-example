package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.WriteFileService;
import pt.tecnico.mydrive.service.ChangeDirectoryService;

import java.util.Arrays;

public class WriteFile extends MyDriveCommand {

    public WriteFile(Shell sh) { super(sh, "update", "changes content of the path file"); }
    public void execute(String[] args) {
		if (args.length < 2)
		    throw new RuntimeException("USAGE: "+name()+" <path> <text>");
		else{
			long token = login();

			ChangeDirectoryService cd = new ChangeDirectoryService(token, ".");
			cd.execute();
			String actual = cd.result();

			String content = String.join(" ",Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
		    
		    String filename = args[0].split("/")[args[0].split("/").length-1];
        int index = args[0].lastIndexOf("/");
        String path = ".";
        
        if(index>0) path = args[0].substring(0,index);

		    new ChangeDirectoryService(token, path).execute();

		    new WriteFileService(token, filename, content).execute();

		    new ChangeDirectoryService(token, actual).execute();
		}
    }
}
