package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ChangeDirectoryService;

public class ChangeDirectory extends MyDriveCommand {

    public ChangeDirectory(Shell sh) { super(sh, "cwd", "change working directory"); }
    public void execute(String[] args) {
		if (args.length < 1)
		    throw new RuntimeException("USAGE: "+name()+" <path>");
		else{
			long token = login();
		    ChangeDirectoryService service = new ChangeDirectoryService(token, args[0]);
			service.execute();
			String actual = service.result();
			System.out.println(actual);
	    }
	}
}