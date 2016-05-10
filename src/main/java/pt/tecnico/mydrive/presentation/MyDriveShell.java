package pt.tecnico.mydrive.presentation;

public class MyDriveShell extends Shell {

  public static void main(String[] args) throws Exception {
    
    // XML Import from args
    for (String s: args) pt.tecnico.mydrive.Main.xmlScan(new java.io.File(s));

    MyDriveShell sh = new MyDriveShell();
    sh.execute();
  }

  public MyDriveShell() { 
    super("MyDrive");
    new Login(this);
    new ChangeDirectory(this);
    new ListDirectory(this);
    new Execute(this);
    new WriteFile(this);
    new Environment(this);
    new Token(this);
  }
}
