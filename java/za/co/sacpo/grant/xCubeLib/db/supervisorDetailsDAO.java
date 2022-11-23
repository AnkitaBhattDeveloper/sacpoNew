package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface supervisorDetailsDAO {

    long insert(supervisorDetailsArray dataArrays);
    int update(supervisorDetailsArray dataArrays);
    int delete(int id);
    supervisorDetailsArray getById(int id);
    void truncate();
    List<supervisorDetailsArray> getAll();

}
