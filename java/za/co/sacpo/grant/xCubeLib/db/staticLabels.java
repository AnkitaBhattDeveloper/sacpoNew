package za.co.sacpo.grant.xCubeLib.db;

/**
 * Created by xcube-06 on 25/7/18.
 */

public class staticLabels {

    private int keyId;
    private String name;
    private String value;

    public staticLabels(int keyId, String dName, String dValue) {
        this.keyId = keyId;
        this.name = dName;
        this.value = dValue;
    }

    public int getKeyId() {
        return keyId;
    }

    public void setKeyId(int keyId) {
        this.keyId = keyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String dName) {
        this.name = dName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String dValue) {
        this.value= dValue;
    }

    @Override
    public String toString() {
        return "staticLabels{" +
                "keyId=" + keyId +
                ", name=" + name +
                ", value='" + value+ '\'' +
                '}';
    }
}
