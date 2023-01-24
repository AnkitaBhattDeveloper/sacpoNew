package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mDocCenterListAdapter implements mDocCenterListDAO{

    DbHelper dbHelper;

    public mDocCenterListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }



    @Override
    public long insert(List<mDocCenterListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getStipend_id()+"'," +
                    "'"+dataArrays.get(i).getYear()+"'," +
                    "'"+dataArrays.get(i).getMonthName()+"'," +
                    "'"+dataArrays.get(i).getDownload_att_register()+"'," +
                    "'"+dataArrays.get(i).getIs_download_att_register()+"'," +
                    "'"+dataArrays.get(i).getDownload_unsigned_claim_form()+"'," +
                    "'"+dataArrays.get(i).getIs_download_unsigned_claim_form()+"'," +
                    "'"+dataArrays.get(i).getUpload_signed_claim_form()+"'," +
                    "'"+dataArrays.get(i).getDownload_signed_claim_form()+"'," +
                    "'"+dataArrays.get(i).getIs_download_signed_claim_form()+"'," +
                    "'"+dataArrays.get(i).getMonth()+"');");

        }

        return 1;
    }

    @Override
    public int update(mDocCenterListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_STIPENDID                       ,dataArrays.getStipend_id());
        contentValues.put(DbSchema.COLUMN_YEAR                            ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_MONTHNAME                       ,dataArrays.getMonthName());
        contentValues.put(DbSchema.COLUMN_DWNLD_ATT_REGISTER              ,dataArrays.getDownload_att_register());
        contentValues.put(DbSchema.COLUMN_IS_DWNLD_ATT_REGISTER           ,dataArrays.getIs_download_att_register());
        contentValues.put(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM       ,dataArrays.getDownload_unsigned_claim_form());
        contentValues.put(DbSchema.COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM    ,dataArrays.getIs_download_unsigned_claim_form());
        contentValues.put(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM        ,dataArrays.getUpload_signed_claim_form());
        contentValues.put(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM         ,dataArrays.getDownload_signed_claim_form());
        contentValues.put(DbSchema.COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM      ,dataArrays.getIs_download_signed_claim_form());
        contentValues.put(DbSchema.COLUMN_MONTH                           ,dataArrays.getMonth());

        int data = db.update(DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS, contentValues, DbSchema.COLUMN_STIPEND_ID + " = " + dataArrays.getStipend_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS, DbSchema.COLUMN_STIPEND_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mDocCenterListArray getById(int id) {
        mDocCenterListArray dataArray=null;
        List<mDocCenterListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPENDID                        ,
                DbSchema.COLUMN_YEAR                             ,
                DbSchema.COLUMN_MONTHNAME                        ,
                DbSchema.COLUMN_DWNLD_ATT_REGISTER               ,
                DbSchema.COLUMN_IS_DWNLD_ATT_REGISTER            ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM        ,
                DbSchema.COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM     ,
                DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM         ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM          ,
                DbSchema.COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM       ,
                DbSchema.COLUMN_MONTH                            };

        Cursor cursors = db.query(DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS, columns, DbSchema.COLUMN_STIPEND_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPENDID                    ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                         ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTHNAME                    ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATT_REGISTER           ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_ATT_REGISTER        ));
                    String approve_stipend                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM    ));
                    String out_of_range_sign_in_counts             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM ));
                    String out_of_range_link                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM     ));
                    String days_worked                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM      ));
                    String leave                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM   ));
                    String download_claim_form_link                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH                        ));
                    dataArray = new mDocCenterListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            approve_stipend,
                            out_of_range_sign_in_counts,
                            out_of_range_link,
                            days_worked,
                            leave,
                            download_claim_form_link
                    );
                  //  dataArrayList.add(dataArray);
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
        dbHelper.truncateTable(DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS);
    }

    @Override
    public List<mDocCenterListArray> getAll() {
        mDocCenterListArray dataArray=null;
        List<mDocCenterListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPENDID                        ,
                DbSchema.COLUMN_YEAR                             ,
                DbSchema.COLUMN_MONTHNAME                        ,
                DbSchema.COLUMN_DWNLD_ATT_REGISTER               ,
                DbSchema.COLUMN_IS_DWNLD_ATT_REGISTER            ,
                DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM        ,
                DbSchema.COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM     ,
                DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM         ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM          ,
                DbSchema.COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM       ,
                DbSchema.COLUMN_MONTH                            };

        Cursor cursors = db.query(DbSchema.TABLE_M_DOC_CENTERLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPENDID                    ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR                         ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTHNAME                    ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATT_REGISTER           ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_ATT_REGISTER        ));
                    String approve_stipend                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_UNSIGNED_CLAIM_FORM    ));
                    String out_of_range_sign_in_counts             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM ));
                    String out_of_range_link                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_SIGNED_CLAIM_FORM     ));
                    String days_worked                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIM_FORM      ));
                    String leave                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM   ));
                    String download_claim_form_link                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH                        ));
                    dataArray = new mDocCenterListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            approve_stipend,
                            out_of_range_sign_in_counts,
                            out_of_range_link,
                            days_worked,
                            leave,
                            download_claim_form_link
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
