package za.co.sacpo.grant.xCubeLib.db;

public class mApprovedClaimListArray {

    String s_m_s_stipend_month,
            month_name,
            s_m_s_stipend_year,
            s_m_s_id,
            stipend_amount,
            approve_stipend,
            out_of_range_sign_in_counts,
            out_of_range_link,
            days_worked,
            leave,
            download_claim_form_link,
            show_claim_form_link,
            download_signed_claim_form_link,
            show_signed_claim_form_link;

    public mApprovedClaimListArray(String s_m_s_stipend_month, String month_name, String s_m_s_stipend_year, String s_m_s_id, String stipend_amount, String approve_stipend, String out_of_range_sign_in_counts, String out_of_range_link, String days_worked, String leave, String download_claim_form_link, String show_claim_form_link, String download_signed_claim_form_link, String show_signed_claim_form_link) {
        this.s_m_s_stipend_month = s_m_s_stipend_month;
        this.month_name = month_name;
        this.s_m_s_stipend_year = s_m_s_stipend_year;
        this.s_m_s_id = s_m_s_id;
        this.stipend_amount = stipend_amount;
        this.approve_stipend = approve_stipend;
        this.out_of_range_sign_in_counts = out_of_range_sign_in_counts;
        this.out_of_range_link = out_of_range_link;
        this.days_worked = days_worked;
        this.leave = leave;
        this.download_claim_form_link = download_claim_form_link;
        this.show_claim_form_link = show_claim_form_link;
        this.download_signed_claim_form_link = download_signed_claim_form_link;
        this.show_signed_claim_form_link = show_signed_claim_form_link;
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

    public String getApprove_stipend() {
        return approve_stipend;
    }

    public void setApprove_stipend(String approve_stipend) {
        this.approve_stipend = approve_stipend;
    }

    public String getOut_of_range_sign_in_counts() {
        return out_of_range_sign_in_counts;
    }

    public void setOut_of_range_sign_in_counts(String out_of_range_sign_in_counts) {
        this.out_of_range_sign_in_counts = out_of_range_sign_in_counts;
    }

    public String getOut_of_range_link() {
        return out_of_range_link;
    }

    public void setOut_of_range_link(String out_of_range_link) {
        this.out_of_range_link = out_of_range_link;
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

    public String getDownload_claim_form_link() {
        return download_claim_form_link;
    }

    public void setDownload_claim_form_link(String download_claim_form_link) {
        this.download_claim_form_link = download_claim_form_link;
    }

    public String getShow_claim_form_link() {
        return show_claim_form_link;
    }

    public void setShow_claim_form_link(String show_claim_form_link) {
        this.show_claim_form_link = show_claim_form_link;
    }

    public String getDownload_signed_claim_form_link() {
        return download_signed_claim_form_link;
    }

    public void setDownload_signed_claim_form_link(String download_signed_claim_form_link) {
        this.download_signed_claim_form_link = download_signed_claim_form_link;
    }

    public String getShow_signed_claim_form_link() {
        return show_signed_claim_form_link;
    }

    public void setShow_signed_claim_form_link(String show_signed_claim_form_link) {
        this.show_signed_claim_form_link = show_signed_claim_form_link;
    }

    @Override
    public String toString() {
        return "mApprovedClaimListArray{" +
                "s_m_s_stipend_month='" + s_m_s_stipend_month + '\'' +
                ", month_name='" + month_name + '\'' +
                ", s_m_s_stipend_year='" + s_m_s_stipend_year + '\'' +
                ", s_m_s_id='" + s_m_s_id + '\'' +
                ", stipend_amount='" + stipend_amount + '\'' +
                ", approve_stipend='" + approve_stipend + '\'' +
                ", out_of_range_sign_in_counts='" + out_of_range_sign_in_counts + '\'' +
                ", out_of_range_link='" + out_of_range_link + '\'' +
                ", days_worked='" + days_worked + '\'' +
                ", leave='" + leave + '\'' +
                ", download_claim_form_link='" + download_claim_form_link + '\'' +
                ", show_claim_form_link='" + show_claim_form_link + '\'' +
                ", download_signed_claim_form_link='" + download_signed_claim_form_link + '\'' +
                ", show_signed_claim_form_link='" + show_signed_claim_form_link + '\'' +
                '}';
    }
}
