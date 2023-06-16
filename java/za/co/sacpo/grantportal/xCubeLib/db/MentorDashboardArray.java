package za.co.sacpo.grantportal.xCubeLib.db;

public class MentorDashboardArray {

    String  learner_id,
            grant_id,
            learner_name,
            learner_status,
            start_date,
            end_date,
            seta_name,
            managed_by,
            past_attendance_register,
            edit_current_attendance,
            total_leave_taken,
            leave_pending_approval,
            past_stipend_claim,
            show_approved_claim_link,
            current_stipend_pending_approval,
            registered_workstation,
            show_workstation_link,
            assigned_workstation,
            workstations_status,
            show_edit_workstations_link,
            linked_student_id,
            monthly_reports_completed,
            supervisor_comments_pending,
            training_program_upload,
            trainging_doc,
            alert_count,
            notes;

    public MentorDashboardArray(String learner_id, String grant_id, String learner_name, String learner_status, String start_date, String end_date, String seta_name, String managed_by, String past_attendance_register, String edit_current_attendance, String total_leave_taken, String leave_pending_approval, String past_stipend_claim, String show_approved_claim_link, String current_stipend_pending_approval, String registered_workstation,String show_workstation_link,String assigned_workstation, String workstations_status, String show_edit_workstations_link, String linked_student_id, String monthly_reports_completed, String supervisor_comments_pending, String training_program_upload, String trainging_doc, String alert_count, String notes) {
        this.learner_id = learner_id;
        this.grant_id = grant_id;
        this.learner_name = learner_name;
        this.learner_status = learner_status;
        this.start_date = start_date;
        this.end_date = end_date;
        this.seta_name = seta_name;
        this.managed_by = managed_by;
        this.past_attendance_register = past_attendance_register;
        this.edit_current_attendance = edit_current_attendance;
        this.total_leave_taken = total_leave_taken;
        this.leave_pending_approval = leave_pending_approval;
        this.past_stipend_claim = past_stipend_claim;
        this.show_approved_claim_link = show_approved_claim_link;
        this.current_stipend_pending_approval = current_stipend_pending_approval;
        this.registered_workstation = registered_workstation;
        this.show_workstation_link = show_workstation_link;
        this.assigned_workstation = assigned_workstation;
        this.workstations_status = workstations_status;
        this.show_edit_workstations_link = show_edit_workstations_link;
        this.linked_student_id = linked_student_id;
        this.monthly_reports_completed = monthly_reports_completed;
        this.supervisor_comments_pending = supervisor_comments_pending;
        this.training_program_upload = training_program_upload;
        this.trainging_doc = trainging_doc;
        this.alert_count = alert_count;
        this.notes = notes;
    }

    public String getShow_workstation_link() {
        return show_workstation_link;
    }

    public void setShow_workstation_link(String show_workstation_link) {
        this.show_workstation_link = show_workstation_link;
    }

    public String getAssigned_workstation() {
        return assigned_workstation;
    }

    public void setAssigned_workstation(String assigned_workstation) {
        this.assigned_workstation = assigned_workstation;
    }

    public String getLearner_id() {
        return learner_id;
    }

    public void setLearner_id(String learner_id) {
        this.learner_id = learner_id;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
    }

    public String getLearner_name() {
        return learner_name;
    }

    public void setLearner_name(String learner_name) {
        this.learner_name = learner_name;
    }

    public String getLearner_status() {
        return learner_status;
    }

    public void setLearner_status(String learner_status) {
        this.learner_status = learner_status;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getSeta_name() {
        return seta_name;
    }

    public void setSeta_name(String seta_name) {
        this.seta_name = seta_name;
    }

    public String getManaged_by() {
        return managed_by;
    }

    public void setManaged_by(String managed_by) {
        this.managed_by = managed_by;
    }

    public String getPast_attendance_register() {
        return past_attendance_register;
    }

    public void setPast_attendance_register(String past_attendance_register) {
        this.past_attendance_register = past_attendance_register;
    }

    public String getEdit_current_attendance() {
        return edit_current_attendance;
    }

    public void setEdit_current_attendance(String edit_current_attendance) {
        this.edit_current_attendance = edit_current_attendance;
    }

    public String getTotal_leave_taken() {
        return total_leave_taken;
    }

    public void setTotal_leave_taken(String total_leave_taken) {
        this.total_leave_taken = total_leave_taken;
    }

    public String getLeave_pending_approval() {
        return leave_pending_approval;
    }

    public void setLeave_pending_approval(String leave_pending_approval) {
        this.leave_pending_approval = leave_pending_approval;
    }

    public String getPast_stipend_claim() {
        return past_stipend_claim;
    }

    public void setPast_stipend_claim(String past_stipend_claim) {
        this.past_stipend_claim = past_stipend_claim;
    }

    public String getShow_approved_claim_link() {
        return show_approved_claim_link;
    }

    public void setShow_approved_claim_link(String show_approved_claim_link) {
        this.show_approved_claim_link = show_approved_claim_link;
    }

    public String getCurrent_stipend_pending_approval() {
        return current_stipend_pending_approval;
    }

    public void setCurrent_stipend_pending_approval(String current_stipend_pending_approval) {
        this.current_stipend_pending_approval = current_stipend_pending_approval;
    }

    public String getRegistered_workstation() {
        return registered_workstation;
    }

    public void setRegistered_workstation(String registered_workstation) {
        this.registered_workstation = registered_workstation;
    }

    public String getWorkstations_status() {
        return workstations_status;
    }

    public void setWorkstations_status(String workstations_status) {
        this.workstations_status = workstations_status;
    }

    public String getShow_edit_workstations_link() {
        return show_edit_workstations_link;
    }

    public void setShow_edit_workstations_link(String show_edit_workstations_link) {
        this.show_edit_workstations_link = show_edit_workstations_link;
    }

    public String getLinked_student_id() {
        return linked_student_id;
    }

    public void setLinked_student_id(String linked_student_id) {
        this.linked_student_id = linked_student_id;
    }

    public String getMonthly_reports_completed() {
        return monthly_reports_completed;
    }

    public void setMonthly_reports_completed(String monthly_reports_completed) {
        this.monthly_reports_completed = monthly_reports_completed;
    }

    public String getSupervisor_comments_pending() {
        return supervisor_comments_pending;
    }

    public void setSupervisor_comments_pending(String supervisor_comments_pending) {
        this.supervisor_comments_pending = supervisor_comments_pending;
    }

    public String getTraining_program_upload() {
        return training_program_upload;
    }

    public void setTraining_program_upload(String training_program_upload) {
        this.training_program_upload = training_program_upload;
    }

    public String getTrainging_doc() {
        return trainging_doc;
    }

    public void setTrainging_doc(String trainging_doc) {
        this.trainging_doc = trainging_doc;
    }

    public String getAlert_count() {
        return alert_count;
    }

    public void setAlert_count(String alert_count) {
        this.alert_count = alert_count;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "MentorDashboardArray{" +
                "learner_id='" + learner_id + '\'' +
                ", grant_id='" + grant_id + '\'' +
                ", learner_name='" + learner_name + '\'' +
                ", learner_status='" + learner_status + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                ", seta_name='" + seta_name + '\'' +
                ", managed_by='" + managed_by + '\'' +
                ", past_attendance_register='" + past_attendance_register + '\'' +
                ", edit_current_attendance='" + edit_current_attendance + '\'' +
                ", total_leave_taken='" + total_leave_taken + '\'' +
                ", leave_pending_approval='" + leave_pending_approval + '\'' +
                ", past_stipend_claim='" + past_stipend_claim + '\'' +
                ", show_approved_claim_link='" + show_approved_claim_link + '\'' +
                ", current_stipend_pending_approval='" + current_stipend_pending_approval + '\'' +
                ", registered_workstation='" + registered_workstation + '\'' +
                ", show_workstation_link='" + show_workstation_link + '\'' +
                ", assigned_workstation='" + assigned_workstation + '\'' +
                ", workstations_status='" + workstations_status + '\'' +
                ", show_edit_workstations_link='" + show_edit_workstations_link + '\'' +
                ", linked_student_id='" + linked_student_id + '\'' +
                ", monthly_reports_completed='" + monthly_reports_completed + '\'' +
                ", supervisor_comments_pending='" + supervisor_comments_pending + '\'' +
                ", training_program_upload='" + training_program_upload + '\'' +
                ", trainging_doc='" + trainging_doc + '\'' +
                ", alert_count='" + alert_count + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
