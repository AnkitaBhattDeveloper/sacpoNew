package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mLearnerProgressReportListAdapter implements mLearnerProgressReportListDAO{

    DbHelper dbHelper;

    public mLearnerProgressReportListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }



    @Override
    public long insert(List<mLearnerProgressReportListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_w_r_id()+"'," +
                    "'"+dataArrays.get(i).getTitle()+"'," +
                    "'"+dataArrays.get(i).getYear()+"'," +
                    "'"+dataArrays.get(i).getSupervisor_status()+"'," +
                    "'"+dataArrays.get(i).getNumber()+"'," +
                    "'"+dataArrays.get(i).getShow_approve_link()+"'," +
                    "'"+dataArrays.get(i).getMonth()+"');");

        }

        return 1;
    }

    @Override
    public int update(mLearnerProgressReportListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_SWR_ID                       ,dataArrays.getS_w_r_id());
        contentValues.put(DbSchema.COLUMN_TITLE                        ,dataArrays.getTitle());
        contentValues.put(DbSchema.COLUMN_YEAR                         ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS            ,dataArrays.getSupervisor_status());
        contentValues.put(DbSchema.COLUMN_NUMBER                       ,dataArrays.getNumber());
        contentValues.put(DbSchema.COLUMN_SHOW_APPROVE_LINK        ,dataArrays.getShow_approve_link());
        contentValues.put(DbSchema.COLUMN_MONTH                   ,dataArrays.getMonth());

        int data = db.update(DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS, contentValues, DbSchema.COLUMN_SWR_ID + " = " + dataArrays.getS_w_r_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS, DbSchema.COLUMN_SWR_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mLearnerProgressReportListArray getById(int id) {
        mLearnerProgressReportListArray dataArray=null;
        List<mLearnerProgressReportListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_SWR_ID                        ,
                DbSchema.COLUMN_TITLE                         ,
                DbSchema.COLUMN_YEAR                          ,
                DbSchema.COLUMN_SUPERVISOR_STATUS             ,
                DbSchema.COLUMN_NUMBER                        ,
                DbSchema.COLUMN_SHOW_APPROVE_LINK         ,
                DbSchema.COLUMN_MONTH                    };

        Cursor cursors = db.query(DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS, columns, DbSchema.COLUMN_SWR_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SWR_ID                   ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TITLE                    ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                     ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS        ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUMBER                   ));
                    String learner_name                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_APPROVE_LINK    ));
                    String approve_stipend_link                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH               ));
                    dataArray = new mLearnerProgressReportListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name,
                            approve_stipend_link
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
        dbHelper.truncateTable(DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS);
    }

    @Override
    public List<mLearnerProgressReportListArray> getAll() {
        mLearnerProgressReportListArray dataArray=null;
        List<mLearnerProgressReportListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_SWR_ID                        ,
                DbSchema.COLUMN_TITLE                         ,
                DbSchema.COLUMN_YEAR                          ,
                DbSchema.COLUMN_SUPERVISOR_STATUS             ,
                DbSchema.COLUMN_NUMBER                        ,
                DbSchema.COLUMN_SHOW_APPROVE_LINK         ,
                DbSchema.COLUMN_MONTH                    };

        Cursor cursors = db.query(DbSchema.TABLE_M_LEARNER_REPORTLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SWR_ID                   ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TITLE                    ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                     ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS        ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUMBER                   ));
                    String learner_name                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_APPROVE_LINK    ));
                    String approve_stipend_link                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH               ));
                    dataArray = new mLearnerProgressReportListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name,
                            approve_stipend_link
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
