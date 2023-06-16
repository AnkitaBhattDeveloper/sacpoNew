package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class docCenterListAdapter implements docCenterListDAO{


    DbHelper dbHelper;
    Context context;

    public docCenterListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<docCenterListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_DOC_CENTERLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getId()+"'," +
                    "'"+dataArrays.get(i).getStudent_id()+"',"+
                    "'"+dataArrays.get(i).getGrant_id()+"',"+
                    "'"+dataArrays.get(i).getPrevious_document()+"',"+
                    "'"+dataArrays.get(i).getPrevious_document_btn()+"',"+
                    "'"+dataArrays.get(i).getName_of_document()+"',"+
                    "'"+dataArrays.get(i).getD_c_doc_status()+"',"+
                    "'"+dataArrays.get(i).getD_c_doc_type()+"',"+
                    "'"+dataArrays.get(i).getUpload_document_type()+"',"+
                    "'"+dataArrays.get(i).getDownload_document_link()+"',"+
                    "'"+dataArrays.get(i).getDownload_document_btn()+"',"+
                    "'"+dataArrays.get(i).getUpload_document_link()+"');");

        }

        return 1;
    }

    @Override
    public int update(docCenterListArray dataArrays) {
        /*when updating please check insert method*/
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_ID                       ,dataArrays.getId());
        contentValues.put(DbSchema.COLUMN_ST_ID                    ,dataArrays.getStudent_id());
        contentValues.put(DbSchema.COLUMN_GRANTID                  ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_PREVIOUS_DOC              ,dataArrays.getPrevious_document());
        contentValues.put(DbSchema.COLUMN_PREVIOUS_DOC_BTN          ,dataArrays.getPrevious_document_btn());
        contentValues.put(DbSchema.COLUMN_NAME_OF_DOC               ,dataArrays.getName_of_document());
        contentValues.put(DbSchema.COLUMN_D_C_DOC_STATUS            ,dataArrays.getD_c_doc_status());
        contentValues.put(DbSchema.COLUMN_D_C_DOC_TYPE              ,dataArrays.getD_c_doc_type());
        contentValues.put(DbSchema.COLUMN_UPLOAD_DOC_TYPE           ,dataArrays.getUpload_document_type());
        contentValues.put(DbSchema.COLUMN_DWNLD_DOC_LINK            ,dataArrays.getDownload_document_link());
        contentValues.put(DbSchema.COLUMN_DWNLD_DOC_BTN             ,dataArrays.getDownload_document_btn());
        contentValues.put(DbSchema.COLUMN_UPLOAD_DOC_LINK           ,dataArrays.getUpload_document_link());

        int data = db.update(DbSchema.TABLE_DOC_CENTERLIST_DETAILS, contentValues, DbSchema.COLUMN_ID + " = " + dataArrays.getId(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_DOC_CENTERLIST_DETAILS, DbSchema.COLUMN_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public docCenterListArray getById(int id) {
        docCenterListArray dataArray=null;
        List<docCenterListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_ID,
                DbSchema.COLUMN_ST_ID           ,
                DbSchema.COLUMN_GRANTID,
                DbSchema.COLUMN_PREVIOUS_DOC     ,
                DbSchema.COLUMN_PREVIOUS_DOC_BTN,
                DbSchema.COLUMN_NAME_OF_DOC        ,
                DbSchema.COLUMN_D_C_DOC_STATUS   ,
                DbSchema.COLUMN_D_C_DOC_TYPE      ,
                DbSchema.COLUMN_UPLOAD_DOC_TYPE   ,
                DbSchema.COLUMN_DWNLD_DOC_LINK   ,
                DbSchema.COLUMN_DWNLD_DOC_BTN     ,
                DbSchema.COLUMN_UPLOAD_DOC_LINK     };
        Cursor cursors = db.query(DbSchema.TABLE_DOC_CENTERLIST_DETAILS, columns, DbSchema.COLUMN_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String id1                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ID              ));
                    String student_id                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ST_ID           ));
                    String grant_id                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTID         ));
                    String previous_document                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PREVIOUS_DOC    ));
                    String previous_document_btn                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PREVIOUS_DOC_BTN));
                    String name_of_document                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NAME_OF_DOC     ));
                    String d_c_doc_status                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_D_C_DOC_STATUS  ));
                    String d_c_doc_type                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_D_C_DOC_TYPE    ));
                    String upload_document_type                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_DOC_TYPE ));
                    String download_document_link               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_DOC_LINK  ));
                    String download_document_btn                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_DOC_BTN   ));
                    String upload_document_link                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_DOC_LINK ));
                    dataArray = new docCenterListArray(id1,student_id,grant_id,previous_document,
                            previous_document_btn, name_of_document,
                            d_c_doc_status,d_c_doc_type,upload_document_type,download_document_link,
                            download_document_btn,upload_document_link);
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
        dbHelper.truncateTable(DbSchema.TABLE_DOC_CENTERLIST_DETAILS);
    }

    @Override
    public List<docCenterListArray> getAll() {
        docCenterListArray dataArray=null;
        List<docCenterListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_ID,
                DbSchema.COLUMN_ST_ID           ,
                DbSchema.COLUMN_GRANTID,
                DbSchema.COLUMN_PREVIOUS_DOC     ,
                DbSchema.COLUMN_PREVIOUS_DOC_BTN,
                DbSchema.COLUMN_NAME_OF_DOC        ,
                DbSchema.COLUMN_D_C_DOC_STATUS   ,
                DbSchema.COLUMN_D_C_DOC_TYPE      ,
                DbSchema.COLUMN_UPLOAD_DOC_TYPE   ,
                DbSchema.COLUMN_DWNLD_DOC_LINK   ,
                DbSchema.COLUMN_DWNLD_DOC_BTN     ,
                DbSchema.COLUMN_UPLOAD_DOC_LINK     };
        Cursor cursors = db.query(DbSchema.TABLE_DOC_CENTERLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String id                                   = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ID              ));
                    String student_id                           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ST_ID           ));
                    String grant_id                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANTID         ));
                    String previous_document                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PREVIOUS_DOC    ));
                    String previous_document_btn                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PREVIOUS_DOC_BTN));
                    String name_of_document                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NAME_OF_DOC     ));
                    String d_c_doc_status                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_D_C_DOC_STATUS  ));
                    String d_c_doc_type                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_D_C_DOC_TYPE    ));
                    String upload_document_type                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_DOC_TYPE ));
                    String download_document_link               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_DOC_LINK  ));
                    String download_document_btn                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DWNLD_DOC_BTN   ));
                    String upload_document_link                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_UPLOAD_DOC_LINK ));
                    dataArray = new docCenterListArray(id,student_id,grant_id,previous_document,
                            previous_document_btn, name_of_document,
                            d_c_doc_status,d_c_doc_type,upload_document_type,download_document_link,
                            download_document_btn,upload_document_link);
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
