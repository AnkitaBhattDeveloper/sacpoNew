package za.co.sacpo.grant.xCubeStudent.details;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;
import za.co.sacpo.grant.xCubeStudent.SDashboardDA;

public class SSupervisorDetailsA extends BaseAPCPrivate {
    private String ActivityId="S190";
    public View mProgressView, mContentView;

    private TextView lblView;
    TextView txtMentorName,txtPosition,txtDepartment,txtMentor_cellNo,txtMentorEmail,txtMentorOfficeNo,txtHostName,txtHostSDLNo,lblDepartment,lblPosition,lblMentor_cellNo,lblMentorEmail,lblMentorOfficeNo,lblHostName,lblHostSDLNo;
    Button mMentorCallButton,mMentorEmailButton,mMentorOfficeCallButton;
    String generator,mEmail,mCell,mOfcNo;
    Bundle activeUri;
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
        Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);
        bootStrapInit();
        internetChangeBroadCast();
        fetchVersionData();
        verifyVersion();
    }
    @Override
    protected void fetchVersionData(){
        printLogs(LogTag,"fetchVersionData","exitConnected");
        syncUpdates(baseApcContext,activityIn);
    }

    @Override
    protected void internetChangeBroadCast() {
        registerBroadcastIC();
    }
    @Override
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SSupervisorDetailsA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void bootStrapInit() {
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext,activityIn);
        if(isConnected) {
            setLayoutXml();
            callFooter(baseApcContext,activityIn,ActivityId);
            initMenusCustom(ActivityId,baseApcContext,activityIn);
            printLogs(LogTag,"bootStrapInit","initConnected");
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
            printLogs(LogTag,"onCreate","exitConnected");
        }
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_supervisor_details");
        setContentView(R.layout.a_supervisor_details);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);

        txtMentorName=findViewById(R.id.txtMentorName);
        txtPosition=findViewById(R.id.txtPosition);
        txtDepartment=findViewById(R.id.txtDepartment);
        txtMentor_cellNo=findViewById(R.id.txtMentor_cellNo);
        txtMentorEmail=findViewById(R.id.txtMentorEmail);
        txtMentorOfficeNo=findViewById(R.id.txtMentorOfficeNo);
        txtHostName=findViewById(R.id.txtHostName);
        txtHostSDLNo=findViewById(R.id.txtHostSDLNo);
        lblDepartment=findViewById(R.id.lblDepartment);
        lblPosition=findViewById(R.id.lblPosition);
        lblMentor_cellNo=findViewById(R.id.lblMentor_cellNo);
        lblMentorEmail=findViewById(R.id.lblMentorEmail);
        lblMentorOfficeNo=findViewById(R.id.lblMentorOfficeNo);
        lblHostName=findViewById(R.id.lblHostName);
        lblHostSDLNo=findViewById(R.id.lblHostSDLNo);
        mMentorCallButton=findViewById(R.id.mMentorCallButton);
        mMentorEmailButton=findViewById(R.id.mMentorEmailButton);
        mMentorOfficeCallButton=findViewById(R.id.mMentorOfficeCallButton);

    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        fetchData();
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_S190_Department",R.string.l_S190_Department);
        lblDepartment.setText(Label);
        Label = getLabelFromDb("l_S190_Position",R.string.l_S190_Position);
        lblPosition.setText(Label);
        Label = getLabelFromDb("l_S190_Mentor_cellNo",R.string.l_S190_Mentor_cellNo);
        lblMentor_cellNo.setText(Label);
        Label = getLabelFromDb("l_S190_MentorEmail",R.string.l_S190_MentorEmail);
        lblMentorEmail.setText(Label);
        Label = getLabelFromDb("l_S190_MentorOfficeNo",R.string.l_S190_MentorOfficeNo);
        lblMentorOfficeNo.setText(Label);
        Label = getLabelFromDb("l_S190_HostName",R.string.l_S190_HostName);
        lblHostName.setText(Label);
        Label = getLabelFromDb("l_S190_HostSDLNo",R.string.l_S190_HostSDLNo);
        lblHostSDLNo.setText(Label);
        Label = getLabelFromDb("mMentorCall_b_S190",R.string.mMentorCall_b_S190);
        mMentorCallButton.setText(Label);
        Label = getLabelFromDb("mMentorEmail_b_S190",R.string.mMentorEmail_b_S190);
        mMentorEmailButton.setText(Label);
        Label = getLabelFromDb("mMentorOfficeCall_b_S190",R.string.mMentorOfficeCall_b_S190);
        mMentorOfficeCallButton.setText(Label);

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
    private void fetchData() {

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_190+"?token="+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
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

                        txtMentorName.setText(dataM.getString("name"));
                        mEmail =dataM.getString("email");
                        mCell =dataM.getString("mobile");
                        mOfcNo =dataM.getString("ofc_no");
                        txtMentorEmail.setText(mEmail);
                        txtMentor_cellNo.setText(mCell);
                        txtDepartment.setText(dataM.getString("u_department"));
                        txtPosition.setText(dataM.getString("u_designation"));
                        txtMentorOfficeNo.setText(mOfcNo);
                        txtHostName.setText(dataM.getString("employer"));
                        txtHostSDLNo.setText(dataM.getString("employer_sdl"));

                        mMentorOfficeCallButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri call = Uri.parse("tel:" + mOfcNo);
                                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                                startActivity(surf);
                            }
                        });

                        mMentorCallButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Uri call = Uri.parse("tel:" + mCell);
                                Intent surf = new Intent(Intent.ACTION_DIAL, call);
                                startActivity(surf);
                            }
                        });

                        mMentorEmailButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setData(Uri.parse("mailto:"));
                                intent.setType("plain/text");
                                intent.putExtra(Intent.EXTRA_EMAIL, new String[] { mEmail });
                                startActivity(Intent.createChooser(intent, "Send Email"));
                            }
                        });

                        showProgress(false,mContentView,mProgressView);
                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }
                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag,"fetchData","ERROR_136_100 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_S136_100",R.string.error_S136_100);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"fetchData","error_try_again : "+e.getMessage());
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
                        printLogs(LogTag,"fetchData","error_try_again : "+error.getMessage());
                        //showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-136";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }){
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SSupervisorDetailsA.this,SDashboardDA.class);
            printLogs(LogTag,"onOptionsItemSelected","sDashboard");
            startActivity(intent);
            finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SSupervisorDetailsA.this,SDashboardDA.class);
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
