package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface attDetailsDAO {


     long insert(List<attDetailsArray> dataArrays);
    int update(attDetailsArray dataArrays);
    int delete(int id);
    attDetailsArray getById(int id);
    void truncate();
    List<attDetailsArray> getAll();

}
