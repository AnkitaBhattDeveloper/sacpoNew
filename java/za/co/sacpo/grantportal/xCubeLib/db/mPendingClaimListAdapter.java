package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mPendingClaimListAdapter implements mPendingClaimListDAO{

    DbHelper dbHelper;

    public mPendingClaimListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<mPendingClaimListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_m_s_id()+"'," +
                    "'"+dataArrays.get(i).getS_m_s_stipend_year()+"'," +
                    "'"+dataArrays.get(i).getMonth_name()+"'," +
                    "'"+dataArrays.get(i).getS_m_s_stipend_month()+"'," +
                    "'"+dataArrays.get(i).getStipend_amount()+"'," +
                    "'"+dataArrays.get(i).getLearner_name()+"'," +
                    "'"+dataArrays.get(i).getApprove_stipend_link()+"');");

        }

        return 1;
    }

    @Override
    public int update(mPendingClaimListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_STIPEND_MONTH                  ,dataArrays.getS_m_s_stipend_month());
        contentValues.put(DbSchema.COLUMN_MONTH_NAME                     ,dataArrays.getMonth_name());
        contentValues.put(DbSchema.COLUMN_STIPEND_YEAR                   ,dataArrays.getS_m_s_stipend_year());
        contentValues.put(DbSchema.COLUMN_STIPEND_ID                     ,dataArrays.getS_m_s_id());
        contentValues.put(DbSchema.COLUMN_STIPEND_AMOUNT                 ,dataArrays.getStipend_amount());
        contentValues.put(DbSchema.COLUMN_LEARNER_NAME               ,dataArrays.getLearner_name());
        contentValues.put(DbSchema.COLUMN_APPROVE_STIPEND_LINK      ,dataArrays.getApprove_stipend_link());

        int data = db.update(DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS, contentValues, DbSchema.COLUMN_STIPEND_ID + " = " + dataArrays.getS_m_s_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS, DbSchema.COLUMN_STIPEND_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mPendingClaimListArray getById(int id) {
        mPendingClaimListArray dataArray=null;
        List<mPendingClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPEND_MONTH                   ,
                DbSchema.COLUMN_MONTH_NAME                      ,
                DbSchema.COLUMN_STIPEND_YEAR                    ,
                DbSchema.COLUMN_STIPEND_ID                      ,
                DbSchema.COLUMN_STIPEND_AMOUNT                  ,
                DbSchema.COLUMN_LEARNER_NAME                ,
                DbSchema.COLUMN_APPROVE_STIPEND_LINK         };

        Cursor cursors = db.query(DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS, columns, DbSchema.COLUMN_STIPEND_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_MONTH               ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_NAME                  ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_YEAR                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_ID                  ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_AMOUNT              ));
                    String learner_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_NAME            ));
                    String approve_stipend_link             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_APPROVE_STIPEND_LINK   ));
                    dataArray = new mPendingClaimListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name,
                            approve_stipend_link
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
        dbHelper.truncateTable(DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS);
    }

    @Override
    public List<mPendingClaimListArray> getAll() {
        mPendingClaimListArray dataArray=null;
        List<mPendingClaimListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_STIPEND_MONTH                   ,
                DbSchema.COLUMN_MONTH_NAME                      ,
                DbSchema.COLUMN_STIPEND_YEAR                    ,
                DbSchema.COLUMN_STIPEND_ID                      ,
                DbSchema.COLUMN_STIPEND_AMOUNT                  ,
                DbSchema.COLUMN_LEARNER_NAME                ,
                DbSchema.COLUMN_APPROVE_STIPEND_LINK       };

        Cursor cursors = db.query(DbSchema.TABLE_M_PENDING_CLAIMLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String s_m_s_stipend_month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_MONTH               ));
                    String month_name                              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH_NAME                  ));
                    String s_m_s_stipend_year                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_YEAR                ));
                    String s_m_s_id                                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_ID                  ));
                    String stipend_amount                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STIPEND_AMOUNT              ));
                    String learner_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_NAME            ));
                    String approve_stipend_link             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_APPROVE_STIPEND_LINK   ));
                    dataArray = new mPendingClaimListArray(
                            s_m_s_stipend_month,
                            month_name,
                            s_m_s_stipend_year,
                            s_m_s_id,
                            stipend_amount,
                            learner_name,
                            approve_stipend_link
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
