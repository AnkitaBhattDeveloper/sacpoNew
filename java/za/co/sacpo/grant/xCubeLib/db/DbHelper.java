package za.co.sacpo.grant.xCubeLib.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by xcube-06 on 25/7/18.
 */

public class DbHelper extends SQLiteOpenHelper {

    private Context context;
    private static DbHelper mInstance = null;
    public static synchronized DbHelper getInstance(Context ctx) {
        if (mInstance == null) {
            mInstance = new DbHelper(ctx.getApplicationContext());
        }
        return mInstance;
    }
    private DbHelper(Context context) {
        super(context, DbSchema.DATABASE_NAME, null, DbSchema.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DbSchema.CREATE_TABLE_STATIC_LABELS);
        db.execSQL(DbSchema.CREATE_TABLE_MASTER_ARRAYS);
        db.execSQL(DbSchema.CREATE_TABLE_HELP_ARRAYS);
        db.execSQL(DbSchema.CREATE_TABLE_STUDENTS);
        db.execSQL(DbSchema.CREATE_TABLE_WORKX);
        db.execSQL(DbSchema.CREATE_TABLE_DASHBOARD_DATA);
        db.execSQL(DbSchema.CREATE_TABLE_BANK_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_GRANT_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_ATTSTATS_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_ATTLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_POPULATE_ATTLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_EXISTING_LEAVELIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_PAST_CLAIMLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_DOC_CENTERLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_ST_WEEKLY_REPORTLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_ST_WEEKLY_REPORT_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_MENTOR_DASHBOARD_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_MENTOR_NOTESLIST_DETAILS);
        db.execSQL(DbSchema.CREATE_TABLE_MENTOR_LEARNER_DETAILS);
        //db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DbSchema.DROP_TABLE_STATIC_LABELS);
        db.execSQL(DbSchema.DROP_TABLE_MASTER_ARRAYS);
        db.execSQL(DbSchema.DROP_TABLE_HELP_ARRAYS);
        db.execSQL(DbSchema.DROP_TABLE_STUDENTS);
        db.execSQL(DbSchema.DROP_TABLE_WORKX);
        db.execSQL(DbSchema.DROP_TABLE_DASHBOARD_DATA);
        db.execSQL(DbSchema.DROP_TABLE_BANK_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_GRANT_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_ATTSTATS_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_POPULATE_ATTLIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_EXISTING_LEAVELIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_PAST_CLAIMLIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_DOC_CENTERLIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_ST_WEEKLY_REPORTLIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_ST_WEEKLY_REPORT_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_MENTOR_DASHBOARD_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_MENTOR_NOTESLIST_DETAILS);
        db.execSQL(DbSchema.DROP_TABLE_MENTOR_LEARNER_DETAILS);
        onCreate(db);
    }
    public void truncateTable(String TABLE_NAME){
        SQLiteDatabase db = this.getWritableDatabase();
        switch(TABLE_NAME) {
            case DbSchema.TABLE_STATIC_LABELS:
                db.execSQL(DbSchema.DROP_TABLE_STATIC_LABELS);
                db.execSQL(DbSchema.CREATE_TABLE_STATIC_LABELS);
                break;
            case DbSchema.TABLE_MASTER_ARRAYS:
                db.execSQL(DbSchema.DROP_TABLE_MASTER_ARRAYS);
                db.execSQL(DbSchema.CREATE_TABLE_MASTER_ARRAYS);
                break;
            case DbSchema.TABLE_HELP_ARRAYS:
                db.execSQL(DbSchema.DROP_TABLE_HELP_ARRAYS);
                db.execSQL(DbSchema.CREATE_TABLE_HELP_ARRAYS);
                break;
            case DbSchema.TABLE_STUDENTS:
                db.execSQL(DbSchema.DROP_TABLE_STUDENTS);
                db.execSQL(DbSchema.CREATE_TABLE_STUDENTS);
                break;
            case DbSchema.TABLE_WORKX:
                db.execSQL(DbSchema.DROP_TABLE_WORKX);
                db.execSQL(DbSchema.CREATE_TABLE_WORKX);
                break;
            case DbSchema.TABLE_DASHBOARD_DATA:
                db.execSQL(DbSchema.DROP_TABLE_DASHBOARD_DATA);
                db.execSQL(DbSchema.CREATE_TABLE_DASHBOARD_DATA);
                break;
            case DbSchema.TABLE_BANK_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_BANK_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_BANK_DETAILS);
                break;
            case DbSchema.TABLE_GRANT_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_GRANT_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_GRANT_DETAILS);
                break;
            case DbSchema.TABLE_SUPERVISOR_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_SUPERVISOR_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_SUPERVISOR_DETAILS);
                break;
            case DbSchema.TABLE_ATTSTATS_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_ATTSTATS_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_ATTSTATS_DETAILS);
                break;
            case DbSchema.TABLE_ATTLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_ATTLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_ATTLIST_DETAILS);
                break;
            case DbSchema.TABLE_POPULATE_ATTLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_POPULATE_ATTLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_POPULATE_ATTLIST_DETAILS);
                break;
            case DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_EXISTING_LEAVELIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_EXISTING_LEAVELIST_DETAILS);
                break;
            case DbSchema.TABLE_PAST_CLAIMLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_PAST_CLAIMLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_PAST_CLAIMLIST_DETAILS);
                break;
            case DbSchema.TABLE_DOC_CENTERLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_DOC_CENTERLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_DOC_CENTERLIST_DETAILS);
                break;
            case DbSchema.TABLE_ST_WEEKLY_REPORTLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_ST_WEEKLY_REPORTLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_ST_WEEKLY_REPORTLIST_DETAILS);
                break;
                case DbSchema.TABLE_ST_WEEKLY_REPORT_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_ST_WEEKLY_REPORT_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_ST_WEEKLY_REPORT_DETAILS);
                break;
                case DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_MENTOR_DASHBOARD_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_MENTOR_DASHBOARD_DETAILS);
                break;
                case DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_MENTOR_NOTESLIST_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_MENTOR_NOTESLIST_DETAILS);
                break;
                case DbSchema.TABLE_MENTOR_LEARNER_DETAILS:
                db.execSQL(DbSchema.DROP_TABLE_MENTOR_LEARNER_DETAILS);
                db.execSQL(DbSchema.CREATE_TABLE_MENTOR_LEARNER_DETAILS);
                break;
        }
    }
    public Cursor getAllData(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        //db.close();
        return res;
    }
    public Cursor getAllByType(String TABLE_NAME,String COLUMN_NAME,int Type) {
        SQLiteDatabase db = this.getWritableDatabase();
        //Log.i("DBH","select * from "+TABLE_NAME+" where "+COLUMN_NAME+"="+Type);
        Cursor res = db.rawQuery("select * from "+TABLE_NAME+" where "+COLUMN_NAME+"="+Type,null);
        //db.close();
        return res;
    }
    public Cursor getDataValueByName(String Name) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ DbSchema.COLUMN_CONTENT_VALUE+" from "+ DbSchema.TABLE_STATIC_LABELS+" where "+ DbSchema.COLUMN_CONTENT_NAME+" = '"+Name+"' limit 0,1",null);
        //db.close();
        return res;
    }
    public Cursor getTitleByActivity(String Activity,int type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ DbSchema.COLUMN_H_TITLE+" from "+ DbSchema.TABLE_HELP_ARRAYS+" where "+ DbSchema.COLUMN_H_ACTIVITY+" = '"+Activity+"' AND "+DbSchema.COLUMN_H_TYPE+"='"+type+"' limit 0,1",null);
        //db.close();
        return res;
    }
    public Cursor getContentByActivity(String Activity,int type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select "+ DbSchema.COLUMN_H_CONTENT+" from "+ DbSchema.TABLE_HELP_ARRAYS+" where "+ DbSchema.COLUMN_H_ACTIVITY+" = '"+Activity+"' AND "+DbSchema.COLUMN_H_TYPE+"='"+type+"' limit 0,1",null);
        //db.close();
        return res;
    }

        /*public String getYourData() {

            final String TABLE_NAME = "greeting";

            String selectQuery = "SELECT  * FROM " + TABLE_NAME;
            SQLiteDatabase db  = this.getReadableDatabase();
            Cursor cursor      = db.rawQuery(selectQuery, null);


            if (cursor.moveToFirst()) {
                do {
                    // get the data into array, or class variable
                } while (cursor.moveToNext());
            }
            cursor.close();
             return null;
        }

        public void openDataBase() {

        }*/
}