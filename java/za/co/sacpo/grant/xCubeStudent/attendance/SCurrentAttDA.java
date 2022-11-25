package za.co.sacpo.grant.xCubeStudent.attendance;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SCurrentAttAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SCurrentAttObj;
import za.co.sacpo.grant.xCubeLib.db.attListArray;
import za.co.sacpo.grant.xCubeLib.db.attListDetailsAdapter;
import za.co.sacpo.grant.xCubeLib.db.populateAttListAdapter;
import za.co.sacpo.grant.xCubeLib.db.populateAttListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SCurrentAttDA extends BaseAPCPrivate {
    private final String ActivityId="S104";
    public View mProgressView, mContentView,mProgressRView, mContentRView , heading;
    public TextView lblView,activity_heading;
    public WebView wv_information;
    public Bundle activeUri;
    public String generator;
    public Button btnSignOut,btnSignIn;
    private RecyclerView recyclerViewQ;
    public SCurrentAttObj rDataObj = new SCurrentAttObj();
    private List<SCurrentAttObj.Item> rDataObjList = null;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
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
        }else{
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
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SCurrentAttDA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_current_attendance");
        setContentView(R.layout.a_current_attendance);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignOut = findViewById(R.id.btnSignOut);
        activity_heading = findViewById(R.id.activity_heading);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        recyclerViewQ.setNestedScrollingEnabled(false);
        wv_information = findViewById(R.id.wv_information);
        final WebSettings webSettingsF = wv_information.getSettings();
        Resources res = getResources();
        float fontSize = res.getDimension(R.dimen.tiniestTextSize);
        webSettingsF.setDefaultFontSize((int)fontSize);
        wv_information.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        wv_information.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);


        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String  Label = getLabelFromDb("h_S104",R.string.h_S104);
        activity_heading.setText(Label);
        Label = getLabelFromDb("b_S104_SIGN_IN",R.string.b_S104_SIGN_IN);
        btnSignIn.setText(Label);
        Label = getLabelFromDb("b_S104_SIGN_OUT",R.string.b_S104_SIGN_OUT);
        btnSignOut.setText(Label);
        Label = getLabelFromDb("info_S104_page",R.string.info_S104_page);
        wv_information.loadData(Label, "text/html", "UTF-8");

        activity_heading.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSignIn.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            btnSignOut.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        }

        }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");

        btnSignIn.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SCurrentAttDA.this, SignInA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        });
        btnSignOut.setOnClickListener(view -> {
            inBundle = new Bundle();
            Intent intent = new Intent(SCurrentAttDA.this, SignOutA.class);
            inBundle.putString("generator", ActivityId);
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();
        });
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
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_120;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject jsonObject= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "fetchData", "response : " + response);
                String Status = jsonObject.getString(KEY_STATUS);
                if (Status.equals("1")) {
                    populateAttListAdapter adapter = new populateAttListAdapter(getApplicationContext());
                    adapter.truncate();
                    ArrayList<populateAttListArray> attArrayList = new ArrayList<>();
                    JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        int pos = i + 1;
                        JSONObject rec = dataM.getJSONObject(i);
                        int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                        String aDate3 = rec.getString("date");
                        String aDay4 = rec.getString("day");
                        String aSignIn5 = rec.getString("login_time");
                        String aSignOut6 = rec.getString("logout_time");
                        String aHoursWorked7 = rec.getString("hours_worked");
                        String aAttendanceStatus8 = rec.getString("attendance_status");
                        String aDistanceFromWorkstation9 = rec.getString("distance_from_workstation");//to view Comments _
                        String aCommentLink10 = rec.getString("view_comment_link");
                        String aSignInColor11 = rec.getString("login_color");//to view Comments _
                        String aSignOutColor12 = rec.getString("logout_color");
                        String aCommentVal13 = rec.getString("s_a_learner_comment");
                        rDataObj.addItem(rDataObj.createItem(pos, aId2, aDate3, aDay4, aSignIn5, aSignOut6, aHoursWorked7, aAttendanceStatus8, aDistanceFromWorkstation9, aCommentLink10, aSignInColor11, aSignOutColor12, aCommentVal13));

                        attArrayList.add(new populateAttListArray(String.valueOf(aId2),rec.getString("sudent_id"),
                                aDate3,aDay4,aSignInColor11,aSignOutColor12,aSignIn5,aSignOut6,
                                aHoursWorked7,aAttendanceStatus8,aDistanceFromWorkstation9,aCommentLink10,aCommentVal13));



                    }
                    adapter.insert(attArrayList);
                    showList();
                    showProgress(false, mContentRView, mProgressRView);
                } else if (Status.equals("2")) {
                    SCurrentAttDA.this.showProgress(false, mContentRView, mProgressRView);
                } else {
                    SCurrentAttDA.this.showProgress(false, mContentRView, mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = SCurrentAttDA.this.getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                SCurrentAttDA.this.showProgress(false, mContentRView, mProgressRView);
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = SCurrentAttDA.this.getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
            }
        },
                error -> {
                    SCurrentAttDA.this.showProgress(false, mContentRView, mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-104";
                    String sMessage = SCurrentAttDA.this.getLabelFromDb("error_try_again", R.string.error_try_again);
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
    private void fetchOfflineData() {

        printLogs(LogTag, "fetchOfflineData", "init");
        populateAttListAdapter adapter  =new populateAttListAdapter(getApplicationContext());
        List<populateAttListArray> adapterAll = adapter.getAll();
        printLogs(LogTag, "fetchOfflineData", "init"+adapterAll);
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i + 1;
            int aId2 = Integer.parseInt(adapterAll.get(i).getS_a_id());
            String aDate3 = adapterAll.get(i).getDate();
            String aDay4 = adapterAll.get(i).getDay();
            String aSignIn5 = adapterAll.get(i).getLogin_time();
            String aSignOut6 = adapterAll.get(i).getLogout_time();
            String aHoursWorked7 = adapterAll.get(i).getHours_worked();
            String aAttendanceStatus8 = adapterAll.get(i).getAttendance_status();
            String aDistanceFromWorkstation9 = adapterAll.get(i).getDistance_from_workstation();
            String aCommentLink10 = adapterAll.get(i).getView_comment_link();
            String aSignInColor11 = adapterAll.get(i).getLogin_color();
            String aSignOutColor12 = adapterAll.get(i).getLogout_color();
            String aCommentVal13 = adapterAll.get(i).getS_a_learner_comment();


            rDataObj.addItem(rDataObj.createItem(pos, aId2, aDate3, aDay4, aSignIn5, aSignOut6, aHoursWorked7, aAttendanceStatus8, aDistanceFromWorkstation9, aCommentLink10, aSignInColor11, aSignOutColor12, aCommentVal13));



        }
        showList();
        showProgress(false, mContentRView, mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S104_in_date",R.string.t_head_S104_in_date);
        String tHeader4 = getLabelFromDb("t_head_S104_day",R.string.t_head_S104_day);
        String tHeader5 = getLabelFromDb("t_head_S104_in_time",R.string.t_head_S104_in_time);
        String tHeader6 = getLabelFromDb("t_head_S104_out_time",R.string.t_head_S104_out_time);
        String tHeader7 = getLabelFromDb("t_head_S104_hours_worked",R.string.t_head_S104_hours_worked);
        String tHeader8 = getLabelFromDb("t_head_S104_status",R.string.t_head_S104_status);
        String tHeader9 = getLabelFromDb("t_head_S104_distance_from_workstation",R.string.t_head_S104_distance_from_workstation);
        String tHeader10 = getLabelFromDb("t_head_S104_comment",R.string.t_head_S104_comment);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,"","",""));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SCurrentAttAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SCurrentAttObj.Item> AttData = rDataObj.getITEMS();
        SCurrentAttAdapter adapter = new SCurrentAttAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SCurrentAttDA.this, SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
            startActivity(intent);
            finish();
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SCurrentAttDA.this, SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","SDashboardDA");
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