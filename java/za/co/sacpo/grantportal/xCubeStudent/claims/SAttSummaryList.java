package za.co.sacpo.grantportal.xCubeStudent.claims;

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
import za.co.sacpo.grantportal.xCubeLib.adapter.SClaimAttAdapter;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.dataObj.SClaimAttObj;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SAttSummaryList extends BaseAPCPrivate {


    public String ActivityId = "S220";
    public View mProgressView, mContentView,mProgressRView, mContentRView,heading;
    String generator,t_id,group_id,month_year,student_id,clamDate,date_input,grant_id,is_upload_attendance,is_claim_submitted,is_report_due,is_feedback_pending;
    private Button btn_continue,btnUploadAtt,btnDownloadAtt;
    private TextView lblView;
    private RecyclerView recyclerViewQ;
    public SClaimAttObj rDataObj = new SClaimAttObj();
    private List<SClaimAttObj.Item> rDataObjList = null;
    private TextView lblAtt,txtAtt,lblALeave,txtALeave,lblSLeave,txtSLeave,lbOPLeave,txtOPLeave,lblUPLeave,txtUPLeave,activity_heading;

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

        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Intent inputIntent = getIntent();
        Bundle activeInputUri = getIntent().getExtras();
        is_claim_submitted= "1";
        is_report_due="1";
        is_feedback_pending="1";
        if (inputIntent.hasExtra("is_claim_submitted")) {
            is_claim_submitted = activeInputUri.getString("is_claim_submitted");
            printLogs(LogTag,"onCreate","is_claim_submitted "+is_claim_submitted);
        }
        if (inputIntent.hasExtra("is_feedback_report_due")) {
            is_feedback_pending = activeInputUri.getString("is_feedback_report_due");
            printLogs(LogTag,"onCreate","is_feedback_pending "+is_feedback_pending);
        }
        if (inputIntent.hasExtra("is_report_due")) {
            is_report_due = activeInputUri.getString("is_report_due");
            printLogs(LogTag,"onCreate","is_report_due "+is_report_due);
        }
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

            printLogs(LogTag, "bootStrapInit", "initConnected");
            setLayoutXml();
            callFooter(baseApcContext, activityIn, ActivityId);

            initMenusCustom(ActivityId, baseApcContext, activityIn);
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            initializeViews();
            initBackBtn();
            showProgress(true, mContentView, mProgressView);
            initializeLabels();
            initializeListeners();
            userToken = userSessionObj.getToken();
            syncToken(baseApcContext, activityIn);
            if (TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            fetchData();
            callHeaderBuilder();
            fetchDataATT();
            callDataApi();
            initializeInputs();
            printLogs(LogTag, "bootStrapInit", "exitConnected");

        }
    }

    @Override
    protected void initializeViews() {


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        mContentRView = findViewById(R.id.content_container_r);
        mProgressRView = findViewById(R.id.progress_bar_r);
        heading = findViewById(R.id.heading);
        btn_continue = (Button) findViewById(R.id.btn_continue);
        rDataObjList = rDataObj.getITEMS();
        recyclerViewQ = (RecyclerView) findViewById(R.id.rVAttendance);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerViewQ.setLayoutManager(linearLayoutManager);

        lblAtt = (TextView) findViewById(R.id.lblAtt);
        txtAtt = (TextView) findViewById(R.id.txtAtt);
        lblALeave = (TextView) findViewById(R.id.lblALeave);
        txtALeave = (TextView) findViewById(R.id.txtALeave);
        lblSLeave = (TextView) findViewById(R.id.lblSLeave);
        txtSLeave = (TextView) findViewById(R.id.txtSLeave);
        lbOPLeave = (TextView) findViewById(R.id.lblOPLeave);
        txtOPLeave = (TextView) findViewById(R.id.txtOPLeave);
        lblUPLeave = (TextView) findViewById(R.id.lblUPLeave);
        txtUPLeave = (TextView) findViewById(R.id.txtUPLeave);
        activity_heading = (TextView) findViewById(R.id.activity_heading);
        setupRecyclerView(recyclerViewQ);
        printLogs(LogTag,"initializeViews","exit");

    }

    @Override
    protected void initializeListeners() {
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            printLogs(LogTag,"initializeListeners","SPastClaimDA");
            inBundle = new Bundle();

            Intent intent = new Intent(SAttSummaryList.this, SConfirmBankDetailsA.class);
                inBundle.putString("generator", ActivityId);
                inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
                inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
                inBundle.putString("is_report_due", String.valueOf(is_report_due));
                intent.putExtras(inBundle);
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

        String Label = getLabelFromDb("l_S220_att",R.string.l_S220_att);
        lblView = (TextView)findViewById(R.id.lblAtt);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_lblALeave",R.string.l_S220_lblALeave);
        lblView = (TextView)findViewById(R.id.lblALeave);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_llblsLeave",R.string.l_S220_llblsLeave);
        lblView = (TextView)findViewById(R.id.lblSLeave);
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_lblopLeave",R.string.l_S220_lblopLeave);
        lblView = (TextView)findViewById(R.id.lblOPLeave);
        lblView.setText(Label);
        Label = getLabelFromDb("l_S220_lblupLeave",R.string.l_S220_lblupLeave);
        lblView = (TextView)findViewById(R.id.lblUPLeave);
        lblView.setText(Label);

        Label = getLabelFromDb("h_S220",R.string.h_S220);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("l_S220_btn_continue",R.string.l_S220_btn_continue);
        lblView = (Button)findViewById(R.id.btn_continue);
        lblView.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btn_continue.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));

        }

    }

    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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


    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_satt_summary_list);

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
        if(isUpdate){
            Intent intent = new Intent(SAttSummaryList.this, AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    public void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_220_1;
        FINAL_URL=FINAL_URL+"?token="+token+"&m_name="+month_year+"&grant_id="+grant_id;
        printLogs(LogTag,"fetchData_ATT","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject jsonObject= new JSONObject(String.valueOf(response));
                    printLogs(LogTag, "fetchData", "response : " + response);
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = jsonObject.getJSONObject(KEY_DATA);

                            txtAtt.setText(dataM.getString("attendance_count"));
                            txtALeave.setText(dataM.getString("annual_leave"));
                            txtSLeave.setText(dataM.getString("sick_leave"));
                            txtOPLeave.setText(dataM.getString("other_paid_leave"));
                            txtUPLeave.setText(dataM.getString("unpaid_leave"));


                            showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
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

                        showProgress(false,mContentView,mProgressView);
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

    public void fetchDataATT(){
        showProgress(true,mContentRView,mProgressRView);
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_220_2;
        FINAL_URL=FINAL_URL+"?token="+token;
        printLogs(LogTag,"fetchDataATT","URL : "+FINAL_URL);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "fetchDataATT", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONArray dataM = jsonObject.getJSONArray(KEY_DATA);
                        for (int i = 0; i < dataM.length(); i++) {
                            int pos = i+1;
                            JSONObject rec = dataM.getJSONObject(i);


                            int aId2 = Integer.parseInt(rec.getString("s_a_id"));
                            String aDate3 = rec.getString("date");
                            String aDay4 = rec.getString("day");
                            String aSignIn5 = rec.getString("login_time");
                            String aSignOut6 = rec.getString("logout_time");
                            String aHoursWorked7 = rec.getString("hours_worked");
                            String a_leave = rec.getString("a_leave");
                            String aDistanceFromWorkstation9 = rec.getString("distance_from_workstation");//to view Comments _
                            String s_leave = rec.getString("s_leave");
                            String op_leave = rec.getString("op_leave");//to view Comments _
                            String up_leave = rec.getString("up_leave");
                            rDataObj.addItem(rDataObj.createItem(pos,aId2,aDate3,aDay4,aSignIn5,aSignOut6,aHoursWorked7,a_leave,aDistanceFromWorkstation9,s_leave,op_leave,up_leave));
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
                        printLogs(LogTag, "fetchData", "VolleyError : " + error);
                        showProgress(false,mContentRView,mProgressRView);
                        String sTitle="Error :"+ActivityId+"-104";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept", "*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    public void callHeaderBuilder(){
        printLogs(LogTag,"callHeaderBuilder","init");
        String tHeader3 = getLabelFromDb("t_head_S220_in_date",R.string.t_head_S220_in_date);
        String tHeader4 = getLabelFromDb("t_head_S220_day",R.string.t_head_S220_day);
        String tHeader5 = getLabelFromDb("t_head_S220_in_time",R.string.t_head_S220_in_time);
        String tHeader6 = getLabelFromDb("t_head_S220_out_time",R.string.t_head_S220_out_time);
        String tHeader7 = getLabelFromDb("t_head_S220_hours_worked",R.string.t_head_S220_hours_worked);
        String tHeader9 = getLabelFromDb("t_head_S220_distance_from_workstation",R.string.t_head_S220_distance_from_workstation);
        String tHeader8 = getLabelFromDb("t_head_S220_a_leave",R.string.t_head_S220_a_leave);
        String tHeader10 = getLabelFromDb("t_head_S220_s_leave",R.string.t_head_S220_s_leave);
        String tHeader11 = getLabelFromDb("t_head_S220_op_leave",R.string.t_head_S220_op_leave);
        String tHeader12 = getLabelFromDb("t_head_S220_up_leave",R.string.t_head_S220_up_leave);
        rDataObj.addItem(rDataObj.createItem(0,0,tHeader3,tHeader4,tHeader5,tHeader6,tHeader7,tHeader8,tHeader9,tHeader10,tHeader11,tHeader12));
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        printLogs(LogTag,"setupRecyclerView","init");
        recyclerView.setAdapter(new SClaimAttAdapter(rDataObjList,baseApcContext,activityIn));
    }
    public void showList(){
        printLogs(LogTag,"showList","init");
        List<SClaimAttObj.Item> AttData = rDataObj.getITEMS();
        SClaimAttAdapter adapter = new SClaimAttAdapter(AttData,baseApcContext,activityIn);
        recyclerViewQ.setAdapter(adapter);
        showProgress(false,mContentRView,mProgressRView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Bundle inBundle = new Bundle();
            Intent intent = new Intent(SAttSummaryList.this, SPastClaimDA.class);
            inBundle.putString("generator", ActivityId);
            inBundle.putString("is_claim_submitted", String.valueOf(is_claim_submitted));
            inBundle.putString("is_feedback_report_due", String.valueOf(is_feedback_pending));
            inBundle.putString("is_report_due", String.valueOf(is_report_due));
            intent.putExtras(inBundle);
            startActivity(intent);
            finish();

            printLogs(LogTag,"onOptionsItemSelected","default");
            super.onOptionsItemSelected(item);
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
