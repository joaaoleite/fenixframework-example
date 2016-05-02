package pt.tecnico.mydrive.service.dto;

public class EnvDto implements Comparable<EnvDto> {
    private String name;
    private String value;

    public EnvDto(String name, String value){
        this.name   = name;
        this.value  = value;
    }

    public final String getName(){
        return this.name;
    }

    public final String getValue(){
        return this.value;
    }
    
    @Override
    public int compareTo(EnvDto other){
        return getName().compareTo(other.getName()); 
    }
}
