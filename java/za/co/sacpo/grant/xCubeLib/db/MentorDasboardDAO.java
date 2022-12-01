package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface MentorDasboardDAO {
    long insert(List<MentorDashboardArray> dataArrays);
    int update(MentorDashboardArray dataArrays);
    int delete(int id);
    MentorDashboardArray getById(int id);
    void truncate();
    List<MentorDashboardArray> getAll();
}
