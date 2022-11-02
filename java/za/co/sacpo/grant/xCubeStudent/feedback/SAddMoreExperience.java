package za.co.sacpo.grant.xCubeStudent.feedback;

import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import za.co.sacpo.grant.AppUpdatedA;
import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseAPCPrivate;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.db.DbHelper;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.session.OlumsStudentSession;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

/*a_s_add_more_experience*/
public class SAddMoreExperience extends BaseAPCPrivate {

    private String ActivityId="186";
  
    private String KEY_EXPERIENCE1="experience1";
    private String KEY_EXPERIENCE2="experience2";
    private String KEY_EXPERIENCE3="experience3";
    private String KEY_EXPERIENCE4="experience4";
    private String KEY_EXPERIENCE5="experience5";


    public View mProgressView, mContentView;
    public TextInputLayout inputLayoutExperience1,inputLayoutExperience2,inputLayoutExperience3
          ,  inputLayoutExperience4,inputLayoutExperience5;
    public EditText inputExperience1,inputExperience2,inputExperience3,inputExperience4,inputExperience5;
    public Button btnSubmitLv;
    public LinearLayout LLFormContainer,LLInformationContainer;
    String generator;

    private TextView lblView;

    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt, String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(), this, this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag, "onCreate", "init");
      /*  Bundle activeInputUri = getIntent().getExtras();
        generator = activeInputUri.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR ID "+generator);*/

        bootStrapInit();
    }
    @Override
    protected void bootStrapInit(){
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
    protected void verifyVersion(){
        printLogs(LogTag,"verifyVersion","init");
        utilSessionObj = new OlumsUtilitySession(baseApcContext);
        Boolean isUpdate = utilSessionObj.getIsUpdateRequired();
        printLogs(LogTag,"verifyVersion","isUpdate "+isUpdate);
        printLogs(LogTag,"verifyVersion","isUpdate "+ utilSessionObj.getIsUpdateRequired());
        if(isUpdate){
            Intent intent = new Intent(SAddMoreExperience.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_s_add_more_experience");
        setContentView(R.layout.a_s_add_more_experience);
    }

    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);


        inputExperience1 = (EditText) findViewById(R.id.inputExperience1);
        inputExperience2 = (EditText) findViewById(R.id.inputExperience2);
        inputExperience3 = (EditText) findViewById(R.id.inputExperience3);
        inputExperience4 = (EditText) findViewById(R.id.inputExperience4);
        inputExperience5 = (EditText) findViewById(R.id.inputExperience5);


        inputLayoutExperience1 = (TextInputLayout) findViewById(R.id.inputLayoutExperience1);
        inputLayoutExperience2 = (TextInputLayout) findViewById(R.id.inputLayoutExperience2);
        inputLayoutExperience3 = (TextInputLayout) findViewById(R.id.inputLayoutExperience3);
        inputLayoutExperience4 = (TextInputLayout) findViewById(R.id.inputLayoutExperience4);
        inputLayoutExperience5 = (TextInputLayout) findViewById(R.id.inputLayoutExperience5);



        LLFormContainer = (LinearLayout) findViewById(R.id.form_container);
        LLInformationContainer = (LinearLayout) findViewById(R.id.informationContainer);
        btnSubmitLv = (Button) findViewById(R.id.btn_submitLeave);
        printLogs(LogTag,"initializeViews","exit");
    }


    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");

        String  Label = getLabelFromDb("l_186_report1",R.string.l_186_report1);
        lblView = (TextView)findViewById(R.id.lblExperience1);
        lblView.setText(Label);

        Label = getLabelFromDb("l_186_report2",R.string.l_186_report2);
        lblView = (TextView)findViewById(R.id.lblExperience2);
        lblView.setText(Label);

         Label = getLabelFromDb("l_186_report3",R.string.l_186_report3);
        lblView = (TextView)findViewById(R.id.lblExperience3);
        lblView.setText(Label);

        Label = getLabelFromDb("l_186_report4",R.string.l_186_report4);
        lblView = (TextView)findViewById(R.id.lblExperience4);
        lblView.setText(Label);

        Label = getLabelFromDb("l_186_report5",R.string.l_186_report5);
        lblView = (TextView)findViewById(R.id.lblExperience5);
        lblView.setText(Label);

        Label = getLabelFromDb("h_186",R.string.h_186);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setText(Label);


        Label = getLabelFromDb("b_apply_feedback",R.string.b_apply_feedback);
        btnSubmitLv.setText(Label);



    }
    @Override
    protected void initializeInputs(){
        printLogs(LogTag,"initializeInputs","init");
        dbSetaObj = DbHelper.getInstance(this);


        studentSessionObj = new OlumsStudentSession(baseApcContext);

    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");

        btnSubmitLv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
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
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void validateForm() {
        boolean cancel = false;
        printLogs(LogTag,"validateForm","init");

      /*  if(!validateReasons(inputDepartment,inputLayoutDepartment)){
            cancel = true;
        }*/
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }
    public void FormSubmit(){


        final String exp1 = inputExperience1.getText().toString().trim();
        final String exp2 = inputExperience2.getText().toString().trim();
        final String exp3 = inputExperience3.getText().toString().trim();
        final String exp4 = inputExperience4.getText().toString().trim();
        final String exp5 = inputExperience5.getText().toString().trim();

        String token = userSessionObj.getToken();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.S_REF_175_2;
        FINAL_URL = FINAL_URL+"?token="+token  ;
        printLogs(LogTag, "FormSubmit", "URL : " + FINAL_URL);

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("token",token);
                jsonBody.put(KEY_EXPERIENCE1, exp1);
                jsonBody.put(KEY_EXPERIENCE2, exp2);
                jsonBody.put(KEY_EXPERIENCE3, exp3);
                jsonBody.put(KEY_EXPERIENCE4, exp4);
                jsonBody.put(KEY_EXPERIENCE5, exp5);
                printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
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
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_186_title",R.string.m_186_title);
                        String sMessage=getLabelFromDb("m_186_message",R.string.m_186_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                    else{
                        printLogs(LogTag,"FormSubmit","else");
                        showProgress(false,mContentView,mProgressView);
                        String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-100";
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showProgress(false,mContentView,mProgressView);
                    String sTitle="Error :"+ActivityId+"-101";
                    String sMessage = getLabelFromDb("error_try_again",R.string.error_try_again);
                    String sButtonLabelClose="Close";
                    ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        printLogs(LogTag,"onCreate","error listener");
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-102";
                        String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }};
            RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            if(generator.equals("174")){
                Intent intent = new Intent(SAddMoreExperience.this,SFeedbackDA.class);
                printLogs(LogTag,"onOptionsItemSelected","SFeedbackDA");
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
