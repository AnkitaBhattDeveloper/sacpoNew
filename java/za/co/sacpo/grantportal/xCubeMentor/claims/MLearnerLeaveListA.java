package za.co.sacpo.grantportal.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MClaimLeaveAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MClaimLeaveObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class MLearnerLeaveListA extends BaseAPCPrivate {

    private String ActivityId = "344";
    public View mProgressView, mContentView,mProgressRView, mContentRView;
    public TextView lblView;
    private RecyclerView recyclerViewQ;
    Bundle activeUri;
    String student_id,generator,month_year,status,grant_id,leave_type,year,month,studentName;
    TextView textAttendance;

    public MClaimLeaveObj rDataObj = new MClaimLeaveObj();
    private List<MClaimLeaveObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
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
        status=null;
        generator=null;
        month_year=null;
        grant_id=null;
        leave_type=null;

        studentName = activeInputUri.getString("studentName");
        student_id = activeInputUri.getString("student_id");
        month = activeInputUri.getString("month");
        year = activeInputUri.getString("year");
        grant_id = activeInputUri.getString("grant_id");
        leave_type = activeInputUri.getString("leave_type");

        printLogs(LogTag,"onCreate","STUDENT ID"+student_id);
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");

        } if (inputIntent.hasExtra(month_year)) {
            month_year = activeInputUri.getString("month_year");

        }  if (inputIntent.hasExtra(month)) {
            month = activeInputUri.getString("month");
            printLogs(LogTag,"onCreate","month"+month);
        } if (inputIntent.hasExtra(year)) {
            year = activeInputUri.getString("year");
            printLogs(LogTag,"onCreate","YYYYYYYYY"+year);

        } if (inputIntent.hasExtra(grant_id)) {
            grant_id = activeInputUri.getString("grant_id");
            printLogs(LogTag,"onCreate","grant_id"+grant_id);
        }
        if (inputIntent.hasExtra("status")) {
            status = activeInputUri.getString("status");
            printLogs(LogTag,"onCreate","status"+status);
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        }
        if (inputIntent.hasExtra("leave_type")) {
            leave_type = activeInputUri.getString("leave_type");
            printLogs(LogTag,"onCreate","PRESENT_TYPE"+leave_type);
        }
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
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
            callHeaderBuilder();
            fetchData();
            showProgress(false, mContentView, mProgressView);
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
            Intent intent = new Intent(MLearnerLeaveListA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_m_learner_leave_list);
        printLogs(LogTag,"setLayoutXml","a_m_learner_leave_list");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        textAttendance = findViewById(R.id.lblAttendance);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVMAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rVMAttendance);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
        textAttendance.setText(studentName);
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");


        String  Label = getLabelFromDb("h_569",R.string.h_569);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs() {

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

    public void validateInput() {

    }

    public void fetchData(){
        showProgress(false,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();

        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.LEAVE_LIST;
        //  FINAL_URL=FINAL_URL+"/token/"+token+"/month_year/"+month_year+"/grant_id/"+grant_id+"/status/"+status+"/student_id/"+student_id;
        FINAL_URL=FINAL_URL+"/token/"+token+"/grant_id/"+grant_id+"/student_id/"+student_id+"/year/"+year+"/month/"+month+"/leave_type/"+leave_type;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"fetchData","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        //
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            printLogs(LogTag,"fetchData","dataM : "+dataM.length());
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                            String FMaDate3 = rec.getString("date");
                            String FMaDay4 = rec.getString("day");
                            String FMaLoginTime5 = rec.getString("login_time");
                            String FMaLogoutTime6 = rec.getString("logout_time");
                            String FMaHoursWorked7 = rec.getString("time_spent");
                            String FMaLearnerComment8 = rec.getString("s_a_learner_comment");
                            String FMaAttStatus9 = rec.getString("leave_status");


                            rDataObj.addItem(rDataObj.createItem(pos,aId2,FMaDate3,FMaDay4,FMaLoginTime5,FMaLogoutTime6,
                                    FMaHoursWorked7,FMaLearnerComment8,FMaAttStatus9));
                            showList();
                            printLogs(LogTag,"fetchData","datshowListaM : "+response);
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

        RequestQueue requestQueue = Volley.newRequestQueue(MLearnerLeaveListA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    public void callHeaderBuilder(){
        String tHeader3 = getLabelFromDb("t_head_344_date",R.string.t_head_344_date);
        String tHeader4 = getLabelFromDb("t_head_344_day",R.string.t_head_344_day);
        String tHeader5 = getLabelFromDb("t_head_344_login_time",R.string.t_head_344_login_time);
        String tHeader6 = getLabelFromDb("t_head_344_logout_time",R.string.t_head_344_logout_time);
        String tHeader7 = getLabelFromDb("t_head_344_hours_worked",R.string.t_head_344_hours_worked);
        String tHeader8 = getLabelFromDb("t_head_344_learner_comment",R.string.t_head_344_learner_comment);
        String tHeader9 = getLabelFromDb("t_head_344_att_status",R.string.t_head_344_att_status);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9));

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new MClaimLeaveAdapter(rDataObjList,baseApcContext,activityIn));

    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void showList(){
        printLogs(LogTag,"fetchData","showList : ");
        List<MClaimLeaveObj.Item> FormData = rDataObj.getITEMS();
        MClaimLeaveAdapter adapter = new MClaimLeaveAdapter(FormData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");

        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("149")){
                Bundle inputUri = new Bundle();
                inputUri.putString("month_year", month_year);
                inputUri.putString("student_id", student_id);
                inputUri.putString("grant_id", grant_id);
                inputUri.putString("status", status);
                inputUri.putString("generator", generator);
                inputUri.putString("studentName", studentName);
                Intent intent = new Intent(MLearnerLeaveListA.this,MLearnerClaimList.class);
                printLogs(LogTag,"onOptionsItemSelected","MLearnerClaimList");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
            }
            else {

                Bundle inputUri = new Bundle();
                inputUri.putString("month_year", month_year);
                inputUri.putString("student_id", student_id);
                inputUri.putString("grant_id", grant_id);
                inputUri.putString("status", status);
                inputUri.putString("generator", generator);
                inputUri.putString("studentName", studentName);
                Intent intent = new Intent(MLearnerLeaveListA.this,MLearnerClaimList.class);
                printLogs(LogTag,"onOptionsItemSelected","MLearnerClaimList");
                intent.putExtras(inputUri);
                startActivity(intent);
                finish();
                super.onOptionsItemSelected(item);
                printLogs(LogTag,"onOptionsItemSelected","default");
            }
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