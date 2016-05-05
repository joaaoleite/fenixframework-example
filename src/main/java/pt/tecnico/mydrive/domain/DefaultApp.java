package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;

public class DefaultApp extends DefaultApp_Base {

    public DefaultApp(String extension, App app, User user) { 
        super();
        setExtension(extension);
        setApp(app);
        setUser(user); 

    }    
}