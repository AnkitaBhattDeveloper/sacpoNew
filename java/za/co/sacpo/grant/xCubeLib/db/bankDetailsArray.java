package za.co.sacpo.grant.xCubeLib.db;

public class bankDetailsArray {


    public String id,bank_name,initial_name,account_no,branch_code,b_d_status,b_d_surname,account_type,
            u_b_id,b_d_a_type,b_d_u_branch_id;

    public bankDetailsArray(String id, String bank_name, String initial_name, String account_no, String branch_code, String b_d_status, String b_d_surname, String account_type, String u_b_id, String b_d_a_type, String b_d_u_branch_id) {
        this.id = id;
        this.bank_name = bank_name;
        this.initial_name = initial_name;
        this.account_no = account_no;
        this.branch_code = branch_code;
        this.b_d_status = b_d_status;
        this.b_d_surname = b_d_surname;
        this.account_type = account_type;
        this.u_b_id = u_b_id;
        this.b_d_a_type = b_d_a_type;
        this.b_d_u_branch_id = b_d_u_branch_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getInitial_name() {
        return initial_name;
    }

    public void setInitial_name(String initial_name) {
        this.initial_name = initial_name;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getBranch_code() {
        return branch_code;
    }

    public void setBranch_code(String branch_code) {
        this.branch_code = branch_code;
    }

    public String getB_d_status() {
        return b_d_status;
    }

    public void setB_d_status(String b_d_status) {
        this.b_d_status = b_d_status;
    }

    public String getB_d_surname() {
        return b_d_surname;
    }

    public void setB_d_surname(String b_d_surname) {
        this.b_d_surname = b_d_surname;
    }

    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public String getU_b_id() {
        return u_b_id;
    }

    public void setU_b_id(String u_b_id) {
        this.u_b_id = u_b_id;
    }

    public String getB_d_a_type() {
        return b_d_a_type;
    }

    public void setB_d_a_type(String b_d_a_type) {
        this.b_d_a_type = b_d_a_type;
    }

    public String getB_d_u_branch_id() {
        return b_d_u_branch_id;
    }

    public void setB_d_u_branch_id(String b_d_u_branch_id) {
        this.b_d_u_branch_id = b_d_u_branch_id;
    }

    @Override
    public String toString() {
        return "bankDetailsArray{" +
                "id='" + id + '\'' +
                ", bank_name='" + bank_name + '\'' +
                ", initial_name='" + initial_name + '\'' +
                ", account_no='" + account_no + '\'' +
                ", branch_code='" + branch_code + '\'' +
                ", b_d_status='" + b_d_status + '\'' +
                ", b_d_surname='" + b_d_surname + '\'' +
                ", account_type='" + account_type + '\'' +
                ", u_b_id='" + u_b_id + '\'' +
                ", b_d_a_type='" + b_d_a_type + '\'' +
                ", b_d_u_branch_id='" + b_d_u_branch_id + '\'' +
                '}';
    }
}
