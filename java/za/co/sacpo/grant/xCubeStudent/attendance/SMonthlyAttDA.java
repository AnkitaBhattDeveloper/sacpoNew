package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
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
import za.co.sacpo.grant.xCubeLib.adapter.SDAttAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SDAttObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SMonthlyAttDA extends BaseAPCPrivate {
    private String ActivityId="S109";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView;
    public LinearLayout LLinformationContainer,LLFilterContainer,LLAttendanceContainer;
    public Bundle activeUri;
    public String generator;
    public WebView wv_information;
    private RecyclerView recyclerViewQ;
    public SDAttObj rDataObj = new SDAttObj();
    private List<SDAttObj.Item> rDataObjList = null;
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
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
            printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
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
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SMonthlyAttDA.this,AppUpdatedA.class);
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
        printLogs(LogTag,"setLayoutXml","a_monthly_attendance");
        setContentView(R.layout.a_monthly_attendance);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        LLinformationContainer = findViewById(R.id.informationContainer);
        LLFilterContainer = findViewById(R.id.filterContainer);
        LLAttendanceContainer = findViewById(R.id.attendanceContainer);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

        View recyclerView = findViewById(R.id.rVAttendance);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("h_S109",R.string.h_S109);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S109_no_active_grant",R.string.l_S109_no_active_grant);
        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
        lblView.setText(Label);

        Label = getLabelFromDb("info_S109_page",R.string.info_S109_page);
        wv_information.loadData(Label, "text/html", "UTF-8");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }

        }
    @Override
    protected void initializeInputs(){
        LLAttendanceContainer.setVisibility(View.GONE);
        printLogs(LogTag,"initializeInputs","init");
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        if(isGrantActive) {
            printLogs(LogTag,"isGrantActive","if_Condition"+isGrantActive);
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String grantId = grantSessionObj.getGrantId();
            int grantIdInt = 0;
            if(!TextUtils.isEmpty(grantId)) {
                grantIdInt =Integer.parseInt(grantId);
            }
            if (grantIdInt > 0) {
                LLinformationContainer.setVisibility(View.GONE);
                LLAttendanceContainer.setVisibility(View.VISIBLE);
            }
            else{
                LLAttendanceContainer.setVisibility(View.GONE);
                LLinformationContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            LLAttendanceContainer.setVisibility(View.GONE);
            LLinformationContainer.setVisibility(View.VISIBLE);
        }
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
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn,ActivityId);
        }
    }
    
    public void fetchData(){
        showProgress(true, mContentView, mProgressView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_109;
        FINAL_URL = FINAL_URL + "?token=" + token;
        printLogs(LogTag, "fetchDataAtt", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "getGrantDetails", "RESPONSE : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("date"));
                            String Month = rec.getString("month");
                            String Year = rec.getString("year");
                            String Count = rec.getString("attendance_days");
                            String aALeaveCount6 = rec.getString("annual_leave");
                            String aSLeaveCount7 = rec.getString("sick_leave");
                            String aOPaidLeaveCount8 = rec.getString("other_paid_leave");
                            String aUPaidLeaveCount9 = rec.getString("unpaid_leave");
                            String aSupervisorStatus10 = rec.getString("supervisor_approval");
                            String aSupervisorComment11 = rec.getString("supervisor_comment");
                            String aDownloadReg12 = rec.getString("download_attendance");
                            String aDownloadLink13 = rec.getString("download_attendance_link");
                            String aCommentLink14 = rec.getString("supervisor_comment_link");
                            /*aCommentLink14="1";
                            aDownloadLink13="1";
                            aDownloadReg12="/SAMPLE_TRANING_PROGRAM.pdf";
                            aSupervisorComment11="SAMPLE_TRANING_PROGRAM";*/
                            rDataObj.addItem(rDataObj.createItem(pos, aId2, Month, Year, Count,aALeaveCount6, aSLeaveCount7, aOPaidLeaveCount8, aUPaidLeaveCount9, aSupervisorStatus10, aSupervisorComment11, aDownloadReg12,aDownloadLink13,aCommentLink14));
                            showList();
                        }
                        showProgress(false, mContentView, mProgressView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentView, mProgressView);
                    } else {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-103";

                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentView, mProgressView);
                    String sTitle = "Error :" + ActivityId + "-103";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, mContentView, mProgressView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

                    }
                })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "*/*");
                // params.put("Content-Type","application/json");

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
        String tHeader3 = getLabelFromDb("t_head_S109_month",R.string.t_head_S109_month);
        String tHeader4 = getLabelFromDb("t_head_S109_year",R.string.t_head_S109_year);
        String tHeader5 = getLabelFromDb("t_head_S109_att",R.string.t_head_S109_att);
        String tHeader6 = getLabelFromDb("t_head_S109_annual_leave",R.string.t_head_S109_annual_leave);
        String tHeader7 = getLabelFromDb("t_head_S109_sick_leave",R.string.t_head_S109_sick_leave);
        String tHeader8 = getLabelFromDb("t_head_S109_o_paid_leave",R.string.t_head_S109_o_paid_leave);
        String tHeader9 = getLabelFromDb("t_head_S109_unpaid_leave",R.string.t_head_S109_unpaid_leave);
        String tHeader10 = getLabelFromDb("t_head_S109_supervisor_app",R.string.t_head_S109_supervisor_app);
        String tHeader11 = getLabelFromDb("t_head_S109_supervisor_comments",R.string.t_head_S109_supervisor_comments);
        String tHeader12 = getLabelFromDb("t_head_S109_download_att",R.string.t_head_S109_download_att);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11,tHeader12,"",""));
    }
    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SDAttAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SDAttObj.Item> AttData = rDataObj.getITEMS();
        SDAttAdapter adapter = new SDAttAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SMonthlyAttDA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SMonthlyAttDA.this,SDashboardDA.class);
        printLogs(LogTag,"onBackPressed","sDashboard");
        startActivity(intent);
        finish();
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