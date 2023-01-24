package za.co.sacpo.grant.xCubeLib.db;

public class mPendingClaimListArray {

    String s_m_s_stipend_month,month_name,s_m_s_stipend_year,s_m_s_id,stipend_amount,learner_name,approve_stipend_link;

    public mPendingClaimListArray(String s_m_s_stipend_month, String month_name, String s_m_s_stipend_year, String s_m_s_id, String stipend_amount, String learner_name, String approve_stipend_link) {
        this.s_m_s_stipend_month = s_m_s_stipend_month;
        this.month_name = month_name;
        this.s_m_s_stipend_year = s_m_s_stipend_year;
        this.s_m_s_id = s_m_s_id;
        this.stipend_amount = stipend_amount;
        this.learner_name = learner_name;
        this.approve_stipend_link = approve_stipend_link;
    }

    public String getS_m_s_stipend_month() {
        return s_m_s_stipend_month;
    }

    public void setS_m_s_stipend_month(String s_m_s_stipend_month) {
        this.s_m_s_stipend_month = s_m_s_stipend_month;
    }

    public String getMonth_name() {
        return month_name;
    }

    public void setMonth_name(String month_name) {
        this.month_name = month_name;
    }

    public String getS_m_s_stipend_year() {
        return s_m_s_stipend_year;
    }

    public void setS_m_s_stipend_year(String s_m_s_stipend_year) {
        this.s_m_s_stipend_year = s_m_s_stipend_year;
    }

    public String getS_m_s_id() {
        return s_m_s_id;
    }

    public void setS_m_s_id(String s_m_s_id) {
        this.s_m_s_id = s_m_s_id;
    }

    public String getStipend_amount() {
        return stipend_amount;
    }

    public void setStipend_amount(String stipend_amount) {
        this.stipend_amount = stipend_amount;
    }

    public String getLearner_name() {
        return learner_name;
    }

    public void setLearner_name(String learner_name) {
        this.learner_name = learner_name;
    }

    public String getApprove_stipend_link() {
        return approve_stipend_link;
    }

    public void setApprove_stipend_link(String approve_stipend_link) {
        this.approve_stipend_link = approve_stipend_link;
    }

    @Override
    public String toString() {
        return "mPendingClaimListArray{" +
                "s_m_s_stipend_month='" + s_m_s_stipend_month + '\'' +
                ", month_name='" + month_name + '\'' +
                ", s_m_s_stipend_year='" + s_m_s_stipend_year + '\'' +
                ", s_m_s_id='" + s_m_s_id + '\'' +
                ", stipend_amount='" + stipend_amount + '\'' +
                ", learner_name='" + learner_name + '\'' +
                ", approve_stipend_link='" + approve_stipend_link + '\'' +
                '}';
    }
}
