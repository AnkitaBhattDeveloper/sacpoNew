package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mApprovedClaimListAdapter implements mApprovedClaimListDAO{

    DbHelper dbHelper;

    public mApprovedClaimListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<mApprovedClaimListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_m_s_id()+"'," +
                    "'"+dataArrays.get(i).getS_m_s_stipend_year()+"'," +
                    "'"+dataArrays.get(i).getMonth_name()+"'," +
                    "'"+dataArrays.get(i).getS_m_s_stipend_month()+"'," +
                    "'"+dataArrays.get(i).getStipend_amount()+"'," +
                    "'"+dataArrays.get(i).getApprove_stipend()+"'," +
                    "'"+dataArrays.get(i).getOut_of_range_sign_in_counts()+"'," +
                    "'"+dataArrays.get(i).getOut_of_range_link()+"'," +
                    "'"+dataArrays.get(i).getDays_worked()+"'," +
                    "'"+dataArrays.get(i).getLeave()+"'," +
                    "'"+dataArrays.get(i).getDownload_claim_form_link()+"'," +
                    "'"+dataArrays.get(i).getShow_claim_form_link()+"'," +
                    "'"+dataArrays.get(i).getDownload_signed_claim_form_link()+"'," +
                    "'"+dataArrays.get(i).getShow_signed_claim_form_link()+"');");

        }

        return 1;
    }

    @Override
    public int update(mApprovedClaimListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_STIPEND_MONTH                  ,dataArrays.getS_m_s_stipend_month());
        contentValues.put(DbSchema.COLUMN_MONTH_NAME                     ,dataArrays.getMonth_name());
        contentValues.put(DbSchema.COLUMN_STIPEND_YEAR                   ,dataArrays.getS_m_s_stipend_year());
        contentValues.put(DbSchema.COLUMN_STIPEND_ID                     ,dataArrays.getS_m_s_id());
        contentValues.put(DbSchema.COLUMN_STIPEND_AMOUNT                 ,dataArrays.getStipend_amount());
        contentValues.put(DbSchema.COLUMN_STIPEND_APPROVED               ,dataArrays.getApprove_stipend());
        contentValues.put(DbSchema.COLUMN_OUTOF_RANGE_SIGNIN_COUNTS      ,dataArrays.getOut_of_range_sign_in_counts());
        contentValues.put(DbSchema.COLUMN_OUTOF_RANGE_LINK               ,dataArrays.getOut_of_range_link());
        contentValues.put(DbSchema.COLUMN_DAYS_WORKED                    ,dataArrays.getDays_worked());
        contentValues.put(DbSchema.COLUMN_LEAVE                          ,dataArrays.getLeave());
        contentValues.put(DbSchema.COLUMN_DWNLD_CLAIM_FORM_LINK          ,dataArrays.getDownload_claim_form_link());
        contentValues.put(DbSchema.COLUMN_SHOW_CLAIM_FORM_LINK           ,dataArrays.getShow_claim_form_link());
        contentValues.put(DbSchema.COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK    ,dataArrays.getDownload_signed_claim_form_link());
        contentValues.put(DbSchema.COLUMN_SHOW_SIGNED_CLAIMFORM_LINK     ,dataArrays.getShow_signed_claim_form_link());

        int data = db.update(DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS, contentValues, DbSchema.COLUMN_STIPEND_ID + " = " + dataArrays.getS_m_s_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS, DbSchema.COLUMN_STIPEND_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mApprovedClaimListArray getById(int id) {
        mApprovedClaimListArray dataArray=null;
        List<mApprovedClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPEND_MONTH                   ,
                DbSchema.COLUMN_MONTH_NAME                      ,
                DbSchema.COLUMN_STIPEND_YEAR                    ,
                DbSchema.COLUMN_STIPEND_ID                      ,
                DbSchema.COLUMN_STIPEND_AMOUNT                  ,
                DbSchema.COLUMN_STIPEND_APPROVED                ,
                DbSchema.COLUMN_OUTOF_RANGE_SIGNIN_COUNTS       ,
                DbSchema.COLUMN_OUTOF_RANGE_LINK                ,
                DbSchema.COLUMN_DAYS_WORKED                     ,
                DbSchema.COLUMN_LEAVE                           ,
                DbSchema.COLUMN_DWNLD_CLAIM_FORM_LINK           ,
                DbSchema.COLUMN_SHOW_CLAIM_FORM_LINK            ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK     ,
                DbSchema.COLUMN_SHOW_SIGNED_CLAIMFORM_LINK      };

        Cursor cursors = db.query(DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS, columns, DbSchema.COLUMN_STIPEND_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_MONTH               ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_NAME                  ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_YEAR                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_ID                  ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_AMOUNT              ));
                    String approve_stipend                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_APPROVED            ));
                    String out_of_range_sign_in_counts             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUTOF_RANGE_SIGNIN_COUNTS   ));
                    String out_of_range_link                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUTOF_RANGE_LINK            ));
                    String days_worked                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAYS_WORKED                 ));
                    String leave                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE                       ));
                    String download_claim_form_link                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_CLAIM_FORM_LINK       ));
                    String show_claim_form_link                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_CLAIM_FORM_LINK        ));
                    String download_signed_claim_form_link         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK ));
                    String show_signed_claim_form_link            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_SIGNED_CLAIMFORM_LINK  ));
                    dataArray = new mApprovedClaimListArray(
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
                            download_claim_form_link,
                            show_claim_form_link,
                            download_signed_claim_form_link,
                            show_signed_claim_form_link
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
        dbHelper.truncateTable(DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS);
    }

    @Override
    public List<mApprovedClaimListArray> getAll() {
        mApprovedClaimListArray dataArray=null;
        List<mApprovedClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPEND_MONTH                   ,
                DbSchema.COLUMN_MONTH_NAME                      ,
                DbSchema.COLUMN_STIPEND_YEAR                    ,
                DbSchema.COLUMN_STIPEND_ID                      ,
                DbSchema.COLUMN_STIPEND_AMOUNT                  ,
                DbSchema.COLUMN_STIPEND_APPROVED                ,
                DbSchema.COLUMN_OUTOF_RANGE_SIGNIN_COUNTS       ,
                DbSchema.COLUMN_OUTOF_RANGE_LINK                ,
                DbSchema.COLUMN_DAYS_WORKED                     ,
                DbSchema.COLUMN_LEAVE                           ,
                DbSchema.COLUMN_DWNLD_CLAIM_FORM_LINK           ,
                DbSchema.COLUMN_SHOW_CLAIM_FORM_LINK            ,
                DbSchema.COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK     ,
                DbSchema.COLUMN_SHOW_SIGNED_CLAIMFORM_LINK      };

        Cursor cursors = db.query(DbSchema.TABLE_M_APPROVED_CLAIMLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_MONTH               ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_NAME                  ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_YEAR                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_ID                  ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_AMOUNT              ));
                    String approve_stipend                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_APPROVED            ));
                    String out_of_range_sign_in_counts             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUTOF_RANGE_SIGNIN_COUNTS   ));
                    String out_of_range_link                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OUTOF_RANGE_LINK            ));
                    String days_worked                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DAYS_WORKED                 ));
                    String leave                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE                       ));
                    String download_claim_form_link                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_CLAIM_FORM_LINK       ));
                    String show_claim_form_link                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_CLAIM_FORM_LINK        ));
                    String download_signed_claim_form_link         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK ));
                    String show_signed_claim_form_link            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_SIGNED_CLAIMFORM_LINK  ));
                    dataArray = new mApprovedClaimListArray(
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
                           download_claim_form_link,
                           show_claim_form_link,
                           download_signed_claim_form_link,
                           show_signed_claim_form_link
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
