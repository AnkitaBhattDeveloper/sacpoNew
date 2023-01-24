package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class noteListAdapter implements noteListDAO{

    DbHelper dbHelper;

    public noteListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<noteListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS +
                    " Values('"+dataArrays.get(i).getN_id()+"'," +
                    "'"+dataArrays.get(i).getN_user_id()+"',"+
                    "'"+dataArrays.get(i).getN_description()+"',"+
                    "'"+dataArrays.get(i).getN_status()+"',"+
                    "'"+dataArrays.get(i).getN_add_date()+"',"+
                    "'"+dataArrays.get(i).getN_add_by()+"',"+
                    "'"+dataArrays.get(i).getN_duration()+"',"+
                    "'"+dataArrays.get(i).getN_category()+"',"+
                    "'"+dataArrays.get(i).getN_note_type_id()+"',"+
                    "'"+dataArrays.get(i).getGrant_id()+"',"+
                    "'"+dataArrays.get(i).getNote_for()+"',"+
                    "'"+dataArrays.get(i).getU_p_cell_no()+"',"+
                    "'"+dataArrays.get(i).getNote_from()+"',"+
                    "'"+dataArrays.get(i).getAdd_by_cell_no()+"',"+
                    "'"+dataArrays.get(i).getAdd_by_u_id()+"',"+
                    "'"+dataArrays.get(i).getN_category_id()+"',"+
                    "'"+dataArrays.get(i).getN_category_name()+"');");

        }

        return 1;
    }

    @Override
    public int update(noteListArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_N_ID                    ,dataArrays.getN_id());
        contentValues.put(DbSchema.COLUMN_NUSER_ID                ,dataArrays.getN_user_id());
        contentValues.put(DbSchema.COLUMN_N_DESCRIPTION           ,dataArrays.getN_description());
        contentValues.put(DbSchema.COLUMN_N_STATUS                ,dataArrays.getN_status());
        contentValues.put(DbSchema.COLUMN_N_ADDDATE               ,dataArrays.getN_add_date());
        contentValues.put(DbSchema.COLUMN_N_ADD_BY                ,dataArrays.getN_add_by());
        contentValues.put(DbSchema.COLUMN_N_DURATION              ,dataArrays.getN_duration());
        contentValues.put(DbSchema.COLUMN_N_CATEGORY              ,dataArrays.getN_category());
        contentValues.put(DbSchema.COLUMN_N_NOTE_TYPE_ID          ,dataArrays.getN_note_type_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ID                ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_NOTE_FOR                ,dataArrays.getNote_for());
        contentValues.put(DbSchema.COLUMN_U_P_CELL_NO             ,dataArrays.getU_p_cell_no());
        contentValues.put(DbSchema.COLUMN_NOTE_FROM               ,dataArrays.getNote_from());
        contentValues.put(DbSchema.COLUMN_ADD_BY_CELL_NO          ,dataArrays.getAdd_by_cell_no());
        contentValues.put(DbSchema.COLUMN_ADD_BY_U_ID             ,dataArrays.getAdd_by_u_id());
        contentValues.put(DbSchema.COLUMN_N_CATEGORY_ID           ,dataArrays.getN_category_id());
        contentValues.put(DbSchema.COLUMN_N_CATEGORY_NAME         ,dataArrays.getN_category_name());

        int data = db.update(DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS, contentValues, DbSchema.COLUMN_N_ID + " = " + dataArrays.getN_id(), null);
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
               int data = db.delete(DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS, DbSchema.COLUMN_N_ID + " = " + id, null);
               //db.close();
               return data;


    }

    @Override
    public noteListArray getById(int id) {
        noteListArray dataArray=null;
        List<noteListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_N_ID            ,
                DbSchema.COLUMN_NUSER_ID        ,
                DbSchema.COLUMN_N_DESCRIPTION   ,
                DbSchema.COLUMN_N_STATUS        ,
                DbSchema.COLUMN_N_ADDDATE       ,
                DbSchema.COLUMN_N_ADD_BY        ,
                DbSchema.COLUMN_N_DURATION      ,
                DbSchema.COLUMN_N_CATEGORY      ,
                DbSchema.COLUMN_N_NOTE_TYPE_ID  ,
                DbSchema.COLUMN_GRANT_ID        ,
                DbSchema.COLUMN_NOTE_FOR        ,
                DbSchema.COLUMN_U_P_CELL_NO     ,
                DbSchema.COLUMN_NOTE_FROM       ,
                DbSchema.COLUMN_ADD_BY_CELL_NO  ,
                DbSchema.COLUMN_ADD_BY_U_ID     ,
                DbSchema.COLUMN_N_CATEGORY_ID   ,
                DbSchema.COLUMN_N_CATEGORY_NAME };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS, columns, DbSchema.COLUMN_N_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   n_id  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ID            ));
                    String   n_user_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUSER_ID        ));
                    String   n_description           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_DESCRIPTION   ));
                    String   n_status      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_STATUS        ));
                    String   n_add_date        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ADDDATE       ));
                    String   n_add_by      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ADD_BY        ));
                    String   n_duration        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_DURATION      ));
                    String   n_category        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY      ));
                    String   n_note_type_id            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_NOTE_TYPE_ID  ));
                    String   grant_id      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID        ));
                    String   note_for      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTE_FOR        ));
                    String   u_p_cell_no         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_P_CELL_NO     ));
                    String   note_from       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTE_FROM       ));
                    String   add_by_cell_no            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADD_BY_CELL_NO  ));
                    String   add_by_u_id         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADD_BY_U_ID     ));
                    String   n_category_id           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY_ID   ));
                    String   n_category_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY_NAME ));

                    dataArray = new noteListArray(
                            n_id,
                            n_user_id,
                            n_description,
                            n_status,
                            n_add_date,
                            n_add_by,
                            n_duration,
                            n_category,
                            n_note_type_id,
                            grant_id,
                            note_for,
                            u_p_cell_no,
                            note_from,
                            add_by_cell_no,
                            add_by_u_id,
                            n_category_id,
                            n_category_name

                    );
                    //dataArrayList.add(dataArray);
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
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS);
    }

    @Override
    public List<noteListArray> getAll() {
        noteListArray dataArray=null;
        List<noteListArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_N_ID            ,
                DbSchema.COLUMN_NUSER_ID        ,
                DbSchema.COLUMN_N_DESCRIPTION   ,
                DbSchema.COLUMN_N_STATUS        ,
                DbSchema.COLUMN_N_ADDDATE       ,
                DbSchema.COLUMN_N_ADD_BY        ,
                DbSchema.COLUMN_N_DURATION      ,
                DbSchema.COLUMN_N_CATEGORY      ,
                DbSchema.COLUMN_N_NOTE_TYPE_ID  ,
                DbSchema.COLUMN_GRANT_ID        ,
                DbSchema.COLUMN_NOTE_FOR        ,
                DbSchema.COLUMN_U_P_CELL_NO     ,
                DbSchema.COLUMN_NOTE_FROM       ,
                DbSchema.COLUMN_ADD_BY_CELL_NO  ,
                DbSchema.COLUMN_ADD_BY_U_ID     ,
                DbSchema.COLUMN_N_CATEGORY_ID   ,
                DbSchema.COLUMN_N_CATEGORY_NAME };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_NOTESLIST_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String   n_id  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ID            ));
                    String   n_user_id       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NUSER_ID        ));
                    String   n_description           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_DESCRIPTION   ));
                    String   n_status      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_STATUS        ));
                    String   n_add_date        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ADDDATE       ));
                    String   n_add_by      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_ADD_BY        ));
                    String   n_duration        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_DURATION      ));
                    String   n_category        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY      ));
                    String   n_note_type_id            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_NOTE_TYPE_ID  ));
                    String   grant_id      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID        ));
                    String   note_for      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTE_FOR        ));
                    String   u_p_cell_no         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_U_P_CELL_NO     ));
                    String   note_from       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTE_FROM       ));
                    String   add_by_cell_no            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADD_BY_CELL_NO  ));
                    String   add_by_u_id         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ADD_BY_U_ID     ));
                    String   n_category_id           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY_ID   ));
                    String   n_category_name             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_N_CATEGORY_NAME ));

                    dataArray = new noteListArray(
                           n_id,
                           n_user_id,
                           n_description,
                           n_status,
                     n_add_date,
                     n_add_by,
                     n_duration,
                     n_category,
                     n_note_type_id,
                     grant_id,
                     note_for,
                     u_p_cell_no,
                     note_from,
                     add_by_cell_no,
                     add_by_u_id,
                     n_category_id,
                     n_category_name

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
