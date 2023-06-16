package za.co.sacpo.grantportal.xCubeStudent.stipends;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsGrantSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SStipendDetailsA extends BaseAPCPrivate {

    private String ActivityId="139";
    public View mProgressView, mContentView;

    public LinearLayout LLInformationContainer;
    public CardView LLDataContainer;
    TextView txtStipendDetails,txt_StipendDate,txt_StipendMonth,txt_StipendYear,txt_TotalStipend,txt_StipendID;
    String date_input,generator,stipend_id,student_id,month_year;
    private TextView lblView;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId) {
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        student_id = activeInputUri.getString("stipend_id");
        month_year = activeInputUri.getString("month_year");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","stipend_id "+stipend_id);
        printLogs(LogTag,"onCreate","MMMMMYEAR "+month_year);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
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
            initializeViews();
            initBackBtn();
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
            printLogs(LogTag,"initializeInputs","abc");
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
            Intent intent = new Intent(SStipendDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_stipend_details");
        setContentView(R.layout.a_stipend_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        txtStipendDetails=(TextView) findViewById(R.id.txtStipendDetails);
        txt_StipendDate=(TextView) findViewById(R.id.txt_StipendDate);
        txt_StipendMonth=(TextView) findViewById(R.id.txt_StipendMonth);
        txt_StipendYear=(TextView) findViewById(R.id.txt_StipendYear);
        txt_TotalStipend=(TextView) findViewById(R.id.txt_TotalStipend);
        txt_StipendID=(TextView) findViewById(R.id.txt_StipendID);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        LLDataContainer = (CardView) findViewById(R.id.dataView);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("lbl_139_StipendDetails",R.string.lbl_139_StipendDetails);
        lblView = (TextView)findViewById(R.id.lblStipendDetails);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_139_StipendDate",R.string.lbl_139_StipendDate);
        lblView = (TextView)findViewById(R.id.lbl_StipendDate);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_139_StipendMonth",R.string.lbl_139_StipendMonth);
        lblView = (TextView)findViewById(R.id.lbl_StipendMonth);
        lblView.setText(Label);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_139_StipendYear",R.string.lbl_139_StipendYear);
        lblView = (TextView)findViewById(R.id.lbl_StipendYear);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_139_TotalStipend",R.string.lbl_139_TotalStipend);
        lblView = (TextView)findViewById(R.id.lbl_TotalStipend);
        lblView.setText(Label);
        Label = getLabelFromDb("lbl_139_student_id",R.string.lbl_139_student_id);
        lblView = (TextView)findViewById(R.id.lbl_StipendID);
        lblView.setText(Label);
        Label = getLabelFromDb("h_139",R.string.h_139);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);
        Label = getLabelFromDb("i_no_active_grant",R.string.i_no_active_grant);
        lblView = (TextView)findViewById(R.id.iNoActiveGrant);
        lblView.setText(Label);
    }
    @Override
    protected void initializeInputs(){
        studentSessionObj = new OlumsStudentSession(baseApcContext);
        Boolean isGrantActive= studentSessionObj.getIsActiveGrant();
        //isGrantActive = false;
        if(isGrantActive) {
            grantSessionObj = new OlumsGrantSession(baseApcContext);
            String grantId = grantSessionObj.getGrantId();
            int grantIdInt = 0;
            if(!TextUtils.isEmpty(grantId)) {
                grantIdInt =Integer.parseInt(grantId);
            }
            if (grantIdInt > 0) {
                printLogs(LogTag, "initializeInputs", "GRANT " + grantIdInt);
                String StartDate = grantSessionObj.getGrantSDate();
                String EndDate = grantSessionObj.getGrantEDate();
                LLDataContainer.setVisibility(View.VISIBLE);
                LLInformationContainer.setVisibility(View.GONE);
                getStipendDetails();
            }
            else{
                LLDataContainer.setVisibility(View.GONE);
                LLInformationContainer.setVisibility(View.VISIBLE);
            }
        }
        else{
            printLogs(LogTag,"initializeInputs","NO GRANT");
            LLDataContainer.setVisibility(View.GONE);
            LLInformationContainer.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void initializeListeners() {

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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void getStipendDetails(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.STUDENT_STIPEND_DETAILS;
        FINAL_URL=FINAL_URL+"/token/"+token+"/month_year/"+month_year;
        printLogs(LogTag,"getStipendDetails","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                printLogs(LogTag,"getStipendDetails","RESPONSE : "+response);
                try {
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);
                        if(dataM.length()>0) {
                            txt_StipendDate.setText(dataM.getString("s_m_s_stipend_date"));
                            txt_StipendMonth.setText(dataM.getString("s_month"));
                            txt_StipendYear.setText(dataM.getString("s_year"));
                            txt_TotalStipend.setText(dataM.getString("s_m_s_stipend"));
                            txt_StipendID.setText(dataM.getString("s_m_s_student_id"));
                            showProgress(false, mContentView, mProgressView);
                        }
                        else{
                            //showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-100";
                            String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                       showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getStipendDetails","error_try_again : "+e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
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
                        printLogs(LogTag,"getAttendanceDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(SStipendDetailsA.this);
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("605")){
                Intent intent = new Intent(SStipendDetailsA.this,SStipendsDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sStipendA");
                startActivity(intent);
                finish();
            }
            else {
                printLogs(LogTag,"onOptionsItemSelected","default");
                super.onOptionsItemSelected(item);
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
