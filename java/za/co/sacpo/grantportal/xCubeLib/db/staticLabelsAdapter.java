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

public class staticLabelsAdapter implements staticLabelsDAO {

    private DbHelper databaseHelper;
    private Context context;

    public staticLabelsAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(staticLabels staticLabels) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_CONTENT_NAME, staticLabels.getName());
        contentValues.put(DbSchema.COLUMN_CONTENT_VALUE, staticLabels.getValue());
        long data = db.insert(DbSchema.TABLE_STATIC_LABELS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(staticLabels staticLabels) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_CONTENT_NAME, staticLabels.getName());
        contentValues.put(DbSchema.COLUMN_CONTENT_VALUE, staticLabels.getValue());
        int data= db.update(DbSchema.TABLE_STATIC_LABELS, contentValues, DbSchema.COLUMN_KEY_ID + " = " + staticLabels.getKeyId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_STATIC_LABELS, DbSchema.COLUMN_KEY_ID + " = " + id, null);
        //db.close();
        return data;
    }
    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_STATIC_LABELS);
    }

    @Override
    public staticLabels getById(int id) {
        staticLabels staticLabels = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_KEY_ID, DbSchema.COLUMN_CONTENT_NAME, DbSchema.COLUMN_CONTENT_VALUE};
        Cursor cursors = db.query(DbSchema.TABLE_STATIC_LABELS, columns, DbSchema.COLUMN_KEY_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_KEY_ID));
                String Name= cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CONTENT_NAME));
                String Value = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CONTENT_VALUE));
                staticLabels = new staticLabels(keyId, Name,Value);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return staticLabels;
    }

    @Override
    public List<staticLabels> getAll() {
        List<staticLabels> staticLabelsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_KEY_ID, DbSchema.COLUMN_CONTENT_NAME, DbSchema.COLUMN_CONTENT_VALUE};
        Cursor cursors = db.query(DbSchema.TABLE_STATIC_LABELS, columns, null, null, null, null, null);
        try{
            while (cursors.moveToNext()) {

                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_KEY_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CONTENT_NAME));
                String Value = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CONTENT_VALUE));
                staticLabels staticLabels = new staticLabels(keyId, Name, Value);
                staticLabelsList.add(staticLabels);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return staticLabelsList;
    }

}