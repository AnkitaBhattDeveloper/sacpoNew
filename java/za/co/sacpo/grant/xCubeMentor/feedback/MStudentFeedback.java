package za.co.sacpo.grant.xCubeMentor.feedback;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MStuFeedbackAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.MentorBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.StuFeedbackObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class MStudentFeedback extends MentorBaseDrawerA{
    private String ActivityId="335";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    private TextView lblView;
    private RecyclerView recyclerViewQ;
    private int selectedYear,selectedMonth;

    public StuFeedbackObj rDataObj = new StuFeedbackObj();

    private List<StuFeedbackObj.Item> rDataObjList = null;

    public static final String KEY_FRIEND_ID = "fid";

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
//        String fId = activeInputUri.getString("fId");
     //   String fName = activeInputUri.getString("fName");
//        String fIsGroup = activeInputUri.getString("fIsGroup");
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            printLogs(LogTag,"bootStrapInit","initConnected");
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initDrawer();
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
            fetchData(selectedYear,selectedMonth);
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
            Intent intent = new Intent(MStudentFeedback.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_list_dashboard");
        setContentView(R.layout.a_student_feedback);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);



        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVstudentFeedback);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVstudentFeedback);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("l_335_Student_feedback",R.string.l_335_Student_feedback);
        lblView = (TextView)findViewById(R.id.lblStudentAttendance);
        lblView.setText(Label);

        Label = getLabelFromDb("h_335",R.string.h_335);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
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
        recyclerView.setAdapter(new MStuFeedbackAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        List<StuFeedbackObj.Item> FormData = rDataObj.getITEMS();
        MStuFeedbackAdapter adapter = new MStuFeedbackAdapter(FormData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }
    public void fetchData(int selectedYearInput, int selectedMonthInput){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
     //   String student_id="3017";
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MSTUDENT_FEEDBACK;
        FINAL_URL=FINAL_URL+"/token/"+token/*+"/student_id/"+student_id*/;
        printLogs(LogTag,"getUrl","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                printLogs(LogTag,"fetchData","RESPONSE : "+response);
                JSONObject outputJson = null;
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;

                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("student_id"));
                            String mLearnerName3 = rec.getString("student_name");
                            String mDepartmentName4 =rec.getString("weekly_report");
                            String mDay5 =rec.getString("mentor_comment");
                            String mWeekEnd6 =rec.getString("unread_report_count");



                            rDataObj.addItem(rDataObj.createItem(pos,aId2,mLearnerName3,mDepartmentName4,mDay5,mWeekEnd6));
                            showList();
                        }
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

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MStudentFeedback.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
        public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_335_learner_name",R.string.t_head_335_learner_name);
        String tHeader4 = getLabelFromDb("t_head_335_department_name",R.string.t_head_335_department_name);
        String tHeader5 = getLabelFromDb("t_head_335_day",R.string.t_head_335_day);
        String tHeader6 = getLabelFromDb("t_head_335_weekend_day",R.string.t_head_335_weekend_day);


        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6));
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