package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mPendingClaimListDAO {

long insert(List<mPendingClaimListArray> dataArrays);
    int update(mPendingClaimListArray dataArrays);
    int delete(int id);
    mPendingClaimListArray getById(int id);
    void truncate();
    List<mPendingClaimListArray> getAll();
}
