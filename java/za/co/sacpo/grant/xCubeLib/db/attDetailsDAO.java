package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface attDetailsDAO {
     long insert(attDetailsArray dataArrays);
    int update(attDetailsArray dataArrays);
    int delete(int id);
    attDetailsArray getById(int id);
    void truncate();
    List<attDetailsArray> getAll();

}
