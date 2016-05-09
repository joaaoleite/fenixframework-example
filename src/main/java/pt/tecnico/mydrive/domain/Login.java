package pt.tecnico.mydrive.domain;
import java.math.BigInteger;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.tecnico.mydrive.exception.*;

public class Login extends Login_Base{

    static final Logger log = LogManager.getRootLogger();

    protected Login( String username) {
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
    public Long getDate(){
        if (getUser().getUsername().equals("nobody")){
            return System.currentTimeMillis();
        }
        return super.getDate();
    }

    @Override
    public void setDate(Long date){}


    
    protected void refresh(){
        super.setDate(System.currentTimeMillis());
    }

    

    protected void verify(){
        long date = getDate();
        long currentTime = System.currentTimeMillis();
        if(currentTime>=(date+(2*3600000))){
            remove();
        }
    }

    protected void remove(){
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


