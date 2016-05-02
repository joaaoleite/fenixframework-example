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

    public static Login  getLoginByToken(long token) throws TokenDoesNotExistException, ExpiredTokenException{
        MyDrive mydrive = MyDrive.getInstance();
        Login login=  mydrive.getLoginByToken(token);
        if (login==null){
            log.warn("token does not exist");
            throw new TokenDoesNotExistException(token);
        }
        long date = login.getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime<(date+(2*3600*1000))){
            login.refresh();
            return login;
        }
        log.warn("Expired Token");
         
        throw new ExpiredTokenException(date);
    }

    protected void verify(){
        long date = super.getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime>=(date+(2*3600000))){
            remove();
        }
    }

    private void remove(){
        setDate(null);
        setToken(null);
        setWorkingDir(null);
        setUser(null);
        deleteDomainObject();
    }
    
    public void setEnv(String name, String value){
        for(Env env : getEnvSet()){
            if(env.getName().equals(name)){
                env.setValue(value);
                return;
            }
        }
        super.addEnv(new Env(name,value));
    }

}


