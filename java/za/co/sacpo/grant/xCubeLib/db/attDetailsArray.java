package za.co.sacpo.grant.xCubeLib.db;

public class attDetailsArray {


    String grant_id, student_id,  month, year, date,attendance_days, annual_leave, sick_leave, other_paid_leave,
            unpaid_leave, supervisor_comment,download_attendance,download_attendance_link,
            supervisor_approval, supervisor_comment_link;

    public attDetailsArray(String grant_id, String student_id, String month, String year, String date, String attendance_days, String annual_leave, String sick_leave, String other_paid_leave, String unpaid_leave, String supervisor_comment, String download_attendance, String download_attendance_link, String supervisor_approval, String supervisor_comment_link) {
        this.grant_id = grant_id;
        this.student_id = student_id;
        this.month = month;
        this.year = year;
        this.date = date;
        this.attendance_days = attendance_days;
        this.annual_leave = annual_leave;
        this.sick_leave = sick_leave;
        this.other_paid_leave = other_paid_leave;
        this.unpaid_leave = unpaid_leave;
        this.supervisor_comment = supervisor_comment;
        this.download_attendance = download_attendance;
        this.download_attendance_link = download_attendance_link;
        this.supervisor_approval = supervisor_approval;
        this.supervisor_comment_link = supervisor_comment_link;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAttendance_days() {
        return attendance_days;
    }

    public void setAttendance_days(String attendance_days) {
        this.attendance_days = attendance_days;
    }

    public String getAnnual_leave() {
        return annual_leave;
    }

    public void setAnnual_leave(String annual_leave) {
        this.annual_leave = annual_leave;
    }

    public String getSick_leave() {
        return sick_leave;
    }

    public void setSick_leave(String sick_leave) {
        this.sick_leave = sick_leave;
    }

    public String getOther_paid_leave() {
        return other_paid_leave;
    }

    public void setOther_paid_leave(String other_paid_leave) {
        this.other_paid_leave = other_paid_leave;
    }

    public String getUnpaid_leave() {
        return unpaid_leave;
    }

    public void setUnpaid_leave(String unpaid_leave) {
        this.unpaid_leave = unpaid_leave;
    }

    public String getSupervisor_comment() {
        return supervisor_comment;
    }

    public void setSupervisor_comment(String supervisor_comment) {
        this.supervisor_comment = supervisor_comment;
    }

    public String getDownload_attendance() {
        return download_attendance;
    }

    public void setDownload_attendance(String download_attendance) {
        this.download_attendance = download_attendance;
    }

    public String getDownload_attendance_link() {
        return download_attendance_link;
    }

    public void setDownload_attendance_link(String download_attendance_link) {
        this.download_attendance_link = download_attendance_link;
    }

    public String getSupervisor_approval() {
        return supervisor_approval;
    }

    public void setSupervisor_approval(String supervisor_approval) {
        this.supervisor_approval = supervisor_approval;
    }

    public String getSupervisor_comment_link() {
        return supervisor_comment_link;
    }

    public void setSupervisor_comment_link(String supervisor_comment_link) {
        this.supervisor_comment_link = supervisor_comment_link;
    }

    @Override
    public String toString() {
        return "attDeatilsArray{" +
                "grant_id='" + grant_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", date='" + date + '\'' +
                ", attendance_days='" + attendance_days + '\'' +
                ", annual_leave='" + annual_leave + '\'' +
                ", sick_leave='" + sick_leave + '\'' +
                ", other_paid_leave='" + other_paid_leave + '\'' +
                ", unpaid_leave='" + unpaid_leave + '\'' +
                ", supervisor_comment='" + supervisor_comment + '\'' +
                ", download_attendance='" + download_attendance + '\'' +
                ", download_attendance_link='" + download_attendance_link + '\'' +
                ", supervisor_approval='" + supervisor_approval + '\'' +
                ", supervisor_comment_link='" + supervisor_comment_link + '\'' +
                '}';
    }
}
