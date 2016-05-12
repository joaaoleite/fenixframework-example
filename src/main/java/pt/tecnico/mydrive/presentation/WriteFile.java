package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.WriteFileService;

import java.util.Arrays;

public class WriteFile extends MyDriveCommand {

    public WriteFile(Shell sh) { super(sh, "update", "changes content of the path file"); }
    public void execute(String[] args) {
		if (args.length < 2)
		    throw new RuntimeException("USAGE: "+name()+" <path> <text>");
		else{
			long token = login();
			String content = String.join(" ",Arrays.asList(Arrays.copyOfRange(args, 1, args.length)));
		    new WriteFileService(token, args[0], content).execute();
		}
    }
}