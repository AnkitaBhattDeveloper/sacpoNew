package za.co.sacpo.grant.xCubeLib.db;

public class helpArrays {

    private int keyId;
    private int id;
    private String title;
    private String content;
    private String activity;
    private int type;

    public helpArrays(int keyId, int id, int type, String Title,String Content,String Activity) {
        this.keyId = keyId;
        this.id = id;
        this.type = type;
        this.title = Title;
        this.content = Content;
        this.activity = Activity;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String dTitle) {
        this.title = dTitle;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String dContent) {
        this.content = dContent;
    }

    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type= type;
    }

    public String getActivity() {
        return activity;
    }
    public void setActivity(String dActivity) {
        this.activity = dActivity;
    }
    @Override
    public String toString() {
        return "masterArrays{" +
                "keyId=" + keyId +
                "Id=" + id +
                ", title=" + title +
                ", value='" + type+ '\'' +
                '}';
    }
}
