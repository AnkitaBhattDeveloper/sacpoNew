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

/*table bank details*/
public static final String TABLE_BANK_DETAILS="tbl_bank_details";
    public static final String COLUMN_ID = "id", COLUMN_BANKNAME="bank_name",COLUMN_INITIAL_NAME="initial_name",
            COLUMN_ACCOUNT_NO="account_no",COLUMN_BRANCHCODE="branch_code",COLUMN_B_D_STATUS="b_d_status",COLUMN_B_D_SURNAME = "b_d_surname",
            COLUMN_ACCOUNT_TYPE="account_type",COLUMN_U_B_ID="u_b_id",COLUMN_B_D_A_TYPE="b_d_a_type",COLUMN_BRANCHID = "b_d_u_branch_id";

    public static final String CREATE_TABLE_BANK_DETAILS="CREATE TABLE "+TABLE_BANK_DETAILS+"( "+COLUMN_ID+" INTEGER, "+
            COLUMN_BANKNAME+" VARCHAR(50) , "+COLUMN_INITIAL_NAME+" VARCHAR(50) , "+COLUMN_ACCOUNT_NO+" VARCHAR(20), "+
            COLUMN_BRANCHCODE+" VARCHAR(20), "+COLUMN_B_D_STATUS+" VARCHAR(20), "+COLUMN_B_D_SURNAME+" VARCHAR(20), "
            +COLUMN_ACCOUNT_TYPE+" VARCHAR(50), "+COLUMN_U_B_ID+" VARCHAR(20), "+COLUMN_B_D_A_TYPE+" VARCHAR(50), "+
            COLUMN_BRANCHID+" VARCHAR(50)); ";


    public static final String DROP_TABLE_BANK_DETAILS= "DROP TABLE IF EXISTS "+TABLE_BANK_DETAILS;
    public static final String EMPTY_TABLE_BANK_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_BANK_DETAILS;

    /*table grant details(ManageBy)*/
    public static final String TABLE_GRANT_DETAILS="tbl_grant_details";
    public static final String
            COLUMN_UID = "u_id",
            COLUMN_GRANTID="grant_id",
            COLUMN_GRANT_SDATE="s_s_g_grant_start_date",
            COLUMN_GRANT_EDATE="s_s_g_grant_end_date",
            COLUMN_GRANT_NAME="grant_name",
            COLUMN_MENTOR_NAME="mentor_name",
            COLUMN_MENTORID = "mentor_id",
            COLUMN_LEM_NAME="lem_name",
            COLUMN_LEM_ID="lem_id",
            COLUMN_HOST_EMP_NAME="host_emp_name",
            COLUMN_SETA_ID = "seta_id",
            COLUMN_SETA_NAME = "seta_name",
            COLUMN_SETA_MANAGER_ID = "seta_manager_id",
            COLUMN_SETAMANAGER_NAME = "seta_manager_name",
            COLUMN_ADMIN_ID = "grant_admin_id",
            COLUMN_GRANT_ADMIN_NAME = "grant_admin",
            COLUMN_GRANTADMIN_EMAIL = "grant_admin_email",
            COLUMN_GRANTADMIN_CELL = "grant_admin_cell",
            COLUMN_SETAMANAGER_EMAIL = "seta_manager_email",
            COLUMN_SETAMANAGER_CELL = "seta_manager_cell";

    public static final String CREATE_TABLE_GRANT_DETAILS="CREATE TABLE "+TABLE_GRANT_DETAILS+"( "+COLUMN_UID+" INTEGER, "+
            COLUMN_GRANTID+" VARCHAR(50) , "+COLUMN_GRANT_SDATE+" VARCHAR(50) , "+COLUMN_GRANT_EDATE+" VARCHAR(20), "+
            COLUMN_GRANT_NAME+" VARCHAR(20), "+COLUMN_MENTOR_NAME+" VARCHAR(20), "+COLUMN_MENTORID+" VARCHAR(20), "+COLUMN_LEM_NAME+" VARCHAR(20), "
            +COLUMN_LEM_ID+" VARCHAR(50), "+COLUMN_HOST_EMP_NAME+" VARCHAR(20), "+COLUMN_SETA_ID+" VARCHAR(50), "+
            COLUMN_SETA_NAME+" VARCHAR(50), "+COLUMN_SETA_MANAGER_ID+" VARCHAR(50), "+COLUMN_SETAMANAGER_NAME+" VARCHAR(50), "+
            COLUMN_ADMIN_ID+" VARCHAR(50), "+COLUMN_GRANT_ADMIN_NAME+" VARCHAR(50), "+COLUMN_GRANTADMIN_EMAIL+" VARCHAR(50), "+
            COLUMN_GRANTADMIN_CELL+" VARCHAR(50), "+COLUMN_SETAMANAGER_EMAIL+" VARCHAR(50), "+
            COLUMN_SETAMANAGER_CELL+" VARCHAR(50)); ";


    public static final String DROP_TABLE_GRANT_DETAILS= "DROP TABLE IF EXISTS "+TABLE_GRANT_DETAILS;
    public static final String EMPTY_TABLE_GRANT_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_GRANT_DETAILS;

    /*table Supervisor details*/
    public static final String TABLE_SUPERVISOR_DETAILS="tbl_supervisor_details";
    public static final String
            COLUMN_M_ID             ="id",
            COLUMN_M_NAME           ="name",
            COLUMN_M_EMAIL          ="email",
            COLUMN_M_MOBILE         ="mobile",
            COLUMN_M_U_DEPARTMENT   ="u_department",
            COLUMN_M_U_DESIGNATION  ="u_designation",
            COLUMN_OFC_NO           ="ofc_no",
            COLUMN_EMPLOYER_NAME    ="employer",
            COLUMN_EMPLOYER_SDL     ="employer_sdl";

    public static final String CREATE_TABLE_SUPERVISOR_DETAILS="CREATE TABLE "+TABLE_SUPERVISOR_DETAILS+"( "+COLUMN_M_ID+" INTEGER, "+
            COLUMN_M_NAME+" VARCHAR(50) , "+COLUMN_M_EMAIL+" VARCHAR(50) , "+COLUMN_M_MOBILE+" VARCHAR(20), "+
            COLUMN_M_U_DEPARTMENT+" VARCHAR(20), "+COLUMN_M_U_DESIGNATION+" VARCHAR(20), "+COLUMN_OFC_NO+" VARCHAR(20), "
            +COLUMN_EMPLOYER_NAME+" VARCHAR(50), "+ COLUMN_EMPLOYER_SDL+" VARCHAR(50)); ";


    public static final String DROP_TABLE_SUPERVISOR_DETAILS= "DROP TABLE IF EXISTS "+TABLE_SUPERVISOR_DETAILS;
    public static final String EMPTY_TABLE_SUPERVISOR_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_SUPERVISOR_DETAILS;




}




