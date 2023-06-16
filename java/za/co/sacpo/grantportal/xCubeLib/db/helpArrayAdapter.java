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

public class helpArrayAdapter implements helpArrayDAO {

    private DbHelper databaseHelper;
    private Context context;

    public helpArrayAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(helpArrays helpArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_H_TITLE, helpArrays.getTitle());
        contentValues.put(DbSchema.COLUMN_H_CONTENT, helpArrays.getContent());
        contentValues.put(DbSchema.COLUMN_H_ACTIVITY, helpArrays.getActivity());
        contentValues.put(DbSchema.COLUMN_H_TYPE, helpArrays.getType());
        contentValues.put(DbSchema.COLUMN_H_SERVER_ID, helpArrays.getId());
        long data =  db.insert(DbSchema.TABLE_HELP_ARRAYS, null, contentValues);
        //db.close();
        return data;
    }





    @Override
    public int update(helpArrays helpArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_H_TITLE, helpArrays.getTitle());
        contentValues.put(DbSchema.COLUMN_H_CONTENT, helpArrays.getContent());
        contentValues.put(DbSchema.COLUMN_H_ACTIVITY, helpArrays.getActivity());
        contentValues.put(DbSchema.COLUMN_H_TYPE, helpArrays.getType());
        contentValues.put(DbSchema.COLUMN_H_SERVER_ID, helpArrays.getId());
        int data = db.update(DbSchema.TABLE_HELP_ARRAYS, contentValues, DbSchema.COLUMN_H_ID + " = " + helpArrays.getKeyId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_HELP_ARRAYS, DbSchema.COLUMN_H_ID + " = " + id, null);
        //db.close();
        return data;
    }
    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_HELP_ARRAYS);
    }

    @Override
    public helpArrays getById(int id) {
        helpArrays helpArrays = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_H_ID, DbSchema.COLUMN_H_SERVER_ID, DbSchema.COLUMN_H_TYPE,DbSchema.COLUMN_H_TITLE,DbSchema.COLUMN_H_CONTENT};
        Cursor cursors = db.query(DbSchema.TABLE_HELP_ARRAYS, columns, DbSchema.COLUMN_H_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_TITLE));
                String Content = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_CONTENT));
                String Activity = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_ACTIVITY));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_TYPE));
                helpArrays = new helpArrays(keyId, Id, Type, Name,Content,Activity);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return helpArrays;
    }

    @Override
    public List<helpArrays> getAll() {
        List<helpArrays> helpArraysList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_H_ID, DbSchema.COLUMN_H_SERVER_ID, DbSchema.COLUMN_H_TYPE,DbSchema.COLUMN_H_TITLE,DbSchema.COLUMN_H_CONTENT};
        Cursor cursors = db.query(DbSchema.TABLE_HELP_ARRAYS, columns, null, null, null, null, null);
        try {
            while (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_TITLE));
                String Content = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_CONTENT));
                String Activity = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_H_ACTIVITY));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_H_TYPE));
                helpArrays helpArrays = new helpArrays(keyId, Id, Type, Name,Content,Activity);
                helpArraysList.add(helpArrays);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return helpArraysList;
    }

}