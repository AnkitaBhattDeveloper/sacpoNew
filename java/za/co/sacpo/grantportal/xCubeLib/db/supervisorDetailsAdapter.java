package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class supervisorDetailsAdapter implements supervisorDetailsDAO{

    private DbHelper databaseHelper;
    private Context context;

    public supervisorDetailsAdapter(Context context){
        databaseHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(supervisorDetailsArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_M_ID           ,dataArrays.getId());
        contentValues.put(DbSchema.COLUMN_M_NAME         ,dataArrays.getName());
        contentValues.put(DbSchema.COLUMN_M_EMAIL        ,dataArrays.getEmail());
        contentValues.put(DbSchema.COLUMN_M_MOBILE       ,dataArrays.getMobile());
        contentValues.put(DbSchema.COLUMN_M_U_DEPARTMENT ,dataArrays.getU_department());
        contentValues.put(DbSchema.COLUMN_M_U_DESIGNATION,dataArrays.getU_designation());
        contentValues.put(DbSchema.COLUMN_OFC_NO         ,dataArrays.getOfc_no());
        contentValues.put(DbSchema.COLUMN_EMPLOYER_NAME  ,dataArrays.getEmployer());
        contentValues.put(DbSchema.COLUMN_EMPLOYER_SDL   ,dataArrays.getEmployer_sdl());

        long data =  db.insert(DbSchema.TABLE_SUPERVISOR_DETAILS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(supervisorDetailsArray dataArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_M_ID           ,dataArrays.getId());
        contentValues.put(DbSchema.COLUMN_M_NAME         ,dataArrays.getName());
        contentValues.put(DbSchema.COLUMN_M_EMAIL        ,dataArrays.getEmail());
        contentValues.put(DbSchema.COLUMN_M_MOBILE       ,dataArrays.getMobile());
        contentValues.put(DbSchema.COLUMN_M_U_DEPARTMENT ,dataArrays.getU_department());
        contentValues.put(DbSchema.COLUMN_M_U_DESIGNATION,dataArrays.getU_designation());
        contentValues.put(DbSchema.COLUMN_OFC_NO         ,dataArrays.getOfc_no());
        contentValues.put(DbSchema.COLUMN_EMPLOYER_NAME  ,dataArrays.getEmployer());
        contentValues.put(DbSchema.COLUMN_EMPLOYER_SDL   ,dataArrays.getEmployer_sdl());

        int data =  db.update(DbSchema.TABLE_SUPERVISOR_DETAILS, contentValues, DbSchema.COLUMN_M_ID + " = " + dataArrays.getId(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_SUPERVISOR_DETAILS, DbSchema.COLUMN_M_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public supervisorDetailsArray getById(int id) {
        supervisorDetailsArray dataArray = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = {
                DbSchema.COLUMN_M_ID   ,
                DbSchema.COLUMN_M_NAME   ,
                DbSchema.COLUMN_M_EMAIL  ,
                DbSchema.COLUMN_M_MOBILE  ,
                DbSchema.COLUMN_M_U_DEPARTMENT  ,
                DbSchema.COLUMN_M_U_DESIGNATION  ,
                DbSchema.COLUMN_OFC_NO   ,
                DbSchema.COLUMN_EMPLOYER_NAME  ,
                DbSchema.COLUMN_EMPLOYER_SDL };

        Cursor cursors = db.query(DbSchema.TABLE_SUPERVISOR_DETAILS, columns, DbSchema.COLUMN_M_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String m_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_ID           ));
                String name      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_NAME         ));
                String email     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_EMAIL        ));
                String mobile     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_MOBILE       ));
                String u_department = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_U_DEPARTMENT ));
                String u_designation = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_U_DESIGNATION));
                String ofc_no     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OFC_NO         ));
                String employer   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMPLOYER_NAME  ));
                String employer_sdl = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMPLOYER_SDL   ));



                dataArray = new supervisorDetailsArray(m_id, name, email, mobile,u_department,u_designation,ofc_no,employer,employer_sdl);


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
        databaseHelper.truncateTable(DbSchema.TABLE_SUPERVISOR_DETAILS);
    }

    @Override
    public List<supervisorDetailsArray> getAll() {
        List<supervisorDetailsArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = {
                DbSchema.COLUMN_M_ID   ,
                DbSchema.COLUMN_M_NAME   ,
                DbSchema.COLUMN_M_EMAIL  ,
                DbSchema.COLUMN_M_MOBILE  ,
                DbSchema.COLUMN_M_U_DEPARTMENT  ,
                DbSchema.COLUMN_M_U_DESIGNATION  ,
                DbSchema.COLUMN_OFC_NO   ,
                DbSchema.COLUMN_EMPLOYER_NAME  ,
                DbSchema.COLUMN_EMPLOYER_SDL };

        Cursor cursors = db.query(DbSchema.TABLE_SUPERVISOR_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String m_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_ID           ));
                String name      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_NAME         ));
                String email     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_EMAIL        ));
                String mobile     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_MOBILE       ));
                String u_department = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_U_DEPARTMENT ));
                String u_designation = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_M_U_DESIGNATION));
                String ofc_no     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OFC_NO         ));
                String employer   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMPLOYER_NAME  ));
                String employer_sdl = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMPLOYER_SDL   ));



                supervisorDetailsArray   dataArray = new supervisorDetailsArray(m_id, name, email, mobile,u_department,u_designation,ofc_no,employer,employer_sdl);

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
