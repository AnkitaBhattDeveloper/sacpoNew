package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mWorkstationListDAO {


    long insert(List<mWorkstationListArray> dataArrays);
    int update(mWorkstationListArray dataArrays);
    int delete(int id);
    mWorkstationListArray getById(int id);
    void truncate();
    List<mWorkstationListArray> getAll();

}
