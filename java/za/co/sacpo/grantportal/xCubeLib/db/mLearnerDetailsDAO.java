package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface mLearnerDetailsDAO {

    long insert(mLearnerDetailsArray dataArrays);
    int update(mLearnerDetailsArray dataArrays);
    int delete(int id);
    mLearnerDetailsArray getById(int id);
    void truncate();
    List<mLearnerDetailsArray> getAll();


}
