package pt.tecnico.mydrive.service;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import pt.tecnico.mydrive.service.dto.EnvDto;

public class AddVariableService extends MyDriveService{
	  MyDrive md = MyDrive.getInstance();

    private String name;
    private String value;
    private ArrayList<EnvDto> res;
    private long token;

    public AddVariableService(long token, String name, String value){
    	  super();
    	  this.name = null;
    	  this.value = null;
    }
    
    public final ArrayList<EnvDto> result(){
        return res;
    }

    protected final void dispatch() throws TokenDoesNotExistException, ExpiredTokenException, InvalidEnvValuesException {
        
        Login login = Login.getLoginByToken(token);

        if(this.name==null || this.value==null || this.name.equals(""))
            throw new InvalidEnvValuesException();
        
        login.setEnv(name,value);

        res = new ArrayList<EnvDto>();
        for(Env e : login.getEnvSet()){
            res.add(new EnvDto(e.getName(),e.getValue()));
        }
    }
}
