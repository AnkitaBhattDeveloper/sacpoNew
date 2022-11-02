package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public interface staticLabelsDAO {

    long insert(staticLabels staticLabels);
    int update(staticLabels staticLabels);
    int delete(int id);
    staticLabels getById(int id);
    void truncate();
    List<staticLabels> getAll();
}
