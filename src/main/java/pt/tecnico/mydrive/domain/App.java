package pt.tecnico.mydrive.domain;

import pt.tecnico.mydrive.exception.*;

import java.io.UnsupportedEncodingException;
import org.jdom2.Element;

public class App extends App_Base {
    
    public App() {
        super();
    }
    
    protected void execute(String[] args){
        String method = getContent();
        //INCOMPLETE!!!
        // @tiagofbfernandes
    }

    protected String type(){
        return "Application";
    }

    public void xmlImport(Element fileElement) throws UnsupportedEncodingException {
        super.xmlImport();
    }

    public Element xmlExport() throws ExportDocException{
        super.xmlExport();
    }
}
