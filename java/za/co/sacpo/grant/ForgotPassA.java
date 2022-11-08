package za.co.sacpo.grant;


import android.content.Context;
import android.content.Intent;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Objects;

import za.co.sacpo.grant.R;
import za.co.sacpo.grant.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grant.xCubeLib.component.URLHelper;
import za.co.sacpo.grant.xCubeLib.component.Utils;
import za.co.sacpo.grant.xCubeLib.baseFramework.BaseFormAPCPublic;
import za.co.sacpo.grant.xCubeLib.session.OlumsUtilitySession;

public class ForgotPassA extends BaseFormAPCPublic {
    private final String ActivityId="A102";
    public EditText mEmailView;
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutEmail;
    public Button mForgotPassButton;

    public void setBaseApcContextParent(Context cnt,AppCompatActivity ain,String lt,String cTAId){
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
        setLayoutXml();
        setAppLogo();
        validateLogin(baseApcContext,activityIn);
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
        if(isConnected) {
            fetchVersionData();
            verifyVersion();
            internetChangeBroadCast();
            printLogs(LogTag,"onCreate","initConnected");
            initializeViews();
            showProgress(true,mContentView,mProgressView);
            initializeListeners();
            initializeLabels();
            initializeInputs();
            initBackBtn();
            showProgress(false,mContentView,mProgressView);
            printLogs(LogTag,"onCreate","exitConnected");
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
        if(utilSessionObj.getIsUpdateRequired()){
            Intent intent = new Intent(ForgotPassA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        setContentView(R.layout.a_forgot_pass);
        printLogs(LogTag,"setLayoutXml","a_forgot_pass");
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.inputLayoutEmail);
        mEmailView = (EditText) findViewById(R.id.inputEmail);

        mForgotPassButton = (Button) findViewById(R.id.btnResetPassword);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_A102_email",R.string.l_A102_email);
        TextView lblView = (TextView) findViewById(R.id.lblEmail);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("i_A102_forgot_password",R.string.i_A102_forgot_password);
        lblView = (TextView)findViewById(R.id.iForgotPassword);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("h_A102",R.string.h_A102);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        Label = getLabelFromDb("b_A102_reset_password",R.string.b_A102_reset_password);
        mForgotPassButton.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            mForgotPassButton.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            mEmailView.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
        }


            printLogs(LogTag,"initializeLabels","exit");
    }
    @Override
    protected void initializeInputs(){

    }
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        mForgotPassButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        printLogs(LogTag,"initializeListeners","exit");
    }
    private void initBackBtn(){
        printLogs(LogTag,"initBackBtn","init");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }
    public void validateForm() {
        printLogs(LogTag,"validateForm","init");
        boolean cancel = false;
        if (!validateEmail(mEmailView,inputLayoutEmail)) {
            cancel = true;
        }
        if (cancel) {
            return;
        } else {
            showProgress(true,mContentView,mProgressView);
            this.FormSubmit();
        }
        printLogs(LogTag,"validateForm","exit");
    }
    public void FormSubmit(){
        final String email = mEmailView.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.FORGOT_URL;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email);
            printLogs(LogTag,"FormSubmit","jsonBody "+jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest= new JsonObjectRequest(Request.Method.POST, FINAL_URL, jsonBody, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject outputJson;
                printLogs(LogTag, "FormSubmit", String.format("RESPONSE : %s", response));
                try {
                    outputJson = new JSONObject(String.valueOf(response));
                    String Status = outputJson.getString(KEY_STATUS);
                    if(Status.equals("1")){
                        printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("m_A102_title",R.string.m_A102_title);
                        String sMessage=getLabelFromDb("m_A102_message",R.string.m_A102_message);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                    else{
                        printLogs(LogTag,"FormSubmit","error_A102_101 : ");
                        showProgress(false,mContentView,mProgressView);
                        String sTitle=getLabelFromDb("error_A102_101_T",R.string.error_A102_101_T);
                        //String sTitle="Error :"+ActivityId+"-101";
                        String sMessage=getLabelFromDb("error_A102_101",R.string.error_A102_101);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                } catch (JSONException e) {
                    printLogs(LogTag,"FormSubmit","error_try_again : "+e.getMessage());
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
                printLogs(LogTag,"FormSubmit","error_try_again : "+error.getMessage());
                showProgress(false,mContentView,mProgressView);
                String sTitle="Error :"+ActivityId+"-103";
                String sMessage=getLabelFromDb("error_try_again",R.string.error_try_again);
                String sButtonLabelClose="Close";
                ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("Content-Type", "application/json; charset=utf-8");
                header.put("Accept","*/*");
                return header;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(ForgotPassA.this,LoginA.class);
            printLogs(LogTag,"onOptionsItemSelected","LoginA");
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