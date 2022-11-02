package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public interface masterArrayDAO {

    long insert(masterArrays masterArrays);
    int update(masterArrays masterArrays);
    int delete(int id);
    masterArrays getById(int id);
    void truncate();
    List<masterArrays> getAll();
}
