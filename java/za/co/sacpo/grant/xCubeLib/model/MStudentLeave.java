package za.co.sacpo.grant.xCubeLib.model;

/**
 * Created by xcube-06 on 7/8/18.
 */

public class MStudentLeave {
    public String getM_stu_id() {
        return m_stu_id;
    }

    public void setM_stu_id(String m_stu_id) {
        this.m_stu_id = m_stu_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getApprove() {
        return approve;
    }

    public void setApprove(String approve) {
        this.approve = approve;
    }

    public String getDisapprove() {
        return disapprove;
    }

    public void setDisapprove(String disapprove) {
        this.disapprove = disapprove;
    }

    public MStudentLeave(String m_stu_id, String date, String approve, String disapprove) {
        this.m_stu_id = m_stu_id;
        this.date = date;
        this.approve = approve;
        this.disapprove = disapprove;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReasons() {
        return reasons;
    }

    public void setReasons(String reasons) {
        this.reasons = reasons;
    }

    private String m_stu_id,date,approve,disapprove,type,reasons;

}
