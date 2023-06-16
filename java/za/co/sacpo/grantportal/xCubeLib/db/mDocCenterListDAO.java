package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface mDocCenterListDAO {

    long insert(List<mDocCenterListArray> dataArrays);
    int update(mDocCenterListArray dataArrays);
    int delete(int id);
    mDocCenterListArray getById(int id);
    void truncate();
    List<mDocCenterListArray> getAll();



}
