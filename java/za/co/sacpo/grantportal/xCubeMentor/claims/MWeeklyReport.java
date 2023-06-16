package za.co.sacpo.grantportal.xCubeMentor.claims;

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
import android.widget.Button;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.adapter.MWeeklyReportAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.MWeeklyReportObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class MWeeklyReport extends BaseAPCPrivate {

    public String ActivityId = "331";
    public String student_id,month_year, generator,stipend_id,mName;
    public View mProgressView, mContentView, mProgressRView, mContentRView;
    private TextView lblView,lblAttendance;
    private RecyclerView recyclerViewQ;
    public MWeeklyReportObj rDataObj = new MWeeklyReportObj();
    private List<MWeeklyReportObj.Item> rDataObjList = null;

    private Button btn_next , btn_back;

    @Override
    protected void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cAId) {

        baseApcContext = cnt;
        CAId = cAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt, "setBaseApcContextParent", "weAreIn");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(), ActivityId);
        printLogs(LogTag, "onCreate", "init");

        Bundle activeInputUri = getIntent().getExtras();
         student_id = activeInputUri.getString("student_id");
        month_year = activeInputUri.getString("month_year");
        stipend_id = activeInputUri.getString("stipend_id");
        mName = activeInputUri.getString("mName");
//        claMonth4 = activeInputUri.getString("claMonth4");
        printLogs(LogTag,"onCreate","student_id "+student_id);
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);
        printLogs(LogTag,"onCreate","month_year "+month_year);
        printLogs(LogTag,"onCreate","mName "+mName);

        bootStrapInit();


    }

    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
        }

    }

    @Override
    protected void initializeViews() {

        printLogs(LogTag, "initializeViews", "init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);
        lblAttendance = (TextView) findViewById(R.id.lblAttendance);
        lblAttendance.setText(mName);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rvMweeklyR);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);
        View recyclerView = findViewById(R.id.rvMweeklyR);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag, "initializeViews", "exit");

    }

    @Override
    protected void initializeListeners() {


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inBundle = new Bundle();
                inBundle.putString("generator", ActivityId);
                inBundle.putString("month_year", month_year);
                inBundle.putString("student_id", student_id);
                inBundle.putString("stipend_id", stipend_id);
                inBundle.putString("mName", mName);
                Intent intent = new Intent(MWeeklyReport.this, MMonthlyFeedbackA.class);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle inBundle = new Bundle();
                inBundle.putString("generator", ActivityId);
                inBundle.putString("month_year", month_year);
                inBundle.putString("student_id", student_id);
                inBundle.putString("stipend_id", stipend_id);
                Intent intent = new Intent(MWeeklyReport.this, MClaimAttApproveA.class);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();

            }
        });

    }

    @Override
    protected void initializeInputs() {
        
        

    }

    @Override
    protected void initializeLabels() {

        printLogs(LogTag, "initializeLabels", "init");
        String Label = getLabelFromDb("h_331", R.string.h_331);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_331_btn_next", R.string.l_331_btn_next);
        lblView = (TextView) findViewById(R.id.btn_next);
        lblView.setText(Label);

        Label = getLabelFromDb("l_331_btn_back", R.string.l_331_btn_back);
        lblView = (TextView) findViewById(R.id.btn_back);
        lblView.setText(Label);


    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_mweekly_report);

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

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            Intent intent = new Intent(MWeeklyReport.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void fetchData() {
        showProgress(true, mContentRView, mProgressRView);
        String token = userSessionObj.getToken();
        //  String grantId = studentSessionObj.getGrantId();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MClaim_Weekly_Report;
        FINAL_URL = FINAL_URL + "?token=" + token+ "&student_id=" + student_id+ "&month_year=" + month_year;
        //*+ "/grant_id/" + grantId*/
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
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
                        JSONArray dataM = outputJson.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i + 1;
                            JSONObject rec = dataM.getJSONObject(i);
                            int aId2 = Integer.parseInt(rec.getString("s_w_r_id"));
                            String MTitle3 = rec.getString("title");
                            String MWeekEnding4 = rec.getString("month_year");
                            String MDepartmentName5= rec.getString("department");
                            String MstudentId6= rec.getString("student_id");
                            rDataObj.addItem(rDataObj.createItem(pos, aId2,MTitle3, MWeekEnding4,MDepartmentName5,MstudentId6));
                            showList(stipend_id);
                        }
                        showProgress(false, mContentRView, mProgressRView);
                    } else if (Status.equals("2")) {
                        showProgress(false, mContentRView, mProgressRView);
                    } else {
                        showProgress(false, mContentRView, mProgressRView);
                        String sTitle = "Error :" + ActivityId + "-100";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false, mContentRView, mProgressRView);
                    String sTitle = "Error :" + ActivityId + "-101";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false, mContentRView, mProgressRView);
                        String sTitle = "Error :" + ActivityId + "-102";
                        String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                        String sButtonLabelClose = "Close";
                        // ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

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

    public void showList(String stipend_id) {
        printLogs(LogTag, "showList", "init");
        printLogs(LogTag, "showList", "stipend_idValueForAdapter__"+stipend_id);
        List<MWeeklyReportObj.Item> AttData = rDataObj.getITEMS();
        MWeeklyReportAdapter adapter = new MWeeklyReportAdapter(AttData, baseApcContext, activityIn,stipend_id);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
        showProgress(false, mContentView, mProgressView);
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag, "setupRecyclerView", "init");
        recyclerView.setAdapter(new MWeeklyReportAdapter(rDataObjList, baseApcContext, activityIn,stipend_id));
    }



    public void callHeaderBuilder() {
        printLogs(LogTag, "callHeaderBuilder", "init");
        String tHeader3  = getLabelFromDb("t_head_331_title", R.string.t_head_331_title);
        String tHeader4 = getLabelFromDb("t_head_331_weekend", R.string.t_head_331_weekend);
        String tHeader5 = getLabelFromDb("t_head_331_deparment_name", R.string.t_head_331_deparment_name);

        rDataObj.addItem(rDataObj.createItem(0, 0,tHeader3, tHeader4,tHeader5,""));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("103")){*/

        Bundle inBundle = new Bundle();
        inBundle.putString("generator", ActivityId);
        inBundle.putString("month_year", month_year);
        inBundle.putString("student_id", student_id);
        inBundle.putString("stipend_id", stipend_id);
        inBundle.putString("mName", mName);
        Intent intent = new Intent(MWeeklyReport.this, MClaimAttApproveA.class);
        intent.putExtras(inBundle);
        startActivity(intent);
        finish();

          /*  }
            else if(generator.equals("120")){
                Intent intent = new Intent(MWeeklyReport.this,MDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","MDashboardDA");
                startActivity(intent);
                finish();
            }
            else {
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
            }*/
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
