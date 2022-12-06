package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mPastAttendanceDAO {

       long insert(List<mPastAttendanceArray> dataArrays);
    int update(mPastAttendanceArray dataArrays);
    int delete(int id);
    List<mPastAttendanceArray> getById(int id);
    void truncate();
    List<mPastAttendanceArray> getAll();


}
