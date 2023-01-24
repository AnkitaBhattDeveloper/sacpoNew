package za.co.sacpo.grant.xCubeLib.db;

public class mShowDetailsArray {

    String lead_emp,
            setaName,
            grantName,
            g_d_grant_number,
            leadManager,
            leadManagerEmail,
            leadManagerContact,
            annual_leave,
            sick_leave,
            other_paid_leave,
            unpaid_leave,
            studName,
            s_id_no,
            stu_email,
            stu_cell;

    public mShowDetailsArray(String lead_emp, String setaName, String grantName, String g_d_grant_number, String leadManager, String leadManagerEmail, String leadManagerContact, String annual_leave, String sick_leave, String other_paid_leave, String unpaid_leave, String studName, String s_id_no, String stu_email, String stu_cell) {
        this.lead_emp = lead_emp;
        this.setaName = setaName;
        this.grantName = grantName;
        this.g_d_grant_number = g_d_grant_number;
        this.leadManager = leadManager;
        this.leadManagerEmail = leadManagerEmail;
        this.leadManagerContact = leadManagerContact;
        this.annual_leave = annual_leave;
        this.sick_leave = sick_leave;
        this.other_paid_leave = other_paid_leave;
        this.unpaid_leave = unpaid_leave;
        this.studName = studName;
        this.s_id_no = s_id_no;
        this.stu_email = stu_email;
        this.stu_cell = stu_cell;
    }

    public String getLead_emp() {
        return lead_emp;
    }

    public void setLead_emp(String lead_emp) {
        this.lead_emp = lead_emp;
    }

    public String getSetaName() {
        return setaName;
    }

    public void setSetaName(String setaName) {
        this.setaName = setaName;
    }

    public String getGrantName() {
        return grantName;
    }

    public void setGrantName(String grantName) {
        this.grantName = grantName;
    }

    public String getG_d_grant_number() {
        return g_d_grant_number;
    }

    public void setG_d_grant_number(String g_d_grant_number) {
        this.g_d_grant_number = g_d_grant_number;
    }

    public String getLeadManager() {
        return leadManager;
    }

    public void setLeadManager(String leadManager) {
        this.leadManager = leadManager;
    }

    public String getLeadManagerEmail() {
        return leadManagerEmail;
    }

    public void setLeadManagerEmail(String leadManagerEmail) {
        this.leadManagerEmail = leadManagerEmail;
    }

    public String getLeadManagerContact() {
        return leadManagerContact;
    }

    public void setLeadManagerContact(String leadManagerContact) {
        this.leadManagerContact = leadManagerContact;
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

    public String getStudName() {
        return studName;
    }

    public void setStudName(String studName) {
        this.studName = studName;
    }

    public String getS_id_no() {
        return s_id_no;
    }

    public void setS_id_no(String s_id_no) {
        this.s_id_no = s_id_no;
    }

    public String getStu_email() {
        return stu_email;
    }

    public void setStu_email(String stu_email) {
        this.stu_email = stu_email;
    }

    public String getStu_cell() {
        return stu_cell;
    }

    public void setStu_cell(String stu_cell) {
        this.stu_cell = stu_cell;
    }

    @Override
    public String toString() {
        return "mShowDetailsArray{" +
                "lead_emp='" + lead_emp + '\'' +
                ", setaName='" + setaName + '\'' +
                ", grantName='" + grantName + '\'' +
                ", g_d_grant_number='" + g_d_grant_number + '\'' +
                ", leadManager='" + leadManager + '\'' +
                ", leadManagerEmail='" + leadManagerEmail + '\'' +
                ", leadManagerContact='" + leadManagerContact + '\'' +
                ", annual_leave='" + annual_leave + '\'' +
                ", sick_leave='" + sick_leave + '\'' +
                ", other_paid_leave='" + other_paid_leave + '\'' +
                ", unpaid_leave='" + unpaid_leave + '\'' +
                ", studName='" + studName + '\'' +
                ", s_id_no='" + s_id_no + '\'' +
                ", stu_email='" + stu_email + '\'' +
                ", stu_cell='" + stu_cell + '\'' +
                '}';
    }
}
