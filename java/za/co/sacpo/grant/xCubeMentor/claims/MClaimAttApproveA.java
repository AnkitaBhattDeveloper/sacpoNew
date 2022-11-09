package za.co.sacpo.grant.xCubeMentor.claims;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MClaimAttApprovalAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MClaimAttAppObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

public class MClaimAttApproveA extends BaseAPCPrivate {

    public String ActivityId = "M329";
    MClaimAttApproveA thisClass;
    public View mProgressView, mContentView, mProgressRView, mContentRView,heading,heading2,heading3,heading4
            ,l_heading1,l_heading2,l_heading3,lt_heading1,lt_heading2,lt_heading3
            ,g_heading1,g_heading2,g_heading3;
    public TextView lblView, lblAttendance;
    private Button btnAddRemark, btn_back;
    public WebView wv_information;
    private int selectedYear, selectedMonth, selectedStudent, selectedStatus;
    private RecyclerView recyclerViewQ;
    Bundle activeUri;
    String student_id, generator, m_student_name, remark, m_remark;
    private String month_year, stipend_id, mName,attendance_approval,attendance_input_str;
    private int all_approved=0;
    private EditText inputRemarks;
    public MClaimAttAppObj rDataObj = new MClaimAttAppObj();
    private List<MClaimAttAppObj.Item> rDataObjList = null;
    private  LinearLayout button_Container,addRemarksContainer;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "baseApcContext");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        month_year = activeInputUri.getString("month_year");
        student_id = null;
        m_student_name = null;
        m_remark = null;
        generator = null;
        attendance_input_str="";
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
            selectedStudent = Integer.parseInt(student_id);
            printLogs(LogTag, "onCreate", "student_id" + student_id);
        }
        if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
            printLogs(LogTag, "onCreate", "m_student_name" + m_student_name);
        }
        if (inputIntent.hasExtra("stipend_id")) {
            stipend_id = activeInputUri.getString("stipend_id");
            printLogs(LogTag, "onCreate", "stipend_id" + stipend_id);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag, "onCreate", "GENERATOR ID " + generator);
        }
        bootStrapInit();


    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
        if (isConnected) {
            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);
            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initBackBtn();
            initializeViews();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            getAllDetails();
            callHeaderBuilder();
            fetchData(selectedStudent);
            showProgress(false, mContentView, mProgressView);
        }
    }



    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading     = findViewById(R.id.heading);
        heading2    = findViewById(R.id.heading2);
        heading3    = findViewById(R.id.heading3);
        heading4    = findViewById(R.id.heading4);
        l_heading1  = findViewById(R.id.l_heading1);
        l_heading2  = findViewById(R.id.l_heading2);
        l_heading3  = findViewById(R.id.l_heading3);
        lt_heading1 = findViewById(R.id.lt_heading1);
        lt_heading2 = findViewById(R.id.lt_heading2);
        lt_heading3 = findViewById(R.id.lt_heading3);
        g_heading1  = findViewById(R.id.g_heading1);
        g_heading2  = findViewById(R.id.g_heading2);
        g_heading3  = findViewById(R.id.g_heading3);

        btnAddRemark = (Button) findViewById(R.id.btnAddRemark);
        inputRemarks =  findViewById(R.id.inputRemarks);
        btn_back = (Button) findViewById(R.id.btn_back);
        lblAttendance = (TextView) findViewById(R.id.lblAttendance);
        button_Container = findViewById(R.id.button_Container);
        addRemarksContainer = findViewById(R.id.addRemarksContainer);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVMViewAtt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag, "initializeViews", "exit");

        lblAttendance.setText(m_student_name);
        lblAttendance.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));


        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


    }

    @Override
    protected void initializeListeners() {

        btnAddRemark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //next(student_id);
                if(all_approved==1){
                    next(student_id);
                }
                else{
                    showProgress(true,mContentView,mProgressView);
                    final String remark = inputRemarks.getText().toString();
                    putApi(attendance_input_str,remark);
                }

                /*if(all_approved !=1){
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=getLabelFromDb("error_M407_107",R.string.error_M407_107);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
                else{
                    showProgress(true,mContentView,mProgressView);
                    final String remark = inputRemarks.getText().toString();
                    putApi(attendance_input_str,remark);
                }*/
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle activeUri = new Bundle();
                Intent intent = new Intent(MClaimAttApproveA.this, MPendingClaimsA.class);
                activeUri.putString("generator", "195");
                activeUri.putString("student_id", student_id);
                activeUri.putString("student_name", m_student_name);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    protected void initializeInputs() {
        button_Container.setVisibility(View.GONE);
        addRemarksContainer.setVisibility(View.GONE);
    }

    @Override
    protected void initializeLabels() {

        String Label = getLabelFromDb("h_329", R.string.h_329);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_329_btn_next", R.string.l_329_btn_next);
        lblView = (Button) findViewById(R.id.btnAddRemark);
        lblView.setText(Label);
        /*Label = getLabelFromDb("l_329_btn_back", R.string.l_329_btn_back);
        lblView = (Button) findViewById(R.id.btn_back);
        lblView.setText(Label);*/
        Label = getLabelFromDb("lbl_189_remark",R.string.lbl_189_remark);
        lblView = (TextView)findViewById(R.id.lblRemarks);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("i_329_message",R.string.i_329_message);
        wv_information.loadData(Label, "text/html", "UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            heading4.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            l_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            lt_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading1.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading2.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));
            g_heading3.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_textColor")));

            btnAddRemark.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputRemarks.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));

        }

    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_matt_summary_list);

    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    public void fetchData(int student) {
        showProgress(true, mContentRView, mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_REF_329;
        FINAL_URL = FINAL_URL + "?token=" + token + "&stipend_id=" + stipend_id;
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        //
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            printLogs(LogTag, "fetchData", "dataM : " + dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("attendance_id");
                            String aDate3 = rec.getString("date");
                            String aDay4 = rec.getString("day");
                            String aDisCord5 = rec.getString("distance_from_workstation");
                            String aLearnerStatus6 = rec.getString("learner_status");
                            String aSignInHours8 = rec.getString("sign_in_hours");
                            String aSignInMin9 = rec.getString("sign_in_min");
                            String aSignInSec10 = rec.getString("sign_in_sec");
                            String aSignOutHours11 = rec.getString("sign_out_hours");
                            String aSignOutMin12 = rec.getString("sign_out_min");
                            String aSignOutSec13 = rec.getString("sign_out_sec");
                            String aSignInTime14 = rec.getString("sign_in_time");
                            String aSignOutTime15 = rec.getString("sign_out_time");
                            String aHoursWorked16 = rec.getString("hours_worked");
                            String aLearnerComment17 = rec.getString("learner_comment");
                            String aLearnerStatusId7 = rec.getString("learner_status_id");
                            //LLAttendanceContainer.setVisibility(View.VISIBLE);
                            rDataObj.addItem(rDataObj.createItem("",aId2,aDate3,aDay4,aDisCord5,aLearnerStatus6,aLearnerStatusId7,aSignInHours8,aSignInMin9,aSignInSec10,aSignOutHours11,aSignOutMin12,aSignOutSec13,aSignInTime14,aSignOutTime15,aHoursWorked16,aLearnerComment17));
                            showList();
                            button_Container.setVisibility(View.VISIBLE);
                            addRemarksContainer.setVisibility(View.VISIBLE);
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                        button_Container.setVisibility(View.VISIBLE);
                        //addRemarksContainer.setVisibility(View.VISIBLE);
                        all_approved=1;
                    } else {

                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentRView, mProgressRView);
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-103";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentRView, mProgressRView);
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-104";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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

    public void getAllDetails() {
        final TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
                tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
        final LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;
        LL_GD = findViewById(R.id.LL_GD);
        LL_LD = findViewById(R.id.LL_LD);
        LL_LT = findViewById(R.id.LL_LT);
        LL_GAD = findViewById(R.id.LL_GAD);

        LL_GD.setVisibility(View.GONE);
        LL_LD.setVisibility(View.GONE);
        LL_LT.setVisibility(View.VISIBLE);
        LL_GAD.setVisibility(View.GONE);

        tv_leadEmpName = findViewById(R.id.txtLeadEmpName);
        tv_setaName = findViewById(R.id.txtSetaName);
        tv_GrantName = findViewById(R.id.txtGrantName);
        tv_GrantNo = findViewById(R.id.txtGrantNo);
        tv_LName = findViewById(R.id.txtLearnerName);
        tv_LEmail = findViewById(R.id.txtLearnerEmail);
        tv_LId = findViewById(R.id.txtLearnerId);
        tv_LTele = findViewById(R.id.txtLearnerTele);
        tv_GAName = findViewById(R.id.txtGrantAdminName);
        tv_GAEmail = findViewById(R.id.txtGrantEmail);
        tv_GATele = findViewById(R.id.txtGrantATele);
        tv_AnnualL = findViewById(R.id.txtTotalALeave);
        tv_PaidL = findViewById(R.id.txtTotalPaidLeave);
        tv_UnpaidL = findViewById(R.id.txtTotalUnPaidLeave);
        tv_SickL = findViewById(R.id.txtTotalSickLeave);

        String Label = getLabelFromDb("lblLeadEmpName",R.string.lblLeadEmpName);
        lblView = (TextView)findViewById(R.id.lblLeadEmpName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblSetaName",R.string.lblSetaName);
        lblView = (TextView)findViewById(R.id.lblSetaName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGrantName",R.string.lblGrantName);
        lblView = (TextView)findViewById(R.id.lblGrantName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGrantNo",R.string.lblGrantNo);
        lblView = (TextView)findViewById(R.id.lblGrantNo);
        lblView.setText(Label);

        Label = getLabelFromDb("lblLearnerName",R.string.lblLearnerName);
        lblView = (TextView)findViewById(R.id.lblLearnerName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearnerEmail",R.string.lblLearnerEmail);
        lblView = (TextView)findViewById(R.id.lblLearnerEmail);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearnerTele",R.string.lblLearnerTele);
        lblView = (TextView)findViewById(R.id.lblLearnerTele);
        lblView.setText(Label);
        Label = getLabelFromDb("lblLearneriD",R.string.lblLearneriD);
        lblView = (TextView)findViewById(R.id.lblLearnerId);
        lblView.setText(Label);

        Label = getLabelFromDb("lblTotalLeave",R.string.lblTotalLeave);
        lblView = (TextView)findViewById(R.id.lblTotalALeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalUnPaidLeave",R.string.lblTotalUnPaidLeave);
        lblView = (TextView)findViewById(R.id.lblTotalUnPaidLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalPaidLeave",R.string.lblTotalPaidLeave);
        lblView = (TextView)findViewById(R.id.lblTotalPaidLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("lblTotalSickLeave",R.string.lblTotalSickLeave);
        lblView = (TextView)findViewById(R.id.lblTotalSickLeave);
        lblView.setText(Label);

        Label = getLabelFromDb("lblGAName",R.string.lblGAName);
        lblView = (TextView)findViewById(R.id.lblGrantAdminName);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGAEmail",R.string.lblGAEmail);
        lblView = (TextView)findViewById(R.id.lblGrantEmail);
        lblView.setText(Label);
        Label = getLabelFromDb("lblGATele",R.string.lblGATele);
        lblView = (TextView)findViewById(R.id.lblGranATele);
        lblView.setText(Label);

        Label = getLabelFromDb("h_GrantDetails",R.string.h_GrantDetails);
        lblView = (TextView)findViewById(R.id.h_GrantDetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_LDetails",R.string.h_LDetails);
        lblView = (TextView)findViewById(R.id.h_LDetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_GADetail",R.string.h_GADetail);
        lblView = (TextView)findViewById(R.id.h_GADetails);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_TotalLeaves",R.string.h_TotalLeaves);
        lblView = (TextView)findViewById(R.id.h_totalLeaves);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.GET_ALL_DETAILS_URL;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student_id;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        tv_leadEmpName.setText(dataM.getString("lead_emp"));
                        tv_setaName.setText(dataM.getString("setaName"));
                        tv_GrantName.setText(dataM.getString("grantName"));
                        tv_GrantNo.setText(dataM.getString("g_d_grant_number"));
                        tv_GAName.setText(dataM.getString("leadManager"));
                        tv_GAEmail.setText(dataM.getString("leadManagerEmail"));
                        tv_GATele.setText(dataM.getString("leadManagerContact"));
                        tv_AnnualL.setText(dataM.getString("annual_leave"));
                        tv_SickL.setText(dataM.getString("sick_leave"));
                        tv_PaidL.setText(dataM.getString("other_paid_leave"));
                        tv_UnpaidL.setText(dataM.getString("unpaid_leave"));
                        tv_LName.setText(dataM.getString("studName"));
                        tv_LId.setText(dataM.getString("s_id_no"));
                        tv_LEmail.setText(dataM.getString("stu_email"));
                        tv_LTele.setText(dataM.getString("stu_cell"));
                       // showProgress(false, mContentView, mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_M345_101 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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

    public void callHeaderBuilder(){

        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_M407_in_date",R.string.t_head_M407_in_date);
        String tHeader4 = getLabelFromDb("t_head_M407_in_day",R.string.t_head_M407_in_day);
        String tHeader16 = getLabelFromDb("t_head_M407_in_d",R.string.t_head_M407_in_d);
        String tHeader5 = getLabelFromDb("t_head_M407_in_edit",R.string.t_head_M407_in_edit);
        String tHeader6 = getLabelFromDb("t_head_M407_in_time",R.string.t_head_M407_in_time);
        String tHeader14 = getLabelFromDb("t_head_M407_out_time",R.string.t_head_M407_out_time);
        String tHeader15 = getLabelFromDb("t_head_M407_hours_worked",R.string.t_head_M407_hours_worked);
        String tHeader17 = getLabelFromDb("t_head_M407_l_c",R.string.t_head_M407_l_c);

        rDataObj.addItem(rDataObj.createItem("","",tHeader3,tHeader4,tHeader5,tHeader6,tHeader14,tHeader15,tHeader16,tHeader17,"","","","","","",""));
    }


    public void setStatusRemarks(String attendanceString) {
        //printLogs(LogTag, "setStatusRemarks", "attendanceString   : " + attendanceString);
        /////11111////putApi(attendance_approval);
        attendance_input_str=attendanceString;

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag, "setupRecyclerView", "init");
        recyclerView.setAdapter(new MClaimAttApprovalAdapter(rDataObjList, baseApcContext, activityIn, this));
    }

    public void showList() {
        printLogs(LogTag, "fetchData", "showList : ");
        List<MClaimAttAppObj.Item> FormData = rDataObj.getITEMS();
        MClaimAttApprovalAdapter adapter = new MClaimAttApprovalAdapter(FormData, baseApcContext, activityIn, this);
        recyclerViewQ.setAdapter(adapter);
        recyclerViewQ.setNestedScrollingEnabled(false);
        showProgress(false, mContentRView, mProgressRView);
    }


    private void putApi(final String attendance_approval,final String remark) {

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_ATTENDENCE_APPRVAL;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        printLogs(LogTag, "putapi", "SHOW_Stipend_id:" + attendance_approval);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put("attendance_approval", attendance_approval);
            jsonBody.put("remark", remark);
            jsonBody.put("student_id", student_id);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = getLabelFromDb("m_329_title", R.string.m_329_title);
                        String sMessage = getLabelFromDb("m_329_message", R.string.m_329_message);
                        String sButtonLabelClose = "Close";
                        //Toast.makeText(thisClass, " Apply Intent", Toast.LENGTH_SHORT).show();

                        next(student_id);
                        // ErrorDialog.showSuccessDialogMAttSummaryList(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose, thisClass);

                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-104";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }

                } catch (JSONException e) {
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-105";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false, mContentView, mProgressView);
                String sTitle = "Error :" + ActivityId + "-106";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    private void next(String student_id) {
        Bundle inputUri = new Bundle();
        inputUri.putString("student_id", student_id);
        inputUri.putString("generator", "329");
        inputUri.putString("stipend_id", stipend_id);
        inputUri.putString("m_student_name", m_student_name);
        Intent intent = new Intent(baseApcContext, MMonthlyFeedbackA.class);
        intent.putExtras(inputUri);
        startActivity(intent);
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
            Intent intent = new Intent(MClaimAttApproveA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void customRedirector() {
            Intent intent = new Intent(MClaimAttApproveA.this, MDashboardDA.class);
            printLogs(LogTag, "customRedirectorr", "MDashboardDA");
            Bundle activeUri = new Bundle();
            activeUri.putString("student_id", student_id);
            activeUri.putString("m_student_name", m_student_name);
            activeUri.putString("stipend_id", stipend_id);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag, "onOptionsItemSelected", "init");
        if (item.getItemId() == android.R.id.home) {
            Bundle activeUri = new Bundle();
            Intent intent = new Intent(MClaimAttApproveA.this, MPendingClaimsA.class);
            activeUri.putString("generator", "195");
            activeUri.putString("student_id", student_id);
            activeUri.putString("m_student_name", m_student_name);
            activeUri.putString("stipend_id", stipend_id);
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
        }
        return true;
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
