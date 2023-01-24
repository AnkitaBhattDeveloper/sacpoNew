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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MSAttAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MSAttObj;
import za.co.sacpo.grant.xCubeLib.db.mPastAttendanceAdapter;
import za.co.sacpo.grant.xCubeLib.db.mPastAttendanceArray;
import za.co.sacpo.grant.xCubeLib.db.mShowDetailsAdapter;
import za.co.sacpo.grant.xCubeLib.db.mShowDetailsArray;
import za.co.sacpo.grant.xCubeLib.db.noteListAdapter;
import za.co.sacpo.grant.xCubeLib.db.noteListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;


public class MAttSummaryA extends BaseAPCPrivate {
    private String ActivityId = "M405";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading,heading2,heading3,heading4
                                ,l_heading1,l_heading2,l_heading3,lt_heading1,lt_heading2,lt_heading3
                                ,g_heading1,g_heading2,g_heading3;
    public TextView lblView,lblAttendance;
    private int selectedStudent;
    private RecyclerView recyclerViewQ;
    Bundle activeUri;
    String student_id,generator,m_student_name;
    TextView tv_leadEmpName , tv_setaName , tv_GrantName , tv_GrantNo , tv_LName , tv_LEmail ,tv_LId , tv_LTele ,
            tv_GAName , tv_GAEmail , tv_GATele , tv_AnnualL , tv_PaidL , tv_UnpaidL , tv_SickL ;
    LinearLayout LL_GD, LL_LD,LL_LT,LL_GAD;

    public MSAttObj rDataObj = new MSAttObj();
    private List<MSAttObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
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
        generator=null;
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
            selectedStudent = Integer.parseInt(student_id);
        }if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
      //  validateLogin(baseApcContext,activityIn);
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
            if(student_id!=null) {
                fetchData(selectedStudent);
            }
            showProgress(false,mContentView,mProgressView);
        }else{
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
            getOfflineAllDetails();
            callHeaderBuilder();
            if(student_id!=null) {
                fetchOfflineData(selectedStudent);
            }
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
            Intent intent = new Intent(MAttSummaryA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_att);
        printLogs(LogTag,"setLayoutXml","a_m_att");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
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
        LL_GD = findViewById(R.id.LL_GD);
        LL_LD = findViewById(R.id.LL_LD);
        LL_LT = findViewById(R.id.LL_LT);
        LL_GAD = findViewById(R.id.LL_GAD);
        LL_GD.setVisibility(View.VISIBLE);
        LL_LD.setVisibility(View.VISIBLE);
        LL_LT.setVisibility(View.VISIBLE);
        LL_GAD.setVisibility(View.VISIBLE);

        lblAttendance = (TextView) findViewById(R.id.lblAttendance);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVMAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        recyclerViewQ.setNestedScrollingEnabled(false);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");

        lblAttendance.setText(m_student_name);
        lblAttendance.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("h_149",R.string.h_149);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("lblLeadEmpName",R.string.lblLeadEmpName);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
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
        }
    }
    @Override
    protected void initializeInputs() {
        printLogs(LogTag,"initializeInputs","init");
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
    public void fetchData(int student){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_405;
        FINAL_URL=FINAL_URL+"?token="+token+"&learner_id="+student;
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
                        mPastAttendanceAdapter adapter = new mPastAttendanceAdapter(getApplicationContext());
                        //adapter.truncate();
                        ArrayList<mPastAttendanceArray> arrayList = new ArrayList<>();
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;

                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            String month3 = rec.getString("month");
                            String year4 = rec.getString("year");
                            String daysWorked5 = rec.getString("days_worked");
                            String annualLeave6 = rec.getString("annual_leave");
                            String sickLeave7 = rec.getString("sick_leave");
                            String oPL8 = rec.getString("other_paid_leave");
                            String uPL9 = rec.getString("unpaid_leave");
                            String yearMonth10 = rec.getString("year_month");
                            String student_id11 = rec.getString("student_id");
                            rDataObj.addItem(rDataObj.createItem(pos, pos,month3,year4,daysWorked5,annualLeave6,sickLeave7,oPL8,uPL9,yearMonth10,student_id11));

                            arrayList.add(new mPastAttendanceArray(rec.getString("grant_id"),
                                   student_id11,month3,year4, daysWorked5,rec.getString("leave"),
                                    annualLeave6,sickLeave7,oPL8,uPL9,rec.getString("sno"),
                                   yearMonth10 ));
                        }
                        adapter.insert(arrayList);
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
                        mShowDetailsAdapter adapter = new mShowDetailsAdapter(getApplicationContext());
                        adapter.truncate();

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

                        ArrayList<mShowDetailsArray> arrayList = new ArrayList<>();
                        arrayList.add(new mShowDetailsArray(dataM.getString("lead_emp"),
                                dataM.getString("setaName"),dataM.getString("grantName"),
                                dataM.getString("g_d_grant_number"),dataM.getString("leadManager"),
                                dataM.getString("leadManagerEmail"),dataM.getString("leadManagerContact"),
                                dataM.getString("annual_leave"),dataM.getString("sick_leave"),
                                dataM.getString("other_paid_leave"),dataM.getString("unpaid_leave"),
                                dataM.getString("studName"),dataM.getString("s_id_no"),
                                dataM.getString("stu_email"),dataM.getString("stu_cell")));

                        adapter.insert(arrayList);

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
    private void fetchOfflineData(int selectedStudent) {

        printLogs(LogTag, "fetchOfflineData", "init");
        mPastAttendanceAdapter adapter  =new mPastAttendanceAdapter(getApplicationContext());
        List<mPastAttendanceArray> adapterAll = adapter.getById(selectedStudent);
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i+1;
            String month3 = adapterAll.get(i).getMonth();
            String year4 = adapterAll.get(i).getYear();
            String daysWorked5 = adapterAll.get(i).getDays_worked();
            String annualLeave6 = adapterAll.get(i).getAnnual_leave();
            String sickLeave7 = adapterAll.get(i).getSick_leave();
            String oPL8 = adapterAll.get(i).getOther_paid_leave();
            String uPL9 = adapterAll.get(i).getUnpaid_leave();
            String yearMonth10 = adapterAll.get(i).getYear_month();
            String student_id11 = adapterAll.get(i).getStudent_id();
            rDataObj.addItem(rDataObj.createItem(pos, pos,month3,year4,daysWorked5,annualLeave6,sickLeave7,oPL8,uPL9,yearMonth10,student_id11));

        }
        showList();
        showProgress(false,mContentRView,mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");



    }

    private void getOfflineAllDetails() {

        printLogs(LogTag, "fetchOfflineData", "init");
        mShowDetailsAdapter adapter  =new mShowDetailsAdapter(getApplicationContext());
        List<mShowDetailsArray> adapterAll = adapter.getAll();
        for (int i = 0; i < adapterAll.size(); i++) {
            tv_leadEmpName.setText(adapterAll.get(i).getLead_emp());
            tv_setaName.setText(adapterAll.get(i).getSetaName());
            tv_GrantName.setText(adapterAll.get(i).getGrantName());
            tv_GrantNo.setText(adapterAll.get(i).getG_d_grant_number());
            tv_GAName.setText(adapterAll.get(i).getLeadManager());
            tv_GAEmail.setText(adapterAll.get(i).getLeadManagerEmail());
            tv_GATele.setText(adapterAll.get(i).getLeadManagerContact());
            tv_AnnualL.setText(adapterAll.get(i).getAnnual_leave());
            tv_SickL.setText(adapterAll.get(i).getSick_leave());
            tv_PaidL.setText(adapterAll.get(i).getOther_paid_leave());
            tv_UnpaidL.setText(adapterAll.get(i).getUnpaid_leave());
            tv_LName.setText(adapterAll.get(i).getStudName());
            tv_LId.setText(adapterAll.get(i).getS_id_no());
            tv_LEmail.setText(adapterAll.get(i).getStu_email());
            tv_LTele.setText(adapterAll.get(i).getStu_cell());

        }
        showProgress(false,mContentRView,mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");




    }
    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_M405_month",R.string.t_head_M405_month);
        String tHeader4 = getLabelFromDb("t_head_M405_year",R.string.t_head_M405_year);
        String tHeader5 = getLabelFromDb("t_head_M405_att",R.string.t_head_M405_att);
        String tHeader6 = getLabelFromDb("t_head_M405_annual_leave",R.string.t_head_M405_annual_leave);
        String tHeader7 = getLabelFromDb("t_head_M405_sick_leave",R.string.t_head_M405_sick_leave);
        String tHeader8 = getLabelFromDb("t_head_M405_o_paid_leave",R.string.t_head_M405_o_paid_leave);
        String tHeader9 = getLabelFromDb("t_head_M405_unpaid_leave",R.string.t_head_M405_unpaid_leave);
        String tHeader10="";
        String tHeader11="";
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11));
    }

    public void callFooterRow(){
        String tHeader3 = "";
        String tHeader4 = "";
        String tHeader5 = "";
        String tHeader6 = "";
        String tHeader7 = "";
        String tHeader8 = "";
        String tHeader9 = "";
        String tHeader10 = "FOOTER@APPROVAL";
        String tHeader11 = "";


        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11));
    }
        private void initBackBtn(){
            printLogs(LogTag,"initBackBtn","init");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MSAttAdapter(rDataObjList,baseApcContext,activityIn,this));

    }

    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<MSAttObj.Item> FormData = rDataObj.getITEMS();
        MSAttAdapter adapter = new MSAttAdapter(FormData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
        showProgress(false,mContentView,mProgressView);
    }
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            printLogs(LogTag,"onOptionsItemSelected","init");
            if (item.getItemId() == android.R.id.home) {
                Intent intent = new Intent(MAttSummaryA.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","mDashboard");
                startActivity(intent);
                finish();
            }
            return true;
        }
    public String getStudentName(){
        printLogs(LogTag,"getStudentName","-"+m_student_name);
        return m_student_name;

    }
    public String getStudentId(){
        printLogs(LogTag,"getStudentId","-"+student_id);
        return student_id;
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterBroadcastIC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
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
        checkInternetConnection();
        registerBroadcastIC();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterBroadcastIC();
    }
}