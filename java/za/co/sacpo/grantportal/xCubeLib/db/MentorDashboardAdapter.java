package za.co.sacpo.grantportal.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class MentorDashboardAdapter implements MentorDasboardDAO{

    DbHelper dbHelper;
    Context context;

    public MentorDashboardAdapter(Context context){
        dbHelper = DbHelper.getInstance(context);
    }


    @Override
    public long insert(List<MentorDashboardArray> dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i <dataArrays.size() ; i++) {

            db.execSQL("INSERT INTO " + DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS +
                    " Values('"+dataArrays.get(i).getLearner_id()+"'," +
                    "'"+dataArrays.get(i).getGrant_id()+"'," +
                    "'"+dataArrays.get(i).getLearner_name()+"'," +
                    "'"+dataArrays.get(i).getLearner_status()+"'," +
                    "'"+dataArrays.get(i).getStart_date()+"'," +
                    "'"+dataArrays.get(i).getEnd_date()+"'," +
                    "'"+dataArrays.get(i).getSeta_name()+"'," +
                    "'"+dataArrays.get(i).getManaged_by()+"'," +
                    "'"+dataArrays.get(i).getPast_attendance_register()+"'," +
                    "'"+dataArrays.get(i).getEdit_current_attendance()+"'," +
                    "'"+dataArrays.get(i).getTotal_leave_taken()+"'," +
                    "'"+dataArrays.get(i).getLeave_pending_approval()+"'," +
                    "'"+dataArrays.get(i).getPast_stipend_claim()+"'," +
                    "'"+dataArrays.get(i).getShow_approved_claim_link()+"'," +
                    "'"+dataArrays.get(i).getCurrent_stipend_pending_approval()+"'," +
                    "'"+dataArrays.get(i).getRegistered_workstation()+"'," +
                    "'"+dataArrays.get(i).getShow_workstation_link()+"'," +
                    "'"+dataArrays.get(i).getAssigned_workstation()+"'," +
                    "'"+dataArrays.get(i).getWorkstations_status()+"'," +
                    "'"+dataArrays.get(i).getShow_edit_workstations_link()+"'," +
                    "'"+dataArrays.get(i).getLinked_student_id()+"'," +
                    "'"+dataArrays.get(i).getMonthly_reports_completed()+"'," +
                    "'"+dataArrays.get(i).getSupervisor_comments_pending()+"'," +
                    "'"+dataArrays.get(i).getTraining_program_upload()+"'," +
                    "'"+dataArrays.get(i).getTrainging_doc()+"'," +
                    "'"+dataArrays.get(i).getAlert_count()+"'," +
                    "'"+dataArrays.get(i).getNotes()+"');");

        }

        return 1;
    }

    @Override
    public int update(MentorDashboardArray dataArrays) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_LEARNER_ID                             ,dataArrays.getLearner_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ID                               ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_LEARNER_NAME                           ,dataArrays.getLearner_name());
        contentValues.put(DbSchema.COLUMN_LEARNER_STATUS                         ,dataArrays.getLearner_status());
        contentValues.put(DbSchema.COLUMN_STARTDATE                              ,dataArrays.getStart_date());
        contentValues.put(DbSchema.COLUMN_END_DATE                               ,dataArrays.getEnd_date());
        contentValues.put(DbSchema.COLUMN_SETA_NAME                              ,dataArrays.getSeta_name());
        contentValues.put(DbSchema.COLUMN_MANAGED_BY                             ,dataArrays.getManaged_by());
        contentValues.put(DbSchema.COLUMN_PAST_ATT_REGISTER                      ,dataArrays.getPast_attendance_register());
        contentValues.put(DbSchema.COLUMN_EDIT_CURRENT_ATT                       ,dataArrays.getEdit_current_attendance());
        contentValues.put(DbSchema.COLUMN_TOTAL_LEAVE_TAKEN                      ,dataArrays.getTotal_leave_taken());
        contentValues.put(DbSchema.COLUMN_LEAVE_PENDING_APPROVAL                 ,dataArrays.getLeave_pending_approval());
        contentValues.put(DbSchema.COLUMN_PAST_STIPEND_CLAIM                     ,dataArrays.getPast_stipend_claim());
        contentValues.put(DbSchema.COLUMN_APPROVED_CLAIM_LINK                    ,dataArrays.getShow_approved_claim_link());
        contentValues.put(DbSchema.COLUMN_CURRENT_STIPEND_PENDING_APPROVAL       ,dataArrays.getCurrent_stipend_pending_approval());
        contentValues.put(DbSchema.COLUMN_REGISTERED_WORKSTATION                 ,dataArrays.getRegistered_workstation());
        contentValues.put(DbSchema.COLUMN_SHOW_WORKSTATION_LINK                     ,dataArrays.getShow_workstation_link());
        contentValues.put(DbSchema.COLUMN_ASSIGNED_WORKSTATION                     ,dataArrays.getAssigned_workstation());
        contentValues.put(DbSchema.COLUMN_WORKSTATION_STATUS                     ,dataArrays.getWorkstations_status());
        contentValues.put(DbSchema.COLUMN_SHOW_EDIT_WORKSTATION_LINK             ,dataArrays.getShow_edit_workstations_link());
        contentValues.put(DbSchema.COLUMN_LINKED_STUDENT_ID                      ,dataArrays.getLinked_student_id());
        contentValues.put(DbSchema.COLUMN_MONTHLY_REPORTS_COMPLETED              ,dataArrays.getMonthly_reports_completed());
        contentValues.put(DbSchema.COLUMN_SUPERVISOR_COMMENTS_PENDING            ,dataArrays.getSupervisor_comments_pending());
        contentValues.put(DbSchema.COLUMN_TRAINING_PROGRAM_UPLOAD                ,dataArrays.getTraining_program_upload());
        contentValues.put(DbSchema.COLUMN_TRAINING_DOC                           ,dataArrays.getTrainging_doc());
        contentValues.put(DbSchema.COLUMN_ALERT_COUNT                            ,dataArrays.getAlert_count());
        contentValues.put(DbSchema.COLUMN_NOTES                                  ,dataArrays.getNotes());

        int data = db.update(DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS, contentValues, DbSchema.COLUMN_LEARNER_ID + " = " + dataArrays.getLearner_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS, DbSchema.COLUMN_LEARNER_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public MentorDashboardArray getById(int id) {
        MentorDashboardArray dataArray=null;
        List<MentorDashboardArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_LEARNER_ID                             ,
                DbSchema.COLUMN_GRANT_ID                               ,
                DbSchema.COLUMN_LEARNER_NAME                           ,
                DbSchema.COLUMN_LEARNER_STATUS                         ,
                DbSchema.COLUMN_STARTDATE                              ,
                DbSchema.COLUMN_END_DATE                               ,
                DbSchema.COLUMN_SETA_NAME                              ,
                DbSchema.COLUMN_MANAGED_BY                             ,
                DbSchema.COLUMN_PAST_ATT_REGISTER                      ,
                DbSchema.COLUMN_EDIT_CURRENT_ATT                       ,
                DbSchema.COLUMN_TOTAL_LEAVE_TAKEN                      ,
                DbSchema.COLUMN_LEAVE_PENDING_APPROVAL                 ,
                DbSchema.COLUMN_PAST_STIPEND_CLAIM                     ,
                DbSchema.COLUMN_APPROVED_CLAIM_LINK                    ,
                DbSchema.COLUMN_CURRENT_STIPEND_PENDING_APPROVAL       ,
                DbSchema.COLUMN_REGISTERED_WORKSTATION                 ,
                DbSchema.COLUMN_SHOW_WORKSTATION_LINK                     ,
                DbSchema.COLUMN_ASSIGNED_WORKSTATION                     ,
                DbSchema.COLUMN_WORKSTATION_STATUS                     ,
                DbSchema.COLUMN_SHOW_EDIT_WORKSTATION_LINK             ,
                DbSchema.COLUMN_LINKED_STUDENT_ID                      ,
                DbSchema.COLUMN_MONTHLY_REPORTS_COMPLETED              ,
                DbSchema.COLUMN_SUPERVISOR_COMMENTS_PENDING            ,
                DbSchema.COLUMN_TRAINING_PROGRAM_UPLOAD                ,
                DbSchema.COLUMN_TRAINING_DOC                           ,
                DbSchema.COLUMN_ALERT_COUNT                            ,
                DbSchema.COLUMN_NOTES                                  };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS, columns, DbSchema.COLUMN_LEARNER_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String learner_id                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_ID                             ));
                    String grant_id                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID                               ));
                    String learner_name                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_NAME                           ));
                    String learner_status                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_STATUS                         ));
                    String start_date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STARTDATE                              ));
                    String end_date                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_END_DATE                               ));
                    String seta_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                              ));
                    String managed_by                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MANAGED_BY                             ));
                    String past_attendance_register          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PAST_ATT_REGISTER                      ));
                    String edit_current_attendance           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EDIT_CURRENT_ATT                       ));
                    String total_leave_taken                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TOTAL_LEAVE_TAKEN                      ));
                    String leave_pending_approval            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE_PENDING_APPROVAL                 ));
                    String past_stipend_claim                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PAST_STIPEND_CLAIM                     ));
                    String show_approved_claim_link          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_APPROVED_CLAIM_LINK                    ));
                    String current_stipend_pending_approval  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CURRENT_STIPEND_PENDING_APPROVAL       ));
                    String registered_workstation            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REGISTERED_WORKSTATION                 ));
                    String show_workstation_link               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_WORKSTATION_LINK                     ));
                    String assigned_workstation               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ASSIGNED_WORKSTATION                     ));
                    String workstations_status               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_WORKSTATION_STATUS                     ));
                    String show_edit_workstations_link       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_EDIT_WORKSTATION_LINK             ));
                    String linked_student_id                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LINKED_STUDENT_ID                      ));
                    String monthly_reports_completed         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTHLY_REPORTS_COMPLETED              ));
                    String supervisor_comments_pending       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENTS_PENDING            ));
                    String training_program_upload           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_PROGRAM_UPLOAD                ));
                    String trainging_doc                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_DOC                           ));
                    String alert_count                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ALERT_COUNT                            ));
                    String notes                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTES                                  ));
                    dataArray = new MentorDashboardArray(
                            learner_id,
                            grant_id,
                            learner_name,
                            learner_status,
                            start_date,
                            end_date,
                            seta_name,
                            managed_by,
                            past_attendance_register,
                            edit_current_attendance,
                            total_leave_taken,
                            leave_pending_approval,
                            past_stipend_claim,
                            show_approved_claim_link,
                            current_stipend_pending_approval,
                            registered_workstation,
                            show_workstation_link,
                            assigned_workstation,
                            workstations_status,
                            show_edit_workstations_link,
                            linked_student_id,
                            monthly_reports_completed,
                            supervisor_comments_pending,
                            training_program_upload,
                            trainging_doc,
                            alert_count,
                            notes
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
        dbHelper.truncateTable(DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS);
    }

    @Override
    public List<MentorDashboardArray> getAll() {
        MentorDashboardArray dataArray=null;
        List<MentorDashboardArray> dataArrayList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] columns = {
                DbSchema.COLUMN_LEARNER_ID                             ,
                DbSchema.COLUMN_GRANT_ID                               ,
                DbSchema.COLUMN_LEARNER_NAME                           ,
                DbSchema.COLUMN_LEARNER_STATUS                         ,
                DbSchema.COLUMN_STARTDATE                              ,
                DbSchema.COLUMN_END_DATE                               ,
                DbSchema.COLUMN_SETA_NAME                              ,
                DbSchema.COLUMN_MANAGED_BY                             ,
                DbSchema.COLUMN_PAST_ATT_REGISTER                      ,
                DbSchema.COLUMN_EDIT_CURRENT_ATT                       ,
                DbSchema.COLUMN_TOTAL_LEAVE_TAKEN                      ,
                DbSchema.COLUMN_LEAVE_PENDING_APPROVAL                 ,
                DbSchema.COLUMN_PAST_STIPEND_CLAIM                     ,
                DbSchema.COLUMN_APPROVED_CLAIM_LINK                    ,
                DbSchema.COLUMN_CURRENT_STIPEND_PENDING_APPROVAL       ,
                DbSchema.COLUMN_REGISTERED_WORKSTATION                 ,
                DbSchema.COLUMN_SHOW_WORKSTATION_LINK                     ,
                DbSchema.COLUMN_ASSIGNED_WORKSTATION                     ,
                DbSchema.COLUMN_WORKSTATION_STATUS                     ,
                DbSchema.COLUMN_SHOW_EDIT_WORKSTATION_LINK             ,
                DbSchema.COLUMN_LINKED_STUDENT_ID                      ,
                DbSchema.COLUMN_MONTHLY_REPORTS_COMPLETED              ,
                DbSchema.COLUMN_SUPERVISOR_COMMENTS_PENDING            ,
                DbSchema.COLUMN_TRAINING_PROGRAM_UPLOAD                ,
                DbSchema.COLUMN_TRAINING_DOC                           ,
                DbSchema.COLUMN_ALERT_COUNT                            ,
                DbSchema.COLUMN_NOTES                                  };

        Cursor cursors = db.query(DbSchema.TABLE_MENTOR_DASHBOARD_DETAILS, columns, null, null, null, null, null);

        try {
            if (cursors.moveToFirst()) {
                do{
                    String learner_id                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_ID                             ));
                    String grant_id                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID                               ));
                    String learner_name                      = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_NAME                           ));
                    String learner_status                    = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_STATUS                         ));
                    String start_date                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_STARTDATE                              ));
                    String end_date                          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_END_DATE                               ));
                    String seta_name                         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETA_NAME                              ));
                    String managed_by                        = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MANAGED_BY                             ));
                    String past_attendance_register          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PAST_ATT_REGISTER                      ));
                    String edit_current_attendance           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EDIT_CURRENT_ATT                       ));
                    String total_leave_taken                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TOTAL_LEAVE_TAKEN                      ));
                    String leave_pending_approval            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEAVE_PENDING_APPROVAL                 ));
                    String past_stipend_claim                = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PAST_STIPEND_CLAIM                     ));
                    String show_approved_claim_link          = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_APPROVED_CLAIM_LINK                    ));
                    String current_stipend_pending_approval  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CURRENT_STIPEND_PENDING_APPROVAL       ));
                    String registered_workstation            = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REGISTERED_WORKSTATION                 ));
                    String show_workstation_link               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_WORKSTATION_LINK                     ));
                    String assigned_workstation               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ASSIGNED_WORKSTATION                     ));
                    String workstations_status               = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_WORKSTATION_STATUS                     ));
                    String show_edit_workstations_link       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_EDIT_WORKSTATION_LINK             ));
                    String linked_student_id                 = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LINKED_STUDENT_ID                      ));
                    String monthly_reports_completed         = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MONTHLY_REPORTS_COMPLETED              ));
                    String supervisor_comments_pending       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISOR_COMMENTS_PENDING            ));
                    String training_program_upload           = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_PROGRAM_UPLOAD                ));
                    String trainging_doc                     = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_DOC                           ));
                    String alert_count                       = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ALERT_COUNT                            ));
                    String notes                             = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_NOTES                                  ));
                    dataArray = new MentorDashboardArray(
                            learner_id,
                            grant_id,
                            learner_name,
                            learner_status,
                            start_date,
                            end_date,
                            seta_name,
                            managed_by,
                            past_attendance_register,
                            edit_current_attendance,
                            total_leave_taken,
                            leave_pending_approval,
                            past_stipend_claim,
                            show_approved_claim_link,
                            current_stipend_pending_approval,
                            registered_workstation,
                            show_workstation_link,
                            assigned_workstation,
                            workstations_status,
                            show_edit_workstations_link,
                            linked_student_id,
                            monthly_reports_completed,
                            supervisor_comments_pending,
                            training_program_upload,
                            trainging_doc,
                            alert_count,
                            notes
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
