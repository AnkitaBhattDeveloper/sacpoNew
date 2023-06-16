package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class sWeeklyReportListAdapter implements sWeeklyListDAO{

    DbHelper dbHelper;
    Context context;


    public sWeeklyReportListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<sWeeklyReportListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_w_r_id()+"'," +
                    "'"+dataArrays.get(i).getTitle()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getYear()+"',"+
                    "'"+dataArrays.get(i).getMonth_year()+"',"+
                    "'"+dataArrays.get(i).getGrant_id()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_status()+"',"+
                    "'"+dataArrays.get(i).getEdit_btn()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_status_id()+"',"+
                    "'"+dataArrays.get(i).getReport_no()+"');");

        }

        return 1;
    }


    @Override
    public int update(sWeeklyReportListArray dataArrays) {
        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_W_R_ID                           ,dataArrays.getS_w_r_id());
        contentValues.put(DbSchema.COLUMN_REPORT_TITLE                       ,dataArrays.getTitle());
        contentValues.put(DbSchema.  COLUMN_MONTH                            ,dataArrays.getMonth());
        contentValues.put(DbSchema.  COLUMN_YEAR                             ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_MONTH_YEAR                         ,dataArrays.getMonth_year());
        contentValues.put(DbSchema.  COLUMN_GRANT_ID                         ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS                  ,dataArrays.getSupervisor_status());
        contentValues.put(DbSchema.COLUMN_EDIT_BTN                           ,dataArrays.getEdit_btn());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS_ID               ,dataArrays.getSupervisor_status_id());
        contentValues.put(DbSchema.COLUMN_REPORT_NO                          ,dataArrays.getReport_no());

        int data = db.update(DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS, contentValues, DbSchema.COLUMN_S_W_R_ID + " = " + dataArrays.getS_w_r_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS, DbSchema.COLUMN_S_W_R_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public sWeeklyReportListArray getById(int id) {
        sWeeklyReportListArray dataArray=null;
        List<sWeeklyReportListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_W_R_ID,
                DbSchema.COLUMN_REPORT_TITLE          ,
                DbSchema.  COLUMN_MONTH              ,
                DbSchema.  COLUMN_YEAR                 ,
                DbSchema.COLUMN_MONTH_YEAR            ,
                DbSchema.  COLUMN_GRANT_ID               ,
                DbSchema.COLUMN_SUPERVISOR_STATUS      ,
                DbSchema.COLUMN_EDIT_BTN                ,
                DbSchema.COLUMN_SUPERVISOR_STATUS_ID    ,
                DbSchema.COLUMN_REPORT_NO              };
        Cursor cursors = db.query(DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS, columns, DbSchema.COLUMN_S_W_R_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_w_r_id                                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ID               ));
                    String title                                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_TITLE           ));
                    String month                                        = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_MONTH                ));
                    String year                                         = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_YEAR                 ));
                    String month_year                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_YEAR             ));
                    String grant_id                                     = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_GRANT_ID             ));
                    String supervisor_status                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS      ));
                    String edit_btn                                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EDIT_BTN               ));
                    String supervisor_status_id                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS_ID   ));
                    String report_no                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_NO              ));
                    dataArray = new sWeeklyReportListArray(s_w_r_id,title,month,year,month_year,
                            grant_id,supervisor_status,edit_btn,supervisor_status_id, report_no);
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
        dbHelper.truncateTable(DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS);
    }

    @Override
    public List<sWeeklyReportListArray> getAll() {
        sWeeklyReportListArray dataArray=null;
        List<sWeeklyReportListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_W_R_ID,
                DbSchema.COLUMN_REPORT_TITLE          ,
                DbSchema.  COLUMN_MONTH              ,
                DbSchema.  COLUMN_YEAR                 ,
                DbSchema.COLUMN_MONTH_YEAR            ,
                DbSchema.  COLUMN_GRANT_ID               ,
                DbSchema.COLUMN_SUPERVISOR_STATUS      ,
                DbSchema.COLUMN_EDIT_BTN                ,
                DbSchema.COLUMN_SUPERVISOR_STATUS_ID    ,
                DbSchema.COLUMN_REPORT_NO              };
        Cursor cursors = db.query(DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_w_r_id                                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ID               ));
                    String title                                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_TITLE           ));
                    String month                                        = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_MONTH                ));
                    String year                                         = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_YEAR                 ));
                    String month_year                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_YEAR             ));
                    String grant_id                                     = cursors.getString(cursors.getColumnIndex(DbSchema.  COLUMN_GRANT_ID             ));
                    String supervisor_status                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS      ));
                    String edit_btn                                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EDIT_BTN               ));
                    String supervisor_status_id                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS_ID   ));
                    String report_no                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_NO              ));
                    dataArray = new sWeeklyReportListArray(s_w_r_id,title,month,year,month_year,
                            grant_id,supervisor_status,edit_btn,supervisor_status_id, report_no);
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
