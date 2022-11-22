package za.co.sacpo.grant.xCubeLib.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public class DashboardDataArrayAdapter implements DashboardDataArrayDAO{

    private DbHelper databaseHelper;
    private Context context;


    public DashboardDataArrayAdapter(Context context){
        databaseHelper = DbHelper.getInstance(context);
    }


           /* COLUMN_GRANT_ID
            COLUMN_LEARNER_NAME
            COLUMN_LEARNER_STATUS
            COLUMN_START_DATE
            COLUMN_END_DATE
            COLUMN_BANK_NAME
            COLUMN_SHOW_ADDBANK_BTN
            COLUMN_SETANAME
            COLUMN_MANAGEBY
            COLUMN_EMPLOYERNAME
            COLUMN_SUPERVISORNAME
            COLUMN_ATT_STATUS
            COLUMN_ISEDIT_ATTENDANCE
            COLUMN_SHOW_ATTENDANCELINK
            COLUMN_SHOW_ATTENDANCECOLOR
            COLUMN_ATTENDANCE_STATUS
            COLUMN_CURRENT_CLAIM_STATUS
            COLUMN_VIEW_ATTENDANCELINK
            COLUMN_PASTMONTHCOUNT
            COLUMN_CLAIM_COLOR
            COLUMN_PASTCLAIM_BTN
            COLUMN_WORKSTATION_BTN
            COLUMN_DEPT_NAME
            COLUMN_COMPLETED_REPORTCOUNT
            COLUMN_REPORTOVERDUE_COUNT
            COLUMN_TRAINING_PROGRAM
            COLUMN_TRAINING_PROGRAM_STATUS*/


    @Override
    public long insert(DashboardDataArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_ID,dataArrays.getS_s_g_student_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ID ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_LEARNER_NAME ,dataArrays.getLearner_name());
        contentValues.put(DbSchema.COLUMN_LEARNER_STATUS ,dataArrays.getLearner_status());
        contentValues.put(DbSchema.COLUMN_START_DATE  ,dataArrays.getStartDate());
        contentValues.put(DbSchema.COLUMN_END_DATE  ,dataArrays.getEnd_Date());
        contentValues.put(DbSchema.COLUMN_BANK_NAME,dataArrays.getBankName());
        contentValues.put(DbSchema.COLUMN_SHOW_ADDBANK_BTN ,dataArrays.getShow_add_bank_btn());
        contentValues.put(DbSchema.COLUMN_SETANAME,dataArrays.getSeta_ame());
        contentValues.put(DbSchema.COLUMN_MANAGEBY ,dataArrays.getManagedBy());
        contentValues.put(DbSchema.COLUMN_EMPLOYERNAME  ,dataArrays.getEmployer_name());
        contentValues.put(DbSchema.COLUMN_SUPERVISORNAME  ,dataArrays.getSupervisor_name());
        contentValues.put(DbSchema.COLUMN_ATT_STATUS  ,dataArrays.getAttStatus());
        contentValues.put(DbSchema.COLUMN_ISEDIT_ATTENDANCE ,dataArrays.getS_s_g_is_edit_attendance());
        contentValues.put(DbSchema.COLUMN_SHOW_ATTENDANCELINK  ,dataArrays.getShow_attendance_link());
        contentValues.put(DbSchema.COLUMN_SHOW_ATTENDANCECOLOR ,dataArrays.getShow_attendance_color());
        contentValues.put(DbSchema.COLUMN_ATTENDANCE_STATUS  ,dataArrays.getAtendance_status());
        contentValues.put(DbSchema.COLUMN_CURRENT_CLAIM_STATUS ,dataArrays.getView_attendance_link());
        contentValues.put(DbSchema.COLUMN_VIEW_ATTENDANCELINK ,dataArrays.getPastmonthCount());
        contentValues.put(DbSchema.COLUMN_PASTMONTHCOUNT  ,dataArrays.getCurrent_claim_status());
        contentValues.put(DbSchema.COLUMN_CLAIM_COLOR   ,dataArrays.getClaim_color());
        contentValues.put(DbSchema.COLUMN_PASTCLAIM_BTN   ,dataArrays.getPast_claim_btn());
        contentValues.put(DbSchema.COLUMN_WORKSTATION_BTN   ,dataArrays.getWorkstation_btn());
        contentValues.put(DbSchema.COLUMN_DEPT_NAME   ,dataArrays.getDeptName());
        contentValues.put(DbSchema.COLUMN_COMPLETED_REPORTCOUNT ,dataArrays.getCompleted_report_count());
        contentValues.put(DbSchema.COLUMN_REPORTOVERDUE_COUNT  ,dataArrays.getReportOverDue_count());
        contentValues.put(DbSchema.COLUMN_TRAINING_PROGRAM   ,dataArrays.getTraining_program());
        contentValues.put(DbSchema.COLUMN_TRAINING_PROGRAM_STATUS,dataArrays.getTraining_program_status());

        long data =  db.insert(DbSchema.TABLE_DASHBOARD_DATA, null, contentValues);
        //db.close();
        return data;
    }

    @Override
    public int update(DashboardDataArray dataArrays) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbSchema.COLUMN_S_ID,dataArrays.getS_s_g_student_id());
        contentValues.put(DbSchema.COLUMN_GRANT_ID ,dataArrays.getGrant_id());
        contentValues.put(DbSchema.COLUMN_LEARNER_NAME ,dataArrays.getLearner_name());
        contentValues.put(DbSchema.COLUMN_LEARNER_STATUS ,dataArrays.getLearner_status());
        contentValues.put(DbSchema.COLUMN_START_DATE  ,dataArrays.getStartDate());
        contentValues.put(DbSchema.COLUMN_END_DATE  ,dataArrays.getEnd_Date());
        contentValues.put(DbSchema.COLUMN_BANK_NAME,dataArrays.getBankName());
        contentValues.put(DbSchema.COLUMN_SHOW_ADDBANK_BTN ,dataArrays.getShow_add_bank_btn());
        contentValues.put(DbSchema.COLUMN_SETANAME,dataArrays.getSeta_ame());
        contentValues.put(DbSchema.COLUMN_MANAGEBY ,dataArrays.getManagedBy());
        contentValues.put(DbSchema.COLUMN_EMPLOYERNAME  ,dataArrays.getEmployer_name());
        contentValues.put(DbSchema.COLUMN_SUPERVISORNAME  ,dataArrays.getSupervisor_name());
        contentValues.put(DbSchema.COLUMN_ATT_STATUS  ,dataArrays.getAttStatus());
        contentValues.put(DbSchema.COLUMN_ISEDIT_ATTENDANCE ,dataArrays.getS_s_g_is_edit_attendance());
        contentValues.put(DbSchema.COLUMN_SHOW_ATTENDANCELINK  ,dataArrays.getShow_attendance_link());
        contentValues.put(DbSchema.COLUMN_SHOW_ATTENDANCECOLOR ,dataArrays.getShow_attendance_color());
        contentValues.put(DbSchema.COLUMN_ATTENDANCE_STATUS  ,dataArrays.getAtendance_status());
        contentValues.put(DbSchema.COLUMN_CURRENT_CLAIM_STATUS ,dataArrays.getView_attendance_link());
        contentValues.put(DbSchema.COLUMN_VIEW_ATTENDANCELINK ,dataArrays.getPastmonthCount());
        contentValues.put(DbSchema.COLUMN_PASTMONTHCOUNT  ,dataArrays.getCurrent_claim_status());
        contentValues.put(DbSchema.COLUMN_CLAIM_COLOR   ,dataArrays.getClaim_color());
        contentValues.put(DbSchema.COLUMN_PASTCLAIM_BTN   ,dataArrays.getPast_claim_btn());
        contentValues.put(DbSchema.COLUMN_WORKSTATION_BTN   ,dataArrays.getWorkstation_btn());
        contentValues.put(DbSchema.COLUMN_DEPT_NAME   ,dataArrays.getDeptName());
        contentValues.put(DbSchema.COLUMN_COMPLETED_REPORTCOUNT ,dataArrays.getCompleted_report_count());
        contentValues.put(DbSchema.COLUMN_REPORTOVERDUE_COUNT  ,dataArrays.getReportOverDue_count());
        contentValues.put(DbSchema.COLUMN_TRAINING_PROGRAM   ,dataArrays.getTraining_program());
        contentValues.put(DbSchema.COLUMN_TRAINING_PROGRAM_STATUS,dataArrays.getTraining_program_status());

        int data = db.update(DbSchema.TABLE_DASHBOARD_DATA, contentValues, DbSchema.COLUMN_S_ID + " = " + dataArrays.getS_s_g_student_id(), null);
        //db.close();
        return data;
    }

    @Override
    public int delete(int id) {

        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int data = db.delete(DbSchema.TABLE_DASHBOARD_DATA, DbSchema.COLUMN_S_ID + " = " + id, null);
        //db.close();
        return data;
    }

    @Override
    public DashboardDataArray getById(int id) {

        DashboardDataArray dataArray = null;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();


        String[] columns = { DbSchema.COLUMN_S_ID,DbSchema.COLUMN_GRANT_ID ,DbSchema.COLUMN_LEARNER_NAME ,
                DbSchema.COLUMN_LEARNER_STATUS ,DbSchema.COLUMN_START_DATE  ,DbSchema.COLUMN_END_DATE  ,
                DbSchema.COLUMN_BANK_NAME,DbSchema.COLUMN_SHOW_ADDBANK_BTN ,DbSchema.COLUMN_SETANAME,
                DbSchema.COLUMN_MANAGEBY ,DbSchema.COLUMN_EMPLOYERNAME  ,DbSchema.COLUMN_SUPERVISORNAME  ,
                DbSchema.COLUMN_ATT_STATUS  , DbSchema.COLUMN_ISEDIT_ATTENDANCE ,DbSchema.COLUMN_SHOW_ATTENDANCELINK  ,
                DbSchema.COLUMN_SHOW_ATTENDANCECOLOR ,DbSchema.COLUMN_ATTENDANCE_STATUS  ,
                DbSchema.COLUMN_CURRENT_CLAIM_STATUS , DbSchema.COLUMN_VIEW_ATTENDANCELINK ,
                DbSchema.COLUMN_PASTMONTHCOUNT  , DbSchema.COLUMN_CLAIM_COLOR   ,  DbSchema.COLUMN_PASTCLAIM_BTN   ,
                DbSchema.COLUMN_WORKSTATION_BTN   , DbSchema.COLUMN_DEPT_NAME   ,DbSchema.COLUMN_COMPLETED_REPORTCOUNT ,
                DbSchema.COLUMN_REPORTOVERDUE_COUNT  ,DbSchema.COLUMN_TRAINING_PROGRAM   ,DbSchema.COLUMN_TRAINING_PROGRAM_STATUS};
        Cursor cursors = db.query(DbSchema.TABLE_DASHBOARD_DATA, columns, DbSchema.COLUMN_S_ID + " = " + id, null, null, null, null);

        try {
            if (cursors.moveToNext()) {
                String s_s_g_student_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_S_ID));
                String grant_id = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_GRANT_ID               ));
                String learner_name  = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_NAME           ));
                String learner_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_LEARNER_STATUS         ));
                String startDate = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_START_DATE             ));
                String end_Date = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_END_DATE               ));
                String bankName = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_BANK_NAME              ));
                String show_add_bank_btn = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_ADDBANK_BTN       ));
                String seta_ame = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SETANAME               ));
                String managedBy = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_MANAGEBY               ));
                String employer_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_EMPLOYERNAME           ));
                String supervisor_name = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SUPERVISORNAME         ));
                String attStatus = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATT_STATUS             ));
                String s_s_g_is_edit_attendance = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ISEDIT_ATTENDANCE      ));
                String show_attendance_link = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_ATTENDANCELINK    ));
                String show_attendance_color = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_SHOW_ATTENDANCECOLOR   ));
                String atendance_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_ATTENDANCE_STATUS      ));
                String view_attendance_link = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CURRENT_CLAIM_STATUS   ));
                String pastmonthCount = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_VIEW_ATTENDANCELINK    ));
                String current_claim_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PASTMONTHCOUNT         ));
                String claim_color = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_CLAIM_COLOR            ));
                String past_claim_btn = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_PASTCLAIM_BTN          ));
                String workstation_btn = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_WORKSTATION_BTN        ));
                String deptName = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_DEPT_NAME              ));
                String completed_report_count = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_COMPLETED_REPORTCOUNT  ));
                String reportOverDue_count = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_REPORTOVERDUE_COUNT    ));
                String training_program = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_PROGRAM       ));
                String training_program_status = cursors.getString(cursors.getColumnIndex(DbSchema.COLUMN_TRAINING_PROGRAM_STATUS));


        dataArray = new DashboardDataArray(s_s_g_student_id , grant_id , learner_name, learner_status , startDate  ,  end_Date  ,
        bankName,show_add_bank_btn,seta_ame ,managedBy,employer_name,supervisor_name,
        attStatus,s_s_g_is_edit_attendance, show_attendance_link,show_attendance_color,atendance_status,
        view_attendance_link,pastmonthCount,current_claim_status,claim_color,past_claim_btn,
        workstation_btn,deptName,completed_report_count,reportOverDue_count,training_program,
        training_program_status);


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
        databaseHelper.truncateTable(DbSchema.TABLE_DASHBOARD_DATA);
    }

    @Override
    public List<DashboardDataArray> getAll() {
        return null;
    }
       /*DbSchema.COLUMN_S_ID,DbSchema.COLUMN_GRANT_ID ,DbSchema.COLUMN_LEARNER_NAME ,
    DbSchema.COLUMN_LEARNER_STATUS ,DbSchema.COLUMN_START_DATE  ,DbSchema.COLUMN_END_DATE  ,
    DbSchema.COLUMN_BANK_NAME,DbSchema.COLUMN_SHOW_ADDBANK_BTN ,DbSchema.COLUMN_SETANAME,
    DbSchema.COLUMN_MANAGEBY ,DbSchema.COLUMN_EMPLOYERNAME  ,DbSchema.COLUMN_SUPERVISORNAME  ,
    DbSchema.COLUMN_ATT_STATUS  , DbSchema.COLUMN_ISEDIT_ATTENDANCE ,DbSchema.COLUMN_SHOW_ATTENDANCELINK  ,
    DbSchema.COLUMN_SHOW_ATTENDANCECOLOR ,DbSchema.COLUMN_ATTENDANCE_STATUS  ,
    DbSchema.COLUMN_CURRENT_CLAIM_STATUS , DbSchema.COLUMN_VIEW_ATTENDANCELINK ,
    DbSchema.COLUMN_PASTMONTHCOUNT  , DbSchema.COLUMN_CLAIM_COLOR   ,  DbSchema.COLUMN_PASTCLAIM_BTN   ,
    DbSchema.COLUMN_WORKSTATION_BTN   , DbSchema.COLUMN_DEPT_NAME   ,DbSchema.COLUMN_COMPLETED_REPORTCOUNT ,
    DbSchema.COLUMN_REPORTOVERDUE_COUNT  ,DbSchema.COLUMN_TRAINING_PROGRAM   ,DbSchema.COLUMN_TRAINING_PROGRAM_STATUS

 */














}
