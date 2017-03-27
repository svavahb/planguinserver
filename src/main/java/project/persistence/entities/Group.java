package project.persistence.entities;
import java.util.*;

/**
 * Created by Þórunn on 11/2/2016.
 * Class for containing info about a group
 */
public class Group {

    private int grpId;
    private List<String> members = new ArrayList<>();
    private String grpName;

    public int getGrpId(){return grpId;}
    public void setGrpId(int grpId){this.grpId = grpId;}

    public String getGrpName(){return grpName;}
    public void setGrpName(String grpName){this.grpName = grpName;}

    public List<String> getMembers(){return members;}
    public void addMember(String user){members.add(user);}
    public void removeMember(String user){members.remove(user);}

    public void changeName(String newname){
        grpName = newname;
    }







}
