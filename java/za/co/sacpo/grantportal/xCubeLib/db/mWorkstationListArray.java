package za.co.sacpo.grantportal.xCubeLib.db;

public class mWorkstationListArray {

    String e_g_l_id,
            employerName,
            e_g_l_department_name,
            e_g_l_student_count,
            e_g_l_latitude,
            e_g_l_longitude;

    public mWorkstationListArray(String e_g_l_id, String employerName, String e_g_l_department_name, String e_g_l_student_count, String e_g_l_latitude, String e_g_l_longitude) {
        this.e_g_l_id = e_g_l_id;
        this.employerName = employerName;
        this.e_g_l_department_name = e_g_l_department_name;
        this.e_g_l_student_count = e_g_l_student_count;
        this.e_g_l_latitude = e_g_l_latitude;
        this.e_g_l_longitude = e_g_l_longitude;
    }

    public String getE_g_l_id() {
        return e_g_l_id;
    }

    public void setE_g_l_id(String e_g_l_id) {
        this.e_g_l_id = e_g_l_id;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getE_g_l_department_name() {
        return e_g_l_department_name;
    }

    public void setE_g_l_department_name(String e_g_l_department_name) {
        this.e_g_l_department_name = e_g_l_department_name;
    }

    public String getE_g_l_student_count() {
        return e_g_l_student_count;
    }

    public void setE_g_l_student_count(String e_g_l_student_count) {
        this.e_g_l_student_count = e_g_l_student_count;
    }

    public String getE_g_l_latitude() {
        return e_g_l_latitude;
    }

    public void setE_g_l_latitude(String e_g_l_latitude) {
        this.e_g_l_latitude = e_g_l_latitude;
    }

    public String getE_g_l_longitude() {
        return e_g_l_longitude;
    }

    public void setE_g_l_longitude(String e_g_l_longitude) {
        this.e_g_l_longitude = e_g_l_longitude;
    }

    @Override
    public String toString() {
        return "mWorkstationListArray{" +
                "e_g_l_id='" + e_g_l_id + '\'' +
                ", employerName='" + employerName + '\'' +
                ", e_g_l_department_name='" + e_g_l_department_name + '\'' +
                ", e_g_l_student_count='" + e_g_l_student_count + '\'' +
                ", e_g_l_latitude='" + e_g_l_latitude + '\'' +
                ", e_g_l_longitude='" + e_g_l_longitude + '\'' +
                '}';
    }
}
