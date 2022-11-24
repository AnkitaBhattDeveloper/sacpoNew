package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class populateAttListAdapter implements populateAttListDAO{

    DbHelper dbHelper;
    Context context;


    public populateAttListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<populateAttListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_POPULATE_ATTLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_a_id()+"'," +
                    "'"+dataArrays.get(i).getSudent_id()+"',"+
                    "'"+dataArrays.get(i).getDate()+"',"+
                    "'"+dataArrays.get(i).getDay()+"',"+
                    "'"+dataArrays.get(i).getLogin_color()+"',"+
                    "'"+dataArrays.get(i).getLogout_color()+"',"+
                    "'"+dataArrays.get(i).getLogin_time()+"',"+
                    "'"+dataArrays.get(i).getLogout_time()+"',"+
                    "'"+dataArrays.get(i).getHours_worked()+"',"+
                    "'"+dataArrays.get(i).getAttendance_status()+"',"+
                    "'"+dataArrays.get(i).getDistance_from_workstation()+"',"+
                    "'"+dataArrays.get(i).getView_comment_link()+"',"+
                    "'"+dataArrays.get(i).getS_a_learner_comment()+"');");

        }

        return 1;
    }

    @Override
    public int update(populateAttListArray dataArrays) {
        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_A_ID                     ,dataArrays.getS_a_id());
        contentValues.put(DbSchema.COLUMN_SUDENT_ID                     ,dataArrays.getSudent_id());
        contentValues.put(DbSchema.COLUMN_DATE                      ,dataArrays.getDate());
        contentValues.put(DbSchema.COLUMN_DAY                        ,dataArrays.getDay());
        contentValues.put(DbSchema.COLUMN_LOGIN_COLOR                ,dataArrays.getLogin_color());
        contentValues.put(DbSchema.COLUMN_LOGOUT_COLOR               ,dataArrays.getLogout_color());
        contentValues.put(DbSchema.COLUMN_LOGIN_TIME               ,dataArrays.getLogin_time());
        contentValues.put(DbSchema.COLUMN_LOGOUT_TIME               ,dataArrays.getLogout_time());
        contentValues.put(DbSchema.COLUMN_HRS_WORKED                 ,dataArrays.getHours_worked());
        contentValues.put(DbSchema.COLUMN_ATTE_STATUS                ,dataArrays.getAttendance_status());
        contentValues.put(DbSchema.COLUMN_DISTANCE_WORKSTATION                ,dataArrays.getDistance_from_workstation());
        contentValues.put(DbSchema.COLUMN_VIEW_COMMENT_LINK                ,dataArrays.getView_comment_link());
        contentValues.put(DbSchema.COLUMN_S_LEARNER_COMMENT       ,dataArrays.getS_a_learner_comment());

        int data = db.update(DbSchema.TABLE_POPULATE_ATTLIST_DETAILS, contentValues, DbSchema.COLUMN_S_A_ID + " = " + dataArrays.getS_a_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_POPULATE_ATTLIST_DETAILS, DbSchema.COLUMN_S_A_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public populateAttListArray getById(int id) {
        populateAttListArray dataArray=null;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DbSchema.COLUMN_S_A_ID                      ,
                DbSchema.COLUMN_SUDENT_ID                   ,
                DbSchema.COLUMN_DATE                          ,
                DbSchema.COLUMN_DAY                          ,
                DbSchema.COLUMN_LOGIN_COLOR                  ,
                DbSchema.COLUMN_LOGOUT_COLOR                 ,
                DbSchema.COLUMN_LOGIN_TIME                 ,
                DbSchema.COLUMN_LOGOUT_TIME                 ,
                DbSchema.COLUMN_HRS_WORKED                    ,
                DbSchema.COLUMN_ATTE_STATUS                  ,
                DbSchema.COLUMN_DISTANCE_WORKSTATION                  ,
                DbSchema.COLUMN_VIEW_COMMENT_LINK                  ,
                DbSchema.COLUMN_S_LEARNER_COMMENT        };

        Cursor cursors = db.query(DbSchema.TABLE_POPULATE_ATTLIST_DETAILS, columns, DbSchema.COLUMN_S_A_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_a_id                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID               ));
                    String sudent_id                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUDENT_ID               ));
                    String date                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                 ));
                    String day                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                  ));
                    String login_color                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_COLOR          ));
                    String logout_color                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_COLOR         ));
                    String login_time                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_TIME         ));
                    String logout_time                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_TIME         ));
                    String hours_worked                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HRS_WORKED           ));
                    String attendance_status           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTE_STATUS          ));
                    String distance_from_workstation            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DISTANCE_WORKSTATION          ));
                    String view_comment_link           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_VIEW_COMMENT_LINK          ));
                    String s_a_learner_comment           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_LEARNER_COMMENT ));
                    dataArray = new populateAttListArray(s_a_id,sudent_id,date,day,login_color,logout_color,
                            login_time,logout_time,hours_worked,attendance_status,
                            distance_from_workstation,view_comment_link,s_a_learner_comment);
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
        dbHelper.truncateTable(DbSchema.TABLE_POPULATE_ATTLIST_DETAILS);
    }

    @Override
    public List<populateAttListArray> getAll() {
        populateAttListArray dataArray=null;
        List<populateAttListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {
                DbSchema.COLUMN_S_A_ID                      ,
                DbSchema.COLUMN_SUDENT_ID                   ,
                DbSchema.COLUMN_DATE                          ,
                DbSchema.COLUMN_DAY                          ,
                DbSchema.COLUMN_LOGIN_COLOR                  ,
                DbSchema.COLUMN_LOGOUT_COLOR                 ,
                DbSchema.COLUMN_LOGIN_TIME                 ,
                DbSchema.COLUMN_LOGOUT_TIME                 ,
                DbSchema.COLUMN_HRS_WORKED                    ,
                DbSchema.COLUMN_ATTE_STATUS                  ,
                DbSchema.COLUMN_DISTANCE_WORKSTATION                  ,
                DbSchema.COLUMN_VIEW_COMMENT_LINK                  ,
                DbSchema.COLUMN_S_LEARNER_COMMENT        };

        Cursor cursors = db.query(DbSchema.TABLE_POPULATE_ATTLIST_DETAILS, columns, null, null, null, null, null);
        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_a_id                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID               ));
                    String sudent_id                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUDENT_ID               ));
                    String date                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                 ));
                    String day                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                  ));
                    String login_color                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_COLOR          ));
                    String logout_color                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_COLOR         ));
                    String login_time                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGIN_TIME         ));
                    String logout_time                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LOGOUT_TIME         ));
                    String hours_worked                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HRS_WORKED           ));
                    String attendance_status           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTE_STATUS          ));
                    String distance_from_workstation            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DISTANCE_WORKSTATION          ));
                    String view_comment_link           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_VIEW_COMMENT_LINK          ));
                    String s_a_learner_comment           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_LEARNER_COMMENT ));
                    dataArray = new populateAttListArray(s_a_id,sudent_id,date,day,login_color,logout_color,
                            login_time,logout_time,hours_worked,attendance_status,
                            distance_from_workstation,view_comment_link,s_a_learner_comment);
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
