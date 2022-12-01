package za.co.sacpo.grant.xCubeMentor;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Build;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MStuListAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.MentorBaseDrawerA;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MStuListObj;
import za.co.sacpo.grant.xCubeLib.db.MentorDashboardAdapter;
import za.co.sacpo.grant.xCubeLib.db.MentorDashboardArray;
import za.co.sacpo.grant.xCubeLib.db.pastClaimListAdapter;
import za.co.sacpo.grant.xCubeLib.db.pastClaimListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class MDashboardDA extends MentorBaseDrawerA{
    private final String ActivityId="M401";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    private TextView lblView;
    private RecyclerView recyclerViewQ;
    public MStuListObj rDataObj = new MStuListObj();
    private List<MStuListObj.Item> rDataObjList = null;
    ImageView iv_logo;
    boolean doubleBackToExitPressedOnce = false;
    RelativeLayout c_m_dashboard;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        bootStrapInit();
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
            fetchData();
            showProgress(false,mContentView,mProgressView);
        }else{
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
            Intent intent = new Intent(MDashboardDA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","init");
        setContentView(R.layout.a_m_list_dashboard);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        c_m_dashboard = findViewById(R.id.c_m_dashboard);
        iv_logo = findViewById(R.id.iv_logo);
        iv_logo.setBackground(getDrawable(getDrwabaleResourceId("app_logo")));
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = findViewById(R.id.rVstudntattendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        recyclerViewQ.setNestedScrollingEnabled(false);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        String Label = getLabelFromDb("h_M401",R.string.h_M401);
        lblView = findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            c_m_dashboard.setBackgroundColor(getColor(getTextcolorResourceId("dashboard_back")));
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
        recyclerView.setAdapter(new MStuListAdapter(rDataObjList,baseApcContext,activityIn));
    }


    public void showList(){
        List<MStuListObj.Item> FormData = rDataObj.getITEMS();
        MStuListAdapter adapter = new MStuListAdapter(FormData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }


    public void fetchData(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_401;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, response -> {
            try {
                JSONObject outputJson= new JSONObject(String.valueOf(response));
                printLogs(LogTag, "fetchData", "response : " + response);
                String Status = outputJson.getString(KEY_STATUS);
                if(Status.equals("1")){
                    MentorDashboardAdapter adapter = new MentorDashboardAdapter(getApplicationContext());
                    adapter.truncate();
                    ArrayList<MentorDashboardArray> arraylist = new ArrayList<>();
                    JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                    for (int i = 0; i < dataM.length(); i++) {
                        int pos = i+1;
                        JSONObject rec = dataM.getJSONObject(i);
                        int aId2 = Integer.parseInt(rec.getString("learner_id"));
                        String notes3 = rec.getString("notes");
                        String lname4 = rec.getString("learner_name");
                        String lstatus5 = rec.getString("learner_status");
                        String sdate6 = rec.getString("start_date");
                        String edate6 = rec.getString("end_date");
                        String seta7 = rec.getString("seta_name");
                        String le8 = rec.getString("managed_by");
                        String patt9 = rec.getString("past_attendance_register");
                        String catt10 = rec.getString("edit_current_attendance");
                        String leave11 = rec.getString("total_leave_taken");
                        String lpending12 = rec.getString("leave_pending_approval");
                        String pstipend13 = rec.getString("past_stipend_claim");
                        String stpending14 = rec.getString("current_stipend_pending_approval");
                        String workx15 = rec.getString("registered_workstation");
                        String workasl16 = rec.getString("assigned_workstation");
                        String reports18 = rec.getString("monthly_reports_completed");
                        String rpending19 = rec.getString("supervisor_comments_pending");
                        String tp20 = rec.getString("trainging_doc");
                        String doc21 = "VIEW";
                        String grantid22 = rec.getString("grant_id");
                        String alertcount23 = rec.getString("alert_count");
                        String isSPending24 = rec.getString("current_stipend_pending_approval");
                        String isWorkxPending25 = rec.getString("workstations_status");
                        String isWorkXassingPending26 = rec.getString("workstations_status");
                        String isRpending27 = rec.getString("supervisor_comments_pending");
                        String isTpPending28 = rec.getString("training_program_upload");
                        rDataObj.addItem(rDataObj.createItem(pos,aId2,notes3,lname4,lstatus5,sdate6,edate6,seta7,le8,patt9,catt10,leave11,lpending12,pstipend13,stpending14,workx15,workasl16,reports18,rpending19,tp20,doc21,grantid22,alertcount23,isSPending24,isWorkxPending25,isWorkXassingPending26,isRpending27,isTpPending28));

                        arraylist.add(new MentorDashboardArray(String.valueOf(aId2),grantid22,lname4,
                                lstatus5,sdate6,edate6,seta7,le8,patt9,catt10,leave11,lpending12,
                                pstipend13,rec.getString("show_approved_claim_link"),stpending14,
                                workx15,rec.getString("show_workstation_link"),workasl16,
                                isWorkxPending25,rec.getString("show_edit_workstations_link"),
                                rec.getString("linked_student_id"),reports18,rpending19,isTpPending28,
                                tp20,alertcount23,notes3));




                    }
                    adapter.insert(arraylist);
                    showList();
                    showProgress(false,mContentRView,mProgressRView);
                }
                else if(Status.equals("2")) {
                    showProgress(false,mContentRView,mProgressRView);
                }
                else{
                    String object = outputJson.getString(KEY_DATA);
                    if(object.equals("USER_ID_BY_TOKEN_FAILED")){
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_99_101",R.string.error_99_101);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }else{
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                showProgress(false,mContentRView,mProgressRView);
                String sTitle="Error :"+ActivityId+"-103";
                String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                String sButtonLabelClose="Close";
                ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
            }
        },
                error -> {
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle="Error :"+ActivityId+"-104";
                    String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

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
                30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    void fetchOfflineData(){
        printLogs(LogTag, "fetchOfflineData", "init");
        MentorDashboardAdapter adapter  =new MentorDashboardAdapter(getApplicationContext());
        List<MentorDashboardArray> adapterAll = adapter.getAll();
        for (int i = 0; i < adapterAll.size(); i++) {
            int pos = i+1;
            int aId2 = Integer.parseInt(adapterAll.get(i).getLearner_id());
            String notes3 = adapterAll.get(i).getNotes();
            String lname4 = adapterAll.get(i).getLearner_name();
            String lstatus5 = adapterAll.get(i).getLearner_status();
            String sdate6 = adapterAll.get(i).getStart_date();
            String edate6 = adapterAll.get(i).getEnd_date();
            String seta7 = adapterAll.get(i).getSeta_name();
            String le8 = adapterAll.get(i).getManaged_by();
            String patt9 = adapterAll.get(i).getPast_attendance_register();
            String catt10 = adapterAll.get(i).getEdit_current_attendance();
            String leave11 = adapterAll.get(i).getTotal_leave_taken();
            String lpending12 = adapterAll.get(i).getLeave_pending_approval();
            String pstipend13 = adapterAll.get(i).getPast_stipend_claim();
            String stpending14 = adapterAll.get(i).getCurrent_stipend_pending_approval();
            String workx15 = adapterAll.get(i).getRegistered_workstation();
            String workasl16 = adapterAll.get(i).getAssigned_workstation();;
            String reports18 = adapterAll.get(i).getMonthly_reports_completed();
            String rpending19 = adapterAll.get(i).getSupervisor_comments_pending();
            String tp20 = adapterAll.get(i).getTrainging_doc();
            String doc21 = "VIEW";
            String grantid22 = adapterAll.get(i).getGrant_id();
            String alertcount23 = adapterAll.get(i).getAlert_count();
            String isSPending24 = adapterAll.get(i).getCurrent_stipend_pending_approval();
            String isWorkxPending25 = adapterAll.get(i).getWorkstations_status();
            String isWorkXassingPending26 = adapterAll.get(i).getWorkstations_status();
            String isRpending27 = adapterAll.get(i).getSupervisor_comments_pending();
            String isTpPending28 = adapterAll.get(i).getTraining_program_upload();
            rDataObj.addItem(rDataObj.createItem(pos,aId2,notes3,lname4,lstatus5,sdate6,edate6,seta7,le8,patt9,catt10,leave11,lpending12,pstipend13,stpending14,workx15,workasl16,reports18,rpending19,tp20,doc21,grantid22,alertcount23,isSPending24,isWorkxPending25,isWorkXassingPending26,isRpending27,isTpPending28));


        }
        showList();
        showProgress(false,mContentRView,mProgressRView);
        printLogs(LogTag, "fetchOfflineData", "exit");


    }



    public void callHeaderBuilder(){
        rDataObj.addItem(rDataObj.createItem(0,0,"","","","","","","","","","","","","","","","","","","","","","","","","",""));
    }

    @Override
    public void onBackPressed() {
        printLogs(LogTag, "onBackPressed", "MDashboardA");
        if (doubleBackToExitPressedOnce) {
            Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);
            finish();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        new Handler(Looper.getMainLooper()).postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
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