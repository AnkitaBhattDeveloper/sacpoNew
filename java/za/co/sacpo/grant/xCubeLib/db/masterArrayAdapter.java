package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public class masterArrayAdapter implements masterArrayDAO {

    private DbHelper databaseHelper;
    private Context context;

    public masterArrayAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(masterArrays masterArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_M_A_NAME, masterArrays.getName());
        contentValues.put(DbSchema.COLUMN_M_A_TYPE, masterArrays.getType());
        contentValues.put(DbSchema.COLUMN_M_A_SERVER_ID, masterArrays.getId());
        long data =  db.insert(DbSchema.TABLE_MASTER_ARRAYS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(masterArrays masterArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_M_A_NAME, masterArrays.getName());
        contentValues.put(DbSchema.COLUMN_M_A_TYPE, masterArrays.getType());
        contentValues.put(DbSchema.COLUMN_M_A_SERVER_ID, masterArrays.getId());
        int data = db.update(DbSchema.TABLE_MASTER_ARRAYS, contentValues, DbSchema.COLUMN_M_A_ID + " = " + masterArrays.getKeyId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MASTER_ARRAYS, DbSchema.COLUMN_M_A_ID + " = " + id, null);
        //db.close();
        return data;
    }
    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_MASTER_ARRAYS);
    }

    @Override
    public masterArrays getById(int id) {
        masterArrays masterArrays = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_M_A_ID, DbSchema.COLUMN_M_A_SERVER_ID, DbSchema.COLUMN_M_A_TYPE,DbSchema.COLUMN_M_A_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_MASTER_ARRAYS, columns, DbSchema.COLUMN_M_A_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_A_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_TYPE));
                masterArrays = new masterArrays(keyId, Id, Type, Name);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return masterArrays;
    }

    @Override
    public List<masterArrays> getAll() {
        List<masterArrays> masterArraysList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_M_A_ID, DbSchema.COLUMN_M_A_SERVER_ID, DbSchema.COLUMN_M_A_TYPE,DbSchema.COLUMN_M_A_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_MASTER_ARRAYS, columns, null, null, null, null, null);
        try {
            while (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_A_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_M_A_TYPE));
                masterArrays masterArrays = new masterArrays(keyId, Id, Type, Name);
                masterArraysList.add(masterArrays);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return masterArraysList;
    }

}