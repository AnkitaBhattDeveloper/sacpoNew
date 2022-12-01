package za.co.sacpo.grant.xCubeLib.db;

public class noteListArray {

    String n_id,
            n_user_id,
            n_description,
            n_status,
            n_add_date,
            n_add_by,
            n_duration,
            n_category,
            n_note_type_id,
            grant_id,
            note_for,
            u_p_cell_no,
            note_from,
            add_by_cell_no,
            add_by_u_id,
            n_category_id,
            n_category_name;

    public noteListArray(String n_id, String n_user_id, String n_description, String n_status, String n_add_date, String n_add_by, String n_duration, String n_category, String n_note_type_id, String grant_id, String note_for, String u_p_cell_no, String note_from, String add_by_cell_no, String add_by_u_id, String n_category_id, String n_category_name) {
        this.n_id = n_id;
        this.n_user_id = n_user_id;
        this.n_description = n_description;
        this.n_status = n_status;
        this.n_add_date = n_add_date;
        this.n_add_by = n_add_by;
        this.n_duration = n_duration;
        this.n_category = n_category;
        this.n_note_type_id = n_note_type_id;
        this.grant_id = grant_id;
        this.note_for = note_for;
        this.u_p_cell_no = u_p_cell_no;
        this.note_from = note_from;
        this.add_by_cell_no = add_by_cell_no;
        this.add_by_u_id = add_by_u_id;
        this.n_category_id = n_category_id;
        this.n_category_name = n_category_name;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public String getN_user_id() {
        return n_user_id;
    }

    public void setN_user_id(String n_user_id) {
        this.n_user_id = n_user_id;
    }

    public String getN_description() {
        return n_description;
    }

    public void setN_description(String n_description) {
        this.n_description = n_description;
    }

    public String getN_status() {
        return n_status;
    }

    public void setN_status(String n_status) {
        this.n_status = n_status;
    }

    public String getN_add_date() {
        return n_add_date;
    }

    public void setN_add_date(String n_add_date) {
        this.n_add_date = n_add_date;
    }

    public String getN_add_by() {
        return n_add_by;
    }

    public void setN_add_by(String n_add_by) {
        this.n_add_by = n_add_by;
    }

    public String getN_duration() {
        return n_duration;
    }

    public void setN_duration(String n_duration) {
        this.n_duration = n_duration;
    }

    public String getN_category() {
        return n_category;
    }

    public void setN_category(String n_category) {
        this.n_category = n_category;
    }

    public String getN_note_type_id() {
        return n_note_type_id;
    }

    public void setN_note_type_id(String n_note_type_id) {
        this.n_note_type_id = n_note_type_id;
    }

    public String getGrant_id() {
        return grant_id;
    }

    public void setGrant_id(String grant_id) {
        this.grant_id = grant_id;
    }

    public String getNote_for() {
        return note_for;
    }

    public void setNote_for(String note_for) {
        this.note_for = note_for;
    }

    public String getU_p_cell_no() {
        return u_p_cell_no;
    }

    public void setU_p_cell_no(String u_p_cell_no) {
        this.u_p_cell_no = u_p_cell_no;
    }

    public String getNote_from() {
        return note_from;
    }

    public void setNote_from(String note_from) {
        this.note_from = note_from;
    }

    public String getAdd_by_cell_no() {
        return add_by_cell_no;
    }

    public void setAdd_by_cell_no(String add_by_cell_no) {
        this.add_by_cell_no = add_by_cell_no;
    }

    public String getAdd_by_u_id() {
        return add_by_u_id;
    }

    public void setAdd_by_u_id(String add_by_u_id) {
        this.add_by_u_id = add_by_u_id;
    }

    public String getN_category_id() {
        return n_category_id;
    }

    public void setN_category_id(String n_category_id) {
        this.n_category_id = n_category_id;
    }

    public String getN_category_name() {
        return n_category_name;
    }

    public void setN_category_name(String n_category_name) {
        this.n_category_name = n_category_name;
    }

    @Override
    public String toString() {
        return "noteListArray{" +
                "n_id='" + n_id + '\'' +
                ", n_user_id='" + n_user_id + '\'' +
                ", n_description='" + n_description + '\'' +
                ", n_status='" + n_status + '\'' +
                ", n_add_date='" + n_add_date + '\'' +
                ", n_add_by='" + n_add_by + '\'' +
                ", n_duration='" + n_duration + '\'' +
                ", n_category='" + n_category + '\'' +
                ", n_note_type_id='" + n_note_type_id + '\'' +
                ", grant_id='" + grant_id + '\'' +
                ", note_for='" + note_for + '\'' +
                ", u_p_cell_no='" + u_p_cell_no + '\'' +
                ", note_from='" + note_from + '\'' +
                ", add_by_cell_no='" + add_by_cell_no + '\'' +
                ", add_by_u_id='" + add_by_u_id + '\'' +
                ", n_category_id='" + n_category_id + '\'' +
                ", n_category_name='" + n_category_name + '\'' +
                '}';
    }
}
