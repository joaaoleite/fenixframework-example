package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.ExecuteFileService;
import java.util.Arrays;

public class Execute extends MyDriveCommand {

    public Execute(Shell sh){
        super(sh, "do", "executes the file in the path with the arguments");
    }

    public void execute(String[] args) {
        if (args.length < 1)
            throw new RuntimeException("USAGE: "+name()+" <path> <args>");
        else{
          long token = login();
          
          new ExecuteFileService(token, args[0], Arrays.copyOfRange(args, 1, args.length)).execute();
        }
    }
}
