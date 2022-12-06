package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mShowDetailsDAO {
       long insert(List<mShowDetailsArray> dataArrays);
    int update(mShowDetailsArray dataArrays);
    int delete(int id);
    mShowDetailsArray getById(int id);
    void truncate();
    List<mShowDetailsArray> getAll();
}
