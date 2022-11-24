package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface attListDetailsDAO {
      long insert(List<attListArray> dataArrays);
    int update(attListArray dataArrays);
    int delete(int id);
    attListArray getById(int id);
    void truncate();
    List<attListArray> getAll();
}
