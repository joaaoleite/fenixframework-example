package pt.tecnico.mydrive.service.dto;

import org.joda.time.DateTime;


public class FileDto implements Comparable<FileDto> {
    private int id;
    private String name;
    private String type;
    private String perm;
    private int size;
    private String ownerUsername;
    private DateTime lastModification;
    
    public FileDto (int id, String name, String type, String perm, int size, String ownerUsername, DateTime lastModification){
        this.id=id;
        this.name=name;
        this.type=type;
        this.perm=perm;
        this.size=size;
        this.ownerUsername=ownerUsername;
        this.lastModification=lastModification;
    }
    public final int getId(){
        return this.id;
    }
    public final String getName(){
        return this.name;
    }
    public final String getType(){
        return this.type;
    } 
    public final String getPerm(){
        return this.perm;
    } 
    public final int getSize(){
        return this.size;
    } 
    public final String getOwner(){
        return this.ownerUsername;
    }
    @Override
    public int compareTo(FileDto other) {
         return getName().compareTo(other.getName());
    }

}
