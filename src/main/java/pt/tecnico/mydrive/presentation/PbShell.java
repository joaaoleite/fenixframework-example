package pt.tecnico.mydrive.presentation;

public class PbShell extends Shell {

  public static void main(String[] args) throws Exception {
    PbShell sh = new PbShell();
    sh.execute();
  }

  public PbShell() { 
    super("MyDrive");
    new Login(this);
    new ChangeDirectory(this);
    //new ListDirectory(this);
    //new Execute(this);
    new WriteFile(this);
    new Environment(this);
    //new Key(this);
  }
}