package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class mLearnerGrantDetailsAdapter implements mLearnerGrantDetailsDAO{

    DbHelper dbHelper;

    public mLearnerGrantDetailsAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(mLearnerGrantDetailsArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_U_ID                 , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_S_ID_NO              , dataArrays.getS_id_no());
        contentValues.put(DbSchema.COLUMN_GRANT_ID             , dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_SETA_NAME            , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_NAME    , dataArrays.getSeta_manager_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_ID      , dataArrays.getSeta_manager_id());
        contentValues.put(DbSchema.COLUMN_LEM_NAME             , dataArrays.getLem_name());
        contentValues.put(DbSchema.COLUMN_LEM_ID               , dataArrays.getLem_id());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME        , dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN          , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_ID       , dataArrays.getGrant_admin_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_EMAIL    , dataArrays.getGrant_admin_email());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_CELL     , dataArrays.getGrant_admin_cell());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_SDL         , dataArrays.getHost_emp_sdl());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_START_DATE , dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_END_DATE   , dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_MENTOR_ID            , dataArrays.getMentor_id());


        long data =  db.insert(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(mLearnerGrantDetailsArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_U_ID                 , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_S_ID_NO              , dataArrays.getS_id_no());
        contentValues.put(DbSchema.COLUMN_GRANT_ID             , dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_SETA_NAME            , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_NAME    , dataArrays.getSeta_manager_name());
        contentValues.put(DbSchema.COLUMN_SETA_MANAGER_ID      , dataArrays.getSeta_manager_id());
        contentValues.put(DbSchema.COLUMN_LEM_NAME             , dataArrays.getLem_name());
        contentValues.put(DbSchema.COLUMN_LEM_ID               , dataArrays.getLem_id());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME        , dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN          , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_ID       , dataArrays.getGrant_admin_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_EMAIL    , dataArrays.getGrant_admin_email());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN_CELL     , dataArrays.getGrant_admin_cell());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_SDL         , dataArrays.getHost_emp_sdl());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_START_DATE , dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_END_DATE   , dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_MENTOR_ID            , dataArrays.getMentor_id());


        int data = db.update(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS, contentValues, DbSchema.COLUMN_UID + " = " + dataArrays.getU_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS, DbSchema.COLUMN_UID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mLearnerGrantDetailsArray getById(int id) {
        List<mLearnerGrantDetailsArray> dataArraysList = new ArrayList<>();
        mLearnerGrantDetailsArray dataArrays = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_U_ID                 ,
                DbSchema.COLUMN_S_ID_NO              ,
                DbSchema.COLUMN_GRANT_ID             ,
                DbSchema.COLUMN_SETA_NAME            ,
                DbSchema.COLUMN_SETA_MANAGER_NAME    ,
                DbSchema.COLUMN_SETA_MANAGER_ID      ,
                DbSchema.COLUMN_LEM_NAME             ,
                DbSchema.COLUMN_LEM_ID               ,
                DbSchema.COLUMN_HOST_EMP_NAME        ,
                DbSchema.COLUMN_GRANT_ADMIN          ,
                DbSchema.COLUMN_GRANT_ADMIN_ID       ,
                DbSchema.COLUMN_GRANT_ADMIN_EMAIL    ,
                DbSchema.COLUMN_GRANT_ADMIN_CELL     ,
                DbSchema.COLUMN_HOST_EMP_SDL         ,
                DbSchema.COLUMN_SSG_GRANT_START_DATE ,
                DbSchema.COLUMN_SSG_GRANT_END_DATE   ,
                DbSchema.COLUMN_MENTOR_ID            ,
        };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS, columns, DbSchema.COLUMN_UID + " = " + id,null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_ID                     ));
                String s_id_no               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_ID_NO                  ));
                String grant_id              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID                 ));
                String seta_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                ));
                String seta_manager_name     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_NAME        ));
                String seta_manager_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_ID          ));
                String lem_name              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME                 ));
                String lem_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_ID                   ));
                String host_emp_name         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME            ));
                String grant_admin           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN              ));
                String grant_admin_id        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_ID           ));
                String grant_admin_email     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_EMAIL        ));
                String grant_admin_cell      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_CELL         ));
                String host_emp_sdl          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_SDL             ));
                String s_s_g_grant_start_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_START_DATE     ));
                String s_s_g_grant_end_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_END_DATE       ));
                String mentor_id      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_ID                ));


                dataArrays  = new mLearnerGrantDetailsArray(
                        u_id,
                        s_id_no,
                        grant_id,
                        seta_name,
                        seta_manager_name,
                        seta_manager_id,
                        lem_name,
                        lem_id,
                        host_emp_name,
                        grant_admin,
                        grant_admin_id,
                        grant_admin_email,
                        grant_admin_cell,
                        host_emp_sdl,
                        s_s_g_grant_start_date,
                        s_s_g_grant_end_date,
                        mentor_id
                );
                //dataArraysList.add(dataArrays);
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
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS);
    }

    @Override
    public List<mLearnerGrantDetailsArray> getAll() {
        List<mLearnerGrantDetailsArray> dataArraysList = new ArrayList<>();
        mLearnerGrantDetailsArray dataArrays = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_U_ID                 ,
                DbSchema.COLUMN_S_ID_NO              ,
                DbSchema.COLUMN_GRANT_ID             ,
                DbSchema.COLUMN_SETA_NAME            ,
                DbSchema.COLUMN_SETA_MANAGER_NAME    ,
                DbSchema.COLUMN_SETA_MANAGER_ID      ,
                DbSchema.COLUMN_LEM_NAME             ,
                DbSchema.COLUMN_LEM_ID               ,
                DbSchema.COLUMN_HOST_EMP_NAME        ,
                DbSchema.COLUMN_GRANT_ADMIN          ,
                DbSchema.COLUMN_GRANT_ADMIN_ID       ,
                DbSchema.COLUMN_GRANT_ADMIN_EMAIL    ,
                DbSchema.COLUMN_GRANT_ADMIN_CELL     ,
                DbSchema.COLUMN_HOST_EMP_SDL         ,
                DbSchema.COLUMN_SSG_GRANT_START_DATE ,
                DbSchema.COLUMN_SSG_GRANT_END_DATE   ,
                DbSchema.COLUMN_MENTOR_ID            ,
        };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_LEARNER_GRANT_DETAILS, columns, null,null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_ID                     ));
                String s_id_no               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_ID_NO                  ));
                String grant_id              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID                 ));
                String seta_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                ));
                String seta_manager_name     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_NAME        ));
                String seta_manager_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_MANAGER_ID          ));
                String lem_name              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME                 ));
                String lem_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_ID                   ));
                String host_emp_name         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME            ));
                String grant_admin           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN              ));
                String grant_admin_id        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_ID           ));
                String grant_admin_email     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_EMAIL        ));
                String grant_admin_cell      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN_CELL         ));
                String host_emp_sdl          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_SDL             ));
                String s_s_g_grant_start_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_START_DATE     ));
                String s_s_g_grant_end_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_END_DATE       ));
                String mentor_id      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_ID                ));


                dataArrays  = new mLearnerGrantDetailsArray(
                      u_id,
                      s_id_no,
                      grant_id,
                      seta_name,
                      seta_manager_name,
                      seta_manager_id,
                      lem_name,
                      lem_id,
                      host_emp_name,
                      grant_admin,
                      grant_admin_id,
                      grant_admin_email,
                      grant_admin_cell,
                      host_emp_sdl,
                      s_s_g_grant_start_date,
                      s_s_g_grant_end_date,
                      mentor_id
                );
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
