package za.co.sacpo.grantportal.xCubeLib.dataObj;

public class CheckBoxListObj {

    boolean isClicked= false;
    String id,Name;

    public CheckBoxListObj(boolean isClicked, String id, String name) {
        this.isClicked = isClicked;
        this.id = id;
        Name = name;
    }

    public boolean isClicked() {
        return isClicked;
    }

    public void setClicked(boolean clicked) {
        isClicked = clicked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
