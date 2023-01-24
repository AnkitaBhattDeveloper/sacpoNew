package za.co.sacpo.grant.xCubeLib.db;

public class mLearnerAlertListArray {

    String id,
            subject,
            date,
            detail_link,
            student_id;

    public mLearnerAlertListArray(String id, String subject, String date, String detail_link, String student_id) {
        this.id = id;
        this.subject = subject;
        this.date = date;
        this.detail_link = detail_link;
        this.student_id = student_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDetail_link() {
        return detail_link;
    }

    public void setDetail_link(String detail_link) {
        this.detail_link = detail_link;
    }

    public String getStudent_id() {
        return student_id;
    }

    public void setStudent_id(String student_id) {
        this.student_id = student_id;
    }

    @Override
    public String toString() {
        return "mLearnerAlertListArray{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", date='" + date + '\'' +
                ", detail_link='" + detail_link + '\'' +
                ", student_id='" + student_id + '\'' +
                '}';
    }
}
