package za.co.sacpo.grant.xCubeLib.model;

public class WeeklyRepport {

    private int id;
    private String name;

    public WeeklyRepport(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}


