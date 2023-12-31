package za.co.sacpo.grantportal.xCubeLib.db;
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

    /*table Att Stats details*/
    public static final String TABLE_ATTSTATS_DETAILS="tbl_attstats_details";
    public static final String
            /*COLUMN_GRANT_ID             ="grant_id"*/
            COLUMN_ST_ID                ="student_id",
            COLUMN_MONTH                ="month",
            COLUMN_YEAR                 ="year",
            COLUMN_DATE                 ="date",
            COLUMN_ATT_DAYS             ="attendance_days",
            COLUMN_ANUAL_LEAVE          ="annual_leave",
            COLUMN_SICK_LEAVE           ="sick_leave",
            COLUMN_O_PAID_LEAVE         ="other_paid_leave",
            COLUMN_UNPAID_LEAVE         ="unpaid_leave",
            COLUMN_SUPERVISOR_COMMENT   ="supervisor_comment",
            COLUMN_DWNLD_ATTENDANCE     ="download_attendance",
            COLUMN_DWNLD_ATT_LINK       ="download_attendance_link",
            COLUMN_SUPERVISOR_APPROVAL  ="supervisor_approval",
            COLUMN_SUPERVISOR_COMMENT_LINK ="supervisor_comment_link";

    public static final String CREATE_TABLE_ATTSTATS_DETAILS="CREATE TABLE "+TABLE_ATTSTATS_DETAILS+"( "+COLUMN_GRANT_ID+" INTEGER, "+
            COLUMN_ST_ID+" VARCHAR(50) , "+COLUMN_MONTH+" VARCHAR(50) , "+COLUMN_YEAR+" VARCHAR(20), "+
            COLUMN_DATE+" VARCHAR(20), "+COLUMN_ATT_DAYS+" VARCHAR(20), "+COLUMN_ANUAL_LEAVE+" VARCHAR(20), "
            +COLUMN_SICK_LEAVE+" VARCHAR(50), "+COLUMN_O_PAID_LEAVE+" VARCHAR(50), "+
            COLUMN_UNPAID_LEAVE+" VARCHAR(50), "+COLUMN_SUPERVISOR_COMMENT+" VARCHAR(50), "+
            COLUMN_DWNLD_ATTENDANCE+" VARCHAR(50), "+COLUMN_DWNLD_ATT_LINK+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_APPROVAL+" VARCHAR(50), "+COLUMN_SUPERVISOR_COMMENT_LINK+" VARCHAR(50)); ";


    public static final String DROP_TABLE_ATTSTATS_DETAILS= "DROP TABLE IF EXISTS "+TABLE_ATTSTATS_DETAILS;
    public static final String EMPTY_TABLE_ATTSTATS_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_ATTSTATS_DETAILS;

    /*table attendance list details*/
    public static final String TABLE_ATTLIST_DETAILS="tbl_attlist_details";
    public static final String

            COLUMN_S_A_ID               ="s_a_id",
    /*COLUMN_DATE                ="date",*/
    COLUMN_DAY                  ="day",
            COLUMN_SIGNIN_TIME          ="sign_in_time",
            COLUMN_SIGNOUT_TIME         ="sign_out_time",
            COLUMN_HRS_WORKED           ="hours_worked",
            COLUMN_ATTE_STATUS          ="attendance_status",
            COLUMN_DISTANCE_WORKSTATION ="distance_from_workstation";


    public static final String CREATE_TABLE_ATTLIST_DETAILS="CREATE TABLE "+TABLE_ATTLIST_DETAILS+"( "+COLUMN_S_A_ID+" INTEGER, "+
            COLUMN_DATE+" VARCHAR(50) , "+COLUMN_DAY+" VARCHAR(50) , "+COLUMN_SIGNIN_TIME+" VARCHAR(50), "+
            COLUMN_SIGNOUT_TIME+" VARCHAR(50), "+COLUMN_HRS_WORKED+" VARCHAR(50), "+
            COLUMN_ATTE_STATUS+" VARCHAR(50), "+COLUMN_DISTANCE_WORKSTATION+" VARCHAR(50)); ";


    public static final String DROP_TABLE_ATTLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_ATTLIST_DETAILS;
    public static final String EMPTY_TABLE_ATTLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_ATTLIST_DETAILS;

    /*table populate attendance list details*/
    public static final String TABLE_POPULATE_ATTLIST_DETAILS="tbl_populate_attlist_details";
    public static final String

            // COLUMN_S_A_ID                 ="s_a_id",
            COLUMN_SUDENT_ID                ="sudent_id",
    //COLUMN_DATE                   ="date",
    //COLUMN_DAY                    ="day",
    COLUMN_LOGIN_COLOR              ="login_color",
            COLUMN_LOGOUT_COLOR             ="logout_color",
            COLUMN_LOGIN_TIME               ="login_time",
            COLUMN_LOGOUT_TIME              ="logout_time",
    // COLUMN_HRS_WORKED             ="hours_worked",
    //COLUMN_ATTE_STATUS            ="attendance_status",
    // COLUMN_DISTANCE_WORKSTATION   ="distance_from_workstation",
    COLUMN_VIEW_COMMENT_LINK        ="view_comment_link",
            COLUMN_S_LEARNER_COMMENT        ="s_a_learner_comment";


    public static final String CREATE_TABLE_POPULATE_ATTLIST_DETAILS="CREATE TABLE "+TABLE_POPULATE_ATTLIST_DETAILS+"( "+COLUMN_S_A_ID+" INTEGER, "+
            COLUMN_SUDENT_ID+" VARCHAR(50) , "+COLUMN_DATE+" VARCHAR(50) , "+COLUMN_DAY+" VARCHAR(50) , "
            +COLUMN_LOGIN_COLOR+" VARCHAR(50), "+COLUMN_LOGOUT_COLOR+" VARCHAR(50), "+
            COLUMN_LOGIN_TIME+" VARCHAR(50), "+COLUMN_LOGOUT_TIME+" VARCHAR(50), "+
            COLUMN_HRS_WORKED+" VARCHAR(50), "+COLUMN_ATTE_STATUS+" VARCHAR(50), "+
            COLUMN_DISTANCE_WORKSTATION+" VARCHAR(50), "+COLUMN_VIEW_COMMENT_LINK+" VARCHAR(50), "+
            COLUMN_S_LEARNER_COMMENT+" VARCHAR(50)); ";


    public static final String DROP_TABLE_POPULATE_ATTLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_POPULATE_ATTLIST_DETAILS;
    public static final String EMPTY_TABLE_POPULATE_ATTLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_POPULATE_ATTLIST_DETAILS;

    /*table Existing leave list details*/
    public static final String TABLE_EXISTING_LEAVELIST_DETAILS="tbl_existing_leavelist_details";
    public static final String

            // COLUMN_S_A_ID                   ="s_a_id",
            COLUMN_ATT_ID                   ="att_ids",
    //  COLUMN_MONTH                    ="month",
    COLUMN_FROM_DATE                ="from_date",
            COLUMN_TO_DATE                  ="to_date",
            COLUMN_ANNUAL_LEAVE             ="annual_leave",
    //  COLUMN_SICK_LEAVE               ="sick_leave",
    //  COLUMN_O_PAID_LEAVE             ="other_paid_leave",
    // COLUMN_UNPAID_LEAVE             ="unpaid_leave",
    COLUMN_MOTIVATION               ="motivation",
            COLUMN_SA_REASON                ="sa_reason",
            COLUMN_S_APPROVAL_STATUS        ="supervisor_approval_status",
            COLUMN_MOTIVATION_BTN           ="motivation_btn",
            COLUMN_REASON_BTN               ="reason_btn",
            COLUMN_UPLOADS                  ="uploads",
            COLUMN_IS_UPLOAD                ="is_upload",
            COLUMN_SHOW_EDIT_LINK           ="show_edit_link",
            COLUMN_SHOW_REMOVE_LINK         ="show_remove_link";


    public static final String CREATE_TABLE_EXISTING_LEAVELIST_DETAILS="CREATE TABLE "+TABLE_EXISTING_LEAVELIST_DETAILS+"( "+COLUMN_S_A_ID+" INTEGER, "+
            COLUMN_ATT_ID+" VARCHAR(50) , "+COLUMN_MONTH+" VARCHAR(50) , "+COLUMN_FROM_DATE+" VARCHAR(50) , "
            +COLUMN_TO_DATE+" VARCHAR(50), "+COLUMN_ANNUAL_LEAVE+" VARCHAR(50), "+
            COLUMN_SICK_LEAVE+" VARCHAR(50), "+COLUMN_O_PAID_LEAVE+" VARCHAR(50), "+
            COLUMN_UNPAID_LEAVE+" VARCHAR(50), "+COLUMN_MOTIVATION+" VARCHAR(50), "+
            COLUMN_SA_REASON+" VARCHAR(50), "+COLUMN_S_APPROVAL_STATUS+" VARCHAR(50), "+
            COLUMN_MOTIVATION_BTN+" VARCHAR(50), "+COLUMN_REASON_BTN+" VARCHAR(50), "+
            COLUMN_UPLOADS+" VARCHAR(50), "+COLUMN_IS_UPLOAD+" VARCHAR(50), "+
            COLUMN_SHOW_EDIT_LINK+" VARCHAR(50), "+
            COLUMN_SHOW_REMOVE_LINK+" VARCHAR(50)); ";


    public static final String DROP_TABLE_EXISTING_LEAVELIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_EXISTING_LEAVELIST_DETAILS;
    public static final String EMPTY_TABLE_EXISTING_LEAVELIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_EXISTING_LEAVELIST_DETAILS;


    /*table Past Claim list details*/
    public static final String TABLE_PAST_CLAIMLIST_DETAILS="tbl_past_claimlist_details";
    public static final String

            COLUMN_S_M_S_ID                     ="s_m_s_id",
            COLUMN_S_M_S_STIPEND                ="s_m_s_stipend",
            COLUMN_S_M_S_STIPEND_MONTH          ="s_m_s_stipend_month",
    //COLUMN_YEAR                         ="year",
    //COLUMN_MONTH                        ="month",
    COLUMN_DATE_OF_CLAIM                ="date_of_claim",
            COLUMN_CLAIMED_AMOUNT               ="claimed_ammount",
            COLUMN_SUPERVISOR_STATUS            ="supervisor_status",
            COLUMN_SUPERVISOR_STATUS_COLOR      ="supervisor_status_color",
            COLUMN_GADMIN_STATUS                ="grant_admin_status",
            COLUMN_GADMIN_STATUS_COLOR          ="grant_admin_status_color",
            COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN="download_unsigned_claim_form_btn",
            COLUMN_DWNLD_UNSIGNED_CLAIM_FORM    ="download_unsigned_claim_form",
            COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN ="upload_signed_claim_form_btn",
            COLUMN_DWNLD_SIGNED_CLAIM_FORM      ="download_signed_claim_form",
            COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN  ="download_sign_claim_form_btn";


    public static final String CREATE_TABLE_PAST_CLAIMLIST_DETAILS="CREATE TABLE "+TABLE_PAST_CLAIMLIST_DETAILS+"( "+COLUMN_S_M_S_ID+" INTEGER, "+
            COLUMN_S_M_S_STIPEND+" VARCHAR(50) , "+COLUMN_S_M_S_STIPEND_MONTH+" VARCHAR(50) , "+COLUMN_YEAR+" VARCHAR(50) , "
            +COLUMN_MONTH+" VARCHAR(50), "+COLUMN_DATE_OF_CLAIM+" VARCHAR(50), "+
            COLUMN_CLAIMED_AMOUNT+" VARCHAR(50), "+COLUMN_SUPERVISOR_STATUS+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_STATUS_COLOR+" VARCHAR(50), "+COLUMN_GADMIN_STATUS+" VARCHAR(50), "+
            COLUMN_GADMIN_STATUS_COLOR+" VARCHAR(50), "+COLUMN_DWNLD_UNSIGNED_CLAIM_FORM_BTN+" VARCHAR(50), "+
            COLUMN_DWNLD_UNSIGNED_CLAIM_FORM+" VARCHAR(50), "+COLUMN_UPLOAD_SIGNED_CLAIM_FORM_BTN+" VARCHAR(50), "+
            COLUMN_DWNLD_SIGNED_CLAIM_FORM+" VARCHAR(50), "+
            COLUMN_DWNLD_SIGNED_CLAIM_FORM_BTN+" VARCHAR(50)); ";


    public static final String DROP_TABLE_PAST_CLAIMLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_PAST_CLAIMLIST_DETAILS;
    public static final String EMPTY_TABLE_PAST_CLAIMLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_PAST_CLAIMLIST_DETAILS;

    /*table Document center list details*/
    public static final String TABLE_DOC_CENTERLIST_DETAILS="tbl_doc_centerlist_details";
    public static final String

            //COLUMN_ID             ="id",
            //COLUMN_ST_ID          ="student_id",
            // COLUMN_GRANTID        ="grant_id",
            COLUMN_PREVIOUS_DOC     ="previous_document",
            COLUMN_PREVIOUS_DOC_BTN ="previous_document_btn",
            COLUMN_NAME_OF_DOC      ="name_of_document",
            COLUMN_D_C_DOC_STATUS   ="d_c_doc_status",
            COLUMN_D_C_DOC_TYPE     ="d_c_doc_type",
            COLUMN_UPLOAD_DOC_TYPE  ="upload_document_type",
            COLUMN_DWNLD_DOC_LINK   ="download_document_link",
            COLUMN_DWNLD_DOC_BTN    ="download_document_btn",
            COLUMN_UPLOAD_DOC_LINK  ="upload_document_link";



    public static final String CREATE_TABLE_DOC_CENTERLIST_DETAILS="CREATE TABLE "+TABLE_DOC_CENTERLIST_DETAILS+"( "+COLUMN_ID+" INTEGER, "+
            COLUMN_ST_ID+" VARCHAR(50) , "+COLUMN_GRANTID+" VARCHAR(50) , "+COLUMN_PREVIOUS_DOC+" VARCHAR(50) , "
            +COLUMN_PREVIOUS_DOC_BTN+" VARCHAR(50), "+COLUMN_NAME_OF_DOC+" VARCHAR(50), "+
            COLUMN_D_C_DOC_STATUS+" VARCHAR(50), "+COLUMN_D_C_DOC_TYPE+" VARCHAR(50), "+
            COLUMN_UPLOAD_DOC_TYPE+" VARCHAR(50), "+COLUMN_DWNLD_DOC_LINK+" VARCHAR(50), "+
            COLUMN_DWNLD_DOC_BTN+" VARCHAR(50), "+
            COLUMN_UPLOAD_DOC_LINK+" VARCHAR(50)); ";


    public static final String DROP_TABLE_DOC_CENTERLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_DOC_CENTERLIST_DETAILS;
    public static final String EMPTY_TABLE_DOC_CENTERLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_DOC_CENTERLIST_DETAILS;

    /*table Document center list details*/
    public static final String TABLE_ST_WEEKLY_REPORTLIST_DETAILS="tbl_st_weekly_reportlist_details";
    public static final String

            COLUMN_S_W_R_ID                 ="s_w_r_id",
            COLUMN_REPORT_TITLE             ="title",
    //  COLUMN_MONTH                  ="month",
    //  COLUMN_YEAR                   ="year",
    COLUMN_MONTH_YEAR               ="month_year",
    //  COLUMN_GRANT_ID               ="grant_id",
    // COLUMN_SUPERVISOR_STATUS        ="supervisor_status",
    COLUMN_EDIT_BTN                 ="edit_btn",
            COLUMN_SUPERVISOR_STATUS_ID     ="supervisor_status_id",
            COLUMN_REPORT_NO                ="report_no";



    public static final String CREATE_TABLE_ST_WEEKLY_REPORTLIST_DETAILS="CREATE TABLE "+TABLE_ST_WEEKLY_REPORTLIST_DETAILS+"( "+COLUMN_S_W_R_ID+" INTEGER, "+
            COLUMN_REPORT_TITLE+" VARCHAR(50) , "+COLUMN_MONTH+" VARCHAR(50) , "+COLUMN_YEAR+" VARCHAR(50) , "
            +COLUMN_MONTH_YEAR+" VARCHAR(50), "+COLUMN_GRANT_ID+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_STATUS+" VARCHAR(50), "+COLUMN_EDIT_BTN+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_STATUS_ID+" VARCHAR(50), "+
            COLUMN_REPORT_NO+" VARCHAR(50)); ";


    public static final String DROP_TABLE_ST_WEEKLY_REPORTLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_ST_WEEKLY_REPORTLIST_DETAILS;
    public static final String EMPTY_TABLE_ST_WEEKLY_REPORTLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_ST_WEEKLY_REPORTLIST_DETAILS;


    /*table Weekly Report details*/
    public static final String TABLE_ST_WEEKLY_REPORT_DETAILS="tbl_st_weekly_report_details";
    public static final String

            //COLUMN_S_W_R_ID                     ="s_w_r_id",
            COLUMN_S_W_R_ST_ID                  ="s_w_r_student_id",
            COLUMN_STU_NAME                     ="student_name",
            COLUMN_TITLE                        ="title",
            COLUMN_TRAINING                     ="training",
    //  COLUMN_DAY                          ="day",
    COLUMN_FEEDBACK                     ="feedback",
            COLUMN_LEARNING_EXP                 ="learning_experices",
            COLUMN_NUMBER                       ="number",
    // COLUMN_MONTH_YEAR                   ="month_year",
    COLUMN_REPORT_ADD_DATE              ="report_add_date",
    //  COLUMN_DATE                         ="date",
    COLUMN_IS_SUPERVISOR_COMMENTED      ="is_supervisor_commented",
            COLUMN_C_U_R_COMMENT                ="c_u_r_comment",
            COLUMN_C_U_R_TRAINING_PROGRESS      ="c_u_r_training_progress",
            COLUMN_C_U_R_REPORT_WRITING         ="c_u_r_report_writing";
    // COLUMN_SUPERVISOR_STATUS            ="supervisor_status";



    public static final String CREATE_TABLE_ST_WEEKLY_REPORT_DETAILS="CREATE TABLE "+TABLE_ST_WEEKLY_REPORT_DETAILS+"( "+COLUMN_S_W_R_ID+" INTEGER, "+
            COLUMN_S_W_R_ST_ID+" VARCHAR(50) , "+COLUMN_STU_NAME+" VARCHAR(50) , "+COLUMN_TITLE+" VARCHAR(50) , "
            +COLUMN_TRAINING+" VARCHAR(50), "+COLUMN_DAY+" VARCHAR(50), "+
            COLUMN_FEEDBACK+" VARCHAR(50), "+COLUMN_LEARNING_EXP+" VARCHAR(50), "+
            COLUMN_NUMBER+" VARCHAR(50), "+COLUMN_MONTH_YEAR+" VARCHAR(50), "+
            COLUMN_REPORT_ADD_DATE+" VARCHAR(50), "+COLUMN_DATE+" VARCHAR(50), "+
            COLUMN_IS_SUPERVISOR_COMMENTED+" VARCHAR(50), "+COLUMN_C_U_R_COMMENT+" VARCHAR(50), "+
            COLUMN_C_U_R_TRAINING_PROGRESS+" VARCHAR(50), "+COLUMN_C_U_R_REPORT_WRITING+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_STATUS+" VARCHAR(50)); ";


    public static final String DROP_TABLE_ST_WEEKLY_REPORT_DETAILS= "DROP TABLE IF EXISTS "+TABLE_ST_WEEKLY_REPORT_DETAILS;
    public static final String EMPTY_TABLE_ST_WEEKLY_REPORT_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_ST_WEEKLY_REPORT_DETAILS;



    /*==================================Mentor Data==========================*/

    /*table Mentor Dashboard details*/
    public static final String TABLE_MENTOR_DASHBOARD_DETAILS="tbl_mentor_dashboard_details";
    public static final String
    COLUMN_LEARNER_ID                            ="learner_id",
  //COLUMN_GRANT_ID                              ="grant_id",
  //COLUMN_LEARNER_NAME                          ="learner_name",
  //COLUMN_LEARNER_STATUS                        ="learner_status",
    COLUMN_STARTDATE                             ="start_date",
  //COLUMN_END_DATE                              ="end_date",
  //COLUMN_SETA_NAME                             ="seta_name",
    COLUMN_MANAGED_BY                            ="managed_by",
    COLUMN_PAST_ATT_REGISTER                     ="past_attendance_register",
    COLUMN_EDIT_CURRENT_ATT                      ="edit_current_attendance",
    COLUMN_TOTAL_LEAVE_TAKEN                     ="total_leave_taken",
    COLUMN_LEAVE_PENDING_APPROVAL                ="leave_pending_approval",
    COLUMN_PAST_STIPEND_CLAIM                    ="past_stipend_claim",
    COLUMN_APPROVED_CLAIM_LINK                   ="show_approved_claim_link",
    COLUMN_CURRENT_STIPEND_PENDING_APPROVAL      ="current_stipend_pending_approval",
    COLUMN_REGISTERED_WORKSTATION                ="registered_workstation",
    COLUMN_SHOW_WORKSTATION_LINK                 ="show_workstation_link",
    COLUMN_ASSIGNED_WORKSTATION                  ="assigned_workstation",
    COLUMN_WORKSTATION_STATUS                    ="workstations_status",
    COLUMN_SHOW_EDIT_WORKSTATION_LINK            ="show_edit_workstations_link",
    COLUMN_LINKED_STUDENT_ID                     ="linked_student_id",
    COLUMN_MONTHLY_REPORTS_COMPLETED             ="monthly_reports_completed",
    COLUMN_SUPERVISOR_COMMENTS_PENDING           ="supervisor_comments_pending",
    COLUMN_TRAINING_PROGRAM_UPLOAD               ="training_program_upload",
    COLUMN_TRAINING_DOC                          ="trainging_doc",
    COLUMN_ALERT_COUNT                           ="alert_count",
    COLUMN_NOTES                                 ="notes";



    public static final String CREATE_TABLE_MENTOR_DASHBOARD_DETAILS="CREATE TABLE "+TABLE_MENTOR_DASHBOARD_DETAILS+"( "+COLUMN_LEARNER_ID+" INTEGER, "+
            COLUMN_GRANT_ID+" VARCHAR(50) , "+COLUMN_LEARNER_NAME+" VARCHAR(50) , "+COLUMN_LEARNER_STATUS+" VARCHAR(50) , "
            +COLUMN_STARTDATE+" VARCHAR(50), "+COLUMN_END_DATE+" VARCHAR(50), "+
            COLUMN_SETA_NAME+" VARCHAR(50), "+COLUMN_MANAGED_BY+" VARCHAR(50), "+
            COLUMN_PAST_ATT_REGISTER+" VARCHAR(50), "+COLUMN_EDIT_CURRENT_ATT+" VARCHAR(50), "+
            COLUMN_TOTAL_LEAVE_TAKEN+" VARCHAR(50), "+COLUMN_LEAVE_PENDING_APPROVAL+" VARCHAR(50), "+
            COLUMN_PAST_STIPEND_CLAIM+" VARCHAR(50), "+COLUMN_APPROVED_CLAIM_LINK+" VARCHAR(50), "+
            COLUMN_CURRENT_STIPEND_PENDING_APPROVAL+" VARCHAR(50), "+COLUMN_REGISTERED_WORKSTATION+" VARCHAR(50), "+
            COLUMN_SHOW_WORKSTATION_LINK+" VARCHAR(50), "+COLUMN_ASSIGNED_WORKSTATION+" VARCHAR(50), "+
            COLUMN_WORKSTATION_STATUS+" VARCHAR(50), "+COLUMN_SHOW_EDIT_WORKSTATION_LINK+" VARCHAR(50), "+
            COLUMN_LINKED_STUDENT_ID+" VARCHAR(50), "+COLUMN_MONTHLY_REPORTS_COMPLETED+" VARCHAR(50), "+
            COLUMN_SUPERVISOR_COMMENTS_PENDING+" VARCHAR(50), "+COLUMN_TRAINING_PROGRAM_UPLOAD+" VARCHAR(50), "+
            COLUMN_TRAINING_DOC+" VARCHAR(50), "+COLUMN_ALERT_COUNT+" VARCHAR(50), "+
            COLUMN_NOTES+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_DASHBOARD_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_DASHBOARD_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_DASHBOARD_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_DASHBOARD_DETAILS;


    /*table Mentor notes list details*/
    public static final String TABLE_MENTOR_NOTESLIST_DETAILS="tbl_mentor_notelist_details";
    public static final String
            COLUMN_N_ID             ="n_id",
            COLUMN_NUSER_ID         ="n_user_id",
            COLUMN_N_DESCRIPTION    ="n_description",
            COLUMN_N_STATUS         ="n_status",
            COLUMN_N_ADDDATE        ="n_add_date",
            COLUMN_N_ADD_BY         ="n_add_by",
            COLUMN_N_DURATION       ="n_duration",
            COLUMN_N_CATEGORY       ="n_category",
            COLUMN_N_NOTE_TYPE_ID   ="n_note_type_id",
          //COLUMN_GRANT_ID         ="grant_id",
            COLUMN_NOTE_FOR         ="note_for",
            COLUMN_U_P_CELL_NO      ="u_p_cell_no",
            COLUMN_NOTE_FROM        ="note_from",
            COLUMN_ADD_BY_CELL_NO   ="add_by_cell_no",
            COLUMN_ADD_BY_U_ID      ="add_by_u_id",
            COLUMN_N_CATEGORY_ID    ="n_category_id",
            COLUMN_N_CATEGORY_NAME  ="n_category_name";



    public static final String CREATE_TABLE_MENTOR_NOTESLIST_DETAILS="CREATE TABLE "+TABLE_MENTOR_NOTESLIST_DETAILS+"( "+
            COLUMN_N_ID+" INTEGER, "+COLUMN_NUSER_ID+" VARCHAR(50) , "+
            COLUMN_N_DESCRIPTION+" VARCHAR(50) , "+COLUMN_N_STATUS+" VARCHAR(50) , "
            +COLUMN_N_ADDDATE+" VARCHAR(50), "+COLUMN_N_ADD_BY+" VARCHAR(50), "+
            COLUMN_N_DURATION+" VARCHAR(50), "+COLUMN_N_CATEGORY+" VARCHAR(50), "+
            COLUMN_N_NOTE_TYPE_ID+" VARCHAR(50), "+COLUMN_GRANT_ID+" VARCHAR(50), "+
            COLUMN_NOTE_FOR+" VARCHAR(50), "+COLUMN_U_P_CELL_NO+" VARCHAR(50), "+
            COLUMN_NOTE_FROM+" VARCHAR(50), "+COLUMN_ADD_BY_CELL_NO+" VARCHAR(50), "+
            COLUMN_ADD_BY_U_ID+" VARCHAR(50), "+COLUMN_N_CATEGORY_ID+" VARCHAR(50), "+
            COLUMN_N_CATEGORY_NAME+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_NOTESLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_NOTESLIST_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_NOTESLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_NOTESLIST_DETAILS;


    /*table Mentor learner details*/
    public static final String TABLE_MENTOR_LEARNER_DETAILS="tbl_mentor_learner_details";
    public static final String
             COLUMN_U_ID                    ="u_id",
             COLUMN_S_ID_NO                 ="s_id_no",
             COLUMN_STUDENT_NAME            ="student_name",
             COLUMN_U_EMAIL                 ="u_email",
             COLUMN_GENDER                  ="gender_name",
             COLUMN_SSG_GRANT_START_DATE    ="s_s_g_grant_start_date",
             COLUMN_SSG_GRANT_END_DATE      ="s_s_g_grant_end_date",
          // COLUMN_MENTOR_NAME             ="mentor_name",
          // COLUMN_HOST_EMP_NAME           ="host_emp_name",
             COLUMN_GRANT_ADMIN             ="grant_admin",
             COLUMN_WORKSTATION             ="workstation";
          // COLUMN_SETA_NAME               ="seta_name",
          // COLUMN_LEM_NAME                ="lem_name";




    public static final String CREATE_TABLE_MENTOR_LEARNER_DETAILS="CREATE TABLE "+TABLE_MENTOR_LEARNER_DETAILS+"( "+
            COLUMN_U_ID+" INTEGER, "+COLUMN_S_ID_NO+" VARCHAR(50) , "+
            COLUMN_STUDENT_NAME+" VARCHAR(50) , "+COLUMN_U_EMAIL+" VARCHAR(50) , "
            +COLUMN_GENDER+" VARCHAR(50), "+COLUMN_SSG_GRANT_START_DATE+" VARCHAR(50), "+
            COLUMN_SSG_GRANT_END_DATE+" VARCHAR(50), "+COLUMN_MENTOR_NAME+" VARCHAR(50), "+
            COLUMN_HOST_EMP_NAME+" VARCHAR(50), "+COLUMN_GRANT_ADMIN+" VARCHAR(50), "+
            COLUMN_WORKSTATION+" VARCHAR(50), "+COLUMN_SETA_NAME+" VARCHAR(50), "+
            COLUMN_LEM_NAME+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_LEARNER_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_LEARNER_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_LEARNER_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_LEARNER_DETAILS;

    /*table Mentor learner Grant details*/
    public static final String TABLE_MENTOR_LEARNER_GRANT_DETAILS="tbl_mentor_learner_grant_details";
    public static final String
         //   COLUMN_U_ID                   ="u_id",
          //  COLUMN_S_ID_NO                ="s_id_no",
          //  COLUMN_GRANT_ID               ="grant_id",
           // COLUMN_SETA_NAME              ="seta_name",
              COLUMN_SETA_MANAGER_NAME      ="seta_manager_name",
          //  COLUMN_SETA_MANAGER_ID        ="seta_manager_id",
       //     COLUMN_LEM_NAME               ="lem_name",
    //        COLUMN_LEM_ID                 ="lem_id",
           // COLUMN_HOST_EMP_NAME          ="host_emp_name",
       //     COLUMN_GRANT_ADMIN            ="grant_admin",
              COLUMN_GRANT_ADMIN_ID         ="grant_admin_id",
              COLUMN_GRANT_ADMIN_EMAIL      ="grant_admin_email",
              COLUMN_GRANT_ADMIN_CELL       ="grant_admin_cell",
              COLUMN_HOST_EMP_SDL           ="host_emp_sdl",
           // COLUMN_SSG_GRANT_START_DATE   ="s_s_g_grant_start_date";
           // COLUMN_SSG_GRANT_END_DATE     ="s_s_g_grant_end_date";
              COLUMN_MENTOR_ID              ="mentor_id";




    public static final String CREATE_TABLE_MENTOR_LEARNER_GRANT_DETAILS="CREATE TABLE "+TABLE_MENTOR_LEARNER_GRANT_DETAILS+"( "+
            COLUMN_U_ID+" INTEGER, "+COLUMN_S_ID_NO+" VARCHAR(50) , "+
            COLUMN_GRANT_ID+" VARCHAR(50) , "+COLUMN_SETA_NAME+" VARCHAR(50) , "
            +COLUMN_SETA_MANAGER_NAME+" VARCHAR(50), "+COLUMN_SETA_MANAGER_ID+" VARCHAR(50), "+
            COLUMN_LEM_NAME+" VARCHAR(50), "+COLUMN_LEM_ID+" VARCHAR(50), "+
            COLUMN_HOST_EMP_NAME+" VARCHAR(50), "+COLUMN_GRANT_ADMIN+" VARCHAR(50), "+
            COLUMN_GRANT_ADMIN_ID+" VARCHAR(50), "+COLUMN_GRANT_ADMIN_EMAIL+" VARCHAR(50), "+
            COLUMN_GRANT_ADMIN_CELL+" VARCHAR(50), "+COLUMN_HOST_EMP_SDL+" VARCHAR(50), "+
            COLUMN_SSG_GRANT_START_DATE+" VARCHAR(50), "+  COLUMN_MENTOR_ID+" VARCHAR(50), "+
            COLUMN_SSG_GRANT_END_DATE+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_LEARNER_GRANT_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_LEARNER_GRANT_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_LEARNER_GRANT_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_LEARNER_GRANT_DETAILS;

    /*table Mentor show details*/
    public static final String TABLE_MENTOR_SHOW_DETAILS="tbl_mentor_show_details";
    public static final String
               COLUMN_LEAD_EMP                  ="lead_emp",
               COLUMN_SETAN_AME                 ="setaName",
               COLUMN_GRANTNAME                 ="grantName",
               COLUMN_GRANT_NUMBER              ="g_d_grant_number",
               COLUMN_LEAD_MANAGER              ="leadManager",
               COLUMN_LEAD_MANAGER_EMAIL        ="leadManagerEmail",
               COLUMN_LEAD_MANAGER_CONTACT      ="leadManagerContact",
            // COLUMN_ANNUAL_LEAVE              ="annual_leave",
     //        COLUMN_SICK_LEAVE                ="sick_leave",
               COLUMN_OTHER_PAID_LEAVE          ="other_paid_leave",
    //         COLUMN_UNPAID_LEAVE              ="unpaid_leave",
               COLUMN_STUDNAME                  ="studName",
               COLUMN_SID_NO                    ="s_id_no",
               COLUMN_STU_EMAIL                 ="stu_email",
               COLUMN_STU_CELL                  ="stu_cell";





    public static final String CREATE_TABLE_MENTOR_SHOW_DETAILS="CREATE TABLE "+TABLE_MENTOR_SHOW_DETAILS+"( "+
            COLUMN_SID_NO+" INTEGER, "+COLUMN_STU_EMAIL+" VARCHAR(50) , "+
            COLUMN_STU_CELL+" VARCHAR(50) , "+COLUMN_STUDNAME+" VARCHAR(50) , "
            +COLUMN_UNPAID_LEAVE+" VARCHAR(50), "+COLUMN_OTHER_PAID_LEAVE+" VARCHAR(50), "+
            COLUMN_SICK_LEAVE+" VARCHAR(50), "+COLUMN_ANNUAL_LEAVE+" VARCHAR(50), "+
            COLUMN_LEAD_MANAGER_CONTACT+" VARCHAR(50), "+COLUMN_LEAD_MANAGER_EMAIL+" VARCHAR(50), "+
            COLUMN_LEAD_MANAGER+" VARCHAR(50), "+COLUMN_GRANT_NUMBER+" VARCHAR(50), "+
            COLUMN_GRANTNAME+" VARCHAR(50), "+COLUMN_SETAN_AME+" VARCHAR(50), "+
            COLUMN_LEAD_EMP+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_SHOW_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_SHOW_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_SHOW_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_SHOW_DETAILS;


    /*table Mentor past attendance details*/
    public static final String TABLE_MENTOR_PAST_ATTENDANCE_DETAILS="tbl_mentor_past_attendance_details";
    public static final String
       //   COLUMN_GRANT_ID                  ="grant_id",
            COLUMN_STUDENTID                 ="student_id",
      //    COLUMN_MONTH                     ="month",
      //    COLUMN_YEAR                      ="year",
            COLUMN_DAYS_WORKED               ="days_worked",
            COLUMN_LEAVE                     ="leave",
    //      COLUMN_ANNUAL_LEAVE              ="annual_leave",
    //      COLUMN_SICK_LEAVE                ="sick_leave",
    //      COLUMN_OTHER_PAID_LEAVE          ="other_paid_leave",
    //      COLUMN_UNPAID_LEAVE              ="unpaid_leave",
            COLUMN_SNO                       ="sno",
            COLUMN_YEAR_MONTH                ="year_month";


    public static final String CREATE_TABLE_MENTOR_PAST_ATTENDANCE_DETAILS="CREATE TABLE "+TABLE_MENTOR_PAST_ATTENDANCE_DETAILS+"( "+
            COLUMN_SNO+" INTEGER, "+COLUMN_YEAR_MONTH+" VARCHAR(50) , "+
            COLUMN_LEAVE+" VARCHAR(50) , "+COLUMN_DAYS_WORKED+" VARCHAR(50) , "
            +COLUMN_UNPAID_LEAVE+" VARCHAR(50), "+COLUMN_OTHER_PAID_LEAVE+" VARCHAR(50), "+
            COLUMN_SICK_LEAVE+" VARCHAR(50), "+COLUMN_ANNUAL_LEAVE+" VARCHAR(50), "+
            COLUMN_YEAR+" VARCHAR(50), "+COLUMN_MONTH+" VARCHAR(50), "+
            COLUMN_STUDENTID+" VARCHAR(50), "+
            COLUMN_GRANT_ID+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_PAST_ATTENDANCE_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_PAST_ATTENDANCE_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_PAST_ATTENDANCE_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_PAST_ATTENDANCE_DETAILS;

    /*table Mentor attendance list by month details*/
    public static final String TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS="tbl_mentor_attlist_bymonth_details";
    public static final String
               COLUMN_SA_ID                  ="s_a_id",
           //  COLUMN_DATE                   ="date",
       //      COLUMN_DAY                    ="day",
     //        COLUMN_LOGIN_TIME             ="login_time",
 //            COLUMN_LOGOUT_TIME            ="logout_time",
               COLUMN_TIME_SPENT             ="time_spent",
               COLUMN_OVERTIME_HOUR          ="overtime_hour",
               COLUMN_LEARNER_COMMENT_BTN    ="learner_comment_btn",
               COLUMN_ATTENDANCESTATUS       ="attendance_status",
               COLUMN_OUT_OF_RANGE           ="out_of_range",
               COLUMN_DATE_INPUT           ="date_input";

    public static final String CREATE_TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS="CREATE TABLE "+TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS+"( "+
            COLUMN_SA_ID+" INTEGER, "+COLUMN_DATE+" VARCHAR(50) , "+
            COLUMN_DAY+" VARCHAR(50) , "+COLUMN_LOGIN_TIME+" VARCHAR(50) , "
            +COLUMN_LOGOUT_TIME+" VARCHAR(50), "+COLUMN_TIME_SPENT+" VARCHAR(50), "+
            COLUMN_OVERTIME_HOUR+" VARCHAR(50), "+COLUMN_LEARNER_COMMENT_BTN+" VARCHAR(50), "+
            COLUMN_ATTENDANCESTATUS+" VARCHAR(50), "+COLUMN_OUT_OF_RANGE+" VARCHAR(50), "+
            COLUMN_DATE_INPUT+" VARCHAR(50)); ";


    public static final String DROP_TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS= "DROP TABLE IF EXISTS "+TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS;
    public static final String EMPTY_TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_MENTOR_ATTLIST_BYMONTH_DETAILS;

    /*table Mentor approved claim list details*/
    public static final String TABLE_M_APPROVED_CLAIMLIST_DETAILS="tbl_mentor_approved_claimlist_details";
    public static final String
                COLUMN_STIPEND_MONTH                    ="s_m_s_stipend_month",
                COLUMN_MONTH_NAME                       ="month_name",
                COLUMN_STIPEND_YEAR                     ="s_m_s_stipend_year",
                COLUMN_STIPEND_ID                       ="s_m_s_id",
                COLUMN_STIPEND_AMOUNT                   ="stipend_amount",
                COLUMN_STIPEND_APPROVED                 ="approve_stipend",
                COLUMN_OUTOF_RANGE_SIGNIN_COUNTS        ="out_of_range_sign_in_counts",
                COLUMN_OUTOF_RANGE_LINK                 ="out_of_range_link",
        //      COLUMN_DAYS_WORKED                      ="days_worked",
       //       COLUMN_LEAVE                            ="leave",
                COLUMN_DWNLD_CLAIM_FORM_LINK            ="download_claim_form_link",
                COLUMN_SHOW_CLAIM_FORM_LINK             ="show_claim_form_link",
                COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK      ="download_signed_claim_form_link",
                COLUMN_SHOW_SIGNED_CLAIMFORM_LINK       ="show_signed_claim_form_link";

    public static final String CREATE_TABLE_M_APPROVED_CLAIMLIST_DETAILS="CREATE TABLE "+TABLE_M_APPROVED_CLAIMLIST_DETAILS+"( "+
            COLUMN_STIPEND_ID+" INTEGER, "+COLUMN_STIPEND_YEAR+" VARCHAR(50) , "+
            COLUMN_MONTH_NAME+" VARCHAR(50) , "+COLUMN_STIPEND_MONTH+" VARCHAR(50) , "
            +COLUMN_STIPEND_AMOUNT+" VARCHAR(50), "+COLUMN_STIPEND_APPROVED+" VARCHAR(50), "+
            COLUMN_OUTOF_RANGE_SIGNIN_COUNTS+" VARCHAR(50), "+COLUMN_OUTOF_RANGE_LINK+" VARCHAR(50), "+
            COLUMN_DAYS_WORKED+" VARCHAR(50), "+COLUMN_LEAVE+" VARCHAR(50), "+
            COLUMN_DWNLD_CLAIM_FORM_LINK+" VARCHAR(50), "+COLUMN_SHOW_CLAIM_FORM_LINK+" VARCHAR(50), "+
            COLUMN_DWNLD_SIGNED_CLAIMFORM_LINK+" VARCHAR(50), "+
            COLUMN_SHOW_SIGNED_CLAIMFORM_LINK+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_APPROVED_CLAIMLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_APPROVED_CLAIMLIST_DETAILS;
    public static final String EMPTY_TABLE_M_APPROVED_CLAIMLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_APPROVED_CLAIMLIST_DETAILS;

    /*table Mentor pending claim list details*/
    public static final String TABLE_M_PENDING_CLAIMLIST_DETAILS="tbl_mentor_pending_claimlist_details";
    public static final String
      //      COLUMN_STIPEND_MONTH                    ="s_m_s_stipend_month",
      //      COLUMN_MONTH_NAME                       ="month_name",
      //      COLUMN_STIPEND_YEAR                     ="s_m_s_stipend_year",
      //      COLUMN_STIPEND_ID                       ="s_m_s_id",
      //      COLUMN_STIPEND_AMOUNT                   ="stipend_amount",
      //      COLUMN_LEARNER_NAME                     ="learner_name",
              COLUMN_APPROVE_STIPEND_LINK             ="approve_stipend_link";

    public static final String CREATE_TABLE_M_PENDING_CLAIMLIST_DETAILS="CREATE TABLE "+TABLE_M_PENDING_CLAIMLIST_DETAILS+"( "+
            COLUMN_STIPEND_ID+" INTEGER, "+COLUMN_STIPEND_YEAR+" VARCHAR(50) , "+
            COLUMN_MONTH_NAME+" VARCHAR(50) , "+COLUMN_STIPEND_MONTH+" VARCHAR(50) , "
            +COLUMN_STIPEND_AMOUNT+" VARCHAR(50), "+COLUMN_LEARNER_NAME+" VARCHAR(50), "+
            COLUMN_APPROVE_STIPEND_LINK+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_PENDING_CLAIMLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_PENDING_CLAIMLIST_DETAILS;
    public static final String EMPTY_TABLE_M_PENDING_CLAIMLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_PENDING_CLAIMLIST_DETAILS;

    /*table Mentor workstation list details*/
    public static final String TABLE_M_WORKSTATION_LIST_DETAILS="tbl_mentor_workstation_list_details";
    public static final String
            COLUMN_EGL_ID                    ="e_g_l_id",
            COLUMN_EMP_NAME                  ="employerName",
            COLUMN_EGL_DEPT_NAME             ="e_g_l_department_name",
            COLUMN_EGL_STUDENT_COUNT         ="e_g_l_student_count",
            COLUMN_EGL_LATITUDE              ="e_g_l_latitude",
            COLUMN_EGL_LOBGITUDE             ="e_g_l_longitude";

    public static final String CREATE_TABLE_M_WORKSTATION_LIST_DETAILS="CREATE TABLE "+TABLE_M_WORKSTATION_LIST_DETAILS+"( "+
            COLUMN_EGL_ID+" INTEGER, "+COLUMN_EMP_NAME+" VARCHAR(50) , "+
            COLUMN_EGL_DEPT_NAME+" VARCHAR(50) , "+COLUMN_EGL_STUDENT_COUNT+" VARCHAR(50) , "
            +COLUMN_EGL_LATITUDE+" VARCHAR(50), "+
            COLUMN_EGL_LOBGITUDE+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_WORKSTATION_LIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_WORKSTATION_LIST_DETAILS;
    public static final String EMPTY_TABLE_M_WORKSTATION_LIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_APPROVED_CLAIMLIST_DETAILS;

    /*table Mentor Learner Progress Report List details*/
    public static final String TABLE_M_LEARNER_REPORTLIST_DETAILS="tbl_mentor_learner_reportlist_details";
    public static final String
                    COLUMN_SWR_ID                   ="s_w_r_id",
                //  COLUMN_TITLE                    ="title",
              //    COLUMN_YEAR                     ="year",
              //    COLUMN_SUPERVISOR_STATUS        ="supervisor_status",
             //     COLUMN_NUMBER                   ="number",
                    COLUMN_SHOW_APPROVE_LINK        ="show_approve_link";
           //       COLUMN_MONTH                    ="month";

    public static final String CREATE_TABLE_M_LEARNER_REPORTLIST_DETAILS="CREATE TABLE "+TABLE_M_LEARNER_REPORTLIST_DETAILS+"( "+
            COLUMN_SWR_ID+" INTEGER, "+COLUMN_TITLE+" VARCHAR(50) , "+
            COLUMN_YEAR+" VARCHAR(50) , "+COLUMN_SUPERVISOR_STATUS+" VARCHAR(50) , "
            +COLUMN_NUMBER+" VARCHAR(50), "+COLUMN_SHOW_APPROVE_LINK+" VARCHAR(50), "+
            COLUMN_MONTH+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_LEARNER_REPORTLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_LEARNER_REPORTLIST_DETAILS;
    public static final String EMPTY_TABLE_M_LEARNER_REPORTLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_LEARNER_REPORTLIST_DETAILS;

    /*table Mentor Document center list details*/
    public static final String TABLE_M_DOC_CENTERLIST_DETAILS="tbl_mentor_doc_centerlist_details";
    public static final String
            COLUMN_STIPENDID                           ="stipend_id",
        //  COLUMN_YEAR                                ="year",
            COLUMN_MONTHNAME                           ="monthName",
            COLUMN_DWNLD_ATT_REGISTER                  ="download_att_register",
            COLUMN_IS_DWNLD_ATT_REGISTER               ="is_download_att_register",
      //    COLUMN_DWNLD_UNSIGNED_CLAIM_FORM           ="download_unsigned_claim_form",
            COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM        ="is_download_unsigned_claim_form",
            COLUMN_UPLOAD_SIGNED_CLAIM_FORM            ="upload_signed_claim_form",
      //    COLUMN_DWNLD_SIGNED_CLAIM_FORM             ="download_signed_claim_form",
            COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM          ="is_download_signed_claim_form";
      //    COLUMN_MONTH                               ="month";

    public static final String CREATE_TABLE_M_DOC_CENTERLIST_DETAILS="CREATE TABLE "+TABLE_M_DOC_CENTERLIST_DETAILS+"( "+
            COLUMN_STIPENDID+" INTEGER, "+COLUMN_YEAR+" VARCHAR(50) , "+
            COLUMN_MONTHNAME+" VARCHAR(50) , "+COLUMN_DWNLD_ATT_REGISTER+" VARCHAR(50) , "
            +COLUMN_IS_DWNLD_ATT_REGISTER+" VARCHAR(50), "+COLUMN_DWNLD_UNSIGNED_CLAIM_FORM+" VARCHAR(50), "+
            COLUMN_IS_DWNLD_UNSIGNED_CLAIM_FORM+" VARCHAR(50), "+COLUMN_UPLOAD_SIGNED_CLAIM_FORM+" VARCHAR(50), "+
            COLUMN_DWNLD_SIGNED_CLAIM_FORM+" VARCHAR(50), "+COLUMN_IS_DWNLD_SIGNED_CLAIM_FORM+" VARCHAR(50), "+
            COLUMN_MONTH+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_DOC_CENTERLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_DOC_CENTERLIST_DETAILS;
    public static final String EMPTY_TABLE_M_DOC_CENTERLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_DOC_CENTERLIST_DETAILS;

    /*table Mentor Learner Alert List details*/
    public static final String TABLE_M_LEARNER_ALERTLIST_DETAILS="tbl_mentor_learner_alertlist_details";
    public static final String
       //       COLUMN_ID                       ="id",
                COLUMN_SUBJECT                  ="subject",
      //        COLUMN_DATE                     ="date",
                COLUMN_DETAIL_LINK              ="detail_link";
       //       COLUMN_STUDENTID                ="student_id",


    public static final String CREATE_TABLE_M_LEARNER_ALERTLIST_DETAILS="CREATE TABLE "+TABLE_M_LEARNER_ALERTLIST_DETAILS+"( "+
            COLUMN_STUDENTID+" INTEGER, "+COLUMN_DETAIL_LINK+" VARCHAR(50) , "+
            COLUMN_DATE+" VARCHAR(50) , "+COLUMN_SUBJECT+" VARCHAR(50) , "+
            COLUMN_ID+" VARCHAR(50)); ";


    public static final String DROP_TABLE_M_LEARNER_ALERTLIST_DETAILS= "DROP TABLE IF EXISTS "+TABLE_M_LEARNER_ALERTLIST_DETAILS;
    public static final String EMPTY_TABLE_M_LEARNER_ALERTLIST_DETAILS= "TRUNCATE TABLE IF EXISTS "+TABLE_M_LEARNER_ALERTLIST_DETAILS;



}




