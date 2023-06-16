package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mPastAttendanceAdapter implements mPastAttendanceDAO{

    DbHelper dbHelper;


    public mPastAttendanceAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(List<mPastAttendanceArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS +
                    " Values('"+dataArrays.get(i).getSno()+"'," +
                    "'"+dataArrays.get(i).getYear_month()+"',"+
                    "'"+dataArrays.get(i).getLeave()+"',"+
                    "'"+dataArrays.get(i).getDays_worked()+"',"+
                    "'"+dataArrays.get(i).getUnpaid_leave()+"',"+
                    "'"+dataArrays.get(i).getOther_paid_leave()+"',"+
                    "'"+dataArrays.get(i).getSick_leave()+"',"+
                    "'"+dataArrays.get(i).getAnnual_leave()+"',"+
                    "'"+dataArrays.get(i).getYear()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getStudent_id()+"',"+
                    "'"+dataArrays.get(i).getGrant_id()+"');");

        }

        return 1;
    }

    @Override
    public int update(mPastAttendanceArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_GRANT_ID                ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_STUDENTID               ,dataArrays.getStudent_id());
        contentValues.put(DbSchema.COLUMN_MONTH                   ,dataArrays.getMonth());
        contentValues.put(DbSchema.COLUMN_YEAR                    ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_DAYS_WORKED             ,dataArrays.getDays_worked());
        contentValues.put(DbSchema.COLUMN_LEAVE                   ,dataArrays.getLeave());
        contentValues.put(DbSchema.COLUMN_ANNUAL_LEAVE            ,dataArrays.getAnnual_leave());
        contentValues.put(DbSchema.COLUMN_SICK_LEAVE              ,dataArrays.getSick_leave());
        contentValues.put(DbSchema.COLUMN_OTHER_PAID_LEAVE        ,dataArrays.getOther_paid_leave());
        contentValues.put(DbSchema.COLUMN_UNPAID_LEAVE            ,dataArrays.getUnpaid_leave());
        contentValues.put(DbSchema.COLUMN_SNO                     ,dataArrays.getSno());
        contentValues.put(DbSchema.COLUMN_YEAR_MONTH              ,dataArrays.getYear_month());

        int data = db.update(DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS, contentValues, DbSchema.COLUMN_SNO + " = " + dataArrays.getSno(), null);
        return data;
    }

    @Override
    public int delete(int id) {
           SQLiteDatabase db = dbHelper.getWritableDatabase();
                  int data = db.delete(DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS, DbSchema.COLUMN_SNO + " = " + id, null);
                  //db.close();
                  return data;

    }

    @Override
    public List<mPastAttendanceArray> getById(int id) {
        mPastAttendanceArray dataArray=null;
        List<mPastAttendanceArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_GRANT_ID            ,
                DbSchema.COLUMN_STUDENTID           ,
                DbSchema.COLUMN_MONTH               ,
                DbSchema.COLUMN_YEAR                ,
                DbSchema.COLUMN_DAYS_WORKED         ,
                DbSchema.COLUMN_LEAVE               ,
                DbSchema.COLUMN_ANNUAL_LEAVE        ,
                DbSchema.COLUMN_SICK_LEAVE          ,
                DbSchema.COLUMN_OTHER_PAID_LEAVE    ,
                DbSchema.COLUMN_UNPAID_LEAVE        ,
                DbSchema.COLUMN_SNO                 ,
                DbSchema.COLUMN_YEAR_MONTH          };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS, columns, DbSchema.COLUMN_STUDENTID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   grant_id               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID            ));
                    String   student_id             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDENTID           ));
                    String   month                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH               ));
                    String   year                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                ));
                    String   days_worked            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAYS_WORKED         ));
                    String   leave                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE               ));
                    String   annual_leave               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE        ));
                    String   sick_leave             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE          ));
                    String   other_paid_leave           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OTHER_PAID_LEAVE    ));
                    String   unpaid_leave               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE        ));
                    String   sno                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SNO                 ));
                    String   year_month                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR_MONTH          ));

                    dataArray = new mPastAttendanceArray(
                            grant_id,
                            student_id,
                            month,
                            year,
                            days_worked,
                            leave,
                            annual_leave,
                            sick_leave,
                            other_paid_leave,
                            unpaid_leave,
                            sno,
                            year_month






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

    @Override
    public void truncate() {
 dbHelper.truncateTable(DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS);
    }

    @Override
    public List<mPastAttendanceArray> getAll() {
        mPastAttendanceArray dataArray=null;
        List<mPastAttendanceArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_GRANT_ID            ,
                DbSchema.COLUMN_STUDENTID           ,
                DbSchema.COLUMN_MONTH               ,
                DbSchema.COLUMN_YEAR                ,
                DbSchema.COLUMN_DAYS_WORKED         ,
                DbSchema.COLUMN_LEAVE               ,
                DbSchema.COLUMN_ANNUAL_LEAVE        ,
                DbSchema.COLUMN_SICK_LEAVE          ,
                DbSchema.COLUMN_OTHER_PAID_LEAVE    ,
                DbSchema.COLUMN_UNPAID_LEAVE        ,
                DbSchema.COLUMN_SNO                 ,
                DbSchema.COLUMN_YEAR_MONTH          };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_PAST_ATTENDANCE_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   grant_id               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID            ));
                    String   student_id             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDENTID           ));
                    String   month                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH               ));
                    String   year                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                ));
                    String   days_worked            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAYS_WORKED         ));
                    String   leave                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE               ));
                    String   annual_leave               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE        ));
                    String   sick_leave             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE          ));
                    String   other_paid_leave           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OTHER_PAID_LEAVE    ));
                    String   unpaid_leave               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE        ));
                    String   sno                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SNO                 ));
                    String   year_month                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR_MONTH          ));

                    dataArray = new mPastAttendanceArray(
                           grant_id,
                           student_id,
                           month,
                           year,
                           days_worked,
                           leave,
                           annual_leave,
                           sick_leave,
                           other_paid_leave,
                           unpaid_leave,
                           sno,
                           year_month






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
