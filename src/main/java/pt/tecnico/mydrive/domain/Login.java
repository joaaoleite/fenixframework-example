package pt.tecnico.mydrive.domain;
import pt.tecnico.mydrive.exception.*;
import java.lang.*;

public class Login{
    private final String pipo;
    private final int token;
    private final long date;
    private final Dir workingDir;
    private final String logUser;
    private Login( String username) {
        pipo="onde e k ta o pinto?";
        date= System.currentTimeMillis();
        token=newBigInteger(64,newRandom()).longValue();
        MyDrive mydrive = MyDrive.getInstance();
        workingDir = mydrive.getRootDir().getDir("home").getDir(username);
        logUser= mydrive.getUserByUsername(username);
   }
    public void init(){
        MyDrive mydrive = MyDrive.getInstance();
        mydrive.add(token,this);
    }
    public long getToken(){
        return token;
    }
    
    public User getUser(){
        return logUser;
    }
    public long getDate(){
        return date;
    }
    public static Login signIn(String username, String password){
          
        MyDrive mydrive = MyDrive.getInstance();
        User user = mydrive.getUserByUsername(username);          
        if (username==null){
            throw new LoginFailedException();
        }
      
        if (password.compareTo(user.getPassword())!=0){
            throw new LoginFailedException();
        }
        return new Login(username);
    
        
    }

    public static Login  getLoginByToken(int token){
        MyDrive mydrive = MyDrive.getInstance();
        Login login=  mydrive.getLoginByToken(token);
        long date = login.getDate();
        long currentTime= System.CurrentTimeMillis();
        if(currentTime<(date+(2*3 600 000))){
            return login;
        }
        throw new ExpiredTokenException(date);
    }
}


