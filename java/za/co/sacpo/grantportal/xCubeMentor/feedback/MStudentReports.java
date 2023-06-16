package za.co.sacpo.grantportal.xCubeMentor.feedback;

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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MSReportAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SReportsObj;
import za.co.sacpo.grantportal.xCubeLib.db.mLearnerProgressReportListAdapter;
import za.co.sacpo.grantportal.xCubeLib.db.mLearnerProgressReportListArray;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grantportal.xCubeMentor.MDashboardDA;

public class MStudentReports extends BaseAPCPrivate{
    private String ActivityId="336";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    private TextView lblView;
    private RecyclerView recyclerViewQ;
    private String student_id,student_name,generator;
   
    public SReportsObj rDataObj = new SReportsObj();
    
    private List<SReportsObj.Item> rDataObjList = null;

    //public static final String KEY_FRIEND_ID = "fid";

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("student_id");
        student_name = activeInputUri.getString("student_name");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"student_id","student_name "+student_name);
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
            initBackBtn();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
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
            callHeaderBuilder();
            fetchData();
            showProgress(false,mContentView,mProgressView);
        }else{
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            initBackBtn();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
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
            callHeaderBuilder();
            fetchOfflineData();
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
        printLogs(LogTag,"verifyVersion", "isUpdate " + isUpdate);
        printLogs(LogTag,"verifyVersion", "isUpdate " + utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MStudentReports.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_student_reports");
        setContentView(R.layout.a_student_reports);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);

      

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVstudentReports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){

        lblView = (TextView)findViewById(R.id.lblStudentAttendance);
        lblView.setText(student_name);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));

        String  Label = getLabelFromDb("h_336",R.string.h_336);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
     
        }
    @Override
    protected void initializeListeners() {
       
    }
   

    public void callDataApi(){
        userToken = userSessionObj.getToken();
        if(!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag,"callDataApi","Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MSReportAdapter(rDataObjList,baseApcContext,activityIn,this));
    }
    public void showList(){
        List<SReportsObj.Item> FormData = rDataObj.getITEMS();
        MSReportAdapter adapter = new MSReportAdapter(FormData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_STUDENT_REPORT;
        FINAL_URL=FINAL_URL+"?token="+token+"&student_id="+student_id;
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

                        mLearnerProgressReportListAdapter adapter = new mLearnerProgressReportListAdapter(getApplicationContext());
                        adapter.truncate();

                        ArrayList<mLearnerProgressReportListArray> arrayList = new ArrayList<>();

                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;

                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_w_r_id"));
                            String msfReportTitle3 =rec.getString("title");
                            String msfYear4 =rec.getString("year");
                            String msupervisorStatus5 =rec.getString("supervisor_status");
                            String mReportN6 = rec.getString("number");
                            String misSupervisorApproved7 = rec.getString("show_approve_link");
                            String month8 = rec.getString("month");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,msfReportTitle3,msfYear4,msupervisorStatus5,mReportN6,misSupervisorApproved7,month8));

                            arrayList.add(new mLearnerProgressReportListArray(String.valueOf(aId2),msfReportTitle3,
                                    msfYear4,msupervisorStatus5,mReportN6,misSupervisorApproved7,month8));



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

    private void fetchOfflineData() {

    mLearnerProgressReportListAdapter adapter = new mLearnerProgressReportListAdapter(getApplicationContext());
        List<mLearnerProgressReportListArray> all = adapter.getAll();
        for (int i = 0; i <all.size() ; i++) {
            int pos = i+1;
            int aId2 = Integer.parseInt(all.get(i).getS_w_r_id());
            String msfReportTitle3 =all.get(i).getTitle();
            String msfYear4 =all.get(i).getYear();
            String msupervisorStatus5 =all.get(i).getSupervisor_status();
            String mReportN6 = all.get(i).getNumber();
            String misSupervisorApproved7 = all.get(i).getShow_approve_link();
            String month8 = all.get(i).getMonth();
            rDataObj.addItem(rDataObj.createItem(pos,aId2,msfReportTitle3,msfYear4,msupervisorStatus5,mReportN6,misSupervisorApproved7,month8));

        }
        showList();
        showProgress(false,mContentRView,mProgressRView);

    }


    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_336_learner_name",R.string.t_head_336_learner_name);
        String tHeader4 = getLabelFromDb("t_head_336_year",R.string.t_head_336_year);
        String tHeader7 ="";
        String tHeader6 = getLabelFromDb("t_head_336_r_no",R.string.t_head_336_r_no);
        String tHeader5 = getLabelFromDb("t_head_336_s_status",R.string.t_head_336_s_status);
        String tHeader8 = getLabelFromDb("t_head_336_month",R.string.t_head_336_month);


        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MStudentReports.this, MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","MDashboardDA"+item);
            startActivity(intent);
            finish();
        }
        return true;
    }
    public String getStudentName(){
        return student_name;
    }
    public String getStudentId(){
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