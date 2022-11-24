package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface existingLeaveListDAO {
    long insert(List<existingLeaveListArray> dataArrays);
    int update(existingLeaveListArray dataArrays);
    int delete(int id);
    existingLeaveListArray getById(int id);
    void truncate();
    List<existingLeaveListArray> getAll();
}
