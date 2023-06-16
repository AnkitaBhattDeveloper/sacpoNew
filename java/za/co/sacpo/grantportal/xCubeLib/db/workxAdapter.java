package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xcube-06 on 25/7/18.
 */

public class workxAdapter implements workxDAO {

    private DbHelper databaseHelper;
    private Context context;

    public workxAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(workx workx) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_W_NAME, workx.getName());
        contentValues.put(DbSchema.COLUMN_W_TYPE, workx.getType());
        contentValues.put(DbSchema.COLUMN_W_SERVER_ID, workx.getId());
        long data =  db.insert(DbSchema.TABLE_WORKX, null, contentValues);
        //db.close();
        return data;
    }
    @Override
    public int update(workx workx) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_W_NAME, workx.getName());
        contentValues.put(DbSchema.COLUMN_W_TYPE, workx.getType());
        contentValues.put(DbSchema.COLUMN_W_SERVER_ID, workx.getId());
        int data = db.update(DbSchema.TABLE_WORKX, contentValues, DbSchema.COLUMN_W_ID + " = " + workx.getKeyId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_WORKX, DbSchema.COLUMN_W_ID + " = " + id, null);
        //db.close();
        return data;
    }
    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_WORKX);
    }
    @Override
    public workx getById(int id) {
        workx workx = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_W_ID, DbSchema.COLUMN_W_SERVER_ID, DbSchema.COLUMN_W_TYPE,DbSchema.COLUMN_W_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_WORKX, columns, DbSchema.COLUMN_W_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_W_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_TYPE));
                workx = new workx(keyId, Id, Type, Name);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }

        return workx;

       }

    @Override
    public List<workx> getAll() {
        List<workx> workxList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_W_ID, DbSchema.COLUMN_W_SERVER_ID, DbSchema.COLUMN_W_TYPE,DbSchema.COLUMN_W_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_WORKX, columns, null, null, null, null, null);
        try {
            while (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_W_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_W_TYPE));
                workx workx = new workx(keyId, Id, Type, Name);
                workxList.add(workx);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
       return workxList;
    }
}