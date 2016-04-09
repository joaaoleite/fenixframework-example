package pt.tecnico.mydrive.domain;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import pt.tecnico.mydrive.exception.ImportDocException;

public class App extends App_Base {
    
    public App(){
        super();
    }

    protected App(Dir parent, User owner, String name, String mask) {
        super();
        init(parent, owner, name, mask);
    }
    
    protected Object execute(String[] args){
        String[] str = getContent().split(" ");
        String[] exec = str[0].split(".");
        str = Arrays.copyOfRange(str, 1, str.length);
        Class cls = Class.forName(exec[0] + exec[1]);
        Object obj = cls.newInstance();
       
        if(exec.length == 3)
            Method method = cls.getDeclaredMethod(exec[2], String[].class);
        else
            Method method = cls.getDeclaredMethod("main", String[].class);
        
        Object obj = method.invoke(obj, str);

        return obj;
    }

    public Element xmlExport(Element xmlmydrive){
        Element app = new Element("app");
        app = super.xmlExportAttributes(app);
        app.addContent(new Element("method").setText(getContent()));
        xmlmydrive.addContent(app);
        return xmlmydrive;
    }

    public void print(){
        System.out.println("The mydrive has a app with path: " + getPath() + " , name: " + getName() + ", owner: " + getOwner().getUsername() + ", perm: " + getMask() + ", method: " + getContent());
    }

    @Override
    public void xmlImport(Element fileElement) throws ImportDocException, DataConversionException{
        String content = new String(fileElement.getChildText("method"));
        setContent(content);
        super.xmlImport(fileElement);
    }
}
