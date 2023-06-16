package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mLearnerDetailsAdapter implements mLearnerDetailsDAO{

    DbHelper dbHelper;

    public mLearnerDetailsAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(mLearnerDetailsArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_U_ID                 , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_S_ID_NO              , dataArrays.getS_id_no());
        contentValues.put(DbSchema.COLUMN_STUDENT_NAME         , dataArrays.getStudent_name());
        contentValues.put(DbSchema.COLUMN_U_EMAIL              , dataArrays.getU_email());
        contentValues.put(DbSchema.COLUMN_GENDER               , dataArrays.getGender_name());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_START_DATE , dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_END_DATE   , dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_MENTOR_NAME          , dataArrays.getMentor_name());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME        , dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN          , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_WORKSTATION          , dataArrays.getWorkstation());
        contentValues.put(DbSchema.COLUMN_SETA_NAME            , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_LEM_NAME             , dataArrays.getLem_name());


        long data =  db.insert(DbSchema.TABLE_MENTOR_LEARNER_DETAILS, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(mLearnerDetailsArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_U_ID                 , dataArrays.getU_id());
        contentValues.put(DbSchema.COLUMN_S_ID_NO              , dataArrays.getS_id_no());
        contentValues.put(DbSchema.COLUMN_STUDENT_NAME         , dataArrays.getStudent_name());
        contentValues.put(DbSchema.COLUMN_U_EMAIL              , dataArrays.getU_email());
        contentValues.put(DbSchema.COLUMN_GENDER               , dataArrays.getGender_name());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_START_DATE , dataArrays.getS_s_g_grant_start_date());
        contentValues.put(DbSchema.COLUMN_SSG_GRANT_END_DATE   , dataArrays.getS_s_g_grant_end_date());
        contentValues.put(DbSchema.COLUMN_MENTOR_NAME          , dataArrays.getMentor_name());
        contentValues.put(DbSchema.COLUMN_HOST_EMP_NAME        , dataArrays.getHost_emp_name());
        contentValues.put(DbSchema.COLUMN_GRANT_ADMIN          , dataArrays.getGrant_admin());
        contentValues.put(DbSchema.COLUMN_WORKSTATION          , dataArrays.getWorkstation());
        contentValues.put(DbSchema.COLUMN_SETA_NAME            , dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_LEM_NAME             , dataArrays.getLem_name());


        int data = db.update(DbSchema.TABLE_MENTOR_LEARNER_DETAILS, contentValues, DbSchema.COLUMN_UID + " = " + dataArrays.getU_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MENTOR_LEARNER_DETAILS, DbSchema.COLUMN_UID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mLearnerDetailsArray getById(int id) {
        List<mLearnerDetailsArray> dataArraysList = new ArrayList<>();
        mLearnerDetailsArray dataArrays = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_U_ID                 ,
                DbSchema.COLUMN_S_ID_NO              ,
                DbSchema.COLUMN_STUDENT_NAME         ,
                DbSchema.COLUMN_U_EMAIL              ,
                DbSchema.COLUMN_GENDER               ,
                DbSchema.COLUMN_SSG_GRANT_START_DATE ,
                DbSchema.COLUMN_SSG_GRANT_END_DATE   ,
                DbSchema.COLUMN_MENTOR_NAME          ,
                DbSchema.COLUMN_HOST_EMP_NAME        ,
                DbSchema.COLUMN_GRANT_ADMIN          ,
                DbSchema.COLUMN_WORKSTATION          ,
                DbSchema.COLUMN_SETA_NAME            ,
                DbSchema.COLUMN_LEM_NAME             ,
        };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_LEARNER_DETAILS, columns, DbSchema.COLUMN_UID + " = " + id,null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_ID                     ));
                String s_id_no                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_ID_NO                  ));
                String student_name              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDENT_NAME             ));
                String u_email                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_EMAIL                  ));
                String gender_name               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GENDER                   ));
                String s_s_g_grant_start_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_START_DATE     ));
                String s_s_g_grant_end_date      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_END_DATE       ));
                String mentor_name               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_NAME              ));
                String host_emp_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME            ));
                String grant_admin               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN              ));
                String workstation               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_WORKSTATION              ));
                String seta_name                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                ));
                String lem_name                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME                 ));


                dataArrays  = new mLearnerDetailsArray(
                        u_id,
                        s_id_no,
                        student_name,
                        u_email,
                        gender_name,
                        s_s_g_grant_start_date,
                        s_s_g_grant_end_date,
                        mentor_name,
                        host_emp_name,
                        grant_admin,
                        workstation,
                        seta_name,
                        lem_name
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
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_LEARNER_DETAILS);
    }

    @Override
    public List<mLearnerDetailsArray> getAll() {
        List<mLearnerDetailsArray> dataArraysList = new ArrayList<>();
        mLearnerDetailsArray dataArrays = null;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] columns = {
                DbSchema.COLUMN_U_ID                 ,
                DbSchema.COLUMN_S_ID_NO              ,
                DbSchema.COLUMN_STUDENT_NAME         ,
                DbSchema.COLUMN_U_EMAIL              ,
                DbSchema.COLUMN_GENDER               ,
                DbSchema.COLUMN_SSG_GRANT_START_DATE ,
                DbSchema.COLUMN_SSG_GRANT_END_DATE   ,
                DbSchema.COLUMN_MENTOR_NAME          ,
                DbSchema.COLUMN_HOST_EMP_NAME        ,
                DbSchema.COLUMN_GRANT_ADMIN          ,
                DbSchema.COLUMN_WORKSTATION          ,
                DbSchema.COLUMN_SETA_NAME            ,
                DbSchema.COLUMN_LEM_NAME             ,
        };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_LEARNER_DETAILS, columns, null,null, null, null, null);
        try {
            if (cursors.moveToNext()) {
                String u_id                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_ID                     ));
                String s_id_no                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_ID_NO                  ));
                String student_name              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDENT_NAME             ));
                String u_email                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_EMAIL                  ));
                String gender_name               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GENDER                   ));
                String s_s_g_grant_start_date    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_START_DATE     ));
                String s_s_g_grant_end_date      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SSG_GRANT_END_DATE       ));
                String mentor_name               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MENTOR_NAME              ));
                String host_emp_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_HOST_EMP_NAME            ));
                String grant_admin               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ADMIN              ));
                String workstation               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_WORKSTATION              ));
                String seta_name                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                ));
                String lem_name                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEM_NAME                 ));


                dataArrays  = new mLearnerDetailsArray(
                        u_id,
                        s_id_no,
                        student_name,
                        u_email,
                gender_name,
                s_s_g_grant_start_date,
                s_s_g_grant_end_date,
                mentor_name,
                host_emp_name,
                grant_admin,
                workstation,
                seta_name,
                lem_name
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
