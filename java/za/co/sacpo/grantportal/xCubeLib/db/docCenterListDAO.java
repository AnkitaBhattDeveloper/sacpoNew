package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface docCenterListDAO {

    long insert(List<docCenterListArray> dataArrays);
    int update(docCenterListArray dataArrays);
    int delete(int id);
    docCenterListArray getById(int id);
    void truncate();
    List<docCenterListArray> getAll();

}
