package pt.tecnico.mydrive.presentation;
import pt.tecnico.mydrive.service.ExecuteFileService;

public class Execute extends MyDriveCommand {

    public Execute(Shell sh) { super(sh, "do", "executes the file in the path with the arguments"); }
    public void execute(String[] args) {
		if (args.length < 3)
		    throw new RuntimeException("USAGE: "+name()+" <path> <args>");
		else{
			long token = login();
			String[] str = new String[args.length-1];
			for(int i = 1; i < args.length; i++){
				str[i] =  args[i];
			}
		    new ExecuteFileService(token, args[0], str).execute();
		}
    }
}
