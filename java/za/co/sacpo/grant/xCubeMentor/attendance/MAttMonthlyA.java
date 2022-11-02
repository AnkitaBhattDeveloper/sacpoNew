package za.co.sacpo.grant.xCubeMentor.attendance;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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

import za.co.sacpo.grant.xCubeLib.adapter.MAttSummaryAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MAttSummaryObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;


public class MAttMonthlyA extends BaseAPCPrivate {


    private String ActivityId = "M406";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public TextView lblView,lblAttendance;
    private int selectedStudent;
    private RecyclerView recyclerViewQ;
    Bundle activeUri;
    String student_id,generator,m_student_name,date_input,month,year;

    public MAttSummaryObj rDataObj = new MAttSummaryObj();
    private List<MAttSummaryObj.Item> rDataObjList = null;
    private MAttMonthlyA thisClass;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","baseApcContext");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        student_id=null;
        m_student_name=null;
        date_input=null;
        generator=null;
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
            selectedStudent = Integer.parseInt(student_id);
        }
        if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
        }
        if (inputIntent.hasExtra("date_input")) {
            date_input = activeInputUri.getString("date_input");
        }
        if (inputIntent.hasExtra("month")) {
            month = activeInputUri.getString("month");
        }
        if (inputIntent.hasExtra("year")) {
            year = activeInputUri.getString("year");
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
        }
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
           // initDrawer();

            initializeViews();
            initBackBtn();
            showProgress(true,mContentView,mProgressView);
            initializeLabels();
            initializeListeners();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
            initializeInputs();
            getAllDetails();
            callHeaderBuilder();

           /* if(student_id!=null) {
                fetchData(selectedYear, selectedMonth, selectedStatus, selectedStudent);
            }*/

            showProgress(false,mContentView,mProgressView);
        }
    }
    @Override
    protected void internetChangeBroadCast(){
        printLogs(LogTag,"internetChangeBroadCast","init");
        registerBroadcastIC();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+ isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MAttMonthlyA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_view_att);
        printLogs(LogTag,"setLayoutXml","a_m_view_att");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);

        lblAttendance = (TextView) findViewById(R.id.lblAttendance);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVMViewAtt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");

        lblAttendance.setText(m_student_name);

    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("h_195",R.string.h_195);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label+" - "+month+"/"+year);
    }
    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");
        fetchData(date_input, selectedStudent);
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
    }

    public void callDataApi(){
        printLogs(LogTag,"callDataApi","init");
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }


    public void fetchData(String date_input, int student){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_406;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student+"&date="+date_input;
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
                        //
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {

                            int pos = i+1;
                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                            String mAsDate3 = rec.getString("date");
                            String mday4 = rec.getString("day");
                            String mTime5 = rec.getString("time_spent");
                            String mLoginTime7 = rec.getString("login_time");
                            String lblLogoutTime6= rec.getString("logout_time");
                            String mcord_diff8 = rec.getString("out_of_range");
                            String attt_id9 = rec.getString("s_a_id");
                            String approval_status10 = rec.getString("attendance_status");
                            String remark11 = rec.getString("learner_comment_btn");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,mAsDate3,mday4,mTime5,lblLogoutTime6,mLoginTime7,mcord_diff8,attt_id9,approval_status10,remark11));
                         //   showList();
                            //printLogs(LogTag,"fetchData","datshowListaM : "+response);
                        }
                        //int lastPost =  dataM.length();
                        //callFooterRow(lastPost);
                        showList();
                        showProgress(false,mContentRView,mProgressRView);
                    }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }

                    else{

                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
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
                        showProgress(false,mContentRView,mProgressRView);
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

    public void getAllDetails() {
        final TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
                tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
        final LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;
        LL_GD = findViewById(R.id.LL_GD);
        LL_LD = findViewById(R.id.LL_LD);
        LL_LT = findViewById(R.id.LL_LT);
        LL_GAD = findViewById(R.id.LL_GAD);

        LL_GD.setVisibility(View.VISIBLE);
        LL_LD.setVisibility(View.VISIBLE);
        LL_LT.setVisibility(View.VISIBLE);
        LL_GAD.setVisibility(View.VISIBLE);

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

        public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_195_date",R.string.t_head_195_date);
        String tHeader4 = getLabelFromDb("t_head_l_195_day",R.string.t_head_l_195_day);
        String tHeader5 = getLabelFromDb("t_head_l_195_time",R.string.t_head_l_195_time);
        String tHeader6 = getLabelFromDb("t_head_l_195_login_time",R.string.t_head_l_195_logout_time);
        String tHeader7 = getLabelFromDb("t_head_l_195_logout_time",R.string.t_head_l_195_login_time);
        String tHeader8 = getLabelFromDb("t_head_l_195_DISTENCE",R.string.t_head_l_195_DISTENCE);
        String tHeader9 ="";
        String tHeader11 = getLabelFromDb("t_head_l_195_COMMENT",R.string.t_head_l_195_COMMENT);
        String tHeader10 = getLabelFromDb("t_head_l_195_STATUS",R.string.t_head_l_195_STATUS);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11));

        }

    private void initBackBtn(){
            printLogs(LogTag,"initBackBtn","init");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MAttSummaryAdapter(rDataObjList,baseApcContext,activityIn,this));

    }

    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<MAttSummaryObj.Item> FormData = rDataObj.getITEMS();
        MAttSummaryAdapter adapter = new MAttSummaryAdapter(FormData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        recyclerViewQ.setNestedScrollingEnabled(false);
        showProgress(false,mContentRView,mProgressRView);
        showProgress(false,mContentView,mProgressView);
    }

    public void customRedirector(){
            Bundle activeUri = new Bundle();
            activeUri.putString("generator", generator);
            activeUri.putString("m_student_name", m_student_name);
            activeUri.putString("student_id", student_id);
            Intent intent = new Intent(MAttMonthlyA.this,MAttSummaryA.class);
            printLogs(LogTag,"onOptionsItemSelected","mDashboard");
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            printLogs(LogTag,"onOptionsItemSelected","init");
            if (item.getItemId() == android.R.id.home) {
                Bundle activeUri = new Bundle();
                activeUri.putString("generator", generator);
                activeUri.putString("m_student_name", m_student_name);
                activeUri.putString("student_id", student_id);
                Intent intent = new Intent(MAttMonthlyA.this,MAttSummaryA.class);
                printLogs(LogTag,"onOptionsItemSelected","mDashboard-i"+student_id+"-n-"+m_student_name);
                intent.putExtras(activeUri);
                startActivity(intent);
                finish();
            }
            return true;
        }

    public String getStudentName(){

        return m_student_name;
    }
    public String getStudentId(){

        return student_id;
    }
    public String getDateInput(){

        return date_input;
    }
    public String getMonth(){

        return month;
    }
    public String getYear(){

        return year;
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