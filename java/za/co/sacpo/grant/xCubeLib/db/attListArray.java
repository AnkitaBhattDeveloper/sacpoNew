package za.co.sacpo.grant.xCubeLib.db;

public class attListArray {

    String s_a_id,date,day,sign_in_time,sign_out_time, hours_worked,attendance_status,
            distance_from_workstation;

    public attListArray() {
    }

    public attListArray(String s_a_id, String date, String day, String sign_in_time, String sign_out_time, String hours_worked, String attendance_status, String distance_from_workstation) {
        this.s_a_id = s_a_id;
        this.date = date;
        this.day = day;
        this.sign_in_time = sign_in_time;
        this.sign_out_time = sign_out_time;
        this.hours_worked = hours_worked;
        this.attendance_status = attendance_status;
        this.distance_from_workstation = distance_from_workstation;
    }

    public String getS_a_id() {
        return s_a_id;
    }

    public void setS_a_id(String s_a_id) {
        this.s_a_id = s_a_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getSign_in_time() {
        return sign_in_time;
    }

    public void setSign_in_time(String sign_in_time) {
        this.sign_in_time = sign_in_time;
    }

    public String getSign_out_time() {
        return sign_out_time;
    }

    public void setSign_out_time(String sign_out_time) {
        this.sign_out_time = sign_out_time;
    }

    public String getHours_worked() {
        return hours_worked;
    }

    public void setHours_worked(String hours_worked) {
        this.hours_worked = hours_worked;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public String getDistance_from_workstation() {
        return distance_from_workstation;
    }

    public void setDistance_from_workstation(String distance_from_workstation) {
        this.distance_from_workstation = distance_from_workstation;
    }

    @Override
    public String toString() {
        return "attListArray{" +
                "s_a_id='" + s_a_id + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", sign_in_time='" + sign_in_time + '\'' +
                ", sign_out_time='" + sign_out_time + '\'' +
                ", hours_worked='" + hours_worked + '\'' +
                ", attendance_status='" + attendance_status + '\'' +
                ", distance_from_workstation='" + distance_from_workstation + '\'' +
                '}';
    }
}
