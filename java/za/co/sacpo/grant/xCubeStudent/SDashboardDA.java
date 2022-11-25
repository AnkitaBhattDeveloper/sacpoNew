package za.co.sacpo.grant.xCubeStudent;

import static android.view.View.GONE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.StudentBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.db.DashboardDataArray;
import za.co.sacpo.grant.xCubeLib.db.DashboardDataArrayAdapter;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.attendance.SCurrentAttDA;
import za.co.sacpo.grant.xCubeStudent.attendance.SMonthlyAttDA;
import za.co.sacpo.grant.xCubeStudent.bank.SBankDA;
import za.co.sacpo.grant.xCubeStudent.claims.SPastClaimDA;
import za.co.sacpo.grant.xCubeStudent.details.SSupervisorDetailsA;
import za.co.sacpo.grant.xCubeStudent.details.SWorkstationDetailsA;
import za.co.sacpo.grant.xCubeStudent.feedback.SAddFeedbackA;
import za.co.sacpo.grant.xCubeStudent.feedback.SFeedbackDA;
import za.co.sacpo.grant.xCubeStudent.grant.SGrantDetailsA;
import za.co.sacpo.grant.xCubeStudent.leaves.SAddLeavesA;
import za.co.sacpo.grant.xCubeStudent.upload.DocumentCenterListA;

public class SDashboardDA extends StudentBaseDrawerA {
    private final String ActivityId = "S103";
    public View mProgressView, mContentView,heading,heading1;
    public Button btn_submit_claim,btn_reports_due,btn_sign_in_out,btn_edit_attendance,btn_view_att,btn_leave,btn_view_docs,btn_view_bank_details,btn_view_managed_by,btn_view_supervisor;
    private TextView tv_sign_done,i_l_name,lbl_l_status,i_l_status,lbl_s_date,i_s_date,lbl_e_date,i_e_date,lbl_bank_details,i_bank_details,
            lbl_seta_name,i_seta_name,lbl_managed_by,i_managed_by,lbl_employer,i_employer,lbl_supervisor,i_supervisor,
            lbl_head_process_1,lbl_head_process_2,lbl_head_process_3,lbl_head_process_4,lbl_head_process_5,lbl_head_process_6,
            lbl_head_process_7,lbl_head_process_1_1,lbl_head_process_1_1_1,lbl_head_process_1_1_2,lbl_head_process_2_1,
            lbl_head_process_3_1,lbl_head_process_3_1_1,lbl_head_process_4_1,lbl_head_process_4_1_1,
            tv_btn_view_workstation,lbl_head_process_5_1,lbl_head_process_5_1_1,lbl_head_process_5_1_2,tv_btn_view_reports,lbl_head_process_6_1,tv_training_prog,lbl_head_process_7_1,tv_reports_done;
    private LinearLayout c_data_tv_btn_view_reports,c_data_process_6;
    private RelativeLayout c_tv_btn_view_workstation;
    private int is_workstation_assigned=0;
    private int is_training_program_assigned=0;
    private int is_claim_submitted=1;
    private int is_report_due =1;
    private String training_program_url="";
    boolean doubleBackToExitPressedOnce = false;
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_READ_EXTERNAL_STORAGE = 2;
    private static final int REQUEST_WRITE_EXTERNAL_STORAGE = 3;
    NestedScrollView c_dashboard;
    String[] permissionsStorage = {Manifest.permission.READ_EXTERNAL_STORAGE};
    int requestExternalStorage = 1;
    TextView tv_net;
    ImageView iv_net;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn" + CAId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        int permission = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissionsStorage, requestExternalStorage);
        }
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_EXTERNAL_STORAGE);
        bootStrapInit();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void bootStrapInit() {
        boolean isConnected = isNetworkConnected(this.getApplicationContext());
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
           // setAppTheme();
            validateLogin(baseApcContext, activityIn);
            setLayoutXml();
            setAppLogo();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            callDataApi();
            initDrawer();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            tv_net.setText("Online");
            iv_net.setImageResource(R.drawable.interview_accept_btn);
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");
        }else{
            validateLogin(baseApcContext, activityIn);
            setLayoutXml();
            setAppLogo();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            callDataApi();
            initDrawer();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            tv_net.setText("Offline");
            iv_net.setImageResource(R.drawable.interview_reject_btn);
            //initializeInputs();
            studentSessionObj = new OlumsStudentSession(baseApcContext);
            fetchOfflineData();
            showProgress(false, mContentView, mProgressView);
            printLogs(LogTag, "bootStrapInit", "exitConnected");
            //Toast.makeText(this, "not connected....", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void internetChangeBroadCast() {
        printLogs(LogTag, "internetChangeBroadCast", "init");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData() {
        printLogs(LogTag, "fetchVersionData", "exitConnected");
        syncUpdates(baseApcContext, activityIn);
    }

    @Override
    protected void verifyVersion() {
        printLogs(LogTag, "verifyVersion", "init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag, "verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag, "verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if (isUpdate) {
            Intent intent = new Intent(SDashboardDA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_dashboard");
        setContentView(R.layout.a_dashboard);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");

        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        heading1 = findViewById(R.id.heading1);
        c_dashboard = findViewById(R.id.c_dashboard);
        btn_sign_in_out=findViewById(R.id.btn_sign_in_out);
        btn_edit_attendance=findViewById(R.id.btn_edit_attendance);
        c_data_tv_btn_view_reports = findViewById(R.id.c_data_tv_btn_view_reports);
        btn_view_att=findViewById(R.id.btn_view_att);
        btn_leave=findViewById(R.id.btn_leave);
        btn_view_docs=findViewById(R.id.btn_view_docs);
        btn_view_bank_details=findViewById(R.id.btn_view_bank_details);
        btn_view_managed_by=findViewById(R.id.btn_view_managed_by);
        btn_view_supervisor=findViewById(R.id.btn_view_supervisor);
        i_l_name=findViewById(R.id.i_l_name);
        tv_sign_done=findViewById(R.id.tv_sign_done);
        c_tv_btn_view_workstation = findViewById(R.id.c_tv_btn_view_workstation);
        c_data_process_6 = findViewById(R.id.c_data_process_6);
        lbl_l_status=findViewById(R.id.lbl_l_status);
        i_l_status=findViewById(R.id.i_l_status);
        lbl_s_date=findViewById(R.id.lbl_s_date);
        i_s_date=findViewById(R.id.i_s_date);
        lbl_e_date=findViewById(R.id.lbl_e_date);
        i_e_date=findViewById(R.id.i_e_date);
        lbl_bank_details=findViewById(R.id.lbl_bank_details);
        i_bank_details=findViewById(R.id.i_bank_details);
        lbl_seta_name=findViewById(R.id.lbl_seta_name);
        i_seta_name=findViewById(R.id.i_seta_name);
        lbl_managed_by=findViewById(R.id.lbl_managed_by);
        i_managed_by=findViewById(R.id.i_managed_by);
        lbl_employer=findViewById(R.id.lbl_employer);
        i_employer=findViewById(R.id.i_employer);
        lbl_supervisor=findViewById(R.id.lbl_supervisor);
        i_supervisor=findViewById(R.id.i_supervisor);
        lbl_head_process_1=findViewById(R.id.lbl_head_process_1);
        lbl_head_process_2=findViewById(R.id.lbl_head_process_2);
        lbl_head_process_3=findViewById(R.id.lbl_head_process_3);
        lbl_head_process_4=findViewById(R.id.lbl_head_process_4);
        lbl_head_process_5=findViewById(R.id.lbl_head_process_5);
        lbl_head_process_6=findViewById(R.id.lbl_head_process_6);
        lbl_head_process_7=findViewById(R.id.lbl_head_process_7);
        lbl_head_process_1_1=findViewById(R.id.lbl_head_process_1_1);
        lbl_head_process_1_1_1=findViewById(R.id.lbl_head_process_1_1_1);
        lbl_head_process_1_1_2=findViewById(R.id.lbl_head_process_1_1_2);
        lbl_head_process_2_1=findViewById(R.id.lbl_head_process_2_1);
        lbl_head_process_3_1=findViewById(R.id.lbl_head_process_3_1);
        lbl_head_process_3_1_1=findViewById(R.id.lbl_head_process_3_1_1);
        btn_submit_claim=findViewById(R.id.btn_submit_claim);
        lbl_head_process_4_1=findViewById(R.id.lbl_head_process_4_1);
        lbl_head_process_4_1_1=findViewById(R.id.lbl_head_process_4_1_1);
        tv_btn_view_workstation=findViewById(R.id.tv_btn_view_workstation);
        lbl_head_process_5_1=findViewById(R.id.lbl_head_process_5_1);
        lbl_head_process_5_1_1=findViewById(R.id.lbl_head_process_5_1_1);
        lbl_head_process_5_1_2=findViewById(R.id.lbl_head_process_5_1_2);
        tv_btn_view_reports=findViewById(R.id.tv_btn_view_reports);
        btn_reports_due=findViewById(R.id.btn_reports_due);
        tv_reports_done=findViewById(R.id.tv_reports_done);
        lbl_head_process_6_1=findViewById(R.id.lbl_head_process_6_1);
        tv_training_prog=findViewById(R.id.tv_training_prog);
        lbl_head_process_7_1=findViewById(R.id.lbl_head_process_7_1);
        tv_net=findViewById(R.id.tv_net);
        iv_net=findViewById(R.id.iv_net);
        printLogs(LogTag, "initializeViews", "exit");

    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("b_S103_sign_in", R.string.b_S103_sign_in);
        btn_sign_in_out.setText(Label);
        Label = getLabelFromDb("b_S103_sign_out", R.string.b_S103_sign_out);
        btn_edit_attendance.setText(Label);
        Label = getLabelFromDb("b_S103_view_att", R.string.b_S103_view_att);
        btn_view_att.setText(Label);
        Label = getLabelFromDb("b_S103_leave", R.string.b_S103_leave);
        btn_leave.setText(Label);
        Label = getLabelFromDb("b_S103_view_docs", R.string.b_S103_view_docs);
        btn_view_docs.setText(Label);
        Label = getLabelFromDb("b_S103_view_bank_details", R.string.b_S103_view_bank_details);
        btn_view_bank_details.setText(Label);
        Label = getLabelFromDb("b_S103_view_managed_by", R.string.b_S103_view_managed_by);
        btn_view_managed_by.setText(Label);
        Label = getLabelFromDb("b_S103_view_supervisor", R.string.b_S103_view_supervisor);
        btn_view_supervisor.setText(Label);
        Label = getLabelFromDb("l_S103_l_status", R.string.l_S103_l_status);
        lbl_l_status.setText(Label);
        Label = getLabelFromDb("l_S103_s_date", R.string.l_S103_s_date);
        lbl_s_date.setText(Label);
        Label = getLabelFromDb("lthemed_button_action_S103_e_date", R.string.l_S103_e_date);
        lbl_e_date.setText(Label);
        Label = getLabelFromDb("l_S103_bank_details", R.string.l_S103_bank_details);
        lbl_bank_details.setText(Label);
        Label = getLabelFromDb("l_S103_seta_name", R.string.l_S103_seta_name);
        lbl_seta_name.setText(Label);
        Label = getLabelFromDb("l_S103_managed_by", R.string.l_S103_managed_by);
        lbl_managed_by.setText(Label);
        Label = getLabelFromDb("l_S103_employer", R.string.l_S103_employer);
        lbl_employer.setText(Label);
        Label = getLabelFromDb("l_S103_supervisor", R.string.l_S103_supervisor);
        lbl_supervisor.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_1", R.string.l_S103_head_process_1);
        lbl_head_process_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_2", R.string.l_S103_head_process_2);
        lbl_head_process_2.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_3", R.string.l_S103_head_process_3);
        lbl_head_process_3.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_4", R.string.l_S103_head_process_4);
        lbl_head_process_4.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_5", R.string.l_S103_head_process_5);
        lbl_head_process_5.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_6", R.string.l_S103_head_process_6);
        lbl_head_process_6.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_7", R.string.l_S103_head_process_7);
        lbl_head_process_7.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_1_1", R.string.l_S103_head_process_1_1);
        lbl_head_process_1_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_1_1_1", R.string.l_S103_head_process_1_1_1);
        lbl_head_process_1_1_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_1_1_2", R.string.l_S103_head_process_1_1_2);
        lbl_head_process_1_1_2.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_2_1", R.string.l_S103_head_process_2_1);
        lbl_head_process_2_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_3_1", R.string.l_S103_head_process_3_1);
        lbl_head_process_3_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_3_1_1", R.string.l_S103_head_process_3_1_1);
        lbl_head_process_3_1_1.setText(Label);
        Label = getLabelFromDb("b_S103_submit_claim", R.string.b_S103_submit_claim);
        btn_submit_claim.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_4_1", R.string.l_S103_head_process_4_1);
        lbl_head_process_4_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_4_1_1", R.string.l_S103_head_process_4_1_1);
        lbl_head_process_4_1_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_5_1", R.string.l_S103_head_process_5_1);
        lbl_head_process_5_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_5_1_1", R.string.l_S103_head_process_5_1_1);
        lbl_head_process_5_1_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_5_1_2", R.string.l_S103_head_process_5_1_2);
        lbl_head_process_5_1_2.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_6_1", R.string.l_S103_head_process_6_1);
        lbl_head_process_6_1.setText(Label);
        Label = getLabelFromDb("l_S103_head_process_7_1", R.string.l_S103_head_process_7_1);
        lbl_head_process_7_1.setText(Label);


        i_l_name.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_l_status.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_l_status.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_s_date.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_s_date.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_e_date.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_e_date.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_bank_details.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_bank_details.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        btn_view_bank_details.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_seta_name.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_seta_name.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_managed_by.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_managed_by.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        btn_view_managed_by.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_employer.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_employer.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_supervisor.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        i_supervisor.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        btn_view_supervisor.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_1_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_1_1_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_1_1_2.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_2.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_2_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_3.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_3_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_3_1_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_4.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_4_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_4_1_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tv_btn_view_workstation.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_5.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_5_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_5_1_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_5_1_2.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tv_btn_view_reports.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tv_reports_done.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_6.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_6_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        tv_training_prog.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_7.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lbl_head_process_7_1.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackgroundColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
            heading1.setBackgroundColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
            btn_view_att.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_leave.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btn_view_docs.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
           // c_dashboard.setBackground(getDrawable(getDrwabaleResourceId("all_back")));
            btn_view_bank_details.setBackground(getDrawable(getDrwabaleResourceId("theme_view_button_small")));
            btn_view_managed_by.setBackground(getDrawable(getDrwabaleResourceId("theme_view_button_small")));
            btn_view_supervisor.setBackground(getDrawable(getDrwabaleResourceId("theme_view_button_small")));

        }

        printLogs(LogTag, "initializeLabels", "exit");
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initializeInputs() {
        printLogs(LogTag, "initializeInputs", "init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        showProgress(true, mContentView, mProgressView);
        fetchData();
        printLogs(LogTag, "initializeInputs", "exit");
    }
    protected void initializeDynamicListeners() {
        printLogs(LogTag, "initializeDynamicListeners", "init");
        if(is_workstation_assigned==1) {
            printLogs(LogTag, "initializeDynamicListeners", "tv_btn_view_workstation");
            tv_btn_view_workstation.setOnClickListener(view -> {
                printLogs(LogTag, "initializeDynamicListeners", "exnewit");
                inBundle = new Bundle();
                Intent intent = new Intent(SDashboardDA.this, SWorkstationDetailsA.class);
                inBundle.putString("generator", ActivityId);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            });
        }
        if(is_training_program_assigned==1){
            printLogs(LogTag, "initializeDynamicListeners", "tv_training_prog");
            tv_training_prog.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                URL url = null;
                try {
                    url = new URL(training_program_url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                intent.setData(Uri.parse(String.valueOf(url)));
                startActivity(intent);
            });
        }
        if(is_claim_submitted==1){
            btn_submit_claim.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SDashboardDA.this, SPastClaimDA.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
            });
        } else{
            btn_submit_claim.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SDashboardDA.this, SPastClaimDA.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
            });
        }
        printLogs(LogTag, "initializeDynamicListeners", "exit");
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");


        btn_view_managed_by.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SDashboardDA.this, SGrantDetailsA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        });
        btn_view_supervisor.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SDashboardDA.this, SSupervisorDetailsA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        });
        btn_view_bank_details.setOnClickListener(view -> {
        inBundle = new Bundle();
        Intent intent = new Intent(SDashboardDA.this, SBankDA.class);
        inBundle.putString("generator", ActivityId);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });
        btn_sign_in_out.setOnClickListener(view -> {
        inBundle = new Bundle();
        Intent intent = new Intent(SDashboardDA.this, SCurrentAttDA.class);
        inBundle.putString("generator", ActivityId);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });
        btn_reports_due.setOnClickListener(view -> {
        inBundle = new Bundle();
        inBundle.putString("generator", ActivityId);
        Intent intent = new Intent(SDashboardDA.this, SAddFeedbackA.class);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });

        btn_leave.setOnClickListener(view -> {
        inBundle = new Bundle();
        inBundle.putString("generator", ActivityId);
        Intent intent = new Intent(SDashboardDA.this, SAddLeavesA.class);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });
        c_data_tv_btn_view_reports.setOnClickListener(view -> {
        inBundle = new Bundle();
        inBundle.putString("generator", ActivityId);
        Intent intent = new Intent(SDashboardDA.this, SFeedbackDA.class);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });
        btn_view_att.setOnClickListener(view -> {
        inBundle = new Bundle();
        inBundle.putString("generator", ActivityId);
        Intent intent = new Intent(SDashboardDA.this, SMonthlyAttDA.class);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();
        });
        btn_view_docs.setOnClickListener(view -> {
            inBundle = new Bundle();
            inBundle.putString("generator", ActivityId);
            Intent intent = new Intent(SDashboardDA.this, DocumentCenterListA.class);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        });
    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void fetchData() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_S_103+ "?token=" + token;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);

        @SuppressLint("SetTextI18n") JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject jsonObject= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "fetchData", "response : " + response);
                String Status = jsonObject.getString(KEY_STATUS);

                if (Status.equals("1")) {
                    DashboardDataArrayAdapter adapter = new DashboardDataArrayAdapter(getApplicationContext());
                    adapter.truncate();
                    JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);

                    for (int i = 0; i < dataM.length(); i++) {
                        JSONObject rec = dataM.getJSONObject(i);
                        String lName = rec.getString("learner_name");
                        String lStatus = rec.getString("learner_status");
                        String lStartDate = rec.getString("startDate");
                        String lEndDate = rec.getString("end_Date");
                        String bankName = rec.getString("bankName");
                        String setaName = rec.getString("seta_ame");
                        String managedBy = rec.getString("managedBy");
                        String employerName = rec.getString("employer_name");
                        String supervisorName = rec.getString("supervisor_name");
                        String attendance_btn_lbl = rec.getString("atendance_status");
                        String past_claim_btn = rec.getString("past_claim_btn");
                        String claim_status = rec.getString("current_claim_status");
                        String reportCompleted = rec.getString("completed_report_count");
                        String reportDue = rec.getString("reportOverDue_count");
                        String attendance_status = rec.getString("show_attendance_link");
                        String workstation = rec.getString("deptName");
                        String is_workstation_set = rec.getString("workstation_btn");
                        String is_training_program_set = rec.getString("training_program_status");
                        String trainingProgram = rec.getString("training_program");


                        DashboardDataArray dataArray = new DashboardDataArray(rec.getString("s_s_g_student_id"),
                                rec.getString("grant_id"),lName,lStatus,lStartDate,lEndDate,bankName,
                                bankName,setaName,managedBy,employerName,supervisorName,
                                rec.getString("attStatus"),rec.getString("s_s_g_is_edit_attendance"),attendance_status,
                                rec.getString("show_attendance_color"),attendance_btn_lbl,rec.getString("view_attendance_link"),
                                rec.getString("pastmonthCount"),claim_status,rec.getString("claim_color"),
                                past_claim_btn,is_workstation_set,workstation,reportCompleted,reportDue,trainingProgram,
                                is_training_program_set);

                        adapter.insert(dataArray);
                        if(bankName.equals("") || bankName.equals("null")){
                            String Label = getLabelFromDb("b_S103_add_bank", R.string.b_S103_add_bank);
                            btn_view_bank_details.setText(Label);
                        }
                        i_l_name.setText(lName);
                        i_l_status.setText(lStatus);
                        i_s_date.setText(lStartDate);
                        i_e_date.setText(lEndDate);
                        i_bank_details.setText(bankName);
                        i_seta_name.setText(setaName);
                        i_managed_by.setText(managedBy);
                        i_employer.setText(employerName);
                        i_supervisor.setText(supervisorName);
                        tv_btn_view_reports.setText(reportCompleted);
                        tv_btn_view_workstation.setText(workstation);
                        btn_sign_in_out.setText(attendance_btn_lbl);
                        btn_edit_attendance.setText(attendance_btn_lbl);
                        tv_sign_done.setText(attendance_btn_lbl);
                        if(Integer.parseInt(attendance_status)==1) {
                            btn_sign_in_out.setVisibility(View.VISIBLE);
                            btn_edit_attendance.setVisibility(GONE);
                            tv_sign_done.setVisibility(GONE);
                        }
                        else if(Integer.parseInt(attendance_status)==2) {
                            btn_sign_in_out.setVisibility(View.VISIBLE);
                            btn_edit_attendance.setVisibility(GONE);
                            tv_sign_done.setVisibility(GONE);
                        }
                        else if(Integer.parseInt(attendance_status)==4) {
                            btn_sign_in_out.setVisibility(GONE);
                            btn_edit_attendance.setVisibility(View.VISIBLE);
                            tv_sign_done.setVisibility(GONE);
                        }
                        else{
                            btn_sign_in_out.setVisibility(GONE);
                            btn_edit_attendance.setVisibility(GONE);
                            tv_sign_done.setVisibility(View.VISIBLE);
                        }
                        btn_submit_claim.setText(claim_status);
                        if(Integer.parseInt(past_claim_btn)==1){
                            btn_submit_claim.setBackgroundResource(getDrwabaleResourceId("themed_button_action"));
                            is_claim_submitted = 1;
                        }
                        else{
                            btn_submit_claim.setBackgroundResource(R.drawable.themed_small_button_action_r);
                            is_claim_submitted = 0;
                        }
                        if(Integer.parseInt(reportDue)>0){
                            is_report_due = 1;
                            btn_reports_due.setText(reportDue);
                            tv_reports_done.setVisibility(GONE);
                            btn_reports_due.setVisibility(View.VISIBLE);
                        }
                        else{
                            is_report_due = 0;
                            tv_reports_done.setVisibility(View.VISIBLE);
                            btn_reports_due.setVisibility(GONE);
                            btn_reports_due.setText(" - ");
                        }
                        printLogs(LogTag, "fetchData", "RESPONSE : " + is_workstation_set+"===="+Integer.parseInt(is_workstation_set));
                        if(Integer.parseInt(is_workstation_set)==1){
                            is_workstation_assigned = 1;
                            tv_btn_view_workstation.setBackgroundResource(R.drawable.theme_view_underline_small_p1);
                        }
                        else{
                            c_tv_btn_view_workstation.setBackground(getDrawable(R.drawable.themed_small_button_action_r));
                            c_tv_btn_view_workstation.setPadding(0,15,0,15);
                            tv_btn_view_workstation.setTextColor(Color.WHITE);
                        }
                        tv_training_prog.setText("PENDING");
                        if(Integer.parseInt(is_training_program_set)==1){
                            is_training_program_assigned = 1;
                            training_program_url = trainingProgram;
                            tv_training_prog.setText("VIEW AND DOWNLOADS");
                            tv_training_prog.setBackgroundResource(R.drawable.theme_view_underline_small_p1);
                        }
                        else{
                            c_data_process_6.setBackground(getDrawable(R.drawable.themed_small_button_action_r));
                            tv_training_prog.setTextColor(Color.WHITE);
                        }
                        initializeDynamicListeners();
                    }
                    showProgress(false, mContentView, mProgressView);
                } else {
                    String object = jsonObject.getString(KEY_DATA);
                    if(object.equals("USER_ID_BY_TOKEN_FAILED")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-S101";
                        String sMessage = getLabelFromDb("error_99_101", R.string.error_99_101);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                    else{
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-S101";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                         }
            } catch (JSONException e) {
                e.printStackTrace();
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-S103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "*/*");
               return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void fetchOfflineData() {

        DashboardDataArrayAdapter adapter = new DashboardDataArrayAdapter(getApplicationContext());
        DashboardDataArray dataArray = adapter.getById(userSessionObj.getUserId());
        String bankName = dataArray.bankName;
         String past_claim_btn = dataArray.past_claim_btn;
         String claim_status = dataArray.current_claim_status;
         String reportDue = dataArray.reportOverDue_count;
         String attendance_status = dataArray.show_attendance_link;
         String is_workstation_set = dataArray.workstation_btn;
        String workstation = dataArray.deptName;
         String is_training_program_set = dataArray.training_program_status;
         String trainingProgram = dataArray.training_program;

        if(bankName.equals("") || bankName.equals("null")){
            String Label = getLabelFromDb("b_S103_add_bank", R.string.b_S103_add_bank);
            btn_view_bank_details.setText(Label);
        }


        i_l_name.setText(dataArray.getLearner_name());
        i_l_status.setText(dataArray.getLearner_status());
        i_s_date.setText(dataArray.getStartDate());
        i_e_date.setText(dataArray.getEnd_Date());
        i_bank_details.setText(dataArray.getBankName());
        i_seta_name.setText(dataArray.getSeta_ame());
        i_managed_by.setText(dataArray.getManagedBy());
        i_employer.setText(dataArray.getEmployer_name());
        i_supervisor.setText(dataArray.getSupervisor_name());
        tv_btn_view_reports.setText(dataArray.completed_report_count);
        tv_btn_view_workstation.setText(workstation);
        btn_sign_in_out.setText(dataArray.atendance_status);
        btn_edit_attendance.setText(dataArray.atendance_status);
        tv_sign_done.setText(dataArray.atendance_status);
        if(Integer.parseInt(attendance_status)==1) {
            btn_sign_in_out.setVisibility(View.VISIBLE);
            btn_edit_attendance.setVisibility(GONE);
            tv_sign_done.setVisibility(GONE);
        }
        else if(Integer.parseInt(attendance_status)==2) {
            btn_sign_in_out.setVisibility(View.VISIBLE);
            btn_edit_attendance.setVisibility(GONE);
            tv_sign_done.setVisibility(GONE);
        }
        else if(Integer.parseInt(attendance_status)==4) {
            btn_sign_in_out.setVisibility(GONE);
            btn_edit_attendance.setVisibility(View.VISIBLE);
            tv_sign_done.setVisibility(GONE);
        }
        else{
            btn_sign_in_out.setVisibility(GONE);
            btn_edit_attendance.setVisibility(GONE);
            tv_sign_done.setVisibility(View.VISIBLE);
        }
        btn_submit_claim.setText(claim_status);
        if(Integer.parseInt(past_claim_btn)==1){
            btn_submit_claim.setBackgroundResource(getDrwabaleResourceId("themed_button_action"));
            is_claim_submitted = 1;
        }
        else{
            btn_submit_claim.setBackgroundResource(R.drawable.themed_small_button_action_r);
            is_claim_submitted = 0;
        }
        if(Integer.parseInt(reportDue)>0){
            is_report_due = 1;
            btn_reports_due.setText(reportDue);
            tv_reports_done.setVisibility(GONE);
            btn_reports_due.setVisibility(View.VISIBLE);
        }
        else{
            is_report_due = 0;
            tv_reports_done.setVisibility(View.VISIBLE);
            btn_reports_due.setVisibility(GONE);
            btn_reports_due.setText(" - ");
        }
        printLogs(LogTag, "fetchData", "RESPONSE : " + is_workstation_set+"===="+Integer.parseInt(is_workstation_set));
        if(Integer.parseInt(is_workstation_set)==1){
            is_workstation_assigned = 1;
            tv_btn_view_workstation.setBackgroundResource(R.drawable.theme_view_underline_small_p1);
        }
        else{
            c_tv_btn_view_workstation.setBackground(getDrawable(R.drawable.themed_small_button_action_r));
            c_tv_btn_view_workstation.setPadding(0,15,0,15);
            tv_btn_view_workstation.setTextColor(Color.WHITE);
        }
        tv_training_prog.setText("PENDING");
        if(Integer.parseInt(is_training_program_set)==1){
            is_training_program_assigned = 1;
            training_program_url = trainingProgram;
            tv_training_prog.setText("VIEW AND DOWNLOADS");
            tv_training_prog.setBackgroundResource(R.drawable.theme_view_underline_small_p1);
        }
        else{
            c_data_process_6.setBackground(getDrawable(R.drawable.themed_small_button_action_r));
            tv_training_prog.setTextColor(Color.WHITE);
        }
        initializeDynamicListeners();


    }


    @Override
    public void onBackPressed() {
        printLogs(LogTag, "onBackPressed", "SDashboardA");
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerBroadcastIC();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBroadcastIC();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBroadcastIC();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }
}