package za.co.sacpo.grantportal.xCubeLib.db;

public class supervisorDetailsArray {

    public String id, name, email, mobile,u_department,u_designation,ofc_no,employer,employer_sdl;

    public supervisorDetailsArray(String id, String name, String email, String mobile, String u_department, String u_designation, String ofc_no, String employer, String employer_sdl) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.u_department = u_department;
        this.u_designation = u_designation;
        this.ofc_no = ofc_no;
        this.employer = employer;
        this.employer_sdl = employer_sdl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getU_department() {
        return u_department;
    }

    public void setU_department(String u_department) {
        this.u_department = u_department;
    }

    public String getU_designation() {
        return u_designation;
    }

    public void setU_designation(String u_designation) {
        this.u_designation = u_designation;
    }

    public String getOfc_no() {
        return ofc_no;
    }

    public void setOfc_no(String ofc_no) {
        this.ofc_no = ofc_no;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getEmployer_sdl() {
        return employer_sdl;
    }

    public void setEmployer_sdl(String employer_sdl) {
        this.employer_sdl = employer_sdl;
    }

    @Override
    public String toString() {
        return "supervisorDetailsArray{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", u_department='" + u_department + '\'' +
                ", u_designation='" + u_designation + '\'' +
                ", ofc_no='" + ofc_no + '\'' +
                ", employer='" + employer + '\'' +
                ", employer_sdl='" + employer_sdl + '\'' +
                '}';
    }
}
