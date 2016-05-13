package pt.tecnico.mydrive.presentation;

import pt.tecnico.mydrive.service.LoginService;

public class MyDriveShell extends Shell {

  public static void main(String[] args) throws Exception {

    // XML Import from args
    for (String s: args) pt.tecnico.mydrive.Main.xmlScan(new java.io.File(s));

    MyDriveShell sh = new MyDriveShell();
    sh.execute();
  }

  public MyDriveShell() { 
    super("MyDrive");

    LoginService service = new LoginService("nobody","");
    service.execute();

    long token = service.result();
    setToken("nobody",token);
    
    new Login(this);
    new ChangeDirectory(this);
    new ListDirectory(this);
    new Execute(this);
    new WriteFile(this);
    new Environment(this);
    new Token(this);
  }
}
