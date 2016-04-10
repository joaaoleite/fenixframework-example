package pt.tecnico.mydrive.domain;
import java.math.BigInteger;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.tecnico.mydrive.exception.*;

public class Login extends Login_Base{

    static final Logger log = LogManager.getRootLogger();

    private Login( String username) {
        super.setDate( System.currentTimeMillis());
        super.setToken( new BigInteger(64,new Random()).longValue());
        MyDrive mydrive = MyDrive.getInstance();
        setWorkingDir(mydrive.getRootDir().getDir("home").getDir(username));
        super.setUser(mydrive.getUserByUsername(username));
   }
    public void init(){
        MyDrive mydrive = MyDrive.getInstance();
        mydrive.login(this);
    }
    
    @Override
    public void setToken(Long t){}

    @Override
    public void setUser(User u){}

    @Override
    public Long getToken(){
        return null;
    }

    protected Long token(){
        return super.getToken();
    }
    
    @Override
    public void setDate(Long date){}


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
    private void refresh(){
        super.setDate(System.currentTimeMillis());
    }

    public static Login  getLoginByToken(long token){
        MyDrive mydrive = MyDrive.getInstance();
        Login login=  mydrive.getLoginByToken(token);
        if (login==null){
            log.warn("token does not exist");
            throw new TokenDoesNotExistException(token);
        }
        long date = login.getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime<(date+(2*3600000))){
            login.refresh();
            return login;
        }
        log.warn("Expired Token");
        login.remove();
        throw new ExpiredTokenException(date);
    }

    public void remove(){
        setDate(null);
        setToken(null);
        setWorkingDir(null);
        setUser(null);
        deleteDomainObject();
    }
}


