package pt.tecnico.mydrive.apps;
    
public class HelloWorld {
    public static void main(String[] args){
        System.out.println("Hello World!");
    }    
    public static void hello(String[] p){
        String result = "";
        for(String arg : p){
            result += "Hello " + arg + "! ";
        }

        System.out.println(result);           
    }
}
