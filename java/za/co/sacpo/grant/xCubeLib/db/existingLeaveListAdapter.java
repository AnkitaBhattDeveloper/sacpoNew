package za.co.sacpo.grant.xCubeLib.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class existingLeaveListAdapter implements existingLeaveListDAO{

    DbHelper dbHelper;
    Context context;

    public existingLeaveListAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }



    @Override
    public long insert(List<existingLeaveListArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS +
                    " Values('"+dataArrays.get(i).getS_a_id()+"'," +
                    "'"+dataArrays.get(i).getAtt_ids()+"',"+
                    "'"+dataArrays.get(i).getMonth()+"',"+
                    "'"+dataArrays.get(i).getFrom_date()+"',"+
                    "'"+dataArrays.get(i).getTo_date()+"',"+
                    "'"+dataArrays.get(i).getAnnual_leave()+"',"+
                    "'"+dataArrays.get(i).getSick_leave()+"',"+
                    "'"+dataArrays.get(i).getOther_paid_leave()+"',"+
                    "'"+dataArrays.get(i).getUnpaid_leave()+"',"+
                    "'"+dataArrays.get(i).getMotivation()+"',"+
                    "'"+dataArrays.get(i).getSa_reason()+"',"+
                    "'"+dataArrays.get(i).getSupervisor_approval_status()+"',"+
                    "'"+dataArrays.get(i).getMotivation_btn()+"',"+
                    "'"+dataArrays.get(i).getReason_btn()+"',"+
                    "'"+dataArrays.get(i).getUploads()+"',"+
                    "'"+dataArrays.get(i).getIs_upload()+"',"+
                    "'"+dataArrays.get(i).getShow_edit_link()+"',"+
                    "'"+dataArrays.get(i).getShow_remove_link()+"');");

        }

        return 1;
    }

    @Override
    public int update(existingLeaveListArray dataArrays) {
        return 0;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_EXISTING_LEAVELIST_DETAILS, DbSchema.COLUMN_S_A_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public existingLeaveListArray getById(int id) {
        return null;
    }

    @Override
    public void truncate() {

    }

    @Override
    public List<existingLeaveListArray> getAll() {
        return null;
    }
}
