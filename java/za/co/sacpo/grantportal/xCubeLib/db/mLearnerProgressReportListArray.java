package za.co.sacpo.grantportal.xCubeLib.db;

public class mLearnerProgressReportListArray {

    String s_w_r_id,
            title,
            year,
            supervisor_status,
            number,
            show_approve_link,
            month;

    public mLearnerProgressReportListArray(String s_w_r_id, String title, String year, String supervisor_status, String number, String show_approve_link, String month) {
        this.s_w_r_id = s_w_r_id;
        this.title = title;
        this.year = year;
        this.supervisor_status = supervisor_status;
        this.number = number;
        this.show_approve_link = show_approve_link;
        this.month = month;
    }

    public String getS_w_r_id() {
        return s_w_r_id;
    }

    public void setS_w_r_id(String s_w_r_id) {
        this.s_w_r_id = s_w_r_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSupervisor_status() {
        return supervisor_status;
    }

    public void setSupervisor_status(String supervisor_status) {
        this.supervisor_status = supervisor_status;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getShow_approve_link() {
        return show_approve_link;
    }

    public void setShow_approve_link(String show_approve_link) {
        this.show_approve_link = show_approve_link;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "mLearnerProgressReportListArray{" +
                "s_w_r_id='" + s_w_r_id + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", supervisor_status='" + supervisor_status + '\'' +
                ", number='" + number + '\'' +
                ", show_approve_link='" + show_approve_link + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
