package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface sWeeklyReportDetailsDAO {

    long insert(List<sWeeklyReportDetails> dataArrays);
    int update(sWeeklyReportDetails dataArrays);
    int delete(int id);
    sWeeklyReportDetails getById(int id);
    void truncate();
    List<sWeeklyReportDetails> getAll();



}
