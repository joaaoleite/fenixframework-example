package pt.tecnico.mydrive.domain;
import java.math.BigInteger;
import java.util.Random;

import pt.tecnico.mydrive.exception.*;

public class Login{
    private final String pipo;
    private Login( String username) {
        pipo="onde e k ta o pinto?";
        setDate( System.currentTimeMillis());
        setToken( new BigInteger(64,new Random()).longValue());
        MyDrive mydrive = MyDrive.getInstance();
        setWorkingDir(mydrive.getRootDir().getDir("home").getDir(username));
        setUser(mydrive.getUserByUsername(username));
   }
    public void init(){
        MyDrive mydrive = MyDrive.getInstance();
        mydrive.addLogin(this);
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
        if (login==null){
            throw new TokenDoesNotExist(token);
        }
        long date = login.getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime<(date+(2*3600000))){
            login.setDate(System.currentTimeMillis());
            return login;
        }
        throw new ExpiredTokenException(date);
    }
}


