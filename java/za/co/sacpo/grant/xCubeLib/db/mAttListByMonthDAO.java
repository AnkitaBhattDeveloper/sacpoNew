package za.co.sacpo.grant.xCubeLib.db;

import java.util.List;

public interface mAttListByMonthDAO {
      long insert(List<mAttListByMonthArray> dataArrays);
    int update(mAttListByMonthArray dataArrays);
    int delete(int id);
    List<mAttListByMonthArray> getById(String id);
    void truncate();
    List<mAttListByMonthArray> getAll();




}
