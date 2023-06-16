package za.co.sacpo.grantportal.xCubeLib.db;

public class workx {

    private int keyId;
    private int id;
    private String name;
    private int type;

    public workx(int keyId, int id, int type, String Name) {
        this.keyId = keyId;
        this.id = id;
        this.type = type;
        this.name = Name;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String dName) {
        this.name = dName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type= type;
    }

    @Override
    public String toString() {
        return "workx{" +
                "keyId=" + keyId +
                "Id=" + id +
                ", name=" + name +
                ", value='" + type+ '\'' +
                '}';
    }
}
