package za.co.sacpo.grant.xCubeLib.db;

public class mPastAttendanceArray {

    String grant_id,
            student_id,
            month,
            year,
            days_worked,
            leave,
            annual_leave,
            sick_leave,
            other_paid_leave,
            unpaid_leave,
            sno,
            year_month;

    public mPastAttendanceArray(String grant_id, String student_id, String month, String year, String days_worked, String leave, String annual_leave, String sick_leave, String other_paid_leave, String unpaid_leave, String sno, String year_month) {
        this.grant_id = grant_id;
        this.student_id = student_id;
        this.month = month;
        this.year = year;
        this.days_worked = days_worked;
        this.leave = leave;
        this.annual_leave = annual_leave;
        this.sick_leave = sick_leave;
        this.other_paid_leave = other_paid_leave;
        this.unpaid_leave = unpaid_leave;
        this.sno = sno;
        this.year_month = year_month;
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

    public String getDays_worked() {
        return days_worked;
    }

    public void setDays_worked(String days_worked) {
        this.days_worked = days_worked;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
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

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getYear_month() {
        return year_month;
    }

    public void setYear_month(String year_month) {
        this.year_month = year_month;
    }

    @Override
    public String toString() {
        return "mPastAttendanceArray{" +
                "grant_id='" + grant_id + '\'' +
                ", student_id='" + student_id + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", days_worked='" + days_worked + '\'' +
                ", leave='" + leave + '\'' +
                ", annual_leave='" + annual_leave + '\'' +
                ", sick_leave='" + sick_leave + '\'' +
                ", other_paid_leave='" + other_paid_leave + '\'' +
                ", unpaid_leave='" + unpaid_leave + '\'' +
                ", sno='" + sno + '\'' +
                ", year_month='" + year_month + '\'' +
                '}';
    }
}
