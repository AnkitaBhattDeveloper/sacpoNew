package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface mLearnerGrantDetailsDAO {

    long insert(mLearnerGrantDetailsArray dataArrays);
    int update(mLearnerGrantDetailsArray dataArrays);
    int delete(int id);
    mLearnerGrantDetailsArray getById(int id);
    void truncate();
    List<mLearnerGrantDetailsArray> getAll();




}
