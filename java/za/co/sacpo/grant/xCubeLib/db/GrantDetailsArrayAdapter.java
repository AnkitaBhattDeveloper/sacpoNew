package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class GrantDetailsArrayAdapter implements grantDetailsDAO{


    private DbHelper databaseHelper;
    private Context context;

    public GrantDetailsArrayAdapter(Context context) {
        databaseHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(grantDetailsArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_UID , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_GRANTID, dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_GRANT_SDATE, dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_GRANT_EDATE, dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_GRANT_NAME, dataArrays.getGrant_name());
        contentValues.put(DbSchema.COLUMN_MENTOR_NAME, dataArrays.getMentor_name());
        contentValues.put(DbSchema.COLUMN_MENTORID , dataArrays.getMentor_id());
        contentValues.put(DbSchema.COLUMN_LEM_NAME, dataArrays.getLem_name());
        contentValues.put(DbSchema.COLUMN_LEM_ID , dataArrays.getLem_id());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME, dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_SETA_ID , dataArrays.getSeta_id());
        contentValues.put(DbSchema.COLUMN_SETA_NAME , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_ID , dataArrays.getSeta_manager_id());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_NAME , dataArrays.getSeta_manager_name());
        contentValues.put(DbSchema.COLUMN_ADMIN_ID , dataArrays.getGrant_admin_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_NAME , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_GRANTADMIN_EMAIL , dataArrays.getGrant_admin_email());
        contentValues.put(DbSchema.COLUMN_GRANTADMIN_CELL , dataArrays.getGrant_admin_cell());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_EMAIL, dataArrays.getSeta_manager_email());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_CELL , dataArrays.getSeta_manager_cell());

        long data =  db.insert(DbSchema.TABLE_GRANT_DETAILS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(grantDetailsArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_UID , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_GRANTID, dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_GRANT_SDATE, dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_GRANT_EDATE, dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_GRANT_NAME, dataArrays.getGrant_name());
        contentValues.put(DbSchema.COLUMN_MENTOR_NAME, dataArrays.getMentor_name());
        contentValues.put(DbSchema.COLUMN_MENTORID , dataArrays.getMentor_id());
        contentValues.put(DbSchema.COLUMN_LEM_NAME, dataArrays.getLem_name());
        contentValues.put(DbSchema.COLUMN_LEM_ID , dataArrays.getLem_id());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME, dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_SETA_ID , dataArrays.getSeta_id());
        contentValues.put(DbSchema.COLUMN_SETA_NAME , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_ID , dataArrays.getSeta_manager_id());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_NAME , dataArrays.getSeta_manager_name());
        contentValues.put(DbSchema.COLUMN_ADMIN_ID , dataArrays.getGrant_admin_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_NAME , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_GRANTADMIN_EMAIL , dataArrays.getGrant_admin_email());
        contentValues.put(DbSchema.COLUMN_GRANTADMIN_CELL , dataArrays.getGrant_admin_cell());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_EMAIL, dataArrays.getSeta_manager_email());
        contentValues.put(DbSchema.COLUMN_SETAMANAGER_CELL , dataArrays.getSeta_manager_cell());

        int data = db.update(DbSchema.TABLE_GRANT_DETAILS, contentValues, DbSchema.COLUMN_UID + " = " + dataArrays.getU_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_GRANT_DETAILS, DbSchema.COLUMN_UID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public grantDetailsArray getById(int id) {

        grantDetailsArray dataArrays = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_UID    ,
                DbSchema.COLUMN_GRANTID,
                DbSchema.COLUMN_GRANT_SDATE,
                DbSchema.COLUMN_GRANT_EDATE,
                DbSchema.COLUMN_GRANT_NAME,
                DbSchema.COLUMN_MENTOR_NAME,
                DbSchema.COLUMN_MENTORID ,
                DbSchema.COLUMN_LEM_NAME,
                DbSchema.COLUMN_LEM_ID,
                DbSchema.COLUMN_HOST_EMP_NAME,
                DbSchema.COLUMN_SETA_ID ,
                DbSchema.COLUMN_SETA_NAME ,
                DbSchema.COLUMN_SETA_MANAGER_ID ,
                DbSchema.COLUMN_SETAMANAGER_NAME ,
                DbSchema.COLUMN_ADMIN_ID,
                DbSchema.COLUMN_GRANT_ADMIN_NAME ,
                DbSchema.COLUMN_GRANTADMIN_EMAIL ,
                DbSchema.COLUMN_GRANTADMIN_CELL ,
                DbSchema.COLUMN_SETAMANAGER_EMAIL,
                DbSchema.COLUMN_H_ID,
        };

        Cursor cursors = db.query(DbSchema.TABLE_GRANT_DETAILS, columns, DbSchema.COLUMN_UID + " = " + id, null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UID             ));
                String grant_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTID         ));
                String grant_start_date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_SDATE     ));
                String grant_end_date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_EDATE     ));
                String grant_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_NAME      ));
                String mentor_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_NAME     ));
                String mentor_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTORID        ));
                String lem_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME        ));
                String lem_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_ID          ));
                String host_emp_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME   ));
                String seta_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_ID         ));
                String seta_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME       ));
                String seta_manager_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_ID ));
                String seta_manager_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_NAME ));
                String grant_admin_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADMIN_ID        ));
                String grant_admin = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_NAME ));
                String grant_admin_email = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTADMIN_EMAIL ));
                String grant_admin_cell = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTADMIN_CELL ));
                String seta_manager_email = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_EMAIL));
                String seta_manager_cell = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_CELL            ));


                dataArrays = new grantDetailsArray(u_id,grant_id,grant_start_date,grant_end_date,grant_name,
                        mentor_name,mentor_id,lem_name,lem_id,host_emp_name,seta_id,seta_name,seta_manager_id,
                        seta_manager_name,grant_admin_id,grant_admin,grant_admin_email,grant_admin_cell,
                        seta_manager_email,seta_manager_cell);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return dataArrays;
    }

    @Override
    public void truncate() {
        databaseHelper.truncateTable(DbSchema.TABLE_GRANT_DETAILS);
    }

    @Override
    public List<grantDetailsArray> getAll() {
        List<grantDetailsArray> dataArraysList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_UID    ,
                DbSchema.COLUMN_GRANTID,
                DbSchema.COLUMN_GRANT_SDATE,
                DbSchema.COLUMN_GRANT_EDATE,
                DbSchema.COLUMN_GRANT_NAME,
                DbSchema.COLUMN_MENTOR_NAME,
                DbSchema.COLUMN_MENTORID ,
                DbSchema.COLUMN_LEM_NAME,
                DbSchema.COLUMN_LEM_ID,
                DbSchema.COLUMN_HOST_EMP_NAME,
                DbSchema.COLUMN_SETA_ID ,
                DbSchema.COLUMN_SETA_NAME ,
                DbSchema.COLUMN_SETA_MANAGER_ID ,
                DbSchema.COLUMN_SETAMANAGER_NAME ,
                DbSchema.COLUMN_ADMIN_ID,
                DbSchema.COLUMN_GRANT_ADMIN_NAME ,
                DbSchema.COLUMN_GRANTADMIN_EMAIL ,
                DbSchema.COLUMN_GRANTADMIN_CELL ,
                DbSchema.COLUMN_SETAMANAGER_EMAIL,
                DbSchema.COLUMN_SETAMANAGER_CELL,
        };

        Cursor cursors = db.query(DbSchema.TABLE_GRANT_DETAILS, columns, null,null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UID             ));
                String grant_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTID         ));
                String grant_start_date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_SDATE     ));
                String grant_end_date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_EDATE     ));
                String grant_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_NAME      ));
                String mentor_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_NAME     ));
                String mentor_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTORID        ));
                String lem_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME        ));
                String lem_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_ID          ));
                String host_emp_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME   ));
                String seta_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_ID         ));
                String seta_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME       ));
                String seta_manager_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_ID ));
                String seta_manager_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_NAME ));
                String grant_admin_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADMIN_ID        ));
                String grant_admin = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_NAME ));
                String grant_admin_email = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTADMIN_EMAIL ));
                String grant_admin_cell = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTADMIN_CELL ));
                String seta_manager_email = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_EMAIL));
                String seta_manager_cell = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAMANAGER_CELL            ));


                grantDetailsArray dataArrays = new grantDetailsArray(u_id,grant_id,grant_start_date,grant_end_date,grant_name,
                        mentor_name,mentor_id,lem_name,lem_id,host_emp_name,seta_id,seta_name,seta_manager_id,
                        seta_manager_name,grant_admin_id,grant_admin,grant_admin_email,grant_admin_cell,
                        seta_manager_email,seta_manager_cell);
                dataArraysList.add(dataArrays);
            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
                //db.close();
            }
        }
        return dataArraysList;
    }
}
