package za.co.sacpo.grantportal.xCubeLib.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by xcube-06 on 8/8/18.
 */

public class MHolydays {
    @SerializedName("h_id")
    @Expose
    private String hId;
    @SerializedName("holiday")
    @Expose
    private String holiday;
    @SerializedName("date")
    @Expose
    private String date;

    public String getHId() {
        return hId;
    }

    public void setHId(String hId) {
        this.hId = hId;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}


