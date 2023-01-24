package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mWorkstationListAdapter implements mWorkstationListDAO{

    DbHelper dbHelper;

    public mWorkstationListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<mWorkstationListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS +
                    " Values('"+dataArrays.get(i).getE_g_l_id()+"'," +
                    "'"+dataArrays.get(i).getEmployerName()+"'," +
                    "'"+dataArrays.get(i).getE_g_l_department_name()+"'," +
                    "'"+dataArrays.get(i).getE_g_l_student_count()+"'," +
                    "'"+dataArrays.get(i).getE_g_l_latitude()+"'," +
                    "'"+dataArrays.get(i).getE_g_l_longitude()+"');");

        }

        return 1;
    }

    @Override
    public int update(mWorkstationListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_EGL_ID                      ,dataArrays.getE_g_l_id());
        contentValues.put(DbSchema.COLUMN_EMP_NAME                    ,dataArrays.getEmployerName());
        contentValues.put(DbSchema.COLUMN_EGL_DEPT_NAME               ,dataArrays.getE_g_l_department_name());
        contentValues.put(DbSchema.COLUMN_EGL_STUDENT_COUNT           ,dataArrays.getE_g_l_student_count());
        contentValues.put(DbSchema.COLUMN_EGL_LATITUDE                ,dataArrays.getE_g_l_latitude());
        contentValues.put(DbSchema.COLUMN_EGL_LOBGITUDE           ,dataArrays.getE_g_l_longitude());

        int data = db.update(DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS, contentValues, DbSchema.COLUMN_EGL_ID + " = " + dataArrays.getE_g_l_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS, DbSchema.COLUMN_EGL_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mWorkstationListArray getById(int id) {
        mWorkstationListArray dataArray=null;
        List<mWorkstationListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_EGL_ID                ,
                DbSchema.COLUMN_EMP_NAME              ,
                DbSchema.COLUMN_EGL_DEPT_NAME         ,
                DbSchema.COLUMN_EGL_STUDENT_COUNT     ,
                DbSchema.COLUMN_EGL_LATITUDE          ,
                DbSchema.COLUMN_EGL_LOBGITUDE         };

        Cursor cursors = db.query(DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS, columns, DbSchema.COLUMN_EGL_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_ID                       ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMP_NAME                     ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_DEPT_NAME                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_STUDENT_COUNT            ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_LATITUDE                 ));
                    String learner_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_LOBGITUDE         ));
                    dataArray = new mWorkstationListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name
                    );
                   // dataArrayList.add(dataArray);
                }while (cursors.moveToNext());

            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
            }
        }
        return dataArray;
    }

    @Override
    public void truncate() {
        dbHelper.truncateTable(DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS);
    }

    @Override
    public List<mWorkstationListArray> getAll() {
        mWorkstationListArray dataArray=null;
        List<mWorkstationListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_EGL_ID                ,
                DbSchema.COLUMN_EMP_NAME              ,
                DbSchema.COLUMN_EGL_DEPT_NAME         ,
                DbSchema.COLUMN_EGL_STUDENT_COUNT     ,
                DbSchema.COLUMN_EGL_LATITUDE          ,
                DbSchema.COLUMN_EGL_LOBGITUDE         };

        Cursor cursors = db.query(DbSchema.TABLE_M_WORKSTATION_LIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_ID                       ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMP_NAME                     ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_DEPT_NAME                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_STUDENT_COUNT            ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_LATITUDE                 ));
                    String learner_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EGL_LOBGITUDE         ));
                    dataArray = new mWorkstationListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name
                    );
                    dataArrayList.add(dataArray);
                }while (cursors.moveToNext());

            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
            }
        }
        return dataArrayList;
    }
}
