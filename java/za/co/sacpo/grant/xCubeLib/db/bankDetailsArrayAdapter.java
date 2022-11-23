package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class bankDetailsArrayAdapter implements bankDetailsDAO{


    private DbHelper databaseHelper;
    private Context context;

    public bankDetailsArrayAdapter(Context context){
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(bankDetailsArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_ID,dataArrays.getId());
        contentValues.put(DbSchema.COLUMN_BANKNAME ,dataArrays.getBank_name());
        contentValues.put(DbSchema.COLUMN_INITIAL_NAME ,dataArrays.getInitial_name());
        contentValues.put(DbSchema.COLUMN_ACCOUNT_NO ,dataArrays.getAccount_no());
        contentValues.put(DbSchema.COLUMN_BRANCHCODE  ,dataArrays.getBranch_code());
        contentValues.put(DbSchema.COLUMN_B_D_STATUS  ,dataArrays.getB_d_status());
        contentValues.put(DbSchema.COLUMN_B_D_SURNAME,dataArrays.getB_d_surname());
        contentValues.put(DbSchema.COLUMN_ACCOUNT_TYPE ,dataArrays.getAccount_type());
        contentValues.put(DbSchema.COLUMN_U_B_ID,dataArrays.getU_b_id());
        contentValues.put(DbSchema.COLUMN_B_D_A_TYPE ,dataArrays.getB_d_a_type());
        contentValues.put(DbSchema.COLUMN_BRANCHID  ,dataArrays.getB_d_u_branch_id());

        long data =  db.insert(DbSchema.TABLE_BANK_DETAILS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(bankDetailsArray dataArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_ID,dataArrays.getId());
        contentValues.put(DbSchema.COLUMN_BANK_NAME ,dataArrays.getBank_name());
        contentValues.put(DbSchema.COLUMN_INITIAL_NAME ,dataArrays.getInitial_name());
        contentValues.put(DbSchema.COLUMN_ACCOUNT_NO ,dataArrays.getAccount_no());
        contentValues.put(DbSchema.COLUMN_BRANCHCODE  ,dataArrays.getBranch_code());
        contentValues.put(DbSchema.COLUMN_B_D_STATUS  ,dataArrays.getB_d_status());
        contentValues.put(DbSchema.COLUMN_B_D_SURNAME,dataArrays.getB_d_surname());
        contentValues.put(DbSchema.COLUMN_ACCOUNT_TYPE ,dataArrays.getAccount_type());
        contentValues.put(DbSchema.COLUMN_U_B_ID,dataArrays.getU_b_id());
        contentValues.put(DbSchema.COLUMN_B_D_A_TYPE ,dataArrays.getB_d_a_type());
        contentValues.put(DbSchema.COLUMN_BRANCHID  ,dataArrays.getB_d_u_branch_id());
        int data = db.update(DbSchema.TABLE_BANK_DETAILS, contentValues, DbSchema.COLUMN_ID + " = " + dataArrays.getId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_BANK_DETAILS, DbSchema.COLUMN_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public bankDetailsArray getById(int id) {

        bankDetailsArray dataArray = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = { DbSchema.COLUMN_ID,DbSchema.COLUMN_BANK_NAME ,DbSchema.COLUMN_INITIAL_NAME ,
                DbSchema.COLUMN_ACCOUNT_NO ,DbSchema.COLUMN_BRANCHCODE  ,DbSchema.COLUMN_B_D_STATUS  ,
                DbSchema.COLUMN_B_D_SURNAME,DbSchema.COLUMN_ACCOUNT_TYPE ,DbSchema.COLUMN_U_B_ID,
                DbSchema.COLUMN_B_D_A_TYPE ,DbSchema.COLUMN_BRANCHID};

        Cursor cursors = db.query(DbSchema.TABLE_BANK_DETAILS, columns, DbSchema.COLUMN_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String col_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ID));
                String bank_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BANK_NAME ));
                String initial_name  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_INITIAL_NAME ));
                String account_no = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ACCOUNT_NO ));
                String branch_code = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BRANCHCODE ));
                String b_d_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_STATUS  ));
                String b_d_surname = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_SURNAME  ));
                String account_type = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ACCOUNT_TYPE  ));
                String u_b_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_B_ID   ));
                String b_d_a_type = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_A_TYPE  ));
                String b_d_u_branch_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BRANCHID   ));

                dataArray = new bankDetailsArray(col_id , bank_name , initial_name, account_no , branch_code  ,
                        b_d_status  ,  b_d_surname,account_type,u_b_id ,b_d_a_type,b_d_u_branch_id);


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
        databaseHelper.truncateTable(DbSchema.TABLE_BANK_DETAILS);
    }

    @Override
    public List<bankDetailsArray> getAll() {

        List<bankDetailsArray> dataArrayList = new ArrayList<>();
        //   DashboardDataArray dataArray = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = { DbSchema.COLUMN_ID,DbSchema.COLUMN_BANKNAME ,DbSchema.COLUMN_INITIAL_NAME ,
                DbSchema.COLUMN_ACCOUNT_NO ,DbSchema.COLUMN_BRANCHCODE  ,DbSchema.COLUMN_B_D_STATUS  ,
                DbSchema.COLUMN_B_D_SURNAME,DbSchema.COLUMN_ACCOUNT_TYPE ,DbSchema.COLUMN_U_B_ID,
                DbSchema.COLUMN_B_D_A_TYPE ,DbSchema.COLUMN_BRANCHID};

        Cursor cursors = db.query(DbSchema.TABLE_BANK_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String col_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ID));
                String bank_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BANKNAME ));
                String initial_name  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_INITIAL_NAME ));
                String account_no = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ACCOUNT_NO ));
                String branch_code = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BRANCHCODE ));
                String b_d_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_STATUS  ));
                String b_d_surname = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_SURNAME  ));
                String account_type = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ACCOUNT_TYPE  ));
                String u_b_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_B_ID   ));
                String b_d_a_type = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_B_D_A_TYPE  ));
                String b_d_u_branch_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BRANCHID   ));

                bankDetailsArray dataArray = new bankDetailsArray(col_id , bank_name , initial_name, account_no , branch_code  ,
                        b_d_status  ,  b_d_surname,account_type,u_b_id ,b_d_a_type,b_d_u_branch_id);

                dataArrayList.add(dataArray);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return dataArrayList;
    }
}
