package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface sWeeklyListDAO {

    long insert(List<sWeeklyReportListArray> dataArrays);
    int update(sWeeklyReportListArray dataArrays);
    int delete(int id);
    sWeeklyReportListArray getById(int id);
    void truncate();
    List<sWeeklyReportListArray> getAll();

}
