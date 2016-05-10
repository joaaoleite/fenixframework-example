package pt.tecnico.mydrive.service;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import pt.ist.fenixframework.Atomic;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.exception.*;

import pt.tecnico.mydrive.service.dto.EnvDto;

public class AddVariableService extends MyDriveService{

    private String name;
    private String value;
    private ArrayList<EnvDto> res;
    private long token;
    private boolean printOne = false;

    public AddVariableService(long token, String name, String value){
        super();
        this.token = token;
    	  this.name = name;
    	  this.value = value;
    }
    
    public AddVariableService(long token){
    	  super();
        this.token = token;
    	  this.name = null;
        this.value = null;
    }

    public final ArrayList<EnvDto> result(){
        return res;
    }

    protected final void dispatch() throws TokenDoesNotExistException, ExpiredTokenException, InvalidEnvValuesException {
        
        Login login = getMyDrive().getLoginByToken(token);

        if(this.name==null && this.value!=null)
            throw new InvalidEnvValuesException();
 
        if(this.name!=null){
            if(this.name.equals(""))
                throw new InvalidEnvValuesException();
            if(this.value!=null)
                login.setEnv(name,value);
            else this.printOne=true;
        }

        res = new ArrayList<EnvDto>();
        for(Env e : login.getEnvSet()){
            if(e.getName().equals(this.name)||!this.printOne)
                res.add(new EnvDto(e.getName(),e.getValue()));
        }
        Collections.sort(res);
    }
}
