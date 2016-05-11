package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.WriteFileService;

public class WriteFile extends MyDriveCommand {

    public WriteFile(Shell sh) { super(sh, "update", "changes content of the path file"); }
    public void execute(String[] args) {
		if (args.length < 2)
		    throw new RuntimeException("USAGE: "+name()+" <path> <text>");
		else{
			long token = login();
			String str = "";
			for(int i = 1; i < args.length; i++){
				str = str + " " + args[i];
			}
		    new WriteFileService(token, args[0], str).execute();
		}
    }
}