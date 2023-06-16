package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public interface helpArrayDAO {

    long insert(helpArrays helpArrays);
    int update(helpArrays helpArrays);
    int delete(int id);
    helpArrays getById(int id);
    void truncate();
    List<helpArrays> getAll();
}
