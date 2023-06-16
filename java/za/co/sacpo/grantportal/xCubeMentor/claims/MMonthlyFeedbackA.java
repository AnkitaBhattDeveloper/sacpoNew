package za.co.sacpo.grantportal.xCubeMentor.claims;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MMonthlyFeedbackAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MMonthlyFeedbackObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;

public class MMonthlyFeedbackA extends BaseAPCPrivate {
    public String ActivityId = "332";
    public View mProgressView, mContentView;
    public TextView activity_heading,lblView;
    private RecyclerView recyclerViewQ;
    public WebView wv_information;

    MMonthlyFeedbackA thisClass;
    private String generator,m_student_name,stipend_id,student_id;
    private Button btn_next,btn_back;
    private TextView lblAttendance;
    private String feedback_ratings_input ="";
    public MMonthlyFeedbackObj rDataObj = new MMonthlyFeedbackObj();
    private List<MMonthlyFeedbackObj.Item> rDataObjList = null;
    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {
        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");

        Bundle activeInputUri = getIntent().getExtras();
        m_student_name = activeInputUri.getString("m_student_name");
        generator = activeInputUri.getString("generator");
        stipend_id = activeInputUri.getString("stipend_id");
        student_id = activeInputUri.getString("student_id");
        printLogs(LogTag,"onCreate","m_student_name "+m_student_name);
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);
        printLogs(LogTag,"onCreate","student_id "+student_id);
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            fetchData();
            showProgress(false, mContentView, mProgressView);
        }
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btn_next = (Button) findViewById(R.id.btn_next);

        lblAttendance = (TextView) findViewById(R.id.lblAttendance);
        lblAttendance.setText(m_student_name);
        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rvMmonthlyfeedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        View recyclerView = findViewById(R.id.rvMmonthlyfeedback);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeListeners() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(feedback_ratings_input.equals("")){
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-107";
                    String sMessage=getLabelFromDb("error_S231_107",R.string.error_S231_107);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
                else{
                    putApi(feedback_ratings_input);
                }
                /*Bundle inputUri = new Bundle();
                inputUri.putString("generator", ActivityId);
                inputUri.putString("month_year", month_year);
                inputUri.putString("student_id", student_id);
                inputUri.putString("stipend_id", stipend_id);
                inputUri.putString("mName", mName);
                Intent intent = new Intent(MMonthlyFeedbackA.this, MLearnerGrantDetailsA.class);
                printLogs(LogTag,"onOptionsItemSelected","MLearnerGrantDetailsA");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();*/
            }
        });
       /* btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inputUri = new Bundle();
                inputUri.putString("generator", ActivityId);
                inputUri.putString("month_year", month_year);
                inputUri.putString("student_id", student_id);
                inputUri.putString("stipend_id", stipend_id);
                Intent intent = new Intent(MMonthlyFeedbackA.this, MClaimAttApproveA.class);
                printLogs(LogTag,"onOptionsItemSelected","MWeeklyReport");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
            }
        });*/
    }
    @Override
    protected void initializeInputs() {
    }

    @Override
    protected void initializeLabels() {
        String Label = getLabelFromDb("h_332",R.string.h_332);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("l_332_btn_next",R.string.l_332_btn_next);
        lblView = (TextView)findViewById(R.id.btn_next);
        lblView.setText(Label);
        Label = getLabelFromDb("l_332_btn_back",R.string.l_332_btn_back);
        lblView = (TextView)findViewById(R.id.btn_back);
        lblView.setText(Label);
        Label = getLabelFromDb("i_332_message",R.string.i_332_message);
        wv_information.loadData(Label, "text/html", "UTF-8");
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_mmonthly_feedback);
    }
    public void callDataApi(){
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void fetchData()    {
        showProgress(true,mContentView,mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_GET_MONTHLY_FEEDBACK;
        FINAL_URL= FINAL_URL + "?token=" + token + "&stipend_id=" + stipend_id;
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
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("f_q_id"));
                            String SFQues3 = rec.getString("f_q_question");
                            String SFQ_Id4 = rec.getString("f_q_id");
                            String SANS_TYPE5 = rec.getString("set_answer_type");
                            String SANS_ID6 = rec.getString("set_answer_id");
                            String SANS_TYPE_17 = rec.getString("answer_type1");
                            String SANS_TYPE_ID18 = rec.getString("answer_type_id1");
                            String SANS_TYPE_29 = rec.getString("answer_type2");
                            String SANS_TYPE_ID210 = rec.getString("answer_type_id2");
                            String SANS_TYPE_311 = rec.getString("answer_type3");
                            String SANS_TYPE_ID312 = rec.getString("answer_type_id3");
                            String SANS_TYPE_413 = rec.getString("answer_type4");
                            String SANS_TYPE_ID414 = rec.getString("answer_type_id4");
                            String SANS_TYPE_515 = rec.getString("answer_type5");
                            String SANS_TYPE_ID516 = rec.getString("answer_type_id5");


                            rDataObj.addItem(rDataObj.createItem(pos,aId2,SFQues3,SFQ_Id4,SANS_TYPE5,SANS_ID6,SANS_TYPE_17,SANS_TYPE_ID18,SANS_TYPE_29,SANS_TYPE_ID210,SANS_TYPE_311,SANS_TYPE_ID312,SANS_TYPE_413,SANS_TYPE_ID414,SANS_TYPE_515,SANS_TYPE_ID516));
                            //   showList();
                            //printLogs(LogTag,"fetchData","datshowListaM : "+response);
                        }
                        showList();
                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-103";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
            Intent intent = new Intent(MMonthlyFeedbackA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void setupRecyclerView(RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MMonthlyFeedbackAdapter(rDataObjList,baseApcContext,activityIn,this));
    }

    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<MMonthlyFeedbackObj.Item> FormData = rDataObj.getITEMS();
        MMonthlyFeedbackAdapter adapter = new MMonthlyFeedbackAdapter(FormData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentView,mProgressView);
    }
    public void setStatusRemarks(String feedback_ratings){
        printLogs(LogTag,"setStatusRemarks","GET_ATT_APPROVAL f: "+feedback_ratings);
        feedback_ratings_input = feedback_ratings;
    }

    public void getAllDetails() {
        final TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
                tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
        final LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;
        LL_GD = findViewById(R.id.LL_GD);
        LL_LD = findViewById(R.id.LL_LD);
        LL_LT = findViewById(R.id.LL_LT);
        LL_GAD = findViewById(R.id.LL_GAD);

        LL_GD.setVisibility(View.VISIBLE);
        LL_LD.setVisibility(View.GONE);
        LL_LT.setVisibility(View.GONE);
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
        lblView.setText(Label);

        Label = getLabelFromDb("h_LDetails",R.string.h_LDetails);
        lblView = (TextView)findViewById(R.id.h_LDetails);
        lblView.setText(Label);

        Label = getLabelFromDb("h_GADetail",R.string.h_GADetail);
        lblView = (TextView)findViewById(R.id.h_GADetails);
        lblView.setText(Label);

        Label = getLabelFromDb("h_TotalLeaves",R.string.h_TotalLeaves);
        lblView = (TextView)findViewById(R.id.h_totalLeaves);
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
                        showProgress(false, mContentView, mProgressView);
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

    private void putApi(final String feedback_ratings) {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_ADD_MONTHLY_FEEDBACK;
        FINAL_URL=FINAL_URL+"/token/"+token+"/stipend_id/"+stipend_id;

        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        printLogs(LogTag,"putapi","SHOW_Stipend_id:"+feedback_ratings);

        StringRequest stringRequest = new StringRequest(Request.Method.PUT,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;

                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")) {
                        /*showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_S231_title",R.string.m_S231_title);
                        String sMessage=getLabelFromDb("m_S231_message",R.string.m_S231_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMMonthlyFeedbackA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose, thisClass);*/
                        Bundle inputUri = new Bundle();
                        inputUri.putString("generator", ActivityId);
                        inputUri.putString("m_student_name", m_student_name);
                        inputUri.putString("stipend_id", stipend_id);
                        inputUri.putString("student_id", student_id);
                        Intent intent = new Intent(MMonthlyFeedbackA.this, MLearnerGrantDetailsA.class);
                        printLogs(LogTag,"onOptionsItemSelected","MMonthlyFeedback");
                        intent.putExtras(inputUri);
                        startActivity(intent);
                        finish();
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }

                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-105";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-106";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })

        {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("feedback_ratings", feedback_ratings);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MMonthlyFeedbackA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void customRedirector(){
        if(generator.equals("S231")) {
            Intent intent = new Intent(MMonthlyFeedbackA.this, MDashboardDA.class);
            printLogs(LogTag,"customRedirectorr","MDashboardDA");
            /*Bundle activeUri = new Bundle();
            activeUri.putString("generator", "195");
            intent.putExtras(activeUri);*/
            startActivity(intent);
            finish();
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("103")){*/
        Bundle inputUri = new Bundle();
        inputUri.putString("generator", ActivityId);
        inputUri.putString("m_student_name", m_student_name);
        inputUri.putString("stipend_id", stipend_id);
        inputUri.putString("student_id", student_id);
        Intent intent = new Intent(MMonthlyFeedbackA.this, MClaimAttApproveA.class);
        printLogs(LogTag,"onOptionsItemSelected","MClaimAttApproveA");
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();


          /*  }
            else if(generator.equals("120")){
                Intent intent = new Intent(MWeeklyReport.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
                startActivity(intent);
                finish();
            }
            else {
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
            }*/
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
