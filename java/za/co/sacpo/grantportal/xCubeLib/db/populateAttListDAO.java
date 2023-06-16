package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface populateAttListDAO {
    long insert(List<populateAttListArray> dataArrays);
    int update(populateAttListArray dataArrays);
    int delete(int id);
    populateAttListArray getById(int id);
    void truncate();
    List<populateAttListArray> getAll();
}
