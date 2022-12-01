package za.co.sacpo.grant.xCubeMentor.leaves;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MLeavesListAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MLeavesListObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;

/*a_m_leave*/
public class MSLeaveListA extends BaseAPCPrivate {
    private String ActivityId="M410";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    private TextView lblView;
    private TextView mILeavesTTText,lblMLeavess;
    String student_id,generator,m_student_name;
    private RecyclerView recyclerViewQ;
    public MLeavesListObj rDataObj = new MLeavesListObj();
    private List<MLeavesListObj.Item> rDataObjList = null;
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
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        student_id=null;
        m_student_name="";
        generator=null;
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
            printLogs(LogTag,"onCreate","student_id"+student_id);
        }if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
            printLogs(LogTag,"onCreate","m_student_name"+m_student_name);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        printLogs(LogTag,"onCreate","init");
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
            initBackBtn();
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
            //fetchData();
            fetchData();
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
            Intent intent = new Intent(MSLeaveListA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_m_leave_list");
        setContentView(R.layout.a_m_leave_list);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        lblMLeavess = (TextView) findViewById(R.id.lblMLeavess);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVLeaves);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        setupRecyclerView(recyclerViewQ);

        

        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label  = getLabelFromDb("h_M410",R.string.h_M410);
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
        lblMLeavess.setText(m_student_name);
        lblMLeavess.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader = "";
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader,tHeader));
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
    
    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_410;
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
                        JSONObject rec = outputJson.getJSONObject(KEY_DATA);
                        int i =0;
                        int pos = i+1;
                        int aId2 = pos;
                        String tal3 = rec.getString("annual_leave");
                        String tsl4 = rec.getString("sick_leave");
                        String topl5 = rec.getString("other_paid_leave");
                        String tul6 = rec.getString("unpaid_leave");
                        String pal7 = rec.getString("annual_leave_working_days");
                        String psl8 = rec.getString("sick_leave_working_days");
                        String popl9 = rec.getString("other_paid_leave_working_days");
                        String pul10 = rec.getString("unpaid_leave_working_days");

                        rDataObj.addItem(rDataObj.createItem(pos,aId2,tal3,tsl4,topl5,tul6,pal7,psl8,popl9,pul10));
                        showList();
                        showProgress(false,mContentRView,mProgressRView);
                    }

                    else if(Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);
                    }

                    else{
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle="Error :"+ActivityId+"-101";
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
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })  {
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
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MSLeaveListA.this,MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","mDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MLeavesListAdapter(rDataObjList,baseApcContext,activityIn,this));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<MLeavesListObj.Item> AttData = rDataObj.getITEMS();
        MLeavesListAdapter adapter = new MLeavesListAdapter(AttData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
        showProgress(false,mContentView,mProgressView);
    }
    public String getStudentName(){

        Bundle inputUri = new Bundle();
        inputUri.putString("m_student_name", m_student_name);
        printLogs(LogTag,"getStudentName","GET_student_name"+m_student_name);

        return m_student_name;
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
