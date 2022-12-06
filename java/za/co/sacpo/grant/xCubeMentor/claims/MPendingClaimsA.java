package za.co.sacpo.grant.xCubeMentor.claims;


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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.MPendingClaimAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.MPendingClaimObj;
import za.co.sacpo.grant.xCubeLib.db.mPendingClaimListAdapter;
import za.co.sacpo.grant.xCubeLib.db.mPendingClaimListArray;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeMentor.MDashboardDA;


public class MPendingClaimsA extends BaseAPCPrivate {

    private String ActivityId = "M413";
    String student_id,m_student_name,generator;
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    public TextView lblView,lblStudentName;
    private RecyclerView recyclerViewQ;
    public MPendingClaimObj rDataObj = new MPendingClaimObj();
    private List<MPendingClaimObj.Item> rDataObjList = null;

    // private String generator;


    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId) {
        baseApcContext = cnt;
        CAId = cTAId;
        activityIn = ain;
        LogTag = lt;
        CAId = CAId;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        student_id="";
        m_student_name=null;
        generator=null;
        if (inputIntent.hasExtra("student_id")) {
            student_id = activeInputUri.getString("student_id");
        }
        if (inputIntent.hasExtra("m_student_name")) {
            m_student_name = activeInputUri.getString("m_student_name");
        }
        if (inputIntent.hasExtra("generator")) {
            generator = activeInputUri.getString("generator");
        }
        printLogs(LogTag, "onCreate", "init");
        bootStrapInit();
    }

    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            callHeaderBuilder();
            fetchData();
            showProgress(false, mContentView, mProgressView);
        }else{
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
            fetchOfflineData();
            showProgress(false, mContentView, mProgressView);
        }
    }


    @Override
    protected void setLayoutXml() {
        printLogs(LogTag, "setLayoutXml", "a_attendance");
        setContentView(R.layout.a_mclaim_approval);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        lblStudentName = findViewById(R.id.lblStudentName);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVPendingClaim);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        setupRecyclerView(recyclerViewQ);

    }

    @Override
    protected void initializeLabels() {
        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("h_340", R.string.h_340);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
        }
    }

    @Override
    protected void initializeInputs() {
        lblStudentName.setText(m_student_name);
        lblStudentName.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
    }

    @Override
    protected void initializeListeners() {
        printLogs(LogTag, "initializeListeners", "init");
    }

    public void callHeaderBuilder() {
        String tHeader3="";
        String tHeader4 = getLabelFromDb("l_claim_M413_year", R.string.l_claim_M413_year);
        String tHeader5 = getLabelFromDb("l_claim_M413_month", R.string.l_claim_M413_month);
        String tHeader6 = getLabelFromDb("l_claim_M413_amount", R.string.l_claim_M413_amount);
        String tHeader7 = getLabelFromDb("l_claim_M413_learner", R.string.l_claim_M413_learner);
        String tHeader8 = getLabelFromDb("l_claim_M413_approve_stipend", R.string.l_claim_M413_approve_stipend);

        rDataObj.addItem(rDataObj.createItem(0, "", tHeader3, tHeader4, tHeader5, tHeader6, tHeader7, tHeader8));

    }


    public void callDataApi() {
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncMentorData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);

        }
    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void validateInput() {
    }

    public void fetchData() {
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.REF_M_413;

        FINAL_URL = FINAL_URL + "?token=" + token +"&learner_id="+student_id;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject outputJson= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if (Status.equals("1")) {
                        mPendingClaimListAdapter adapter = new mPendingClaimListAdapter(getApplicationContext());
                        adapter.truncate();

                        ArrayList<mPendingClaimListArray> arrayList = new ArrayList<>();
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            String aId2 = rec.getString("s_m_s_id");
                            String month_year3 = rec.getString("s_m_s_stipend_month");
                            String PcYear4 = rec.getString("s_m_s_stipend_year");
                            String PcMonth5 = rec.getString("month_name");
                            String PcAmount6 = rec.getString("stipend_amount");
                            String PcLearnerName7 = rec.getString("learner_name");
                            String PcApproval8 = rec.getString("approve_stipend_link");

                            rDataObj.addItem(rDataObj.createItem(pos, aId2, month_year3, PcYear4, PcMonth5, PcAmount6, PcLearnerName7,PcApproval8));

                            arrayList.add(new mPendingClaimListArray(month_year3,PcMonth5,PcYear4,
                                   aId2,PcAmount6,PcLearnerName7, PcApproval8));


                        }
                        adapter.insert(arrayList);
                        showList();
                        showProgress(false,mContentRView,mProgressRView);
                    } else if (Status.equals("2")) {
                        showProgress(false,mContentRView,mProgressRView);

                    } else {
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentRView,mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-103";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showProgress(false,mContentRView,mProgressRView);
                String sTitle = "Error :" + ActivityId + "-104";
                String sMessage = getLabelFromDb("error_340_104", R.string.error_340_104);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                printLogs(LogTag, "fetchData", "error_340_104 : " + error.getMessage());
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

    private void fetchOfflineData(){
        mPendingClaimListAdapter adapter = new mPendingClaimListAdapter(getApplicationContext());
        List<mPendingClaimListArray> all = adapter.getAll();
        for (int i = 0; i < all.size(); i++) {
            int pos = i + 1;
            String aId2 = all.get(i).getS_m_s_id();
            String month_year3 = all.get(i).getS_m_s_stipend_month();
            String PcYear4 = all.get(i).getS_m_s_stipend_year();
            String PcMonth5 = all.get(i).getMonth_name();
            String PcAmount6 = all.get(i).getStipend_amount();
            String PcLearnerName7 = all.get(i).getLearner_name();
            String PcApproval8 = all.get(i).getApprove_stipend_link();

            rDataObj.addItem(rDataObj.createItem(pos, aId2, month_year3, PcYear4, PcMonth5, PcAmount6, PcLearnerName7,PcApproval8));
        }
        showList();
        showProgress(false,mContentRView,mProgressRView);

    }


    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new MPendingClaimAdapter(rDataObjList,baseApcContext,activityIn,this));
    }

    public void showList(){
        List<MPendingClaimObj.Item> ClaimData = rDataObj.getITEMS();
        MPendingClaimAdapter adapter = new MPendingClaimAdapter(ClaimData,baseApcContext,activityIn,this);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentView, mProgressView);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(MPendingClaimsA.this, MDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
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
            Intent intent = new Intent(MPendingClaimsA.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
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
