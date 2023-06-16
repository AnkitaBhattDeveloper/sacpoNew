package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface pastClaimListDAO {

    long insert(List<pastClaimListArray> dataArrays);
    int update(pastClaimListArray dataArrays);
    int delete(int id);
    pastClaimListArray getById(int id);
    void truncate();
    List<pastClaimListArray> getAll();

}
