package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface bankDetailsDAO {

    long insert(bankDetailsArray dataArrays);
    int update(bankDetailsArray dataArrays);
    int delete(int id);
    bankDetailsArray getById(int id);
    void truncate();
    List<bankDetailsArray> getAll();


}
