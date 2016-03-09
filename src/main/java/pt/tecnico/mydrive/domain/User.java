package pt.tecnico.mydrive.domain;

public class User extends User_Base {
    private Dir home;
    public User(String username, String name, String password,String umask ) {
        init(username, name, password, umask);
        //home = new Dir();
     }
    protected void init(String username, String name, String password,String umask ) {
        setUsername(username);
        setName(name);
        setPassword(password);
        setUmask(umask);
   }

    
}
