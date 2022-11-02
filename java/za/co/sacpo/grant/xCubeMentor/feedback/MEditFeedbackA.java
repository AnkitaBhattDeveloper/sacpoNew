package za.co.sacpo.grant.xCubeMentor.feedback;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;

import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

/*a_m_edit_suporvisor_comment*/
public class MEditFeedbackA extends  BaseFormAPCPrivate {

    private String ActivityId="228";

    private String KEY_USER_ID="c_u_r_id";
    private String KEY_DURATION="comment";

    // private MEditFeedbackA thisClass;
    String user_id;
    public EditText inputDuration;
    public View mProgressView, mContentView;

    public Button btnAddNote;
     String feedback;
    private TextView lblView;
    String student_name,generator,c_u_r_id;
     MEditFeedbackA  thisClass;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        printLogs(LogTag,"onCreate","init");
        Bundle activeInputUri = getIntent().getExtras();
        c_u_r_id = activeInputUri.getString("c_u_r_id");
        generator = activeInputUri.getString("generator");
        student_name = activeInputUri.getString("student_name");
        feedback = activeInputUri.getString("feedback");

        printLogs(LogTag,"onCreate","COMMENT ID "+c_u_r_id);
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);

        bootStrapInit();
        printLogs(LogTag,"onCreate","initConnected");
    }
    @Override
    protected void bootStrapInit() {
        Boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        validateLogin(baseApcContext, activityIn);
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
            initializeInputs();
            userToken =userSessionObj.getToken();
            syncToken(baseApcContext,activityIn);
            if(TextUtils.isEmpty(userToken)) {
                syncToken(baseApcContext, activityIn);
            }
            callDataApi();
         //   fetchData();
            initializeListeners();
            printLogs(LogTag,"bootStrapInit","exitConnected");
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
    protected void verifyVersion() {
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(MEditFeedbackA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml(){
        printLogs(LogTag,"setLayoutXml","a_m_edit_notelist");
        setContentView(R.layout.a_m_edit_suporvisor_comment);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");


        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        btnAddNote = (Button) findViewById(R.id.btnAddNote);

        inputDuration = (EditText) findViewById(R.id.inputDuration);
        inputDuration.setText(feedback);
    }
    @Override
    protected void initializeLabels(){

        printLogs(LogTag,"initializeLabels","init");





        String  Label = getLabelFromDb("lbl_edit_comment",R.string.lbl_edit_comment);
        lblView = (TextView)findViewById(R.id.lblduration);
        lblView.setText(Label);

        Label = getLabelFromDb("h_228",R.string.h_228);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);



        Label = getLabelFromDb("b_228_edit_comment", R.string.b_228_edit_comment);
        lblView = (TextView) findViewById(R.id.btnAddNote);
        btnAddNote.setText(Label);


    }
    @Override
    protected void initializeInputs(){


        printLogs(LogTag,"bootStrapInit","exitConnected");
        showProgress(false,mContentView,mProgressView);

        printLogs(LogTag,"initializeInputs","init");

    }


    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeInputs","init");




        btnAddNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
    }

    public void validateForm() {
        boolean cancel = false;

        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
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

 /*   private void fetchData(){
        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.M_STUDENT_REPORT_DETAILS;
        FINAL_URL=FINAL_URL+"/token/"+token;
        printLogs(LogTag,"fetchData","URL : "+FINAL_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET,FINAL_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject outputJson = null;
                try {
                    printLogs(LogTag,"fetchData","RESPONSE : "+response);
                    outputJson = new JSONObject(response);
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        JSONObject dataM = outputJson.getJSONObject(KEY_DATA);


                        inputDuration.setText(dataM.getString("feedback"));
                       
                        showProgress(false, mContentView, mProgressView);

                    }
                    else if(Status.equals("2")) {
                        showProgress(false,mContentView,mProgressView);
                    }

                    else{
                        //showProgress(false,mContentView,mProgressView);
                        printLogs(LogTag, "fetchData", "error_228_101 : DATA_ERROR");
                        String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_228_101",R.string.error_228_101);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag, "fetchData", "error_try_again : " + e.getMessage());
                    e.printStackTrace();
                    //showProgress(false,mContentView,mProgressView);
                    String sTitle = "Error :" + ActivityId + "-102";
                    String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                    String sButtonLabelClose = "Close";
                    ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                printLogs(LogTag, "fetchData", "error_try_again: " + error.getMessage());
                String sTitle = "Error :" + ActivityId + "-103";
                String sMessage = getLabelFromDb("error_try_again", R.string.error_try_again);
                String sButtonLabelClose = "Close";
                ErrorDialog.showErrorDialog(baseApcContext, activityIn, sTitle, sMessage, sButtonLabelClose);

            }
        }) {

        };
        RequestQueue requestQueue = Volley.newRequestQueue(MEditFeedbackA.this);
        requestQueue.add(stringRequest);
    }*/
    public void FormSubmit(){

        final String comment = inputDuration.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.MEDIT_SUPERVISER_COMMENT;
        FINAL_URL=FINAL_URL+"/token/"+token+"/c_u_r_id/"+c_u_r_id;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_USER_ID, c_u_r_id);
            jsonBody.put(KEY_DURATION, comment);
            printLogs(LogTag, "FormSubmit", "jsonBody " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject jsonObject;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    jsonObject = new JSONObject(String.valueOf(response));
                    String Status = jsonObject.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_228_title",R.string.m_228_title);
                        String sMessage=getLabelFromDb("m_228_message",R.string.m_228_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showSuccessDialogMEditFeedback(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                    }
                    else{
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-102";
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
                        String sTitle="Error :"+ActivityId+"-103";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> header = new HashMap<>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MEditFeedbackA.this);
        requestQueue.add(jsonObjectRequest);
    }

    public void  customRedirector(){
        if(generator.equals("337")) {
            Intent intent = new Intent(MEditFeedbackA.this, MFeedbackA.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator", "337");
            intent.putExtras(activeUri);
            startActivity(intent);
            finish();

        }
      }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        printLogs(LogTag,"onOptionsItemSelected","init");
        if(generator.equals("337")){
            printLogs(LogTag,"onOptionsItemSelected","MFeedbackA");
            Intent intent = new Intent(MEditFeedbackA.this,MFeedbackA.class);
            Bundle activeUri = new Bundle();
            activeUri.putString("generator","149");
            activeUri.putString("user_id",c_u_r_id);
            activeUri.putString("student_name", student_name);
            intent.putExtras(activeUri);
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

