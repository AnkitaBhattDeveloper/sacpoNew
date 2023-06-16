package za.co.sacpo.grantportal.xCubeLib.dataObj;


public class ChatMessageObj {
    private int usersId;
    private String message;
    private String sentAt;
    private String name;
    private String type;
    private String image;
    private String myname;
    private String otherimage;
    private String filepdf;
    private String Extension;

    public ChatMessageObj(int usersId, String message, String sentAt, String name,String type,String image,String myname,
                          String otherimage, String filepdf, String Extension) {
        this.usersId = usersId;
        this.message = message;
        this.sentAt = sentAt;
        this.name = name;
        this.type = type;
        this.image = image;
        this.myname = myname;
        this.otherimage = otherimage;
        this.filepdf = filepdf;
        this.Extension = Extension;
    }
    public String getType(){return type;}

    public int getUsersId() {
        return usersId;
    }

    public String getMessage() {
        return message;
    }

    public String getSentAt() {
        return sentAt;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }


    public String getMyImage() {
        return myname;
    }

    public String getOtherImage() {
        return otherimage;
    }
    public String getfilepdf() {
        return filepdf;
    }
    public String Extension() {
        return Extension;
    }

}