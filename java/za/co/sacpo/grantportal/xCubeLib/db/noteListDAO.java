package za.co.sacpo.grantportal.xCubeLib.db;

import java.util.List;

public interface noteListDAO {

     long insert(List<noteListArray> dataArrays);
    int update(noteListArray dataArrays);
    int delete(int id);
    noteListArray getById(int id);
    void truncate();
    List<noteListArray> getAll();

}
