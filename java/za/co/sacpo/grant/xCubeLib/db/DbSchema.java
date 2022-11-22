package za.co.sacpo.grant.xCubeLib.db;
public class DbSchema {
    public static final String DATABASE_NAME="olums_seta_grant.db";
    public static final int DATABASE_VERSION = 5;
    public static final String TABLE_STATIC_LABELS="tbl_static_labels";
    public static final String COLUMN_KEY_ID = "s_l_id", COLUMN_CONTENT_NAME="s_l_name",COLUMN_CONTENT_VALUE = "s_l_value";
    public static final String CREATE_TABLE_STATIC_LABELS="CREATE TABLE "+TABLE_STATIC_LABELS+"( "+COLUMN_KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_CONTENT_NAME+" VARCHAR(255), "+COLUMN_CONTENT_VALUE+" TEXT);";
    public static final String DROP_TABLE_STATIC_LABELS= "DROP TABLE IF EXISTS "+TABLE_STATIC_LABELS;
    public static final String TRUNCATE_TABLE_LABELS= "TRUNCATE TABLE IF EXISTS "+TABLE_STATIC_LABELS;

    public static final String TABLE_MASTER_ARRAYS="tbl_master_arrays";
    public static final String COLUMN_M_A_ID = "m_key_id", COLUMN_M_A_NAME="m_name",COLUMN_M_A_SERVER_ID="m_id",COLUMN_M_A_TYPE="m_type";
    public static final String CREATE_TABLE_MASTER_ARRAYS="CREATE TABLE "+TABLE_MASTER_ARRAYS+"( "+COLUMN_M_A_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_M_A_SERVER_ID+" INTEGER(11), "+COLUMN_M_A_NAME+" VARCHAR(255) ,"+COLUMN_M_A_TYPE+" INTEGER(2));";
    public static final String DROP_TABLE_MASTER_ARRAYS= "DROP TABLE IF EXISTS "+TABLE_MASTER_ARRAYS;
    public static final String EMPTY_TABLE_MASTER_ARRAYS= "TRUNCATE TABLE IF EXISTS "+TABLE_MASTER_ARRAYS;


    public static final String TABLE_STUDENTS="tbl_students";
    public static final String COLUMN_S_ID = "s_key_id", COLUMN_S_NAME="s_name",COLUMN_S_SERVER_ID="s_id",COLUMN_S_TYPE="s_type";
    public static final String CREATE_TABLE_STUDENTS="CREATE TABLE "+TABLE_STUDENTS+"( "+COLUMN_S_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_S_SERVER_ID+" INTEGER(11), "+COLUMN_S_NAME+" VARCHAR(255) ,"+COLUMN_S_TYPE+" INTEGER(2));";
    public static final String DROP_TABLE_STUDENTS= "DROP TABLE IF EXISTS "+TABLE_STUDENTS;
    public static final String EMPTY_TABLE_STUDENTS= "TRUNCATE TABLE IF EXISTS "+TABLE_STUDENTS;


    public static final String TABLE_WORKX="tbl_works";
    public static final String COLUMN_W_ID = "w_key_id", COLUMN_W_NAME="w_name",COLUMN_W_SERVER_ID="w_id",COLUMN_W_TYPE="w_type";
    public static final String CREATE_TABLE_WORKX="CREATE TABLE "+TABLE_WORKX+"( "+COLUMN_W_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_W_SERVER_ID+" INTEGER(11), "+COLUMN_W_NAME+" VARCHAR(255) ,"+COLUMN_W_TYPE+" INTEGER(2));";
    public static final String DROP_TABLE_WORKX= "DROP TABLE IF EXISTS "+TABLE_WORKX;
    public static final String EMPTY_TABLE_WORKX= "TRUNCATE TABLE IF EXISTS "+TABLE_WORKX;


    public static final String TABLE_HELP_ARRAYS="tbl_help_arrays";
    public static final String COLUMN_H_ID = "h_key_id", COLUMN_H_TITLE="h_title",COLUMN_H_SERVER_ID="h_id",COLUMN_H_TYPE="h_type",COLUMN_H_CONTENT="h_content",COLUMN_H_ACTIVITY="h_activity";
    public static final String CREATE_TABLE_HELP_ARRAYS="CREATE TABLE "+TABLE_HELP_ARRAYS+"( "+COLUMN_H_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
            COLUMN_H_SERVER_ID+" INTEGER(11), "+COLUMN_H_TITLE+" VARCHAR(255) , "+COLUMN_H_CONTENT+" TEXT , "+COLUMN_H_TYPE+" INTEGER(2), "+COLUMN_H_ACTIVITY+" VARCHAR(8));";
    public static final String DROP_TABLE_HELP_ARRAYS= "DROP TABLE IF EXISTS "+TABLE_HELP_ARRAYS;
    public static final String EMPTY_TABLE_HELP_ARRAYS= "TRUNCATE TABLE IF EXISTS "+TABLE_HELP_ARRAYS;


/*table dashboard*/
    public static final String TABLE_DASHBOARD_DATA="tbl_dashboard_data";
    public static final String COLUMN_STUDENT_ID = "s_s_g_student_id", COLUMN_GRANT_ID="grant_id",COLUMN_LEARNER_NAME="learner_name",
            COLUMN_LEARNER_STATUS="learner_status",COLUMN_START_DATE="startDate",COLUMN_END_DATE="end_Date",COLUMN_BANK_NAME = "bankName",
            COLUMN_SHOW_ADDBANK_BTN="show_add_bank_btn",COLUMN_SETANAME="seta_ame",COLUMN_MANAGEBY="managedBy",COLUMN_EMPLOYERNAME = "employer_name",
            COLUMN_SUPERVISORNAME="supervisor_name",COLUMN_ATT_STATUS="attStatus",COLUMN_ISEDIT_ATTENDANCE="s_s_g_is_edit_attendance",
            COLUMN_SHOW_ATTENDANCELINK = "show_attendance_link", COLUMN_SHOW_ATTENDANCECOLOR="show_attendance_color",COLUMN_ATTENDANCE_STATUS="atendance_status",
            COLUMN_VIEW_ATTENDANCELINK="view_attendance_link",COLUMN_PASTMONTHCOUNT="pastmonthCount",COLUMN_CURRENT_CLAIM_STATUS="current_claim_status",
            COLUMN_CLAIM_COLOR = "claim_color", COLUMN_PASTCLAIM_BTN="past_claim_btn",COLUMN_WORKSTATION_BTN="workstation_btn",
            COLUMN_DEPT_NAME="deptName",COLUMN_COMPLETED_REPORTCOUNT="completed_report_count",COLUMN_REPORTOVERDUE_COUNT="reportOverDue_count",
            COLUMN_TRAINING_PROGRAM="training_program",COLUMN_TRAINING_PROGRAM_STATUS="training_program_status";

    public static final String CREATE_TABLE_DASHBOARD_DATA="CREATE TABLE "+TABLE_DASHBOARD_DATA+"( "+COLUMN_STUDENT_ID+" INTEGER, "+
            COLUMN_GRANT_ID+" INTEGER , "+COLUMN_LEARNER_NAME+" VARCHAR(50) , "+COLUMN_LEARNER_STATUS+" VARCHAR(20), "+
            COLUMN_START_DATE+" VARCHAR(20), "+COLUMN_END_DATE+" VARCHAR(20), "
            +COLUMN_BANK_NAME+" VARCHAR(50), "+COLUMN_SHOW_ADDBANK_BTN+" VARCHAR(20), "+COLUMN_SETANAME+" VARCHAR(50), "
            +COLUMN_MANAGEBY+" VARCHAR(50), "+COLUMN_EMPLOYERNAME+" VARCHAR(50), "+COLUMN_SUPERVISORNAME+" VARCHAR(50), "
            +COLUMN_ATT_STATUS+" VARCHAR(20), "+COLUMN_ISEDIT_ATTENDANCE+" VARCHAR(20), "+COLUMN_SHOW_ATTENDANCELINK+" VARCHAR(100), "
            +COLUMN_SHOW_ATTENDANCECOLOR+" VARCHAR(20), "+COLUMN_ATTENDANCE_STATUS+" VARCHAR(20), "+COLUMN_CURRENT_CLAIM_STATUS+" VARCHAR(20), "+
            COLUMN_VIEW_ATTENDANCELINK+" VARCHAR(100), "+COLUMN_PASTMONTHCOUNT+" VARCHAR(20), "+COLUMN_CLAIM_COLOR+" VARCHAR(20), "+
            COLUMN_PASTCLAIM_BTN+" VARCHAR(20), "+COLUMN_WORKSTATION_BTN+" VARCHAR(20), "+COLUMN_DEPT_NAME+" VARCHAR(20), "+
            COLUMN_COMPLETED_REPORTCOUNT+" VARCHAR(20), "+COLUMN_REPORTOVERDUE_COUNT+" VARCHAR(20), "+COLUMN_TRAINING_PROGRAM+" VARCHAR(20), "+
            COLUMN_TRAINING_PROGRAM_STATUS+" VARCHAR(8));";


    public static final String DROP_TABLE_DASHBOARD_DATA= "DROP TABLE IF EXISTS "+TABLE_DASHBOARD_DATA;
    public static final String EMPTY_TABLE_DASHBOARD_DATA= "TRUNCATE TABLE IF EXISTS "+TABLE_DASHBOARD_DATA;



}
