package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.WriteFileService;

public class WriteFile extends MyDriveCommand {

    public WriteFile(Shell sh) { super(sh, "update", "changes content of the path file"); }
    public void execute(String[] args) {
		if (args.length < 2)
		    throw new RuntimeException("USAGE: "+name()+" <path> <text>");
		else{
			long token = login();
		    new WriteFileService(token, args[0], args[1]).execute();
		}
    }
}