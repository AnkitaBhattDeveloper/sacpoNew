package za.co.sacpo.grantportal.xCubeLib.db;

public class grantDetailsArray {


    String u_id,grant_id,s_s_g_grant_start_date,s_s_g_grant_end_date,grant_name,mentor_name,
    mentor_id,lem_name,lem_id,host_emp_name,seta_id,seta_name,seta_manager_id,
    seta_manager_name,grant_admin_id,grant_admin,grant_admin_email,grant_admin_cell,
    seta_manager_email,seta_manager_cell;

    public grantDetailsArray(String u_id, String grant_id, String s_s_g_grant_start_date, String s_s_g_grant_end_date, String grant_name, String mentor_name, String mentor_id, String lem_name, String lem_id, String host_emp_name, String seta_id, String seta_name, String seta_manager_id, String seta_manager_name, String grant_admin_id, String grant_admin, String grant_admin_email, String grant_admin_cell, String seta_manager_email, String seta_manager_cell) {
        this.u_id = u_id;
        this.grant_id = grant_id;
        this.s_s_g_grant_start_date = s_s_g_grant_start_date;
        this.s_s_g_grant_end_date = s_s_g_grant_end_date;
        this.grant_name = grant_name;
        this.mentor_name = mentor_name;
        this.mentor_id = mentor_id;
        this.lem_name = lem_name;
        this.lem_id = lem_id;
        this.host_emp_name = host_emp_name;
        this.seta_id = seta_id;
        this.seta_name = seta_name;
        this.seta_manager_id = seta_manager_id;
        this.seta_manager_name = seta_manager_name;
        this.grant_admin_id = grant_admin_id;
        this.grant_admin = grant_admin;
        this.grant_admin_email = grant_admin_email;
        this.grant_admin_cell = grant_admin_cell;
        this.seta_manager_email = seta_manager_email;
        this.seta_manager_cell = seta_manager_cell;
    }

    public String getU_id() {
        return u_id;
    }

    public void setU_id(String u_id) {
        this.u_id = u_id;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
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

    public String getGrant_name() {
        return grant_name;
    }

    public void setGrant_name(String grant_name) {
        this.grant_name = grant_name;
    }

    public String getMentor_name() {
        return mentor_name;
    }

    public void setMentor_name(String mentor_name) {
        this.mentor_name = mentor_name;
    }

    public String getMentor_id() {
        return mentor_id;
    }

    public void setMentor_id(String mentor_id) {
        this.mentor_id = mentor_id;
    }

    public String getLem_name() {
        return lem_name;
    }

    public void setLem_name(String lem_name) {
        this.lem_name = lem_name;
    }

    public String getLem_id() {
        return lem_id;
    }

    public void setLem_id(String lem_id) {
        this.lem_id = lem_id;
    }

    public String getHost_emp_name() {
        return host_emp_name;
    }

    public void setHost_emp_name(String host_emp_name) {
        this.host_emp_name = host_emp_name;
    }

    public String getSeta_id() {
        return seta_id;
    }

    public void setSeta_id(String seta_id) {
        this.seta_id = seta_id;
    }

    public String getSeta_name() {
        return seta_name;
    }

    public void setSeta_name(String seta_name) {
        this.seta_name = seta_name;
    }

    public String getSeta_manager_id() {
        return seta_manager_id;
    }

    public void setSeta_manager_id(String seta_manager_id) {
        this.seta_manager_id = seta_manager_id;
    }

    public String getSeta_manager_name() {
        return seta_manager_name;
    }

    public void setSeta_manager_name(String seta_manager_name) {
        this.seta_manager_name = seta_manager_name;
    }

    public String getGrant_admin_id() {
        return grant_admin_id;
    }

    public void setGrant_admin_id(String grant_admin_id) {
        this.grant_admin_id = grant_admin_id;
    }

    public String getGrant_admin() {
        return grant_admin;
    }

    public void setGrant_admin(String grant_admin) {
        this.grant_admin = grant_admin;
    }

    public String getGrant_admin_email() {
        return grant_admin_email;
    }

    public void setGrant_admin_email(String grant_admin_email) {
        this.grant_admin_email = grant_admin_email;
    }

    public String getGrant_admin_cell() {
        return grant_admin_cell;
    }

    public void setGrant_admin_cell(String grant_admin_cell) {
        this.grant_admin_cell = grant_admin_cell;
    }

    public String getSeta_manager_email() {
        return seta_manager_email;
    }

    public void setSeta_manager_email(String seta_manager_email) {
        this.seta_manager_email = seta_manager_email;
    }

    public String getSeta_manager_cell() {
        return seta_manager_cell;
    }

    public void setSeta_manager_cell(String seta_manager_cell) {
        this.seta_manager_cell = seta_manager_cell;
    }

    @Override
    public String toString() {
        return "grantDetailsArray{" +
                "u_id='" + u_id + '\'' +
                ", grant_id='" + grant_id + '\'' +
                ", s_s_g_grant_start_date='" + s_s_g_grant_start_date + '\'' +
                ", s_s_g_grant_end_date='" + s_s_g_grant_end_date + '\'' +
                ", grant_name='" + grant_name + '\'' +
                ", mentor_name='" + mentor_name + '\'' +
                ", mentor_id='" + mentor_id + '\'' +
                ", lem_name='" + lem_name + '\'' +
                ", lem_id='" + lem_id + '\'' +
                ", host_emp_name='" + host_emp_name + '\'' +
                ", seta_id='" + seta_id + '\'' +
                ", seta_name='" + seta_name + '\'' +
                ", seta_manager_id='" + seta_manager_id + '\'' +
                ", seta_manager_name='" + seta_manager_name + '\'' +
                ", grant_admin_id='" + grant_admin_id + '\'' +
                ", grant_admin='" + grant_admin + '\'' +
                ", grant_admin_email='" + grant_admin_email + '\'' +
                ", grant_admin_cell='" + grant_admin_cell + '\'' +
                ", seta_manager_email='" + seta_manager_email + '\'' +
                ", seta_manager_cell='" + seta_manager_cell + '\'' +
                '}';
    }
}
