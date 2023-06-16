package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class mAttListbyMonthAdapter implements mAttListByMonthDAO{

    DbHelper dbHelper;


    public mAttListbyMonthAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(List<mAttListByMonthArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS +
                    " Values('"+dataArrays.get(i).getS_a_id()+"'," +
                    "'"+dataArrays.get(i).getDate()+"',"+
                    "'"+dataArrays.get(i).getDay()+"',"+
                    "'"+dataArrays.get(i).getLogin_time()+"',"+
                    "'"+dataArrays.get(i).getLogout_time()+"',"+
                    "'"+dataArrays.get(i).getTime_spent()+"',"+
                    "'"+dataArrays.get(i).getOvertime_hour()+"',"+
                    "'"+dataArrays.get(i).getLearner_comment_btn()+"',"+
                    "'"+dataArrays.get(i).getAttendance_status()+"',"+
                    "'"+dataArrays.get(i).getOut_of_range()+"',"+
                    "'"+dataArrays.get(i).getDate_input()+"');");

        }
        Log.d("insert","dataArrays "+dataArrays);
        return 1;
    }

    @Override
    public int update(mAttListByMonthArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_SA_ID                      ,dataArrays.getS_a_id());
        contentValues.put(DbSchema.COLUMN_DATE                       ,dataArrays.getDate());
        contentValues.put(DbSchema.COLUMN_DAY                        ,dataArrays.getDay());
        contentValues.put(DbSchema.COLUMN_LOGIN_TIME                 ,dataArrays.getLogin_time());
        contentValues.put(DbSchema.COLUMN_LOGOUT_TIME                ,dataArrays.getLogout_time());
        contentValues.put(DbSchema.COLUMN_TIME_SPENT                 ,dataArrays.getTime_spent());
        contentValues.put(DbSchema.COLUMN_OVERTIME_HOUR              ,dataArrays.getOvertime_hour());
        contentValues.put(DbSchema.COLUMN_LEARNER_COMMENT_BTN        ,dataArrays.getLearner_comment_btn());
        contentValues.put(DbSchema.COLUMN_ATTENDANCESTATUS           ,dataArrays.getAttendance_status());
        contentValues.put(DbSchema.COLUMN_OUT_OF_RANGE               ,dataArrays.getOut_of_range());
        contentValues.put(DbSchema.COLUMN_DATE_INPUT               ,dataArrays.getDate_input());

        int data = db.update(DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS, contentValues, DbSchema.COLUMN_DATE_INPUT + " = " + dataArrays.getDate_input(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS, DbSchema.COLUMN_SA_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public List<mAttListByMonthArray> getById(String id) {
        mAttListByMonthArray dataArray=null;
        List<mAttListByMonthArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_SA_ID                   ,
                DbSchema.COLUMN_DATE                    ,
                DbSchema.COLUMN_DAY                     ,
                DbSchema.COLUMN_LOGIN_TIME              ,
                DbSchema.COLUMN_LOGOUT_TIME             ,
                DbSchema.COLUMN_TIME_SPENT              ,
                DbSchema.COLUMN_OVERTIME_HOUR           ,
                DbSchema.COLUMN_LEARNER_COMMENT_BTN     ,
                DbSchema.COLUMN_ATTENDANCESTATUS        ,
                DbSchema.COLUMN_OUT_OF_RANGE            ,
                DbSchema.COLUMN_DATE_INPUT            };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS, columns, DbSchema.COLUMN_DATE_INPUT + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   s_a_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SA_ID                  ));
                    String   date                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                   ));
                    String   day                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                    ));
                    String   login_time            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_TIME             ));
                    String   logout_time           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_TIME            ));
                    String   time_spent            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TIME_SPENT             ));
                    String   overtime_hour         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OVERTIME_HOUR          ));
                    String   learner_comment_btn       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_COMMENT_BTN    ));
                    String   attendance_status         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTENDANCESTATUS       ));
                    String   out_of_range              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUT_OF_RANGE           ));
                    String   date_input              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE_INPUT           ));

                    dataArray = new mAttListByMonthArray(
                            s_a_id,
                            date,
                            day,
                            login_time,
                            logout_time,
                            time_spent,
                            overtime_hour,
                            learner_comment_btn,
                            attendance_status,
                            out_of_range,
                            date_input);
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

    @Override
    public void truncate() {
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS);
    }

    @Override
    public List<mAttListByMonthArray> getAll() {
        mAttListByMonthArray dataArray=null;
        List<mAttListByMonthArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_SA_ID                   ,
                DbSchema.COLUMN_DATE                    ,
                DbSchema.COLUMN_DAY                     ,
                DbSchema.COLUMN_LOGIN_TIME              ,
                DbSchema.COLUMN_LOGOUT_TIME             ,
                DbSchema.COLUMN_TIME_SPENT              ,
                DbSchema.COLUMN_OVERTIME_HOUR           ,
                DbSchema.COLUMN_LEARNER_COMMENT_BTN     ,
                DbSchema.COLUMN_ATTENDANCESTATUS        ,
                DbSchema.COLUMN_OUT_OF_RANGE            ,
                DbSchema.COLUMN_DATE_INPUT            };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   s_a_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SA_ID                  ));
                    String   date                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                   ));
                    String   day                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                    ));
                    String   login_time            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_TIME             ));
                    String   logout_time           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_TIME            ));
                    String   time_spent            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TIME_SPENT             ));
                    String   overtime_hour         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OVERTIME_HOUR          ));
                    String   learner_comment_btn       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_COMMENT_BTN    ));
                    String   attendance_status         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTENDANCESTATUS       ));
                    String   out_of_range              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUT_OF_RANGE           ));
                    String   date_input              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE_INPUT           ));

                    dataArray = new mAttListByMonthArray(
                           s_a_id,
                           date,
                           day,
                           login_time,
                           logout_time,
                           time_spent,
                           overtime_hour,
                           learner_comment_btn,
                           attendance_status,
                           out_of_range,


                            date_input);
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
