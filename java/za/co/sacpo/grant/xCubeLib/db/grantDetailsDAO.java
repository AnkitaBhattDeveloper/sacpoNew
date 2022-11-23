package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface grantDetailsDAO {

    long insert(grantDetailsArray dataArrays);
    int update(grantDetailsArray dataArrays);
    int delete(int id);
    grantDetailsArray getById(int id);
    void truncate();
    List<grantDetailsArray> getAll();

}
