package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.service.dto.*;
import java.util.ArrayList;

public class AddVariableTest extends AbstractServiceTest{    
    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        mydrive.createUser("antonio", "toni","tonitoni12","rwxd----");
    }

    @Test
    public void successAddVariable(){
        final long token = login("toni", "tonitoni");
        final String name = "aaa";
        final String value = "bbb";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
        ArrayList<EnvDto> list = service.result();

        EnvDto env = list.get(0);
        assertEquals("Correct name", "aaa", env.getName());
        assertEquals("Correct value", "bbb", env.getValue());
    }

    @Test
    public void sucessAddVariableOverrideAnotherName(){
        final long token = login("toni", "tonitoni");
        final String name = "JJ";
        final String value = "Jorge";
        final String value1 = "Jesus";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();

        AddVariableService service1 = new AddVariableService(token, name, value1);
        service1.execute();
        ArrayList<EnvDto> list = service1.result();

        EnvDto env = list.get(0);
        assertEquals("Correct length", 1, list.size());
        assertEquals("Correct name", "JJ", env.getName());
        assertEquals("Correct value", "Jesus", env.getValue());
    }

    @Test
    public void sucessAddMultipleVariables(){
        final long token = login("toni", "tonitoni");
        final String name = "RV";
        final String value = "Rui";
        final String name1 = "RV1";
        final String value1 = "Vitoria";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();

        AddVariableService service1 = new AddVariableService(token, name1, value1);
        service1.execute();
        ArrayList<EnvDto> list = service1.result();

        EnvDto env = list.get(0);
        EnvDto env1 = list.get(1);
        assertEquals("Correct length", 2, list.size());
        assertEquals("Correct name", "RV", env.getName());
        assertEquals("Correct value", "Rui", env.getValue());
        assertEquals("Correct name", "RV1", env1.getName());
        assertEquals("Correct value", "Vitoria", env1.getValue());
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void AddVariableEmptyName(){
        final long token = login("toni", "tonitoni");
        final String name = "";
        final String value = "Leicester";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void AddVariableNullName(){
        final long token = login("toni", "tonitoni");
        final String name = null;
        final String value = "Vardy";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void AddVariableNullValue(){
        final long token = login("toni", "tonitoni");
        final String name = "Jamie";
        final String value = null;
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String name = "Jamie";
        final String value = "Vardy";

        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();

    }
}