package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class sWeeklyReportDetailsAdapter implements sWeeklyReportDetailsDAO{

    DbHelper dbHelper;
    Context context;


    public sWeeklyReportDetailsAdapter(Context context){
        dbHelper =DbHelper.getInstance(context);
    }

    @Override
    public long insert(List<sWeeklyReportDetails> dataArrays) {

        Log.d("insert","=====insert====="+dataArrays);

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < dataArrays.size(); i++) {


            db.execSQL("INSERT INTO " + DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS +
                    " Values('"+dataArrays.get(i).getS_w_r_id()+"'," +
                    "'"+dataArrays.get(i).getS_w_r_student_id()+"',"+
                    "'"+dataArrays.get(i).getStudent_name()+"',"+
                    "'"+dataArrays.get(i).getTitle()+"',"+
                    "'"+dataArrays.get(i).getTraining()+"',"+
                    "'"+dataArrays.get(i).getDay()+"',"+
                    "'"+dataArrays.get(i).getFeedback()+"',"+
                    "'"+dataArrays.get(i).getLearning_experices()+"',"+
                    "'"+dataArrays.get(i).getNumber()+"',"+
                    "'"+dataArrays.get(i).getMonth_year()+"',"+
                    "'"+dataArrays.get(i).getReport_add_date()+"',"+
                    "'"+dataArrays.get(i).getDate()+"',"+
                    "'"+dataArrays.get(i).getIs_supervisor_commented()+"',"+
                    "'"+dataArrays.get(i).getC_u_r_comment()+"',"+
                    "'"+dataArrays.get(i).getC_u_r_training_progress()+"',"+
                    "'"+dataArrays.get(i).getC_u_r_report_writing()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_status()+"');");
        }
        return 1;
    }

    @Override
    public int update(sWeeklyReportDetails dataArrays) {
        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_W_R_ID                         ,dataArrays.getS_w_r_id());
        contentValues.put(DbSchema.COLUMN_S_W_R_ST_ID                      ,dataArrays.getS_w_r_student_id());
        contentValues.put(DbSchema.COLUMN_STU_NAME                         ,dataArrays.getStudent_name());
        contentValues.put(DbSchema.COLUMN_TITLE                            ,dataArrays.getTitle());
        contentValues.put(DbSchema.COLUMN_TRAINING                         ,dataArrays.getTraining());
        contentValues.put(DbSchema.COLUMN_DAY                              ,dataArrays.getDay());
        contentValues.put(DbSchema.COLUMN_FEEDBACK                         ,dataArrays.getFeedback());
        contentValues.put(DbSchema.COLUMN_LEARNING_EXP                     ,dataArrays.getLearning_experices());
        contentValues.put(DbSchema.COLUMN_NUMBER                           ,dataArrays.getNumber());
        contentValues.put(DbSchema.COLUMN_MONTH_YEAR                       ,dataArrays.getMonth_year());
        contentValues.put(DbSchema.COLUMN_REPORT_ADD_DATE                  ,dataArrays.getReport_add_date());
        contentValues.put(DbSchema.COLUMN_DATE                             ,dataArrays.getDate());
        contentValues.put(DbSchema.COLUMN_IS_SUPERVISOR_COMMENTED          ,dataArrays.getIs_supervisor_commented());
        contentValues.put(DbSchema.COLUMN_C_U_R_COMMENT                    ,dataArrays.getC_u_r_comment());
        contentValues.put(DbSchema.COLUMN_C_U_R_TRAINING_PROGRESS          ,dataArrays.getC_u_r_training_progress());
        contentValues.put(DbSchema.COLUMN_C_U_R_REPORT_WRITING             ,dataArrays.getC_u_r_report_writing());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS                ,dataArrays.getSupervisor_status());
        int data = db.update(DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS, contentValues, DbSchema.COLUMN_S_W_R_ID + " = " + dataArrays.getS_w_r_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS, DbSchema.COLUMN_S_W_R_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public sWeeklyReportDetails getById(int id) {
        sWeeklyReportDetails dataArray=null;
        List<sWeeklyReportDetails> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_W_R_ID                 ,
                DbSchema.COLUMN_S_W_R_ST_ID             ,
                DbSchema.COLUMN_STU_NAME                ,
                DbSchema.COLUMN_TITLE                    ,
                DbSchema.COLUMN_TRAINING                  ,
                DbSchema.COLUMN_DAY                      ,
                DbSchema.COLUMN_FEEDBACK                  ,
                DbSchema.COLUMN_LEARNING_EXP            ,
                DbSchema.COLUMN_NUMBER                    ,
                DbSchema.COLUMN_MONTH_YEAR               ,
                DbSchema.COLUMN_REPORT_ADD_DATE        ,
                DbSchema.COLUMN_DATE                      ,
                DbSchema.COLUMN_IS_SUPERVISOR_COMMENTED   ,
                DbSchema.COLUMN_C_U_R_COMMENT             ,
                DbSchema.COLUMN_C_U_R_TRAINING_PROGRESS   ,
                DbSchema.COLUMN_C_U_R_REPORT_WRITING      ,
                DbSchema.COLUMN_SUPERVISOR_STATUS
        };
        Cursor cursors = db.query(DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS, columns, DbSchema.COLUMN_S_W_R_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_w_r_id                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ID                 ));
                    String s_w_r_student_id                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ST_ID              ));
                    String student_name                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_NAME                 ));
                    String title                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TITLE                    ));
                    String training                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING                 ));
                    String day                                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                      ));
                    String feedback                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_FEEDBACK                 ));
                    String learning_experices                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNING_EXP             ));
                    String number                                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUMBER                   ));
                    String month_year                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_YEAR               ));
                    String report_add_date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_ADD_DATE          ));
                    String date                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                     ));
                    String is_supervisor_commented                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_SUPERVISOR_COMMENTED  ));
                    String c_u_r_comment                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_COMMENT            ));
                    String c_u_r_training_progress                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_TRAINING_PROGRESS  ));
                    String c_u_r_report_writing                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_REPORT_WRITING     ));
                    String supervisor_status                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS        ));
                    dataArray = new sWeeklyReportDetails(s_w_r_id,s_w_r_student_id,student_name,
                            title,training, day,
                            feedback,learning_experices,number,month_year,report_add_date,
                            date,is_supervisor_commented,c_u_r_comment,c_u_r_training_progress,
                            c_u_r_report_writing, supervisor_status);

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
        dbHelper.truncateTable(DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS);
    }

    @Override
    public List<sWeeklyReportDetails> getAll() {

        sWeeklyReportDetails dataArray=null;
        List<sWeeklyReportDetails> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_W_R_ID                 ,
                DbSchema.COLUMN_S_W_R_ST_ID             ,
                DbSchema.COLUMN_STU_NAME                ,
                DbSchema.COLUMN_TITLE                    ,
                DbSchema.COLUMN_TRAINING                  ,
                DbSchema.COLUMN_DAY                      ,
                DbSchema.COLUMN_FEEDBACK                  ,
                DbSchema.COLUMN_LEARNING_EXP            ,
                DbSchema.COLUMN_NUMBER                    ,
                DbSchema.COLUMN_MONTH_YEAR               ,
                DbSchema.COLUMN_REPORT_ADD_DATE        ,
                DbSchema.COLUMN_DATE                      ,
                DbSchema.COLUMN_IS_SUPERVISOR_COMMENTED   ,
                DbSchema.COLUMN_C_U_R_COMMENT             ,
                DbSchema.COLUMN_C_U_R_TRAINING_PROGRESS   ,
                DbSchema.COLUMN_C_U_R_REPORT_WRITING      ,
                DbSchema.COLUMN_SUPERVISOR_STATUS
                };
        Cursor cursors = db.query(DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_w_r_id                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ID                 ));
                    String s_w_r_student_id                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_W_R_ST_ID              ));
                    String student_name                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_NAME                 ));
                    String title                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TITLE                    ));
                    String training                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING                 ));
                    String day                                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAY                      ));
                    String feedback                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_FEEDBACK                 ));
                    String learning_experices                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNING_EXP             ));
                    String number                                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUMBER                   ));
                    String month_year                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_YEAR               ));
                    String report_add_date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORT_ADD_DATE          ));
                    String date                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE                     ));
                    String is_supervisor_commented                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_SUPERVISOR_COMMENTED  ));
                    String c_u_r_comment                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_COMMENT            ));
                    String c_u_r_training_progress                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_TRAINING_PROGRESS  ));
                    String c_u_r_report_writing                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_C_U_R_REPORT_WRITING     ));
                    String supervisor_status                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS        ));
                    dataArray = new sWeeklyReportDetails(s_w_r_id,s_w_r_student_id,student_name,
                            title,training, day,
                            feedback,learning_experices,number,month_year,report_add_date,
                            date,is_supervisor_commented,c_u_r_comment,c_u_r_training_progress,
                            c_u_r_report_writing, supervisor_status);
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
