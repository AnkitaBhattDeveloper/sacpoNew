package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class attDetailsArrayAdapter implements attDetailsDAO {

     private DbHelper databaseHelper;
    private Context context;

    public attDetailsArrayAdapter(Context context){
        databaseHelper = DbHelper.getInstance(context);
    }




    @Override
    public long insert(List<attDetailsArray> dataArrays) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_ATTSTATS_DETAILS +
                    " Values('"+dataArrays.get(i).getGrant_id()+"'," +
                    "'"+dataArrays.get(i).getStudent_id()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getYear()+"',"+
                    "'"+dataArrays.get(i).getDate()+"',"+
                    "'"+dataArrays.get(i).getAttendance_days()+"',"+
                    "'"+dataArrays.get(i).getAnnual_leave()+"',"+
                    "'"+dataArrays.get(i).getSick_leave()+"',"+
                    "'"+dataArrays.get(i).getOther_paid_leave()+"',"+
                    "'"+dataArrays.get(i).getUnpaid_leave()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_comment()+"',"+
                    "'"+dataArrays.get(i).getDownload_attendance()+"',"+
                    "'"+dataArrays.get(i).getDownload_attendance_link()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_approval()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_comment_link()+"');");

        }

        return 1;

    }


    @Override
    public int update(attDetailsArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_GRANT_ID,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_ST_ID              ,dataArrays.getStudent_id());
        contentValues.put(DbSchema.COLUMN_MONTH              ,dataArrays.getMonth());
        contentValues.put(DbSchema.COLUMN_YEAR               ,dataArrays.getYear());
        contentValues.put(DbSchema.COLUMN_DATE               ,dataArrays.getDate());
        contentValues.put(DbSchema.COLUMN_ATT_DAYS           ,dataArrays.getAttendance_days());
        contentValues.put(DbSchema.COLUMN_ANUAL_LEAVE        ,dataArrays.getAnnual_leave());
        contentValues.put(DbSchema.COLUMN_SICK_LEAVE         ,dataArrays.getSick_leave());
        contentValues.put(DbSchema.COLUMN_O_PAID_LEAVE       ,dataArrays.getOther_paid_leave());
        contentValues.put(DbSchema.COLUMN_UNPAID_LEAVE       ,dataArrays.getUnpaid_leave());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_COMMENT ,dataArrays.getSupervisor_comment());
        contentValues.put(DbSchema.COLUMN_DWNLD_ATTENDANCE   ,dataArrays.getDownload_attendance());
        contentValues.put(DbSchema.COLUMN_DWNLD_ATT_LINK     ,dataArrays.getDownload_attendance_link());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_APPROVAL,dataArrays.getSupervisor_approval());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_COMMENT_LINK,dataArrays.getSupervisor_comment_link());

        int data = db.update(DbSchema.TABLE_ATTSTATS_DETAILS, contentValues, DbSchema.COLUMN_ST_ID + " = " + dataArrays.getStudent_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_ATTSTATS_DETAILS, DbSchema.COLUMN_ST_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public attDetailsArray getById(int id) {
        attDetailsArray dataArray=null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = {
                DbSchema.COLUMN_GRANT_ID           ,
                DbSchema.COLUMN_ST_ID                ,
                DbSchema.COLUMN_MONTH               ,
                DbSchema.COLUMN_YEAR                ,
                DbSchema.COLUMN_DATE                ,
                DbSchema.COLUMN_ATT_DAYS             ,
                DbSchema.COLUMN_ANUAL_LEAVE         ,
                DbSchema.COLUMN_SICK_LEAVE         ,
                DbSchema.COLUMN_O_PAID_LEAVE       ,
                DbSchema.COLUMN_UNPAID_LEAVE       ,
                DbSchema.COLUMN_SUPERVISOR_COMMENT ,
                DbSchema.COLUMN_DWNLD_ATTENDANCE   ,
                DbSchema.COLUMN_DWNLD_ATT_LINK     ,
                DbSchema.COLUMN_SUPERVISOR_APPROVAL,
                DbSchema.COLUMN_SUPERVISOR_COMMENT_LINK };

        Cursor cursors = db.query(DbSchema.TABLE_ATTSTATS_DETAILS, columns, DbSchema.COLUMN_ST_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String grant_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID           ));
                String student_id                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ST_ID              ));
                String month                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH              ));
                String year                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR               ));
                String date                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE               ));
                String attendance_days           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATT_DAYS           ));
                String annual_leave              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANUAL_LEAVE        ));
                String sick_leave                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE         ));
                String other_paid_leave          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_O_PAID_LEAVE       ));
                String unpaid_leave              = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE       ));
                String supervisor_comment        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENT ));
                String download_attendance       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATTENDANCE   ));
                String download_attendance_link    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATT_LINK     ));
                String supervisor_approval       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_APPROVAL));
                String supervisor_comment_link    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENT_LINK));




               dataArray = new attDetailsArray(grant_id, student_id,  month, year, date,attendance_days, annual_leave, sick_leave, other_paid_leave,
                        unpaid_leave, supervisor_comment,download_attendance,download_attendance_link,supervisor_approval, supervisor_comment_link);


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
        databaseHelper.truncateTable(DbSchema.TABLE_ATTSTATS_DETAILS);
    }

    @Override
    public List<attDetailsArray> getAll() {
        List<attDetailsArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = databaseHelper.getWritableDatabase();

        String[] columns = {
                DbSchema.COLUMN_GRANT_ID           ,
                DbSchema.COLUMN_ST_ID                ,
                DbSchema.COLUMN_MONTH               ,
                DbSchema.COLUMN_YEAR                ,
                DbSchema.COLUMN_DATE                ,
                DbSchema.COLUMN_ATT_DAYS             ,
                DbSchema.COLUMN_ANUAL_LEAVE         ,
                DbSchema.COLUMN_SICK_LEAVE         ,
                DbSchema.COLUMN_O_PAID_LEAVE       ,
                DbSchema.COLUMN_UNPAID_LEAVE       ,
                DbSchema.COLUMN_SUPERVISOR_COMMENT ,
                DbSchema.COLUMN_DWNLD_ATTENDANCE   ,
                DbSchema.COLUMN_DWNLD_ATT_LINK     ,
                DbSchema.COLUMN_SUPERVISOR_APPROVAL,
                DbSchema.COLUMN_SUPERVISOR_COMMENT_LINK };

        Cursor cursors = db.query(DbSchema.TABLE_ATTSTATS_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do {
                    String grant_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID));
                    String student_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ST_ID));
                    String month = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTH));
                    String year = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_YEAR));
                    String date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DATE));
                    String attendance_days = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATT_DAYS));
                    String annual_leave = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ANUAL_LEAVE));
                    String sick_leave = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SICK_LEAVE));
                    String other_paid_leave = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_O_PAID_LEAVE));
                    String unpaid_leave = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UNPAID_LEAVE));
                    String supervisor_comment = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENT));
                    String download_attendance = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATTENDANCE));
                    String download_attendance_link = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_ATT_LINK));
                    String supervisor_approval = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_APPROVAL));
                    String supervisor_comment_link = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENT_LINK));


                    attDetailsArray dataArray = new attDetailsArray(grant_id, student_id, month, year, date, attendance_days, annual_leave, sick_leave, other_paid_leave,
                            unpaid_leave, supervisor_comment, download_attendance, download_attendance_link, supervisor_approval, supervisor_comment_link);

                    dataArrayList.add(dataArray);
                } while (cursors.moveToNext());
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
