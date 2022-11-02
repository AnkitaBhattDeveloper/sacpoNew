package za.co.sacpo.grant.xCubeStudent.claims;

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

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.adapter.SCWeeklyReportAdapter;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dataObj.SCWeeklyReportObj;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.feedback.SAddFeedbackA;

public class SCWeeklyReport extends BaseAPCPrivate {
    private String ActivityId = "S223";
    public View mProgressView, mContentView, mProgressRView, mContentRView;
    private TextView lblView;
    public String date_input, month_year, generator, grant_id,student_id,is_upload_attendance;

    private RecyclerView recyclerViewQ;
    public SCWeeklyReportObj rDataObj = new SCWeeklyReportObj();
    private List<SCWeeklyReportObj.Item> rDataObjList = null;
    private Button btn_addWeeklyReport, btn_next, btn_back;

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
        date_input = activeInputUri.getString("date_input");
        month_year = activeInputUri.getString("month_year");
        grant_id = activeInputUri.getString("grant_id");
        student_id = activeInputUri.getString("student_id");
        is_upload_attendance = activeInputUri.getString("is_upload_attendance");

        printLogs(LogTag, "onCreate", "date_input_ID_FORWEEKLY " + date_input);
        printLogs(LogTag, "onCreate", "Newmonth_year " + month_year);
        printLogs(LogTag, "onCreate", "Getgrant_id__" + grant_id);
        printLogs(LogTag, "onCreate", "student_id_ID" + student_id);
        printLogs(LogTag, "onCreate", "is_upload_attendance____" + is_upload_attendance);
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
            //showProgress(true, mContentView, mProgressView);
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
        btn_addWeeklyReport = findViewById(R.id.btn_addWeeklyReport);
        btn_next = findViewById(R.id.btn_next);
        btn_back = findViewById(R.id.btn_back);

        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rvSCweeklyR);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        View recyclerView = findViewById(R.id.rvSCweeklyR);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        printLogs(LogTag, "initializeViews", "exit");

    }

    @Override
    protected void initializeListeners() {

        btn_addWeeklyReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inBundle = new Bundle();
                inBundle.putString("generator", ActivityId);
                inBundle.putString("month_year", month_year);
                inBundle.putString("grant_id", grant_id);
                inBundle.putString("student_id", student_id);
                Intent intent = new Intent(SCWeeklyReport.this, SAddFeedbackA.class);
                intent.putExtras(inBundle);
                startActivity(intent);
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle inBundle = new Bundle();
                inBundle.putString("generator", ActivityId);
                inBundle.putString("month_year", month_year);
                inBundle.putString("grant_id", grant_id);
                inBundle.putString("student_id", student_id);
                inBundle.putString("is_upload_attendance", is_upload_attendance);
                Intent intent = new Intent(SCWeeklyReport.this, SCMonthlyFeedbackA.class);
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
                inBundle.putString("grant_id", grant_id);
                inBundle.putString("student_id", student_id);
                inBundle.putString("is_upload_attendance", is_upload_attendance);
                Intent intent = new Intent(SCWeeklyReport.this, SAttSummaryList.class);
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
        String Label = getLabelFromDb("h_S223", R.string.h_S223);
        lblView = (TextView) findViewById(R.id.activity_heading);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S223_btn_addweeklyrpt", R.string.l_S223_btn_addweeklyrpt);
        lblView = (TextView) findViewById(R.id.btn_addWeeklyReport);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S223_btn_next", R.string.l_S223_btn_next);
        lblView = (TextView) findViewById(R.id.btn_next);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S223_btn_back", R.string.l_S223_btn_back);
        lblView = (TextView) findViewById(R.id.btn_back);
        lblView.setText(Label);


    }

    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_scweekly_report);

    }

    public void callDataApi() {
        printLogs(LogTag, "callDataApi", "init");
        userToken = userSessionObj.getToken();
        if (!TextUtils.isEmpty(userToken)) {
            printLogs(LogTag, "callDataApi", "Token Update");
            syncUserData(baseApcContext, activityIn);
            syncStudentData(baseApcContext, activityIn);
            syncGrantData(baseApcContext, activityIn);
            syncAlerts(baseApcContext, activityIn, ActivityId);
        }
    }

    private void initBackBtn() {
        printLogs(LogTag, "initBackBtn", "init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            Intent intent = new Intent(SCWeeklyReport.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }

    public void fetchData() {
        showProgress(true, mContentRView, mProgressRView);
        String token = userSessionObj.getToken();
        //  String grant_id = studentSessionObj.getgrant_id();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_223;
        FINAL_URL = FINAL_URL + "?token=" + token + "&date_input=" + month_year;
        //*+ "/grant_id/" + grant_id*/
        printLogs(LogTag, "fetchData", "URL : " + FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
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
                            String STitle = rec.getString("title");
                            String SMonth3 = rec.getString("month");
                            String SYear5 = rec.getString("year");
                            String SDepartment5 = rec.getString("department");
                            String DateInput8 = rec.getString("month_year");
                            String grant_id = rec.getString("grant_id");
                            rDataObj.addItem(rDataObj.createItem(pos, aId2,STitle, SMonth3, SYear5, SDepartment5, DateInput8,grant_id));
                            showList(is_upload_attendance,student_id);
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
        }, new Response.ErrorListener() {
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


    public void callHeaderBuilder() {
        printLogs(LogTag, "callHeaderBuilder", "init");
        String tHeader3 = getLabelFromDb("t_head_S223_title", R.string.t_head_S223_title);
        String tHeader4 = getLabelFromDb("t_head_S223_month", R.string.t_head_S223_month);
        String tHeader5 = getLabelFromDb("t_head_S223_year", R.string.t_head_S223_year);
        String tHeader6 = getLabelFromDb("t_head_S223_department", R.string.t_head_S223_department);
        String tHeader7 = "";

        rDataObj.addItem(rDataObj.createItem(0, 0, tHeader3, tHeader4, tHeader5, tHeader6, tHeader7,""));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag, "setupRecyclerView", "init");
        recyclerView.setAdapter(new SCWeeklyReportAdapter(rDataObjList, baseApcContext, activityIn,is_upload_attendance,student_id));
    }

    public void showList(String is_upload_attendance,String student_id) {
        printLogs(LogTag, "showList", "init");
        printLogs(LogTag, "showList", "is_upload_attendance_value"+is_upload_attendance);
        printLogs(LogTag, "showList", "student_id"+student_id);
        List<SCWeeklyReportObj.Item> AttData = rDataObj.getITEMS();
        SCWeeklyReportAdapter adapter = new SCWeeklyReportAdapter(AttData, baseApcContext, activityIn,is_upload_attendance,student_id);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false, mContentRView, mProgressRView);
        showProgress(false, mContentView, mProgressView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

       /* printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("103")){*/
       Bundle inputUri = new Bundle();
        inputUri.putString("month_year", month_year);
        inputUri.putString("grant_id", grant_id);
        inputUri.putString("date_input", date_input);
        inputUri.putString("student_id", student_id);
        inputUri.putString("is_upload_attendance", is_upload_attendance);
        Intent intent = new Intent(SCWeeklyReport.this, SAttSummaryList.class);
        printLogs(LogTag, "onOptionsItemSelected", "sDashboard");
        intent.putExtras(inputUri);
        startActivity(intent);
        finish();

          /*  }
            else if(generator.equals("120")){
                Intent intent = new Intent(SAttDetailsA.this,SAttDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sAttendance");
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
