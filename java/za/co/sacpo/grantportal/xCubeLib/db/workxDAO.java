package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public interface workxDAO {

    long insert(workx workx);
    int update(workx workx);
    int delete(int id);
    workx getById(int id);
    void truncate();
    List<workx> getAll();
}
