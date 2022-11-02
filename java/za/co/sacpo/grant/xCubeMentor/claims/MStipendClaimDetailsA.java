package za.co.sacpo.grant.xCubeMentor.claims;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

/*activity_mstipend_claim_details*/
public class MStipendClaimDetailsA extends BaseAPCPrivate {
     private String ActivityId="173";
     public View mProgressView, mContentView;
     private TextView lblView;
     String date_input,generator,claim_id;
     String date,grant_id;
    TextView txtClaimDate,txtApprovedBy,txtProceedBy,txtStipendAmount,txtEmail,txtStudent_id;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
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
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        //   date = activeInputUri.getString("date");
        date = activeInputUri.getString("date");
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GRANT ID "+grant_id);
        printLogs(LogTag,"onCreate","Date Input"+date);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();



    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected ==true) {
            setLayoutXml();

            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");

            internetChangeBroadCast();

            fetchVersionData();
            verifyVersion();
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
            printLogs(LogTag,"initializeInputs","exitConnected");
        }
    }

    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
       }
        protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate==true){
            Intent intent = new Intent(MStipendClaimDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_leave_details");
        setContentView(R.layout.activity_mstipend_claim_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        txtClaimDate=(TextView) findViewById(R.id.txtClaimDate);
        txtApprovedBy=(TextView) findViewById(R.id.txtApprovedBy);
        txtProceedBy=(TextView) findViewById(R.id.txtProceedBy);
        txtStipendAmount=(TextView) findViewById(R.id.txtStipendAmount);
        txtStudent_id=(TextView) findViewById(R.id.txtStudent_id);
        txtEmail=(TextView) findViewById(R.id.txtEmail);


    }

    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");

        String Label = getLabelFromDb("lbl_173_claim_details",R.string.lbl_173_claim_details);
        lblView = (TextView)findViewById(R.id.lblLoginData);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_173_claim_date",R.string.lbl_173_claim_date);
        lblView = (TextView)findViewById(R.id.lblClaimDate);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_173_proceed_by",R.string.lbl_173_proceed_by);
        lblView = (TextView)findViewById(R.id.lblApprovedBy);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_173_proceed_by",R.string.lbl_173_proceed_by);
        lblView = (TextView)findViewById(R.id.lblProceedBy);
        lblView.setText(Label);

        lblView.setText(Label);
        Label = getLabelFromDb("lbl_173_stipend_ammount",R.string.lbl_173_stipend_ammount);
        lblView = (TextView)findViewById(R.id.lblStipendAmount);
        lblView.setText(Label);


        Label = getLabelFromDb("lbl_173_student_id",R.string.lbl_173_student_id);
        lblView = (TextView)findViewById(R.id.lblStudent_id);
        lblView.setText(Label);

        Label = getLabelFromDb("lbl_173_email",R.string.lbl_173_email);
        lblView = (TextView)findViewById(R.id.lblEmail);
        lblView.setText(Label);

        Label = getLabelFromDb("h_173",R.string.h_173);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);



    }
    @Override
    protected void initializeListeners() {

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");

        getLeaveDetails();
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    private void getLeaveDetails() {
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_CLAIM_DETAILS;
        String grant_id="7";
        String student_id="21";
      if(!grant_id.isEmpty()){
            FINAL_URL=FINAL_URL+"/token/"+token+"/date/"+date+"/grant_id/"+grant_id+"/student_id/"+student_id;
        }
        printLogs(LogTag,"getstipendclaimDetails","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"getstipendclaimDetails","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);

                        txtClaimDate.setText(dataM.getString("claim_date"));
                        txtApprovedBy.setText(dataM.getString("mapproved_by"));
                        txtProceedBy.setText(dataM.getString("pby"));
                        txtStipendAmount.setText(dataM.getString("claim_amount"));
                        txtEmail.setText(dataM.getString("u_email"));
                        txtStudent_id.setText(dataM.getString("s_leave_approval_status"));

                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"getClaimDetails","ERROR_306_100 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"getClaimDetails","error_try_again : "+e.getMessage());
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
                        printLogs(LogTag,"getClaimDetails","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                })
        {

        };

        RequestQueue requestQueue = Volley.newRequestQueue(MStipendClaimDetailsA.this);
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
            Intent intent = new Intent(MStipendClaimDetailsA.this, MPastClaimA.class);
            printLogs(LogTag,"onOptionsItemSelected","MPastClaimA");
            startActivity(intent);
            finish();
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

