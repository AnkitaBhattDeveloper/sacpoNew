package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class pastClaimListAdapter implements pastClaimListDAO{

    DbHelper dbHelper;
    Context context;


   public pastClaimListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<pastClaimListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_PAST_CLAIMLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_m_s_id()+"'," +
                    "'"+dataArrays.get(i).getS_m_s_stipend()+"',"+
                    "'"+dataArrays.get(i).getS_m_s_stipend_month()+"',"+
                    "'"+dataArrays.get(i).getYear()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getDate_of_claim()+"',"+
                    "'"+dataArrays.get(i).getClaimed_ammount()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_status()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_status_color()+"',"+
                    "'"+dataArrays.get(i).getGrant_admin_status()+"',"+
                    "'"+dataArrays.get(i).getGrant_admin_status_color()+"',"+
                    "'"+dataArrays.get(i).getDownload_unsigned_claim_form_btn()+"',"+
                    "'"+dataArrays.get(i).getDownload_unsigned_claim_form()+"',"+
                    "'"+dataArrays.get(i).getUpload_signed_claim_form_btn()+"',"+
                    "'"+dataArrays.get(i).getDownload_signed_claim_form()+"',"+
                    "'"+dataArrays.get(i).getDownload_sign_claim_form_btn()+"');");

        }

        return 1;
    }

    @Override
    public int update(pastClaimListArray dataArrays) {
        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_M_S_ID                               ,dataArrays.getS_m_s_id());
        contentValues.put(DbSchema.COLUMN_S_M_S_STIPEND                          ,dataArrays.getS_m_s_stipend());
        contentValues.put(DbSchema.COLUMN_S_M_S_STIPEND_MONTH                    ,dataArrays.getS_m_s_stipend_month());
        contentValues.put(DbSchema.COLUMN_YEAR                                   ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_MONTH                                  ,dataArrays.getMonth());
        contentValues.put(DbSchema.COLUMN_DATE_OF_CLAIM                          ,dataArrays.getDate_of_claim());
        contentValues.put(DbSchema.COLUMN_CLAIMED_AMOUNT                         ,dataArrays.getClaimed_ammount());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS                      ,dataArrays.getSupervisor_status());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_STATUS_COLOR                ,dataArrays.getSupervisor_status_color());
        contentValues.put(DbSchema.COLUMN_GADMIN_STATUS                          ,dataArrays.getGrant_admin_status());
        contentValues.put(DbSchema.COLUMN_GADMIN_STATUS_COLOR                    ,dataArrays.getGrant_admin_status_color());
        contentValues.put(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN          ,dataArrays.getDownload_unsigned_claim_form_btn());
        contentValues.put(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM              ,dataArrays.getDownload_unsigned_claim_form());
        contentValues.put(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN           ,dataArrays.getUpload_signed_claim_form_btn());
        contentValues.put(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM                ,dataArrays.getDownload_signed_claim_form());
        contentValues.put(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN            ,dataArrays.getDownload_sign_claim_form_btn());

        int data = db.update(DbSchema.TABLE_PAST_CLAIMLIST_DETAILS, contentValues, DbSchema.COLUMN_S_M_S_ID + " = " + dataArrays.getS_m_s_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_PAST_CLAIMLIST_DETAILS, DbSchema.COLUMN_S_M_S_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public pastClaimListArray getById(int id) {
        pastClaimListArray dataArray=null;
        List<pastClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_M_S_ID                    ,
                DbSchema.COLUMN_S_M_S_STIPEND               ,
                DbSchema.COLUMN_S_M_S_STIPEND_MONTH         ,
                DbSchema.COLUMN_YEAR                         ,
                DbSchema.COLUMN_MONTH                        ,
                DbSchema.COLUMN_DATE_OF_CLAIM                ,
                DbSchema.COLUMN_CLAIMED_AMOUNT               ,
                DbSchema.COLUMN_SUPERVISOR_STATUS            ,
                DbSchema.COLUMN_SUPERVISOR_STATUS_COLOR       ,
                DbSchema.COLUMN_GADMIN_STATUS               ,
                DbSchema.COLUMN_GADMIN_STATUS_COLOR        ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM     ,
                DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN  ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM       ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN  };
        Cursor cursors = db.query(DbSchema.TABLE_PAST_CLAIMLIST_DETAILS, columns, DbSchema.COLUMN_S_M_S_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_id                                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_ID                     ));
                    String s_m_s_stipend                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_STIPEND                ));
                    String s_m_s_stipend_month                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_STIPEND_MONTH          ));
                    String year                                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                         ));
                    String month                                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH                        ));
                    String date_of_claim                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE_OF_CLAIM                ));
                    String claimed_ammount                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CLAIMED_AMOUNT               ));
                    String supervisor_status                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS            ));
                    String supervisor_status_color                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS_COLOR      ));
                    String grant_admin_status                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GADMIN_STATUS                ));
                    String grant_admin_status_color                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GADMIN_STATUS_COLOR          ));
                    String download_unsigned_claim_form_btn               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN));
                    String download_unsigned_claim_form                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM    ));
                    String upload_signed_claim_form_btn                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN ));
                    String download_signed_claim_form                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM      ));
                    String download_sign_claim_form_btn                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN  ));
                    dataArray = new pastClaimListArray(s_m_s_id,s_m_s_stipend,s_m_s_stipend_month,year,
                            month, date_of_claim,
                            claimed_ammount,supervisor_status,supervisor_status_color,grant_admin_status,grant_admin_status_color,
                            download_unsigned_claim_form_btn,download_unsigned_claim_form,upload_signed_claim_form_btn,
                            download_signed_claim_form,download_sign_claim_form_btn);

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
        dbHelper.truncateTable(DbSchema.TABLE_PAST_CLAIMLIST_DETAILS);
    }

    @Override
    public List<pastClaimListArray> getAll() {
        pastClaimListArray dataArray=null;
        List<pastClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_S_M_S_ID                    ,
                DbSchema.COLUMN_S_M_S_STIPEND               ,
                DbSchema.COLUMN_S_M_S_STIPEND_MONTH         ,
                DbSchema.COLUMN_YEAR                         ,
                DbSchema.COLUMN_MONTH                        ,
                DbSchema.COLUMN_DATE_OF_CLAIM                ,
                DbSchema.COLUMN_CLAIMED_AMOUNT               ,
                DbSchema.COLUMN_SUPERVISOR_STATUS            ,
                DbSchema.COLUMN_SUPERVISOR_STATUS_COLOR       ,
                DbSchema.COLUMN_GADMIN_STATUS               ,
                DbSchema.COLUMN_GADMIN_STATUS_COLOR        ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM     ,
                DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN  ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM       ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN  };
        Cursor cursors = db.query(DbSchema.TABLE_PAST_CLAIMLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_id                                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_ID                     ));
                    String s_m_s_stipend                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_STIPEND                ));
                    String s_m_s_stipend_month                            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_M_S_STIPEND_MONTH          ));
                    String year                                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                         ));
                    String month                                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH                        ));
                    String date_of_claim                                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE_OF_CLAIM                ));
                    String claimed_ammount                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CLAIMED_AMOUNT               ));
                    String supervisor_status                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS            ));
                    String supervisor_status_color                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_STATUS_COLOR      ));
                    String grant_admin_status                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GADMIN_STATUS                ));
                    String grant_admin_status_color                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GADMIN_STATUS_COLOR          ));
                    String download_unsigned_claim_form_btn               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN));
                    String download_unsigned_claim_form                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM    ));
                    String upload_signed_claim_form_btn                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN ));
                    String download_signed_claim_form                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM      ));
                    String download_sign_claim_form_btn                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN  ));
        dataArray = new pastClaimListArray(s_m_s_id,s_m_s_stipend,s_m_s_stipend_month,year,
         month, date_of_claim,
         claimed_ammount,supervisor_status,supervisor_status_color,grant_admin_status,grant_admin_status_color,
         download_unsigned_claim_form_btn,download_unsigned_claim_form,upload_signed_claim_form_btn,
         download_signed_claim_form,download_sign_claim_form_btn);
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
