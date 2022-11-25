package za.co.sacpo.grant.xCubeLib.db;

public class pastClaimListArray {


    String s_m_s_id,
            s_m_s_stipend,
            s_m_s_stipend_month,
            year,
            month,
            date_of_claim,
            claimed_ammount,
            supervisor_status,
            supervisor_status_color,
            grant_admin_status,
            grant_admin_status_color,
            download_unsigned_claim_form_btn,
            download_unsigned_claim_form,
            upload_signed_claim_form_btn,
            download_signed_claim_form,
            download_sign_claim_form_btn;

    public pastClaimListArray(String s_m_s_id, String s_m_s_stipend, String s_m_s_stipend_month, String year, String month, String date_of_claim, String claimed_ammount, String supervisor_status, String supervisor_status_color, String grant_admin_status, String grant_admin_status_color, String download_unsigned_claim_form_btn, String download_unsigned_claim_form, String upload_signed_claim_form_btn, String download_signed_claim_form, String download_sign_claim_form_btn) {
        this.s_m_s_id = s_m_s_id;
        this.s_m_s_stipend = s_m_s_stipend;
        this.s_m_s_stipend_month = s_m_s_stipend_month;
        this.year = year;
        this.month = month;
        this.date_of_claim = date_of_claim;
        this.claimed_ammount = claimed_ammount;
        this.supervisor_status = supervisor_status;
        this.supervisor_status_color = supervisor_status_color;
        this.grant_admin_status = grant_admin_status;
        this.grant_admin_status_color = grant_admin_status_color;
        this.download_unsigned_claim_form_btn = download_unsigned_claim_form_btn;
        this.download_unsigned_claim_form = download_unsigned_claim_form;
        this.upload_signed_claim_form_btn = upload_signed_claim_form_btn;
        this.download_signed_claim_form = download_signed_claim_form;
        this.download_sign_claim_form_btn = download_sign_claim_form_btn;
    }

    public String getS_m_s_id() {
        return s_m_s_id;
    }

    public void setS_m_s_id(String s_m_s_id) {
        this.s_m_s_id = s_m_s_id;
    }

    public String getS_m_s_stipend() {
        return s_m_s_stipend;
    }

    public void setS_m_s_stipend(String s_m_s_stipend) {
        this.s_m_s_stipend = s_m_s_stipend;
    }

    public String getS_m_s_stipend_month() {
        return s_m_s_stipend_month;
    }

    public void setS_m_s_stipend_month(String s_m_s_stipend_month) {
        this.s_m_s_stipend_month = s_m_s_stipend_month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate_of_claim() {
        return date_of_claim;
    }

    public void setDate_of_claim(String date_of_claim) {
        this.date_of_claim = date_of_claim;
    }

    public String getClaimed_ammount() {
        return claimed_ammount;
    }

    public void setClaimed_ammount(String claimed_ammount) {
        this.claimed_ammount = claimed_ammount;
    }

    public String getSupervisor_status() {
        return supervisor_status;
    }

    public void setSupervisor_status(String supervisor_status) {
        this.supervisor_status = supervisor_status;
    }

    public String getSupervisor_status_color() {
        return supervisor_status_color;
    }

    public void setSupervisor_status_color(String supervisor_status_color) {
        this.supervisor_status_color = supervisor_status_color;
    }

    public String getGrant_admin_status() {
        return grant_admin_status;
    }

    public void setGrant_admin_status(String grant_admin_status) {
        this.grant_admin_status = grant_admin_status;
    }

    public String getGrant_admin_status_color() {
        return grant_admin_status_color;
    }

    public void setGrant_admin_status_color(String grant_admin_status_color) {
        this.grant_admin_status_color = grant_admin_status_color;
    }

    public String getDownload_unsigned_claim_form_btn() {
        return download_unsigned_claim_form_btn;
    }

    public void setDownload_unsigned_claim_form_btn(String download_unsigned_claim_form_btn) {
        this.download_unsigned_claim_form_btn = download_unsigned_claim_form_btn;
    }

    public String getDownload_unsigned_claim_form() {
        return download_unsigned_claim_form;
    }

    public void setDownload_unsigned_claim_form(String download_unsigned_claim_form) {
        this.download_unsigned_claim_form = download_unsigned_claim_form;
    }

    public String getUpload_signed_claim_form_btn() {
        return upload_signed_claim_form_btn;
    }

    public void setUpload_signed_claim_form_btn(String upload_signed_claim_form_btn) {
        this.upload_signed_claim_form_btn = upload_signed_claim_form_btn;
    }

    public String getDownload_signed_claim_form() {
        return download_signed_claim_form;
    }

    public void setDownload_signed_claim_form(String download_signed_claim_form) {
        this.download_signed_claim_form = download_signed_claim_form;
    }

    public String getDownload_sign_claim_form_btn() {
        return download_sign_claim_form_btn;
    }

    public void setDownload_sign_claim_form_btn(String download_sign_claim_form_btn) {
        this.download_sign_claim_form_btn = download_sign_claim_form_btn;
    }

    @Override
    public String toString() {
        return "pastClaimListArray{" +
                "s_m_s_id='" + s_m_s_id + '\'' +
                ", s_m_s_stipend='" + s_m_s_stipend + '\'' +
                ", s_m_s_stipend_month='" + s_m_s_stipend_month + '\'' +
                ", year='" + year + '\'' +
                ", month='" + month + '\'' +
                ", date_of_claim='" + date_of_claim + '\'' +
                ", claimed_ammount='" + claimed_ammount + '\'' +
                ", supervisor_status='" + supervisor_status + '\'' +
                ", supervisor_status_color='" + supervisor_status_color + '\'' +
                ", grant_admin_status='" + grant_admin_status + '\'' +
                ", grant_admin_status_color='" + grant_admin_status_color + '\'' +
                ", download_unsigned_claim_form_btn='" + download_unsigned_claim_form_btn + '\'' +
                ", download_unsigned_claim_form='" + download_unsigned_claim_form + '\'' +
                ", upload_signed_claim_form_btn='" + upload_signed_claim_form_btn + '\'' +
                ", download_signed_claim_form='" + download_signed_claim_form + '\'' +
                ", download_sign_claim_form_btn='" + download_sign_claim_form_btn + '\'' +
                '}';
    }
}
