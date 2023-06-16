package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public interface studentsDAO {

    long insert(students students);
    int update(students students);
    int delete(int id);
    students getById(int id);
    void truncate();
    List<students> getAll();
}
