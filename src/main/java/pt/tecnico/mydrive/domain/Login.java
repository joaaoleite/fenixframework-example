package pt.tecnico.mydrive.domain;
import java.math.BigInteger;
import java.util.Random;

import pt.tecnico.mydrive.exception.*;

public class Login{
    private final String pipo;
    private final long token;
    private final long date;
    private Dir workingDir;
    private final User logUser;
    private Login( String username) {
        pipo="onde e k ta o pinto?";
        date= System.currentTimeMillis();
        token = new BigInteger(64,new Random()).longValue();
        MyDrive mydrive = MyDrive.getInstance();
        workingDir = mydrive.getRootDir().getDir("home").getDir(username);
        logUser= mydrive.getUserByUsername(username);
   }
    public void init(){
        MyDrive mydrive = MyDrive.getInstance();
        mydrive.addHash(token,this);
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

    public Dir getWorkingDir(){
        return workingDir;
    }

    public void setWorkingDir(Dir newDir){
        workingDir = newDir;
    }
    public static Login signIn(String username, String password){
          
        MyDrive mydrive = MyDrive.getInstance();
        User user = mydrive.getUserByUsername(username);          
        if (user==null){
            throw new LoginFailedException();
        }
      
        if (password.compareTo(user.getPassword())!=0){
            throw new LoginFailedException();
        }
        return new Login(username);
    
        
    }

    public static Login  getLoginByToken(long token){
        MyDrive mydrive = MyDrive.getInstance();
        Login login=  mydrive.getLoginByToken(token);
        long date = login.getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime<(date+(2*3600000))){
            return login;
        }
        throw new ExpiredTokenException(date);
    }
}


