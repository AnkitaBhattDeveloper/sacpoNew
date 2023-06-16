package za.co.sacpo.grantportal.xCubeLib.db;

public class existingLeaveListArray {

    String s_a_id,
            att_ids,
            month,
            from_date,
            to_date,
            annual_leave,
            sick_leave,
            other_paid_leave,
            unpaid_leave,
            motivation,
            sa_reason,
            supervisor_approval_status,
            motivation_btn,
            reason_btn,
            uploads,
            is_upload,
            show_edit_link,
            show_remove_link;

    public existingLeaveListArray(String s_a_id, String att_ids, String month, String from_date, String to_date, String annual_leave, String sick_leave, String other_paid_leave, String unpaid_leave, String motivation, String sa_reason, String supervisor_approval_status, String motivation_btn, String reason_btn, String uploads, String is_upload, String show_edit_link, String show_remove_link) {
        this.s_a_id = s_a_id;
        this.att_ids = att_ids;
        this.month = month;
        this.from_date = from_date;
        this.to_date = to_date;
        this.annual_leave = annual_leave;
        this.sick_leave = sick_leave;
        this.other_paid_leave = other_paid_leave;
        this.unpaid_leave = unpaid_leave;
        this.motivation = motivation;
        this.sa_reason = sa_reason;
        this.supervisor_approval_status = supervisor_approval_status;
        this.motivation_btn = motivation_btn;
        this.reason_btn = reason_btn;
        this.uploads = uploads;
        this.is_upload = is_upload;
        this.show_edit_link = show_edit_link;
        this.show_remove_link = show_remove_link;
    }

    public String getS_a_id() {
        return s_a_id;
    }

    public void setS_a_id(String s_a_id) {
        this.s_a_id = s_a_id;
    }

    public String getAtt_ids() {
        return att_ids;
    }

    public void setAtt_ids(String att_ids) {
        this.att_ids = att_ids;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFrom_date() {
        return from_date;
    }

    public void setFrom_date(String from_date) {
        this.from_date = from_date;
    }

    public String getTo_date() {
        return to_date;
    }

    public void setTo_date(String to_date) {
        this.to_date = to_date;
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

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getSa_reason() {
        return sa_reason;
    }

    public void setSa_reason(String sa_reason) {
        this.sa_reason = sa_reason;
    }

    public String getSupervisor_approval_status() {
        return supervisor_approval_status;
    }

    public void setSupervisor_approval_status(String supervisor_approval_status) {
        this.supervisor_approval_status = supervisor_approval_status;
    }

    public String getMotivation_btn() {
        return motivation_btn;
    }

    public void setMotivation_btn(String motivation_btn) {
        this.motivation_btn = motivation_btn;
    }

    public String getReason_btn() {
        return reason_btn;
    }

    public void setReason_btn(String reason_btn) {
        this.reason_btn = reason_btn;
    }

    public String getUploads() {
        return uploads;
    }

    public void setUploads(String uploads) {
        this.uploads = uploads;
    }

    public String getIs_upload() {
        return is_upload;
    }

    public void setIs_upload(String is_upload) {
        this.is_upload = is_upload;
    }

    public String getShow_edit_link() {
        return show_edit_link;
    }

    public void setShow_edit_link(String show_edit_link) {
        this.show_edit_link = show_edit_link;
    }

    public String getShow_remove_link() {
        return show_remove_link;
    }

    public void setShow_remove_link(String show_remove_link) {
        this.show_remove_link = show_remove_link;
    }

    @Override
    public String toString() {
        return "existingLeaveListArray{" +
                "s_a_id='" + s_a_id + '\'' +
                ", att_ids='" + att_ids + '\'' +
                ", month='" + month + '\'' +
                ", from_date='" + from_date + '\'' +
                ", to_date='" + to_date + '\'' +
                ", annual_leave='" + annual_leave + '\'' +
                ", sick_leave='" + sick_leave + '\'' +
                ", other_paid_leave='" + other_paid_leave + '\'' +
                ", unpaid_leave='" + unpaid_leave + '\'' +
                ", motivation='" + motivation + '\'' +
                ", sa_reason='" + sa_reason + '\'' +
                ", supervisor_approval_status='" + supervisor_approval_status + '\'' +
                ", motivation_btn='" + motivation_btn + '\'' +
                ", reason_btn='" + reason_btn + '\'' +
                ", uploads='" + uploads + '\'' +
                ", is_upload='" + is_upload + '\'' +
                ", show_edit_link='" + show_edit_link + '\'' +
                ", show_remove_link='" + show_remove_link + '\'' +
                '}';
    }
}
