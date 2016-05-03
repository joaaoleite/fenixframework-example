package pt.tecnico.mydrive.service;

import org.junit.Test;
import static org.junit.Assert.*;

import pt.tecnico.mydrive.exception.*;
import pt.tecnico.mydrive.domain.*;
import pt.tecnico.mydrive.service.dto.*;

public class AddVariableTest extends AbstractServiceTest{    
    protected void populate(){
        
        MyDrive mydrive = MyDrive.getInstance();        
        User antonio = mydrive.createUser("antonio", "toni","toni","rwxd----");
    }

    @Test
    public void successAddVariable(){
        final long token = login("toni", "toni");
        final String name = "aaa";
        final String value = "bbb";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
        List<EnvDto> list = service.result();

        Env env = list.get(0);
        assertEquals("Correct name", "aaa", env.getName());
        assertEquals("Correct value", "bbb", env.getValue());
    }

    @Test
    public void sucessAddVariableOverrideAnotherName(){
        final long token = login("toni", "toni");
        final String name = "aaa";
        final String value = "bbb";
        final String value1 = "ccc";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();

        AddVariableService service1 = new AddVariableService(token, name, value1);
        service1.execute();
        List<EnvDto> list = service1.result();

        Env env = list.get(0);
        assertEquals("Correct length", 1, list.length());
        assertEquals("Correct name", "aaa", env.getName());
        assertEquals("Correct value", "ccc", env.getValue());
    }

    @Test
    public void sucessAddMultipleVariables(){
        final long token = login("toni", "toni");
        final String name = "aaa";
        final String value = "bbb";
        final String name1 = "newName"
        final String value1 = "newValue";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();

        AddVariableService service1 = new AddVariableService(token, name1, value1);
        service1.execute();
        List<EnvDto> list = service1.result();

        Env env = list.get(0);
        Env env1 = list.get(1);
        assertEquals("Correct length", 2, list.length());
        assertEquals("Correct name", "aaa", env.getName());
        assertEquals("Correct value", "bbb", env.getValue());
        assertEquals("Correct name", "newName", env1.getName());
        assertEquals("Correct value", "newValue", env1.getValue());
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void cantFindFile(){
        final long token = login("toni", "toni");
        final String name = "";
        final String value = "bbb";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void cantFindFile(){
        final long token = login("toni", "toni");
        final String name = null;
        final String value = "bbb";
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = InvalidEnvValuesException.class)
    public void cantFindFile(){
        final long token = login("toni", "toni");
        final String name = "aaa";
        final String value = null;
        
        AddVariableService service = new AddVariableService(token, name, value);
        service.execute();
    }

    @Test(expected = TokenDoesNotExistException.class)
    public void tokenExpired(){
        final long token = 1111111;
        final String filename = "teste";
        ReadFileService service = new ReadFileService(token, filename);
        service.execute();

    }
}