package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mLearnerProgressReportListDAO {

    long insert(List<mLearnerProgressReportListArray> dataArrays);
    int update(mLearnerProgressReportListArray dataArrays);
    int delete(int id);
    mLearnerProgressReportListArray getById(int id);
    void truncate();
    List<mLearnerProgressReportListArray> getAll();



}
