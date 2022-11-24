package za.co.sacpo.grant.xCubeLib.db;

public class populateAttListArray {


    String  s_a_id,
            sudent_id,
            date,
            day,
            login_color,
            logout_color,
            login_time,
            logout_time,
            hours_worked,
            attendance_status,
            distance_from_workstation,
            view_comment_link,
            s_a_learner_comment;

    public populateAttListArray(String s_a_id, String sudent_id, String date, String day, String login_color, String logout_color, String login_time, String logout_time, String hours_worked, String attendance_status, String distance_from_workstation, String view_comment_link, String s_a_learner_comment) {
        this.s_a_id = s_a_id;
        this.sudent_id = sudent_id;
        this.date = date;
        this.day = day;
        this.login_color = login_color;
        this.logout_color = logout_color;
        this.login_time = login_time;
        this.logout_time = logout_time;
        this.hours_worked = hours_worked;
        this.attendance_status = attendance_status;
        this.distance_from_workstation = distance_from_workstation;
        this.view_comment_link = view_comment_link;
        this.s_a_learner_comment = s_a_learner_comment;
    }

    public String getS_a_id() {
        return s_a_id;
    }

    public void setS_a_id(String s_a_id) {
        this.s_a_id = s_a_id;
    }

    public String getSudent_id() {
        return sudent_id;
    }

    public void setSudent_id(String sudent_id) {
        this.sudent_id = sudent_id;
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

    public String getLogin_color() {
        return login_color;
    }

    public void setLogin_color(String login_color) {
        this.login_color = login_color;
    }

    public String getLogout_color() {
        return logout_color;
    }

    public void setLogout_color(String logout_color) {
        this.logout_color = logout_color;
    }

    public String getLogin_time() {
        return login_time;
    }

    public void setLogin_time(String login_time) {
        this.login_time = login_time;
    }

    public String getLogout_time() {
        return logout_time;
    }

    public void setLogout_time(String logout_time) {
        this.logout_time = logout_time;
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

    public String getView_comment_link() {
        return view_comment_link;
    }

    public void setView_comment_link(String view_comment_link) {
        this.view_comment_link = view_comment_link;
    }

    public String getS_a_learner_comment() {
        return s_a_learner_comment;
    }

    public void setS_a_learner_comment(String s_a_learner_comment) {
        this.s_a_learner_comment = s_a_learner_comment;
    }

    @Override
    public String toString() {
        return "populateAttListArray{" +
                "s_a_id='" + s_a_id + '\'' +
                ", sudent_id='" + sudent_id + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", login_color='" + login_color + '\'' +
                ", logout_color='" + logout_color + '\'' +
                ", login_time='" + login_time + '\'' +
                ", logout_time='" + logout_time + '\'' +
                ", hours_worked='" + hours_worked + '\'' +
                ", attendance_status='" + attendance_status + '\'' +
                ", distance_from_workstation='" + distance_from_workstation + '\'' +
                ", view_comment_link='" + view_comment_link + '\'' +
                ", s_a_learner_comment='" + s_a_learner_comment + '\'' +
                '}';
    }
}
