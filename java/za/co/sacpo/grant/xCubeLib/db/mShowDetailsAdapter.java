package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class mShowDetailsAdapter implements mShowDetailsDAO{

    DbHelper dbHelper;

    public mShowDetailsAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }

    @Override
    public long insert(List<mShowDetailsArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_MENTOR_SHOW_DETAILS +
                    " Values('"+dataArrays.get(i).getS_id_no()+"'," +
                    "'"+dataArrays.get(i).getStu_email()+"',"+
                    "'"+dataArrays.get(i).getStu_cell()+"',"+
                    "'"+dataArrays.get(i).getStudName()+"',"+
                    "'"+dataArrays.get(i).getUnpaid_leave()+"',"+
                    "'"+dataArrays.get(i).getOther_paid_leave()+"',"+
                    "'"+dataArrays.get(i).getSick_leave()+"',"+
                    "'"+dataArrays.get(i).getAnnual_leave()+"',"+
                    "'"+dataArrays.get(i).getLeadManagerContact()+"',"+
                    "'"+dataArrays.get(i).getLeadManagerEmail()+"',"+
                    "'"+dataArrays.get(i).getLeadManager()+"',"+
                    "'"+dataArrays.get(i).getG_d_grant_number()+"',"+
                    "'"+dataArrays.get(i).getGrantName()+"',"+
                    "'"+dataArrays.get(i).getSetaName()+"',"+
                    "'"+dataArrays.get(i).getLead_emp()+"');");

        }

        return 1;
    }

    @Override
    public int update(mShowDetailsArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_LEAD_EMP                    ,dataArrays.getLead_emp());
        contentValues.put(DbSchema.COLUMN_SETAN_AME                   ,dataArrays.getSetaName());
        contentValues.put(DbSchema.COLUMN_GRANTNAME                   ,dataArrays.getGrantName());
        contentValues.put(DbSchema.COLUMN_GRANT_NUMBER                ,dataArrays.getG_d_grant_number());
        contentValues.put(DbSchema.COLUMN_LEAD_MANAGER                ,dataArrays.getLeadManager());
        contentValues.put(DbSchema.COLUMN_LEAD_MANAGER_EMAIL          ,dataArrays.getLeadManagerEmail());
        contentValues.put(DbSchema.COLUMN_LEAD_MANAGER_CONTACT        ,dataArrays.getLeadManagerContact());
        contentValues.put(DbSchema.COLUMN_ANNUAL_LEAVE                ,dataArrays.getAnnual_leave());
        contentValues.put(DbSchema.COLUMN_SICK_LEAVE                  ,dataArrays.getSick_leave());
        contentValues.put(DbSchema.COLUMN_OTHER_PAID_LEAVE            ,dataArrays.getOther_paid_leave());
        contentValues.put(DbSchema.COLUMN_UNPAID_LEAVE                ,dataArrays.getUnpaid_leave());
        contentValues.put(DbSchema.COLUMN_STUDNAME                    ,dataArrays.getStudName());
        contentValues.put(DbSchema.COLUMN_SID_NO                      ,dataArrays.getS_id_no());
        contentValues.put(DbSchema.COLUMN_STU_EMAIL                   ,dataArrays.getStu_email());
        contentValues.put(DbSchema.COLUMN_STU_CELL                    ,dataArrays.getStu_cell());

        int data = db.update(DbSchema.TABLE_MENTOR_SHOW_DETAILS, contentValues, DbSchema.COLUMN_SID_NO + " = " + dataArrays.getS_id_no(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MENTOR_SHOW_DETAILS, DbSchema.COLUMN_SID_NO + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public mShowDetailsArray getById(int id) {
        mShowDetailsArray dataArray=null;
        List<mShowDetailsArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_LEAD_EMP               ,
                DbSchema.COLUMN_SETAN_AME              ,
                DbSchema.COLUMN_GRANTNAME              ,
                DbSchema.COLUMN_GRANT_NUMBER           ,
                DbSchema.COLUMN_LEAD_MANAGER           ,
                DbSchema.COLUMN_LEAD_MANAGER_EMAIL     ,
                DbSchema.COLUMN_LEAD_MANAGER_CONTACT   ,
                DbSchema.COLUMN_ANNUAL_LEAVE           ,
                DbSchema.COLUMN_SICK_LEAVE             ,
                DbSchema.COLUMN_OTHER_PAID_LEAVE       ,
                DbSchema.COLUMN_UNPAID_LEAVE           ,
                DbSchema.COLUMN_STUDNAME               ,
                DbSchema.COLUMN_SID_NO                 ,
                DbSchema.COLUMN_STU_EMAIL              ,
                DbSchema.COLUMN_STU_CELL               };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_SHOW_DETAILS, columns, DbSchema.COLUMN_SID_NO + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   lead_emp                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_EMP               ));
                    String   setaName                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAN_AME              ));
                    String   grantName                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTNAME              ));
                    String   g_d_grant_number                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_NUMBER           ));
                    String   leadManager                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER           ));
                    String   leadManagerEmail                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER_EMAIL     ));
                    String   leadManagerContact                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER_CONTACT   ));
                    String   annual_leave                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE           ));
                    String   sick_leave                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE             ));
                    String   other_paid_leave                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OTHER_PAID_LEAVE       ));
                    String   unpaid_leave                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE           ));
                    String   studName                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDNAME               ));
                    String   s_id_no                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SID_NO                 ));
                    String   stu_email                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_EMAIL              ));
                    String   stu_cell                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_CELL               ));

                    dataArray = new mShowDetailsArray(
                            lead_emp,
                            setaName,
                            grantName,
                            g_d_grant_number,
                            leadManager,
                            leadManagerEmail,
                            leadManagerContact,
                            annual_leave,
                            sick_leave,
                            other_paid_leave,
                            unpaid_leave,
                            studName,
                            s_id_no,
                            stu_email,
                            stu_cell



                    );
                   // dataArrayList.add(dataArray);
                }while (cursors.moveToNext());

            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
            }
        }
        return dataArray;
    }

    @Override
    public void truncate() {
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_SHOW_DETAILS);
    }

    @Override
    public List<mShowDetailsArray> getAll() {
        mShowDetailsArray dataArray=null;
        List<mShowDetailsArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_LEAD_EMP               ,
                DbSchema.COLUMN_SETAN_AME              ,
                DbSchema.COLUMN_GRANTNAME              ,
                DbSchema.COLUMN_GRANT_NUMBER           ,
                DbSchema.COLUMN_LEAD_MANAGER           ,
                DbSchema.COLUMN_LEAD_MANAGER_EMAIL     ,
                DbSchema.COLUMN_LEAD_MANAGER_CONTACT   ,
                DbSchema.COLUMN_ANNUAL_LEAVE           ,
                DbSchema.COLUMN_SICK_LEAVE             ,
                DbSchema.COLUMN_OTHER_PAID_LEAVE       ,
                DbSchema.COLUMN_UNPAID_LEAVE           ,
                DbSchema.COLUMN_STUDNAME               ,
                DbSchema.COLUMN_SID_NO                 ,
                DbSchema.COLUMN_STU_EMAIL              ,
                DbSchema.COLUMN_STU_CELL               };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_SHOW_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   lead_emp                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_EMP               ));
                    String   setaName                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETAN_AME              ));
                    String   grantName                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTNAME              ));
                    String   g_d_grant_number                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_NUMBER           ));
                    String   leadManager                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER           ));
                    String   leadManagerEmail                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER_EMAIL     ));
                    String   leadManagerContact                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAD_MANAGER_CONTACT   ));
                    String   annual_leave                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANNUAL_LEAVE           ));
                    String   sick_leave                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE             ));
                    String   other_paid_leave                  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_OTHER_PAID_LEAVE       ));
                    String   unpaid_leave                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE           ));
                    String   studName                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STUDNAME               ));
                    String   s_id_no                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SID_NO                 ));
                    String   stu_email                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_EMAIL              ));
                    String   stu_cell                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STU_CELL               ));

                    dataArray = new mShowDetailsArray(
                           lead_emp,
                           setaName,
                           grantName,
                           g_d_grant_number,
                           leadManager,
                           leadManagerEmail,
                           leadManagerContact,
                           annual_leave,
                           sick_leave,
                           other_paid_leave,
                           unpaid_leave,
                           studName,
                           s_id_no,
                           stu_email,
                           stu_cell



                    );
                    dataArrayList.add(dataArray);
                }while (cursors.moveToNext());

            }
        }
        finally {
            if (cursors != null && !cursors.isClosed()) {
                cursors.close();
            }
        }
        return dataArrayList;
    }
}
