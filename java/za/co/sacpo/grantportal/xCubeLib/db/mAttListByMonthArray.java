package za.co.sacpo.grantportal.xCubeLib.db;

public class mAttListByMonthArray {


    String s_a_id,
            date,
            day,
            login_time,
            logout_time,
            time_spent,
            overtime_hour,
            learner_comment_btn,
            attendance_status,
            out_of_range,
            date_input;

    public mAttListByMonthArray(String s_a_id, String date, String day, String login_time, String logout_time, String time_spent, String overtime_hour, String learner_comment_btn, String attendance_status, String out_of_range, String date_input) {
        this.s_a_id = s_a_id;
        this.date = date;
        this.day = day;
        this.login_time = login_time;
        this.logout_time = logout_time;
        this.time_spent = time_spent;
        this.overtime_hour = overtime_hour;
        this.learner_comment_btn = learner_comment_btn;
        this.attendance_status = attendance_status;
        this.out_of_range = out_of_range;
        this.date_input = date_input;
    }

    public String getDate_input() {
        return date_input;
    }

    public void setDate_input(String date_input) {
        this.date_input = date_input;
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

    public String getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(String time_spent) {
        this.time_spent = time_spent;
    }

    public String getOvertime_hour() {
        return overtime_hour;
    }

    public void setOvertime_hour(String overtime_hour) {
        this.overtime_hour = overtime_hour;
    }

    public String getLearner_comment_btn() {
        return learner_comment_btn;
    }

    public void setLearner_comment_btn(String learner_comment_btn) {
        this.learner_comment_btn = learner_comment_btn;
    }

    public String getAttendance_status() {
        return attendance_status;
    }

    public void setAttendance_status(String attendance_status) {
        this.attendance_status = attendance_status;
    }

    public String getOut_of_range() {
        return out_of_range;
    }

    public void setOut_of_range(String out_of_range) {
        this.out_of_range = out_of_range;
    }

    @Override
    public String toString() {
        return "mAttListByMonthArray{" +
                "s_a_id='" + s_a_id + '\'' +
                ", date='" + date + '\'' +
                ", day='" + day + '\'' +
                ", login_time='" + login_time + '\'' +
                ", logout_time='" + logout_time + '\'' +
                ", time_spent='" + time_spent + '\'' +
                ", overtime_hour='" + overtime_hour + '\'' +
                ", learner_comment_btn='" + learner_comment_btn + '\'' +
                ", attendance_status='" + attendance_status + '\'' +
                ", out_of_range='" + out_of_range + '\'' +
                ", date_input='" + date_input + '\'' +
                '}';
    }
}
