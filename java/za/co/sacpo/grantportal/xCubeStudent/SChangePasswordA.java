package za.co.sacpo.grantportal.xCubeStudent;

import android.content.Context;
import android.content.Intent;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.textfield.TextInputLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
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

import za.co.sacpo.grantportal.AppUpdatedA;
import za.co.sacpo.grantportal.R;
import za.co.sacpo.grantportal.xCubeLib.baseFramework.BaseFormAPCPrivate;
import za.co.sacpo.grantportal.xCubeLib.dialogs.ErrorDialog;
import za.co.sacpo.grantportal.xCubeLib.component.URLHelper;
import za.co.sacpo.grantportal.xCubeLib.component.Utils;
import za.co.sacpo.grantportal.xCubeLib.session.OlumsUtilitySession;

public class SChangePasswordA extends BaseFormAPCPrivate {
    private String ActivityId="S106";
    private String KEY_OLD_PASS="oldpassword";
    private String KEY_NEW_PASS="newpassword";
    private String KEY_CON_NEW_PASS="con_newpassword";
    public View mProgressView, mContentView,heading;
    public TextInputLayout inputLayoutOldPass,inputLayoutNewPass,inputLayoutConfirmPass;
    String generator;
    Button btnSubmit;
    EditText inputOldPass,inputNewPass,inputConfirmPass;
    private TextView lblView;
    SChangePasswordA thisClass;
    public void setBaseApcContextParent(Context cnt, AppCompatActivity ain, String lt,String cTAId){
        baseApcContext = cnt;
        CAId=cTAId;
        activityIn = ain;
        LogTag = lt;
        thisClass = this;
        printLogs(lt,"setBaseApcContextParent","weAreIn");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseApcContextParent(getApplicationContext(),this,this.getClass().getSimpleName(),ActivityId);
        printLogs(LogTag, "onCreate", "init");
        inBundle = getIntent().getExtras();
        generator = inBundle.getString("generator");
        printLogs(LogTag,"onCreate","GENERATOR "+generator);
        bootStrapInit();
    }
    @Override
    protected void bootStrapInit(){
        boolean isConnected = Utils.isNetworkConnected(this.getApplicationContext());
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
            Intent intent = new Intent(SChangePasswordA.this,AppUpdatedA.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void setLayoutXml() {
        printLogs(LogTag,"setLayoutXml","a_change_password");
        setContentView(R.layout.a_change_password);
    }
    @Override
    protected void initializeViews() {
        printLogs(LogTag,"initializeViews","init");
        mContentView = findViewById(R.id.content_container);
        mProgressView = findViewById(R.id.progress_bar);
        heading = findViewById(R.id.heading);

        inputOldPass = (EditText) findViewById(R.id.inputOldPass);
        inputNewPass = (EditText) findViewById(R.id.inputNewPass);
        inputConfirmPass = (EditText) findViewById(R.id.inputConfirmPass);
        inputLayoutOldPass = (TextInputLayout) findViewById(R.id.inputLayoutOldPass);
        inputLayoutNewPass = (TextInputLayout) findViewById(R.id.inputLayoutNewPass);
        inputLayoutConfirmPass = (TextInputLayout) findViewById(R.id.inputLayoutConfirmPass);
        btnSubmit=findViewById(R.id.btnSubmit);
        printLogs(LogTag,"initializeViews","exit");
    }
    @Override
    protected void initializeLabels(){
        printLogs(LogTag,"initializeLabels","init");
        String Label = getLabelFromDb("l_S106_old_pass",R.string.l_S106_old_pass);
        lblView = (TextView)findViewById(R.id.lblOldPass);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S106_new_pass",R.string.l_S106_new_pass);
        lblView = (TextView)findViewById(R.id.lblNewPass);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S106_confirm_pass",R.string.l_S106_confirm_pass);
        lblView = (TextView)findViewById(R.id.lblConfirmPass);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);
        Label = getLabelFromDb("l_S106_update_pass",R.string.l_S106_update_pass);
        btnSubmit.setText(Label);
        Label = getLabelFromDb("h_106",R.string.h_106);
        lblView = (TextView)findViewById(R.id.activity_heading);
        lblView.setTextColor(getResources().getColor(getTextcolorResourceId("dashboard_textColor")));
        lblView.setText(Label);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            heading.setBackground(getDrawable(getDrwabaleResourceId("heading")));
            btnSubmit.setBackground(getDrawable(getDrwabaleResourceId("themed_button_action")));
            inputOldPass.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputNewPass.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));
            inputConfirmPass.setBackground(getDrawable(getDrwabaleResourceId("input_boder_profile")));

        }


    }
    @Override
    protected void initializeInputs(){}
    @Override
    protected void initializeListeners() {
        printLogs(LogTag,"initializeListeners","init");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
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
        if(!validatePassword(inputOldPass,inputLayoutOldPass)) {
            cancel = true;
        }
        else if(!validatePassword(inputNewPass,inputLayoutNewPass)) {
            cancel = true;
        }
        else if(!validateConfirmPass(inputConfirmPass,inputLayoutConfirmPass,inputNewPass)){
            cancel =true;
        }else if(!validateOldNewPass(inputOldPass,inputNewPass)){
              cancel =true;
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
        String token = userSessionObj.getToken();
        final String old_pass = inputOldPass.getText().toString().trim();
        final String new_pass = inputNewPass.getText().toString().trim();
        final String con_new_pass = inputConfirmPass.getText().toString().trim();
        String FINAL_URL = URLHelper.DOMAIN_BASE_URL + URLHelper.R_REF_106;
        printLogs(LogTag,"FormSubmit","URL : "+FINAL_URL);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("token", token);
            jsonBody.put(KEY_OLD_PASS, old_pass);
            jsonBody.put(KEY_NEW_PASS, new_pass);
            jsonBody.put(KEY_CON_NEW_PASS, con_new_pass);
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
                            printLogs(LogTag,"FormSubmit","SUCCESS : "+Status);
                            showProgress(false,mContentView,mProgressView);
                            String sTitle=getLabelFromDb("m_S106_title",R.string.m_S106_title);
                            String sMessage=getLabelFromDb("m_178_message",R.string.m_178_message);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showSuccessDialogSChangePasswordA(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose,thisClass);

                        } else if(Status.equals("2")){showProgress(false,mContentView,mProgressView);
                            String sTitle="OLD PASSWORD";
                            String sMessage=getLabelFromDb("error_S106_107",R.string.error_S106_107);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                        else{
                            showProgress(false,mContentView,mProgressView);
                            String sTitle="Error :"+ActivityId+"-104";
                            String sMessage=getLabelFromDb("error_S106_104",R.string.error_S106_104);
                            String sButtonLabelClose="Close";
                            ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                        }
                    } catch (JSONException e) {
                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-105";
                        String sMessage=getLabelFromDb("error_S106_105",R.string.error_S106_105);
                        String sButtonLabelClose="Close";
                        ErrorDialog.showErrorDialog(baseApcContext,activityIn,sTitle,sMessage,sButtonLabelClose);
                    }
                }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        showProgress(false,mContentView,mProgressView);
                        String sTitle="Error :"+ActivityId+"-106";
                        String sMessage=getLabelFromDb("error_S106_106",R.string.error_S106_106);
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
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }
    public boolean validatePassword(EditText mPasswordView,TextInputLayout inputLayoutPassword) {
        String pass = mPasswordView.getText().toString().trim();
        setCustomError(inputLayoutPassword,null,mPasswordView);
        if (pass.isEmpty()) {
            String sMessage = getLabelFromDb("error_A101_104",R.string.error_A101_104);
            setCustomError(inputLayoutPassword,sMessage,mPasswordView);
            return false;
        } else {
            if(pass.length()<5){
                String sMessage = getLabelFromDb("error_A101_105",R.string.error_A101_105);
                setCustomError(inputLayoutPassword,sMessage,mPasswordView);
                return false;
            }
            else if(pass.length()>15){
                String sMessage = getLabelFromDb("error_A101_S106",R.string.error_A101_106);
                setCustomError(inputLayoutPassword,sMessage,mPasswordView);
                return false;
            }
            else{
                setCustomErrorDisabled(inputLayoutPassword,mPasswordView);
                return true;
            }
        }
    }
    //error_S106_101
    //error_S106_102
    public boolean validateConfirmPass(EditText inputConfirmPass,TextInputLayout inputLayoutConfirmPass,EditText inputNewPass) {
        String confirm_pass = inputConfirmPass.getText().toString().trim();
        String new_pass = inputNewPass.getText().toString().trim();
        setCustomError(inputLayoutConfirmPass,null,inputConfirmPass);
        if (confirm_pass.isEmpty() || ! confirm_pass.equals(new_pass)) {
            String sMessage = getLabelFromDb("error_S106_102",R.string.error_S106_102);
            setCustomError(inputLayoutConfirmPass,sMessage,inputConfirmPass);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutNewPass,inputConfirmPass);
            return true;
        }
    }
    public boolean validateOldNewPass(EditText inputOldPass, EditText inputNewPass) {
        String new_pass = inputNewPass.getText().toString().trim();
        String OldPass = inputOldPass.getText().toString().trim();
        if(OldPass.equals(new_pass)){
            String sMessage = getLabelFromDb("error_S106_103",R.string.error_S106_103);
            setCustomError(inputLayoutNewPass,sMessage,inputNewPass);
            return false;
        } else {
            setCustomErrorDisabled(inputLayoutNewPass,inputConfirmPass);
            return true;
        }
    }
    protected class ErrorTextWatcher implements TextWatcher {
        private EditText EditView;
        private TextInputLayout EditLayout;
        private ErrorTextWatcher(EditText EditView,TextInputLayout EditLayout) {
            this.EditView = EditView;
            this.EditLayout = EditLayout;
        }
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }
        public void afterTextChanged(Editable editable) {
            if (EditView.getId() == R.id.inputOldPass) {
            }
        }
    }

    public void customRedirector(){

            Intent intent = new Intent(SChangePasswordA.this, SDashboardDA.class);
            startActivity(intent);
            printLogs(LogTag,"customRedirectorr","SDashboardDA");
            finish();
        }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {



        printLogs(LogTag,"onOptionsItemSelected","init");
        if (item.getItemId() == android.R.id.home) {

                Intent intent = new Intent(SChangePasswordA.this,SDashboardDA.class);
                printLogs(LogTag,"onOptionsItemSelected","sDashboard");
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
