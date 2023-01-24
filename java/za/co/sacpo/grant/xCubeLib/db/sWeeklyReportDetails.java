package za.co.sacpo.grant.xCubeLib.db;

public class sWeeklyReportDetails {

String s_w_r_id,
        s_w_r_student_id,
        student_name,
        title,
        training,
        day,
        feedback,
        learning_experices,
        number,
        month_year,
        report_add_date,
        date,
        is_supervisor_commented,
        c_u_r_comment,
        c_u_r_training_progress,
        c_u_r_report_writing,
        supervisor_status;

    public sWeeklyReportDetails(String s_w_r_id, String s_w_r_student_id, String student_name, String title, String training, String day, String feedback, String learning_experices, String number, String month_year, String report_add_date, String date, String is_supervisor_commented, String c_u_r_comment, String c_u_r_training_progress, String c_u_r_report_writing, String supervisor_status) {
        this.s_w_r_id = s_w_r_id;
        this.s_w_r_student_id = s_w_r_student_id;
        this.student_name = student_name;
        this.title = title;
        this.training = training;
        this.day = day;
        this.feedback = feedback;
        this.learning_experices = learning_experices;
        this.number = number;
        this.month_year = month_year;
        this.report_add_date = report_add_date;
        this.date = date;
        this.is_supervisor_commented = is_supervisor_commented;
        this.c_u_r_comment = c_u_r_comment;
        this.c_u_r_training_progress = c_u_r_training_progress;
        this.c_u_r_report_writing = c_u_r_report_writing;
        this.supervisor_status = supervisor_status;
    }

    public String getS_w_r_id() {
        return s_w_r_id;
    }

    public void setS_w_r_id(String s_w_r_id) {
        this.s_w_r_id = s_w_r_id;
    }

    public String getS_w_r_student_id() {
        return s_w_r_student_id;
    }

    public void setS_w_r_student_id(String s_w_r_student_id) {
        this.s_w_r_student_id = s_w_r_student_id;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTraining() {
        return training;
    }

    public void setTraining(String training) {
        this.training = training;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getLearning_experices() {
        return learning_experices;
    }

    public void setLearning_experices(String learning_experices) {
        this.learning_experices = learning_experices;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getMonth_year() {
        return month_year;
    }

    public void setMonth_year(String month_year) {
        this.month_year = month_year;
    }

    public String getReport_add_date() {
        return report_add_date;
    }

    public void setReport_add_date(String report_add_date) {
        this.report_add_date = report_add_date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIs_supervisor_commented() {
        return is_supervisor_commented;
    }

    public void setIs_supervisor_commented(String is_supervisor_commented) {
        this.is_supervisor_commented = is_supervisor_commented;
    }

    public String getC_u_r_comment() {
        return c_u_r_comment;
    }

    public void setC_u_r_comment(String c_u_r_comment) {
        this.c_u_r_comment = c_u_r_comment;
    }

    public String getC_u_r_training_progress() {
        return c_u_r_training_progress;
    }

    public void setC_u_r_training_progress(String c_u_r_training_progress) {
        this.c_u_r_training_progress = c_u_r_training_progress;
    }

    public String getC_u_r_report_writing() {
        return c_u_r_report_writing;
    }

    public void setC_u_r_report_writing(String c_u_r_report_writing) {
        this.c_u_r_report_writing = c_u_r_report_writing;
    }

    public String getSupervisor_status() {
        return supervisor_status;
    }

    public void setSupervisor_status(String supervisor_status) {
        this.supervisor_status = supervisor_status;
    }

    @Override
    public String toString() {
        return "sWeeklyReportDetails{" +
                "s_w_r_id='" + s_w_r_id + '\'' +
                ", s_w_r_student_id='" + s_w_r_student_id + '\'' +
                ", student_name='" + student_name + '\'' +
                ", title='" + title + '\'' +
                ", training='" + training + '\'' +
                ", day='" + day + '\'' +
                ", feedback='" + feedback + '\'' +
                ", learning_experices='" + learning_experices + '\'' +
                ", number='" + number + '\'' +
                ", month_year='" + month_year + '\'' +
                ", report_add_date='" + report_add_date + '\'' +
                ", date='" + date + '\'' +
                ", is_supervisor_commented='" + is_supervisor_commented + '\'' +
                ", c_u_r_comment='" + c_u_r_comment + '\'' +
                ", c_u_r_training_progress='" + c_u_r_training_progress + '\'' +
                ", c_u_r_report_writing='" + c_u_r_report_writing + '\'' +
                ", supervisor_status='" + supervisor_status + '\'' +
                '}';
    }
}

