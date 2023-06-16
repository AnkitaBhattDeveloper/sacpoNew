package za.co.sacpo.grantportal.xCubeLib.db;

public class mLearnerDetailsArray {

    String u_id,
            s_id_no,
            student_name,
            u_email,
            gender_name,
            s_s_g_grant_start_date,
            s_s_g_grant_end_date,
            mentor_name,
            host_emp_name,
            grant_admin,
    workstation,
    seta_name,
    lem_name;

    public mLearnerDetailsArray(String u_id, String s_id_no, String student_name, String u_email, String gender_name, String s_s_g_grant_start_date, String s_s_g_grant_end_date, String mentor_name, String host_emp_name, String grant_admin, String workstation, String seta_name, String lem_name) {
        this.u_id = u_id;
        this.s_id_no = s_id_no;
        this.student_name = student_name;
        this.u_email = u_email;
        this.gender_name = gender_name;
        this.s_s_g_grant_start_date = s_s_g_grant_start_date;
        this.s_s_g_grant_end_date = s_s_g_grant_end_date;
        this.mentor_name = mentor_name;
        this.host_emp_name = host_emp_name;
        this.grant_admin = grant_admin;
        this.workstation = workstation;
        this.seta_name = seta_name;
        this.lem_name = lem_name;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getS_id_no() {
        return s_id_no;
    }

    public void setS_id_no(String s_id_no) {
        this.s_id_no = s_id_no;
    }

    public String getStudent_name() {
        return student_name;
    }

    public void setStudent_name(String student_name) {
        this.student_name = student_name;
    }

    public String getU_email() {
        return u_email;
    }

    public void setU_email(String u_email) {
        this.u_email = u_email;
    }

    public String getGender_name() {
        return gender_name;
    }

    public void setGender_name(String gender_name) {
        this.gender_name = gender_name;
    }

    public String getS_s_g_grant_start_date() {
        return s_s_g_grant_start_date;
    }

    public void setS_s_g_grant_start_date(String s_s_g_grant_start_date) {
        this.s_s_g_grant_start_date = s_s_g_grant_start_date;
    }

    public String getS_s_g_grant_end_date() {
        return s_s_g_grant_end_date;
    }

    public void setS_s_g_grant_end_date(String s_s_g_grant_end_date) {
        this.s_s_g_grant_end_date = s_s_g_grant_end_date;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getHost_emp_name() {
        return host_emp_name;
    }

    public void setHost_emp_name(String host_emp_name) {
        this.host_emp_name = host_emp_name;
    }

    public String getGrant_admin() {
        return grant_admin;
    }

    public void setGrant_admin(String grant_admin) {
        this.grant_admin = grant_admin;
    }

    public String getWorkstation() {
        return workstation;
    }

    public void setWorkstation(String workstation) {
        this.workstation = workstation;
    }

    public String getSeta_name() {
        return seta_name;
    }

    public void setSeta_name(String seta_name) {
        this.seta_name = seta_name;
    }

    public String getLem_name() {
        return lem_name;
    }

    public void setLem_name(String lem_name) {
        this.lem_name = lem_name;
    }

    @Override
    public String toString() {
        return "mLearnerDetailsArray{" +
                "u_id='" + u_id + '\'' +
                ", s_id_no='" + s_id_no + '\'' +
                ", student_name='" + student_name + '\'' +
                ", u_email='" + u_email + '\'' +
                ", gender_name='" + gender_name + '\'' +
                ", s_s_g_grant_start_date='" + s_s_g_grant_start_date + '\'' +
                ", s_s_g_grant_end_date='" + s_s_g_grant_end_date + '\'' +
                ", mentor_name='" + mentor_name + '\'' +
                ", host_emp_name='" + host_emp_name + '\'' +
                ", grant_admin='" + grant_admin + '\'' +
                ", workstation='" + workstation + '\'' +
                ", seta_name='" + seta_name + '\'' +
                ", lem_name='" + lem_name + '\'' +
                '}';
    }
}
