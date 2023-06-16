package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface mApprovedClaimListDAO {

    long insert(List<mApprovedClaimListArray> dataArrays);
    int update(mApprovedClaimListArray dataArrays);
    int delete(int id);
    mApprovedClaimListArray getById(int id);
    void truncate();
    List<mApprovedClaimListArray> getAll();


}
