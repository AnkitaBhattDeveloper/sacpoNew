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

public class studentsAdapter implements studentsDAO {

    private DbHelper databaseHelper;
    private Context context;

    public studentsAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(students students) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_NAME, students.getName());
        contentValues.put(DbSchema.COLUMN_S_TYPE, students.getType());
        contentValues.put(DbSchema.COLUMN_S_SERVER_ID, students.getId());
        long data =  db.insert(DbSchema.TABLE_STUDENTS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(students students) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_NAME, students.getName());
        contentValues.put(DbSchema.COLUMN_S_TYPE, students.getType());
        contentValues.put(DbSchema.COLUMN_S_SERVER_ID, students.getId());
        int data = db.update(DbSchema.TABLE_STUDENTS, contentValues, DbSchema.COLUMN_S_ID + " = " + students.getKeyId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_STUDENTS, DbSchema.COLUMN_S_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_STUDENTS);
    }

    @Override
    public students getById(int id) {
        students students = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_S_ID, DbSchema.COLUMN_S_SERVER_ID, DbSchema.COLUMN_S_TYPE,DbSchema.COLUMN_S_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_STUDENTS, columns, DbSchema.COLUMN_S_ID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_TYPE));
                students = new students(keyId, Id, Type, Name);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return students;
    }

    @Override
    public List<students> getAll() {
        List<students> studentsList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DbSchema.COLUMN_S_ID, DbSchema.COLUMN_S_SERVER_ID, DbSchema.COLUMN_S_TYPE,DbSchema.COLUMN_S_NAME};
        Cursor cursors = db.query(DbSchema.TABLE_STUDENTS, columns, null, null, null, null, null);
        try {
            while (cursors.moveToNext()) {
                int keyId = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_ID));
                int Id = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_SERVER_ID));
                String Name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_NAME));
                int Type = cursors.getInt(cursors.getColumnIndex(DbSchema.COLUMN_S_TYPE));
                students students = new students(keyId, Id, Type, Name);
                studentsList.add(students);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return studentsList;
    }

}