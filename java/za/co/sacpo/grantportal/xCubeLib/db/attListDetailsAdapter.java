package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class attListDetailsAdapter implements attListDetailsDAO{

    private DbHelper dbHelper;
    private Context context;

    public attListDetailsAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(List<attListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_ATTLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_a_id()+"'," +
                    "'"+dataArrays.get(i).getDate()+"',"+
                    "'"+dataArrays.get(i).getDay()+"',"+
                    "'"+dataArrays.get(i).getSign_in_time()+"',"+
                    "'"+dataArrays.get(i).getSign_out_time()+"',"+
                    "'"+dataArrays.get(i).getHours_worked()+"',"+
                    "'"+dataArrays.get(i).getAttendance_status()+"',"+
                    "'"+dataArrays.get(i).getDistance_from_workstation()+"');");

        }

        return 1;
    }

    @Override
    public int update(attListArray dataArrays) {
/*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_A_ID                     ,dataArrays.getS_a_id());
        contentValues.put(DbSchema.COLUMN_DATE                      ,dataArrays.getDate());
        contentValues.put(DbSchema.COLUMN_DAY                        ,dataArrays.getDay());
        contentValues.put(DbSchema.COLUMN_SIGNIN_TIME                ,dataArrays.getSign_in_time());
        contentValues.put(DbSchema.COLUMN_SIGNOUT_TIME               ,dataArrays.getSign_out_time());
        contentValues.put(DbSchema.COLUMN_HRS_WORKED                 ,dataArrays.getHours_worked());
        contentValues.put(DbSchema.COLUMN_ATTE_STATUS                ,dataArrays.getAttendance_status());
        contentValues.put(DbSchema.COLUMN_DISTANCE_WORKSTATION       ,dataArrays.getDistance_from_workstation());

        int data = db.update(DbSchema.TABLE_ATTLIST_DETAILS, contentValues, DbSchema.COLUMN_S_A_ID + " = " + dataArrays.getS_a_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_ATTLIST_DETAILS, DbSchema.COLUMN_S_A_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public attListArray getById(int id) {
        attListArray dataArray=null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_A_ID                      ,
                DbSchema.COLUMN_DATE                          ,
                DbSchema.COLUMN_DAY                          ,
                DbSchema.COLUMN_SIGNIN_TIME                  ,
                DbSchema.COLUMN_SIGNOUT_TIME                 ,
                DbSchema.COLUMN_HRS_WORKED                    ,
                DbSchema.COLUMN_ATTE_STATUS                  ,
                DbSchema.COLUMN_DISTANCE_WORKSTATION        };

        Cursor cursors = db.query(DbSchema.TABLE_ATTLIST_DETAILS, columns, DbSchema.COLUMN_S_A_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String s_a_id                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID               ));
                String date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                 ));
                String day                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                  ));
                String sign_in_time                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SIGNIN_TIME          ));
                String sign_out_time                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SIGNOUT_TIME         ));
                String hours_worked                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HRS_WORKED           ));
                String attendance_status             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTE_STATUS          ));
                String distance_from_workstation       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DISTANCE_WORKSTATION ));




                dataArray = new attListArray(s_a_id,date,day,sign_in_time,sign_out_time, hours_worked,attendance_status,
                        distance_from_workstation);


            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return dataArray;
    }

    @Override
    public void truncate() {
        dbHelper.truncateTable(DbSchema.TABLE_ATTLIST_DETAILS);
    }

    @Override
    public List<attListArray> getAll() {
        attListArray dataArray=null;
        List<attListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DbSchema.COLUMN_S_A_ID                      ,
                DbSchema.COLUMN_DATE                          ,
                DbSchema.COLUMN_DAY                          ,
                DbSchema.COLUMN_SIGNIN_TIME                  ,
                DbSchema.COLUMN_SIGNOUT_TIME                 ,
                DbSchema.COLUMN_HRS_WORKED                    ,
                DbSchema.COLUMN_ATTE_STATUS                  ,
                DbSchema.COLUMN_DISTANCE_WORKSTATION        };

        Cursor cursors = db.query(DbSchema.TABLE_ATTLIST_DETAILS, columns, null, null, null, null, null);
        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_a_id                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID               ));
                    String date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                 ));
                    String day                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                  ));
                    String sign_in_time                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SIGNIN_TIME          ));
                    String sign_out_time                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SIGNOUT_TIME         ));
                    String hours_worked                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HRS_WORKED           ));
                    String attendance_status             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTE_STATUS          ));
                    String distance_from_workstation       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DISTANCE_WORKSTATION ));
                    dataArray = new attListArray(s_a_id,date,day,sign_in_time,sign_out_time, hours_worked,attendance_status,
                            distance_from_workstation);
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
