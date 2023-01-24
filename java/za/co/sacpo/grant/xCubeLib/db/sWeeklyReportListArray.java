package za.co.sacpo.grant.xCubeLib.db;

public class sWeeklyReportListArray {

    String s_w_r_id,
            title,
            month,
            year,
            month_year,
            grant_id,
            supervisor_status,
            edit_btn,
            supervisor_status_id,
            report_no;

    public sWeeklyReportListArray(String s_w_r_id, String title, String month, String year, String month_year, String grant_id, String supervisor_status, String edit_btn, String supervisor_status_id, String report_no) {
        this.s_w_r_id = s_w_r_id;
        this.title = title;
        this.month = month;
        this.year = year;
        this.month_year = month_year;
        this.grant_id = grant_id;
        this.supervisor_status = supervisor_status;
        this.edit_btn = edit_btn;
        this.supervisor_status_id = supervisor_status_id;
        this.report_no = report_no;
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

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
    }

    public String getSupervisor_status() {
        return supervisor_status;
    }

    public void setSupervisor_status(String supervisor_status) {
        this.supervisor_status = supervisor_status;
    }

    public String getEdit_btn() {
        return edit_btn;
    }

    public void setEdit_btn(String edit_btn) {
        this.edit_btn = edit_btn;
    }

    public String getSupervisor_status_id() {
        return supervisor_status_id;
    }

    public void setSupervisor_status_id(String supervisor_status_id) {
        this.supervisor_status_id = supervisor_status_id;
    }

    public String getReport_no() {
        return report_no;
    }

    public void setReport_no(String report_no) {
        this.report_no = report_no;
    }

    @Override
    public String toString() {
        return "sWeeklyReportListArray{" +
                "s_w_r_id='" + s_w_r_id + '\'' +
                ", title='" + title + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", month_year='" + month_year + '\'' +
                ", grant_id='" + grant_id + '\'' +
                ", supervisor_status='" + supervisor_status + '\'' +
                ", edit_btn='" + edit_btn + '\'' +
                ", supervisor_status_id='" + supervisor_status_id + '\'' +
                ", report_no='" + report_no + '\'' +
                '}';
    }
}
