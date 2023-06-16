package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface DashboardDataArrayDAO {
    long insert(DashboardDataArray dataArrays);
    int update(DashboardDataArray dataArrays);
    int delete(int id);
    DashboardDataArray getById(int id);
    void truncate();
    List<DashboardDataArray> getAll();
}
