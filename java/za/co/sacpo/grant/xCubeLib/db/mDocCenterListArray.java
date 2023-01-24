package za.co.sacpo.grant.xCubeLib.db;

public class mDocCenterListArray {

    String stipend_id,
            year,
            monthName,
            download_att_register,
            is_download_att_register,
            download_unsigned_claim_form,
            is_download_unsigned_claim_form,
            upload_signed_claim_form,
            download_signed_claim_form,
            is_download_signed_claim_form,
            month;

    public mDocCenterListArray(String stipend_id, String year, String monthName, String download_att_register, String is_download_att_register, String download_unsigned_claim_form, String is_download_unsigned_claim_form, String upload_signed_claim_form, String download_signed_claim_form, String is_download_signed_claim_form, String month) {
        this.stipend_id = stipend_id;
        this.year = year;
        this.monthName = monthName;
        this.download_att_register = download_att_register;
        this.is_download_att_register = is_download_att_register;
        this.download_unsigned_claim_form = download_unsigned_claim_form;
        this.is_download_unsigned_claim_form = is_download_unsigned_claim_form;
        this.upload_signed_claim_form = upload_signed_claim_form;
        this.download_signed_claim_form = download_signed_claim_form;
        this.is_download_signed_claim_form = is_download_signed_claim_form;
        this.month = month;
    }

    public String getStipend_id() {
        return stipend_id;
    }

    public void setStipend_id(String stipend_id) {
        this.stipend_id = stipend_id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public String getDownload_att_register() {
        return download_att_register;
    }

    public void setDownload_att_register(String download_att_register) {
        this.download_att_register = download_att_register;
    }

    public String getIs_download_att_register() {
        return is_download_att_register;
    }

    public void setIs_download_att_register(String is_download_att_register) {
        this.is_download_att_register = is_download_att_register;
    }

    public String getDownload_unsigned_claim_form() {
        return download_unsigned_claim_form;
    }

    public void setDownload_unsigned_claim_form(String download_unsigned_claim_form) {
        this.download_unsigned_claim_form = download_unsigned_claim_form;
    }

    public String getIs_download_unsigned_claim_form() {
        return is_download_unsigned_claim_form;
    }

    public void setIs_download_unsigned_claim_form(String is_download_unsigned_claim_form) {
        this.is_download_unsigned_claim_form = is_download_unsigned_claim_form;
    }

    public String getUpload_signed_claim_form() {
        return upload_signed_claim_form;
    }

    public void setUpload_signed_claim_form(String upload_signed_claim_form) {
        this.upload_signed_claim_form = upload_signed_claim_form;
    }

    public String getDownload_signed_claim_form() {
        return download_signed_claim_form;
    }

    public void setDownload_signed_claim_form(String download_signed_claim_form) {
        this.download_signed_claim_form = download_signed_claim_form;
    }

    public String getIs_download_signed_claim_form() {
        return is_download_signed_claim_form;
    }

    public void setIs_download_signed_claim_form(String is_download_signed_claim_form) {
        this.is_download_signed_claim_form = is_download_signed_claim_form;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return "mDocCenterListArray{" +
                "stipend_id='" + stipend_id + '\'' +
                ", year='" + year + '\'' +
                ", monthName='" + monthName + '\'' +
                ", download_att_register='" + download_att_register + '\'' +
                ", is_download_att_register='" + is_download_att_register + '\'' +
                ", download_unsigned_claim_form='" + download_unsigned_claim_form + '\'' +
                ", is_download_unsigned_claim_form='" + is_download_unsigned_claim_form + '\'' +
                ", upload_signed_claim_form='" + upload_signed_claim_form + '\'' +
                ", download_signed_claim_form='" + download_signed_claim_form + '\'' +
                ", is_download_signed_claim_form='" + is_download_signed_claim_form + '\'' +
                ", month='" + month + '\'' +
                '}';
    }
}
