package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class existingLeaveListAdapter implements existingLeaveListDAO{

    DbHelper dbHelper;
    Context context;

    public existingLeaveListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }



    @Override
    public long insert(List<existingLeaveListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_a_id()+"'," +
                    "'"+dataArrays.get(i).getAtt_ids()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getFrom_date()+"',"+
                    "'"+dataArrays.get(i).getTo_date()+"',"+
                    "'"+dataArrays.get(i).getAnnual_leave()+"',"+
                    "'"+dataArrays.get(i).getSick_leave()+"',"+
                    "'"+dataArrays.get(i).getOther_paid_leave()+"',"+
                    "'"+dataArrays.get(i).getUnpaid_leave()+"',"+
                    "'"+dataArrays.get(i).getMotivation()+"',"+
                    "'"+dataArrays.get(i).getSa_reason()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_approval_status()+"',"+
                    "'"+dataArrays.get(i).getMotivation_btn()+"',"+
                    "'"+dataArrays.get(i).getReason_btn()+"',"+
                    "'"+dataArrays.get(i).getUploads()+"',"+
                    "'"+dataArrays.get(i).getIs_upload()+"',"+
                    "'"+dataArrays.get(i).getShow_edit_link()+"',"+
                    "'"+dataArrays.get(i).getShow_remove_link()+"');");

        }

        return 1;
    }

    @Override
    public int update(existingLeaveListArray dataArrays) {

        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_A_ID                     ,dataArrays.getS_a_id());
        contentValues.put(DbSchema.COLUMN_ATT_ID                     ,dataArrays.getAtt_ids());
        contentValues.put(DbSchema.COLUMN_MONTH                      ,dataArrays.getMonth());
        contentValues.put(DbSchema.COLUMN_FROM_DATE                  ,dataArrays.getFrom_date());
        contentValues.put(DbSchema.COLUMN_TO_DATE                    ,dataArrays.getTo_date());
        contentValues.put(DbSchema.COLUMN_ANNUAL_LEAVE               ,dataArrays.getAnnual_leave());
        contentValues.put(DbSchema.COLUMN_SICK_LEAVE                 ,dataArrays.getSick_leave());
        contentValues.put(DbSchema.COLUMN_O_PAID_LEAVE               ,dataArrays.getOther_paid_leave());
        contentValues.put(DbSchema.COLUMN_UNPAID_LEAVE               ,dataArrays.getUnpaid_leave());
        contentValues.put(DbSchema.COLUMN_MOTIVATION                 ,dataArrays.getMotivation());
        contentValues.put(DbSchema.COLUMN_SA_REASON                  ,dataArrays.getSa_reason());
        contentValues.put(DbSchema.COLUMN_S_APPROVAL_STATUS          ,dataArrays.getSupervisor_approval_status());
        contentValues.put(DbSchema.COLUMN_MOTIVATION_BTN             ,dataArrays.getMotivation_btn());
        contentValues.put(DbSchema.COLUMN_REASON_BTN                 ,dataArrays.getReason_btn());
        contentValues.put(DbSchema.COLUMN_UPLOADS                    ,dataArrays.getUploads());
        contentValues.put(DbSchema.COLUMN_IS_UPLOAD                  ,dataArrays.getIs_upload());
        contentValues.put(DbSchema.COLUMN_SHOW_EDIT_LINK             ,dataArrays.getShow_edit_link());
        contentValues.put(DbSchema.COLUMN_SHOW_REMOVE_LINK           ,dataArrays.getShow_remove_link());

        int data = db.update(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS, contentValues, DbSchema.COLUMN_S_A_ID + " = " + dataArrays.getS_a_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS, DbSchema.COLUMN_S_A_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public existingLeaveListArray getById(int id) {
        existingLeaveListArray dataArray=null;
        List<existingLeaveListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_A_ID   ,
                DbSchema.COLUMN_ATT_ID        ,
                DbSchema.COLUMN_MONTH        ,
                DbSchema.COLUMN_FROM_DATE      ,
                DbSchema.COLUMN_TO_DATE       ,
                DbSchema.COLUMN_ANNUAL_LEAVE     ,
                DbSchema.COLUMN_SICK_LEAVE     ,
                DbSchema.COLUMN_O_PAID_LEAVE    ,
                DbSchema.COLUMN_UNPAID_LEAVE    ,
                DbSchema.COLUMN_MOTIVATION     ,
                DbSchema.COLUMN_SA_REASON       ,
                DbSchema.COLUMN_S_APPROVAL_STATUS ,
                DbSchema.COLUMN_MOTIVATION_BTN    ,
                DbSchema.COLUMN_REASON_BTN        ,
                DbSchema.COLUMN_UPLOADS           ,
                DbSchema.COLUMN_IS_UPLOAD         ,
                DbSchema.COLUMN_SHOW_EDIT_LINK    ,
                DbSchema.COLUMN_SHOW_REMOVE_LINK };
        Cursor cursors = db.query(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS, columns, DbSchema.COLUMN_S_A_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_a_id                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID           ));
                    String att_ids                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATT_ID           ));
                    String month                                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH            ));
                    String from_date                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_FROM_DATE        ));
                    String to_date                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TO_DATE          ));
                    String annual_leave                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE     ));
                    String sick_leave                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE       ));
                    String other_paid_leave                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_O_PAID_LEAVE     ));
                    String unpaid_leave                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE     ));
                    String motivation                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MOTIVATION       ));
                    String sa_reason                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SA_REASON        ));
                    String supervisor_approval_status               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_APPROVAL_STATUS));
                    String motivation_btn                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MOTIVATION_BTN   ));
                    String reason_btn                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REASON_BTN       ));
                    String uploads                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOADS          ));
                    String is_upload                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_UPLOAD        ));
                    String show_edit_link                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_EDIT_LINK   ));
                    String show_remove_link         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_REMOVE_LINK ));
                    dataArray = new existingLeaveListArray(s_a_id,att_ids,month,from_date,to_date, annual_leave,
                            sick_leave,other_paid_leave,unpaid_leave,motivation,sa_reason,
                            supervisor_approval_status,motivation_btn,reason_btn,uploads,is_upload,
                            show_edit_link, show_remove_link);

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
        dbHelper.truncateTable(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS);
    }

    @Override
    public List<existingLeaveListArray> getAll() {

        existingLeaveListArray dataArray=null;
        List<existingLeaveListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
               DbSchema.COLUMN_S_A_ID   ,
               DbSchema.COLUMN_ATT_ID        ,
               DbSchema.COLUMN_MONTH        ,
               DbSchema.COLUMN_FROM_DATE      ,
               DbSchema.COLUMN_TO_DATE       ,
               DbSchema.COLUMN_ANNUAL_LEAVE     ,
               DbSchema.COLUMN_SICK_LEAVE     ,
               DbSchema.COLUMN_O_PAID_LEAVE    ,
               DbSchema.COLUMN_UNPAID_LEAVE    ,
               DbSchema.COLUMN_MOTIVATION     ,
               DbSchema.COLUMN_SA_REASON       ,
               DbSchema.COLUMN_S_APPROVAL_STATUS ,
               DbSchema.COLUMN_MOTIVATION_BTN    ,
               DbSchema.COLUMN_REASON_BTN        ,
               DbSchema.COLUMN_UPLOADS           ,
               DbSchema.COLUMN_IS_UPLOAD         ,
               DbSchema.COLUMN_SHOW_EDIT_LINK    ,
               DbSchema.COLUMN_SHOW_REMOVE_LINK };
        Cursor cursors = db.query(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_a_id                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_A_ID           ));
                    String att_ids                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATT_ID           ));
                    String month                                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH            ));
                    String from_date                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_FROM_DATE        ));
                    String to_date                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TO_DATE          ));
                    String annual_leave                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE     ));
                    String sick_leave                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE       ));
                    String other_paid_leave                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_O_PAID_LEAVE     ));
                    String unpaid_leave                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE     ));
                    String motivation                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MOTIVATION       ));
                    String sa_reason                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SA_REASON        ));
                    String supervisor_approval_status               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_APPROVAL_STATUS));
                    String motivation_btn                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MOTIVATION_BTN   ));
                    String reason_btn                               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REASON_BTN       ));
                    String uploads                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOADS          ));
                    String is_upload                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_UPLOAD        ));
                    String show_edit_link                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_EDIT_LINK   ));
                    String show_remove_link         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_REMOVE_LINK ));
                    dataArray = new existingLeaveListArray(s_a_id,att_ids,month,from_date,to_date, annual_leave,
                            sick_leave,other_paid_leave,unpaid_leave,motivation,sa_reason,
                            supervisor_approval_status,motivation_btn,reason_btn,uploads,is_upload,
                            show_edit_link, show_remove_link);
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
